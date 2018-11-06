package com.bancolombia.mercadolibreempresas.definitions.sending;

import java.sql.SQLException;

import com.bancolombia.mercadolibreempresas.steps.sending.EnabledRatesWithFutureComplianceDateDifferentOfTheDollarCurrencySteps;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class EnabledRatesWithFutureComplianceDateDifferentOfTheDollarCurrencyDefinitions {
	@Steps
	private EnabledRatesWithFutureComplianceDateDifferentOfTheDollarCurrencySteps currencySteps;

	private String nit, idContigen1, idContigen2, nitType;

	@Before("@EnabledRatesWithFutureTradingDateDifferentOfTheDollarCurrency")
	public void insertRatesWithBalanceZero() throws SQLException {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		nitType = PropertiesReader.getInstance().getProperty("user.nitType");

		RatesQueries ratesQueries = new RatesQueries();

		// Se genera un número aleatorio de 10 dígitos para poder insertar la tasa sin
		// que quede repetida.
		idContigen1 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen1 = Util.addingNumbersToValue(idContigen1, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, Util.randomNumber(7) + "",
				idContigen1, "SelectOnlyOneRateForTheEuroCurrency", Util.addOrSubtractDaysToTheCurrentDate(1),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");

		idContigen2 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen2 = Util.addingNumbersToValue(idContigen2, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, Util.randomNumber(7) + "",
				idContigen2, "SelectOnlyOneRateForTheEuroCurrency", Util.addOrSubtractDaysToTheCurrentDate(1),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");
	}

	@Then("^Se valida que se pueda seleccionar la moneda diferente a dolar con fecha de negociacion futura$")
	public void CheckThatARateWithAFutureComplianceDateCanBeSelected() {
		currencySteps.CheckThatARateWithAFutureComplianceDateCanBeSelected();
	}

	@After("@EnabledRatesWithFutureTradingDateDifferentOfTheDollarCurrency")
	public void deleteNewRates() {
		RatesQueries ratesQueries = new RatesQueries();

		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen1,
				"SelectOnlyOneRateForTheEuroCurrency");
		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen2,
				"SelectOnlyOneRateForTheEuroCurrency");
	}
}
