package io.github.polysantiago.spring.rest;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

class RestClientRetryConfigurer {

    private final RetryOperationsInterceptor retryOperationsInterceptor;

    public RestClientRetryConfigurer(RetryOperationsInterceptor retryOperationsInterceptor) {
    	this.retryOperationsInterceptor = retryOperationsInterceptor;
    }
    void configure(ProxyFactory proxyFactory, RestClientInterceptor restClientInterceptor) {
        proxyFactory.addAdvice(retryOperationsInterceptor);
        restClientInterceptor.setRetryEnabled(true);
    }


}
