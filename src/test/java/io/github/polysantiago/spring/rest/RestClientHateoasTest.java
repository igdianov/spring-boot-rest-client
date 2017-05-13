package io.github.polysantiago.spring.rest;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestClientHateoasTest {

    private static final String SINGLE_RESOURCE = "/foo/{id}";
    private static final String COLLECTION_RESOURCE = "/foos";
    private static final String PAGED_RESOURCE = "/foosPaged";

    @Configuration
    @EnableAutoConfiguration
    @EnableRestClients(basePackageClasses = RestClientTest.FooClient.class)
    protected static class ContextConfiguration {

        @Bean
        Module halModule() {
            return new Jackson2HalModule();
        }

        @Bean
        HttpMessageConverter halMessageConverter(ObjectMapper objectMapper) {
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(singletonList(MediaTypes.HAL_JSON));
            converter.setObjectMapper(objectMapper);
            return converter;
        }

    }

    @RestClient(value = "localhost", url = "${localhost.uri}")
    interface FooClient {

        @GetMapping(value = SINGLE_RESOURCE, produces = MediaTypes.HAL_JSON_VALUE)
        FooResource getFoo(@PathVariable("id") String id);

        @GetMapping(value = COLLECTION_RESOURCE, produces = MediaTypes.HAL_JSON_VALUE)
        Resources<FooResource> getFoos();

        @GetMapping(value = PAGED_RESOURCE, produces = MediaTypes.HAL_JSON_VALUE)
        PagedResources<FooResource> getPagedFoos();

    }

    private PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("{", "}");

    @Autowired
    private FooClient fooClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("foo_resource.json")
    private ClassPathResource fooResourceJson;

    @Value("foo_resources.json")
    private ClassPathResource fooResourcesJson;

    @Value("foo_pagedResources.json")
    private ClassPathResource fooPagedResourcesJson;

    private MockRestServiceServer server;

    @Before
    public void setUp() throws Exception {
        server = createServer(restTemplate);
    }

    @After
    public void tearDown() throws Exception {
        server.verify();
    }

    @Test
    public void testSingleResource() throws Exception {
        String endpoint = helper.replacePlaceholders(SINGLE_RESOURCE, s -> "1234");

        mockServerHalResponse(endpoint, fooResourceJson);

        FooResource foo = fooClient.getFoo("1234");

        assertThat(foo).isNotNull();
        assertThat(foo.getId()).isNotNull();
        assertThat(foo.getLink("foo").getHref()).isNotEmpty();
    }

    @Test
    public void testCollectionResource() throws Exception {
        mockServerHalResponse(COLLECTION_RESOURCE, fooResourcesJson);

        Resources<FooResource> foos = fooClient.getFoos();

        assertThat(foos.getId()).isNotNull();
        assertThat(foos.getContent()).isNotEmpty();
        assertThat(foos.getContent()).containsExactly(new FooResource("some-value"));
    }

    @Test
    public void testPagedCollectionResource() throws Exception {
        mockServerHalResponse(PAGED_RESOURCE, fooPagedResourcesJson);

        PagedResources<FooResource> pagedFoos = fooClient.getPagedFoos();

        assertThat(pagedFoos).isNotEmpty();
        assertThat(pagedFoos.getContent()).isNotEmpty();
        assertThat(pagedFoos.getMetadata()).isNotNull();
        assertThat(pagedFoos.getMetadata().getTotalElements()).isEqualTo(1);
    }

    private void mockServerHalResponse(String endpoint, Resource reply) {
        mockServerHalResponse(endpoint)
            .andRespond(withSuccess(reply, MediaTypes.HAL_JSON));
    }

    private ResponseActions mockServerHalResponse(String endpoint) {
        return server.expect(requestTo("http://localhost" + endpoint))
            .andExpect(method(HttpMethod.GET))
            .andExpect(header(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE));
    }
}