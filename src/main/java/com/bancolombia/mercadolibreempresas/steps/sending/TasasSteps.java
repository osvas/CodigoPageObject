package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.core.annotation.Page;

import com.bancolombia.mercadolibreempresas.pageobjects.HomePageSve;
import com.bancolombia.mercadolibreempresas.pageobjects.LoginPageSVE;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.TasasPage;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import net.thucydides.core.annotations.Step;

public class TasasSteps {
	@Page
	TasasPage tasasPage;
	@Page
	HomePageSve homePage;
	LoginPageSVE loguinPage;

	/**
	 * Método que realiza la inactivación de las tasas
	 */
	@Step
	public void inactivateRatesCIB() {
		RatesQueries ratesQueries = new RatesQueries();
		ratesQueries.inactivateRates("43189104", "INACTIVO", "SinTasas");
	}

	/**
	 * Verificar que el mensaje que aparece en pantalla si corresponda al definido
	 * funcionalmente para cuando un usuario no tienen tasas pactadas
	 * 
	 * @author Carolina Galeano
	 */

	@Step
	public void checkMessage() throws InterruptedException {
		Thread.sleep(8000);
		tasasPage.initMld();
		assertTrue("No está saliendo la validación de que no se tienen tasas negociadas",
				tasasPage.textoTasasInactivas.getText().equals("No existen tasas negociadas."));

		String clases = tasasPage.iconoAdvertencia.getAttribute("class");
		String[] claseTextos = clases.split(" ");

		boolean textoEncontrado = false;

		for (int i = 0; i < claseTextos.length; i++) {
			switch (claseTextos[i]) {
			case "Icono-Advertencia":
				textoEncontrado = true;
				break;
			}

			if (textoEncontrado) {
				break;
			}
		}

		assertTrue(
				"No está bien el mensaje de alerta, ya que se está mostrando como un error y no como una advertencia.",
				textoEncontrado);
	}
}
