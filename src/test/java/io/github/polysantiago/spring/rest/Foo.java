package io.github.polysantiago.spring.rest;

class Foo {

    private String bar;

    Foo() {}
    
	public Foo(String bar) {
		this.bar = bar;
	}

	public String getBar() {
		return bar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bar == null) ? 0 : bar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foo other = (Foo) obj;
		if (bar == null) {
			if (other.bar != null)
				return false;
		} else if (!bar.equals(other.bar))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Foo [bar=" + bar + "]";
	}

}
