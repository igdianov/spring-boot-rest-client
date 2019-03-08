package io.github.polysantiago.spring.rest.support;

import java.lang.reflect.Type;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;

public class SyntheticParametrizedTypeReference<T> extends ParameterizedTypeReference<T> {

    private final Type type;

    public static <T> SyntheticParametrizedTypeReference<T> fromResolvableType(ResolvableType resolvedType) {
        if (resolvedType.hasGenerics()) {
            return new SyntheticParametrizedTypeReference<>(new SyntheticParametrizedType(resolvedType));
        }
        return new SyntheticParametrizedTypeReference<>(resolvedType.resolve());
    }

	public Type getType() {
		return type;
	}

	private SyntheticParametrizedTypeReference(Type type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SyntheticParametrizedTypeReference other = (SyntheticParametrizedTypeReference) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
