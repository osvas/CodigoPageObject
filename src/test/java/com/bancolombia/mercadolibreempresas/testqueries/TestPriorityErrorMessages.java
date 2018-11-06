package com.bancolombia.mercadolibreempresas.testqueries;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.PriorityInErrorMessagesQueries;

public class TestPriorityErrorMessages {

	@Test
	public void testGetMessageOfAttentionLines() {
		PriorityInErrorMessagesQueries querie = new PriorityInErrorMessagesQueries();
		querie.getMessageOfAttentionLines("JUnitTest");
	}

	@Test
	public void testGetMessageOfUserInChecklist() {
		PriorityInErrorMessagesQueries querie = new PriorityInErrorMessagesQueries();
		String[] resultado = querie.getMessageOfUserInChecklist("JUnitTest");
		for (int i = 0; i < resultado.length; i++) {
			System.out.println(resultado[i]);
		}
	}

	@Test
	public void testGetMessageOfUserWithoutRates() {
		PriorityInErrorMessagesQueries querie = new PriorityInErrorMessagesQueries();
		String[] resultado = querie.getMessageOfUserWithoutRates("JUnitTest");
		for (int i = 0; i < resultado.length; i++) {
			System.out.println(resultado[i]);
		}
	}

	@Test
	public void testGetMessageOfCibIsNotAvailable() {
		PriorityInErrorMessagesQueries querie = new PriorityInErrorMessagesQueries();
		String[] resultado = querie.getMessageOfCibIsNotAvailable("JUnitTest");
		for (int i = 0; i < resultado.length; i++) {
			System.out.println(resultado[i]);
		}
	}
}
