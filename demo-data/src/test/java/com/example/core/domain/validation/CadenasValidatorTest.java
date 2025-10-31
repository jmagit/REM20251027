package com.example.core.domain.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CadenasValidatorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "VALOR" })
	void testIsUppercaseOK(String caso) {
		// Preparar
		
		// Actuar
		var actual = CadenasValidator.isUppercase(caso);
		// Afirmar
		assertTrue(actual);
	}

	@Test
	void testIsUppercaseKO() {
		// Preparar
		
		// Actuar
		var actual = CadenasValidator.isUppercase("Valor");
		// Afirmar
		assertFalse(actual);
	}

	@Test
	void testIsUppercaseNull() {
		var ex = assertThrows(IllegalArgumentException.class, () -> CadenasValidator.isUppercase(null));
		assertEquals("No puede ser nulo", ex.getMessage());
	}

	@Test
	void testIsNotUppercaseOK() {
		var actual = CadenasValidator.isNotUppercase("VALOR");
		assertFalse(actual);
	}

	@Test
	void testIsNotUppercaseKO() {
		var actual = CadenasValidator.isNotUppercase("Valor");
		assertTrue(actual);
	}

}
