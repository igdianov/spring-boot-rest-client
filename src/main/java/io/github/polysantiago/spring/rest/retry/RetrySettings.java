package io.github.polysantiago.spring.rest.retry;

public class RetrySettings {

    private int maxAttempts = 3;
    private BackOffSettings backOff = new BackOffSettings();
    
	public int getMaxAttempts() {
		return maxAttempts;
	}
	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
	public BackOffSettings getBackOff() {
		return backOff;
	}
	public void setBackOff(BackOffSettings backOff) {
		this.backOff = backOff;
	}

}
