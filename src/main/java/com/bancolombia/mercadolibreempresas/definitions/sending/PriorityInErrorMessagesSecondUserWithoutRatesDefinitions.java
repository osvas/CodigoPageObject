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

public class PriorityInErrorMessagesSecondUserWithoutRatesDefinitions {
	private String nit;

	@Steps
	PriorityInErrorMessagesSteps errorMessages;

	@Before("@PriorityInErrorMessagesSecondUserWithoutRates")
	public void setInitialData() {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		PriorityInErrorMessagesQueries priorityQueries = new PriorityInErrorMessagesQueries();
		InitialValidationsQueries queries = new InitialValidationsQueries();
		RatesQueries ratesQueries = new RatesQueries();
		queries.addUserToTheControlList("PriorityInErrorMessages");
		ratesQueries.inactivateRates(nit, "INACTIVO", "PriorityInErrorMessages");
		priorityQueries.setSecondUserWithoutFees();
	}

	@Then("^Se valida que se muestre el mensaje de usuario sin tasas cuando esta parametrizado de segundo$")
	public void validationOfTheErrorMessage() {
		errorMessages.checkMessageUserWithoutFeesSecond();
	}

	@After("@PriorityInErrorMessagesSecondUserWithoutRates")
	public void returnToTheOriginalData() {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		RatesQueries ratesQueries = new RatesQueries();
		queries.removeUserFromControlList("PriorityInErrorMessages");
		ratesQueries.inactivateRates(nit, "ACTIVO", "PriorityInErrorMessages");
	}
}
