package com.example.aop;

import java.util.Optional;

import org.springframework.aop.framework.AopContext;
import org.springframework.lang.NonNull;

public class DummyService {
	private String value = null;

	public Optional<String> getValue() {
		return Optional.ofNullable(value);
	}

	public void setValue(@NonNull String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("No acepto argumentos nulos");
		}
		this.value = value;
	}

	public void clearValue() {
		value = null;
//		value = alwaysNull(); // auto referenciado
//		value = ((DummyService) AopContext.currentProxy()).alwaysNull(); // auto referenciado
	}

	@NonNull
	public String echo(@NonNull String input) {
		return input;
	}

	public String alwaysNull() {
		return null;
	}

}
