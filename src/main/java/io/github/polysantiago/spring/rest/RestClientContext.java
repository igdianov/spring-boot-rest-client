package io.github.polysantiago.spring.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

class RestClientContext {

    private List<RestClientSpecification> specifications = new ArrayList<>();
    private Map<String, Object> services = new HashMap<>();

	RestClientContext(List<RestClientSpecification> specifications, Map<String, Object> services) {
		this.specifications = specifications;
		this.services = services;
	}    
    RestClientSpecification findByRestClientName(String name) {
        return specifications
            .stream()
            .filter(specification -> StringUtils.equals(specification.getName(), name))
            .findAny()
            .orElseThrow(() -> new IllegalStateException("Unable to find a @RestClient with name: " + name));
    }

    URI findServiceUriByName(String name) {
        return Optional.ofNullable(services.get(name))
            .map(Object::toString)
            .map(URI::create)
            .orElseThrow(() -> new IllegalStateException("Invalid URL for service " + name));
    }


}
