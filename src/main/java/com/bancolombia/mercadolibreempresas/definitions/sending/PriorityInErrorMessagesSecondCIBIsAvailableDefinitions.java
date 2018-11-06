package com.bancolombia.mercadolibreempresas.definitions.sending;

import com.bancolombia.mercadolibreempresas.steps.sending.PriorityInErrorMessagesSteps;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.InitialValidationsQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.PriorityInErrorMessagesQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class PriorityInErrorMessagesSecondCIBIsAvailableDefinitions {
	private String nit;

	@Steps
	PriorityInErrorMessagesSteps errorMessages;

	@Before("@PriorityInErrorMessagesSecondCIBIsAvailable")
	public void setInitialData() {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		PriorityInErrorMessagesQueries priorityQueries = new PriorityInErrorMessagesQueries();
		InitialValidationsQueries queries = new InitialValidationsQueries();
		RatesQueries ratesQueries = new RatesQueries();
		queries.setCIBNotAvailable("PriorityInErrorMessages");
		ratesQueries.inactivateRates(nit, "INACTIVO", "PriorityInErrorMessages");
		priorityQueries.setSecondCIBIsNotAvailable();
	}

	@Then("^Se valida que se muestre el segundo mensaje con CIB no disponible$")
	public void validationOfTheErrorMessage(String typeOfError) {
		errorMessages.checkMessageCIBIsNotAvailableSecond();
	}

	@After("@PriorityInErrorMessagesSecondCIBIsAvailable")
	public void returnToTheOriginalData() {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		RatesQueries ratesQueries = new RatesQueries();
		queries.setCIBIsAvailable("PriorityInErrorMessages");
		ratesQueries.inactivateRates(nit, "ACTIVO", "PriorityInErrorMessages");
	}
}
