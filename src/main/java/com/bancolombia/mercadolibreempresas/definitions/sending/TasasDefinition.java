package com.bancolombia.mercadolibreempresas.definitions.sending;

import com.bancolombia.mercadolibreempresas.steps.sending.TasasSteps;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class TasasDefinition {

	@Steps
	TasasSteps tasasSteps;

	@And("^Igresar a CIB a inactivar las tasas de envio$")
	public void inactivateRatesCIB() {
		tasasSteps.inactivateRatesCIB();
	}

	@Then("^Aparece mensaje de alerta por usuario sin tasas$")
	public void checkMessage() throws InterruptedException {
		tasasSteps.checkMessage();
	}

	@After("@SinTasas")
	public void activateRates() {
		RatesQueries ratesQueries = new RatesQueries();
		ratesQueries.inactivateRates("43189104", "ACTIVO", "SinTasas");
	}
}
