package com.bancolombia.mercadolibreempresas.testqueries.testfiles;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CDCFFCOMISQueries;

public class TestCDCFFCOMISQueries {
	CDCFFCOMISQueries comis;

	public TestCDCFFCOMISQueries() {
		comis = new CDCFFCOMISQueries();
	}

	@Test
	public void testGetDebitoComisionConIva() {
		System.out.println(comis.getDebitoComisionConIva("025060003420", "JunitTest"));
	}

	@Test
	public void testGetTRM() {
		System.out.println(comis.getTRM("025060003420", "JunitTest"));
	}

}
