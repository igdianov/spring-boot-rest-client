package io.github.polysantiago.spring.rest;

import org.springframework.hateoas.ResourceSupport;

public class FooResource extends ResourceSupport {

    private String bar;

    FooResource() { }
    
	public FooResource(String bar) {
		this.bar = bar;
	}

	public String getBar() {
		return bar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bar == null) ? 0 : bar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		FooResource other = (FooResource) obj;
		if (bar == null) {
			if (other.bar != null)
				return false;
		} else if (!bar.equals(other.bar))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FooResource [bar=" + bar + "]";
	}

}
