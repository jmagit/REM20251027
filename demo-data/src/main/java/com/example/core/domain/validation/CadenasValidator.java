package com.example.core.domain.validation;

public final class CadenasValidator {
	private CadenasValidator() {
	}

	public static boolean isNIF(String value) {
		if (value == null)
			return false;
		value = value.toUpperCase();
		if (!value.matches("^\\d{1,8}[A-Z]$") || Integer.parseInt(value.substring(0, value.length() - 1)) == 0)
			return false;
		return "TRWAGMYFPDXBNJZSQVHLCKE".charAt(Integer.parseInt(value.substring(0, value.length() - 1)) % 23) == value
				.charAt(value.length() - 1);
	}

	public static boolean isNotNIF(String value) {
		return !isNIF(value);
	}

	public static boolean isUppercase(String valor) {
		if(valor == null)
			throw new IllegalArgumentException("No puede ser nulo");
		return valor.equals(valor.toUpperCase());
	}
	public static boolean isNotUppercase(String valor) {
		return !isUppercase(valor);
	}
}
