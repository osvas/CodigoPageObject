package com.bancolombia.mercadolibreempresas.definitions.sending;

import com.bancolombia.mercadolibreempresas.steps.sending.DollarRatesWithPataShouldNotBeShownSteps;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class DollarRatesWithPataShouldNotBeShownDefinitions {
	@Steps
	private DollarRatesWithPataShouldNotBeShownSteps dollarWithPataSteps;

	private String nit, nitType;

	@When("^Ingreso tasas dolar con patas$")
	public void incomeDollarRatesWithoutPata() {
		String pataIdentification, pataIdentification2, pataIdentification3;
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		nitType = PropertiesReader.getInstance().getProperty("user.nitType");

		RatesQueries ratesQueries = new RatesQueries();

		pataIdentification = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification = Util.addingNumbersToValue(pataIdentification, 10, "1");
		ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
				"" + Util.randomNumber(7), "Tasas Dolar con PATA", Util.getFechaActual(), "USD", pataIdentification,
				"00001.00000", "0.00", "V");

		pataIdentification2 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification2 = Util.addingNumbersToValue(pataIdentification2, 10, "1");
		ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
				"" + Util.randomNumber(7), "Tasas Dolar con PATA", Util.getFechaActual(), "USD", pataIdentification2,
				"00001.00000", "0.00", "V");

		pataIdentification3 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification3 = Util.addingNumbersToValue(pataIdentification3, 10, "1");
		ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
				"" + Util.randomNumber(7), "Tasas Dolar con PATA", Util.getFechaActual(), "USD", pataIdentification3,
				"00001.00000", "0.00", "V");
	}

	@Then("^Se valida que las tasas dolar con patas que se encuentren en el backend no se muestren en el frontend$")
	public void validateDollarRatesWithPata() {
		dollarWithPataSteps.validateDollarRatesWithPata(nit);
	}
}
