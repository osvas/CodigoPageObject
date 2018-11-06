package com.bancolombia.mercadolibreempresas.testqueries;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.recepcion.RecepcionQueries;

public class TestRecepcionQueries {
	RecepcionQueries recepcion = new RecepcionQueries();

	/**
	 * Se debe de ingresar el tipo de moneda, la cantidad de giros que se desean
	 * crear y el nombre del caso de prueba que la ejecuta
	 */
	@Test
	public void testInsertarGiros() {
		recepcion.insertarGiros("EUR", 1, "JUNITTEST");
	}

	@Test
	public void testBorrarGirosDelCliente() {
		recepcion.borrarGirosDelCliente("JUNITTEST");
	}

}
