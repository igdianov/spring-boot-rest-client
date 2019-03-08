package io.github.polysantiago.spring.rest;

import org.springframework.http.HttpStatus;

public class RestClientSpecification {

	public RestClientSpecification() {}
	
    private String name;
	private HttpStatus[] retryableStatuses;
    private Class<? extends Exception>[] retryableExceptions;
    
	public RestClientSpecification(String name, HttpStatus[] retryableStatuses,
			Class<? extends Exception>[] retryableExceptions) {
		this.name = name;
		this.retryableStatuses = retryableStatuses;
		this.retryableExceptions = retryableExceptions;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HttpStatus[] getRetryableStatuses() {
		return retryableStatuses;
	}

	public void setRetryableStatuses(HttpStatus[] retryableStatuses) {
		this.retryableStatuses = retryableStatuses;
	}

	public Class<? extends Exception>[] getRetryableExceptions() {
		return retryableExceptions;
	}

	public void setRetryableExceptions(Class<? extends Exception>[] retryableExceptions) {
		this.retryableExceptions = retryableExceptions;
	}
	

}
