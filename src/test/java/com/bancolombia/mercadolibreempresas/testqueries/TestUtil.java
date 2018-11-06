package com.bancolombia.mercadolibreempresas.testqueries;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.Util;

public class TestUtil {

	@Test
	public void testDates() {
		System.out.println("La fecha actual es: " + Util.getFechaActual() + ", Pero la fecha añadida de un día es: "
				+ Util.addOrSubtractDaysToTheCurrentDate(1));
	}
	
	@Test
	public void testCompareDates() {		
		System.out.println("Valor que retorna el método: " + Util.compareDates("2018/06/07"));
	}

}
