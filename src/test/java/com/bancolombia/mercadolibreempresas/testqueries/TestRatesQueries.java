package com.bancolombia.mercadolibreempresas.testqueries;

import java.util.List;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

/**
 * Clase que realiza las pruebas unitarias a las queries de las tasas
 * 
 * @author Carolina Galeano Hoyos <cghoyos@bancolombia.com.co>
 *
 */
public class TestRatesQueries {

	private String nit = "43189101";
	// Tipo del documento del cliente 1=nit, 2=cédula
	private static final String TIPO_DOCUMENTO = "2";
	// Tipo de operación que tendrá la tasa V=venta, C=compra
	private static final String TYPE_OF_OPERATION = "C";

	/*@Test
	public void testActiveInactiveRates() {
		RatesQueries ratesQueries = new RatesQueries();
		ratesQueries.inactivateRates("9737181", "INACTIVO", "JUnitTest");
	}*/

	@Test
	public void testInsertNewDolarRate() {
		RatesQueries ratesQueries = new RatesQueries();
		// Se genera un número aleatorio de 10 dígitos para poder insertar la tasa sin
		// que quede repetida.
		for (int i = 0; i < 1; i++) {
			String idContigen = "" + Util.randomNumber(9) + Util.randomNumber(1);
			idContigen = Util.addingNumbersToValue(idContigen, 10, "1");
			nit = Util.addingNumbersToValue(nit, 15, "0");
			ratesQueries.insertNewDolarRate(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), idContigen, "JUnitTest",
					Util.getFechaActual(), Util.randomNumber(4) + "." + Util.randomNumber(2), TYPE_OF_OPERATION);
		}

	}

	@Test
	public void testInsertNewEuroRate() {
		RatesQueries ratesQueries = new RatesQueries();
		String pataIdentification = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification = Util.addingNumbersToValue(pataIdentification, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "USD", pataIdentification, "00001.00000", Util.randomNumber(4) + ".00",
				TYPE_OF_OPERATION);

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "EUR", pataIdentification, "00000.80158", "" + Util.randomNumber(4),
				TYPE_OF_OPERATION);
	}

	@Test
	public void testInsertNewPoundRate() {
		RatesQueries ratesQueries = new RatesQueries();
		String pataIdentification = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification = Util.addingNumbersToValue(pataIdentification, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "USD", pataIdentification, "00001.00000", Util.randomNumber(4) + ".00",
				TYPE_OF_OPERATION);

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "GBP", pataIdentification, "00000.80158", "" + Util.randomNumber(4),
				TYPE_OF_OPERATION);
	}

	@Test
	public void testInsertNewMexicanPesosRate() {
		RatesQueries ratesQueries = new RatesQueries();
		String pataIdentification = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification = Util.addingNumbersToValue(pataIdentification, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "USD", pataIdentification, "00001.00000", Util.randomNumber(4) + ".00",
				TYPE_OF_OPERATION);

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "MXN", pataIdentification, "00014.96874", "" + Util.randomNumber(4),
				TYPE_OF_OPERATION);
	}

	@Test
	public void testInsertNewCanadianDolarRate() {
		RatesQueries ratesQueries = new RatesQueries();
		String pataIdentification = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification = Util.addingNumbersToValue(pataIdentification, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "USD", pataIdentification, "00001.00000", Util.randomNumber(4) + ".00",
				TYPE_OF_OPERATION);

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "CAD", pataIdentification, "00014.96874", "" + Util.randomNumber(4),
				TYPE_OF_OPERATION);
	}

	@Test
	public void testInsertNewYenRate() {
		RatesQueries ratesQueries = new RatesQueries();
		String pataIdentification = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification = Util.addingNumbersToValue(pataIdentification, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "USD", pataIdentification, "00001.00000", Util.randomNumber(4) + ".00",
				TYPE_OF_OPERATION);

		ratesQueries.insertDifferentRatesToDollar(nit, TIPO_DOCUMENTO, "" + Util.randomNumber(7), "JUnitTest",
				Util.getFechaActual(), "JPY", pataIdentification, "00115.98564", "" + Util.randomNumber(4),
				TYPE_OF_OPERATION);
	}

	/*@Test
	public void testDeleteAllUserRates() {
		RatesQueries ratesQueries = new RatesQueries();
		ratesQueries.deleteAllUserRates("43103865", "JUnitTest");
	}

	@Test
	public void testGetAllTheDollarRatesWithoutPata() {
		RatesQueries ratesQueries = new RatesQueries();
		List<List<String>> lista = ratesQueries.getAllTheDollarRatesWithoutPata("9737181", "JUnitTest");

		System.out.println("Tamaño de la lista: " + lista.size());

		for (List<String> list : lista) {
			System.out.println("Valor de la lista: " + list.get(0));
		}
	}*/

}
