package com.bancolombia.mercadolibreempresas.definitions;

import com.bancolombia.mercadolibreempresas.steps.HomePageSteps;

import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class HomePageNavigateDefinitions {
	@Steps
	private HomePageSteps homePageSteps;

    @When(value = "^Elijo opcion de envio de divisas$")
	public void selectEnvioDivisasOption() throws InterruptedException {
		homePageSteps.selectEnvioDivisasOption();
	}
    
    @When(value = "^Elijo opcion administrar productos propios$")
	public void selectAdministrarProductosPropiosOption() {
		homePageSteps.selectAdministrarProductosPropiosOption();
	}
    
    @When(value = "^Elijo la opcion recepcion de divisas$")
	public void iSelectTheCurrencyReceptionOption() {
		homePageSteps.iSelectTheCurrencyReceptionOption();
	}
}
