package io.github.polysantiago.spring.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.github.polysantiago.spring.rest.retry.RetrySettings;

@ConfigurationProperties(prefix = RestClientProperties.PREFIX)
public class RestClientProperties {

    public static final String PREFIX = "spring.rest.client";

    private Boolean isoDateTimeFormat = true;
    private RetrySettings retry = new RetrySettings();
    private Map<String, Object> services = new HashMap<>();
    
    public Boolean getIsoDateTimeFormat() {
		return isoDateTimeFormat;
	}
	public void setIsoDateTimeFormat(Boolean isoDateTimeFormat) {
		this.isoDateTimeFormat = isoDateTimeFormat;
	}
	public RetrySettings getRetry() {
		return retry;
	}
	public void setRetry(RetrySettings retry) {
		this.retry = retry;
	}
	public Map<String, Object> getServices() {
		return services;
	}
	public void setServices(Map<String, Object> services) {
		this.services = services;
	}

}
