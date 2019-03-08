package io.github.polysantiago.spring.rest.retry;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import io.github.polysantiago.spring.rest.RestClientProperties;

public class RetryOperationsInterceptorFactory extends AbstractFactoryBean<RetryOperationsInterceptor> {

    private final RestClientProperties restClientProperties;

    public RetryOperationsInterceptorFactory(RestClientProperties restClientProperties) {
    	this.restClientProperties = restClientProperties;
    }

    @Override
    public Class<?> getObjectType() {
        return RetryOperationsInterceptor.class;
    }

    @Override
    protected RetryOperationsInterceptor createInstance() throws Exception {
        return new RetryInterceptor(restClientProperties.getRetry()).buildInterceptor();
    }


}
