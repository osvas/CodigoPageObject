package com.bancolombia.mercadolibreempresas.testqueries;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.NumeralCambiarioQueries;

public class TestNumeralCambiarioQueries {
	private static NumeralCambiarioQueries numeral;

	@Before
	public void initTest() {
		numeral = new NumeralCambiarioQueries();
	}

	@Test
	public void testGetMismoTitularCuenta() {
		System.out.println(numeral.getMismoTitularCuenta("GB1895580112369074951659678723102", "JUNITTEST"));
	}

	@Test
	public void testUpdateMismoTitularCuenta() {
		numeral.updateMismoTitularCuenta("1", "GB1895580112369074951659678723102", "", "JUNITTEST");
	}

	@Test
	public void testGetNumeralesExcentosEnCIBFFPAGEN() {
		System.out.println(numeral.getNumeralesExcentosEnCIBFFPAGEN("JUNITTEST"));
	}

	@Test
	public void testGetCobroGMF() {
		System.out.println(numeral.getCobroGMF("025060003182", "JUNITTEST"));
	}

	@Test
	public void testGetNumeralesCambiariosHabilitados() {
		List<List<String>> numerales = numeral.getNumeralesCambiariosHabilitados("JUNITTEST");

		for (List<String> list : numerales) {
			for (String string : list) {
				System.out.println(string);
			}
		}
	}

	@Test
	public void testUpdateNumeralesCambiariosDelPagen() {
		numeral.updateNumeralesCambiariosDelPagen("2950290320175382", "JUNITTEST");
	}

	@Test
	public void testGetIdTitularDeLaCuenta() {
		System.out.println(numeral.getIdTitularDeLaCuenta("779390453", "JUNITTEST"));
	}
}