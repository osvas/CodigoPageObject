package com.bancolombia.mercadolibreempresas.definitions.sending;

import com.bancolombia.mercadolibreempresas.steps.sending.DollarRatesWithoutPataShouldBeShownSteps;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class DollarRatesWithoutPataShouldBeShownDefinitions {
	@Steps
	private DollarRatesWithoutPataShouldBeShownSteps dollarWithoutPataSteps;

	private String nit, nitType;

	@When("^Ingreso tasas dolar sin patas$")
	public void incomeDollarRatesWithoutPata() {
		String idContigen1, idContigen2, idContigen3;
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		nitType = PropertiesReader.getInstance().getProperty("user.nitType");

		RatesQueries ratesQueries = new RatesQueries();

		// Se genera un número aleatorio de 10 dígitos para poder insertar la tasa sin
		// que quede repetida.
		idContigen1 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen1 = Util.addingNumbersToValue(idContigen1, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, "" + Util.randomNumber(7),
				idContigen1, "SelectOnlyDollarCurrencyRatesDefinitions", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");

		idContigen2 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen2 = Util.addingNumbersToValue(idContigen2, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, "" + Util.randomNumber(7),
				idContigen2, "SelectOnlyDollarCurrencyRatesDefinitions", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");

		idContigen3 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen3 = Util.addingNumbersToValue(idContigen3, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, "" + Util.randomNumber(7),
				idContigen3, "SelectOnlyDollarCurrencyRatesDefinitions", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");
	}

	@Then("^Se valida que las tasas dolar sin patas que se encuentren en el backend se muestren en el frontend$")
	public void validateDollarRatesWithoutPata() {
		dollarWithoutPataSteps.validateDollarRatesWithoutPata(nit);
	}
}
