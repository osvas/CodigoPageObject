package com.bancolombia.mercadolibreempresas.definitions.sending;

import java.sql.SQLException;

import com.bancolombia.mercadolibreempresas.steps.sending.SelectOnlyDollarCurrencyRatesSteps;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class SelectOnlyDollarCurrencyRatesDefinitions {
	@Steps
	private SelectOnlyDollarCurrencyRatesSteps currencySteps;

	private String nit, idContigen1, idContigen2, idContigen3, nitType;

	@Before("@SelectOnlyDollarCurrencyRates")
	public void insertRatesWithBalanceZero() throws SQLException {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		nitType = PropertiesReader.getInstance().getProperty("user.nitType");

		RatesQueries ratesQueries = new RatesQueries();

		// Se genera un número aleatorio de 10 dígitos para poder insertar la tasa sin
		// que quede repetida.
		idContigen1 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen1 = Util.addingNumbersToValue(idContigen1, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, "" + Util.randomNumber(7),
				idContigen1, "SelectOnlyDollarCurrencyRates", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");

		idContigen2 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen2 = Util.addingNumbersToValue(idContigen2, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, "" + Util.randomNumber(7),
				idContigen2, "SelectOnlyDollarCurrencyRates", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");

		idContigen3 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen3 = Util.addingNumbersToValue(idContigen3, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, "" + Util.randomNumber(7),
				idContigen3, "SelectOnlyDollarCurrencyRates", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");
	}

	@Then("^Se seleccionan solo las tasas de la moneda dolar$")
	public void selectRatesOfTheDollarCurrency() {
		currencySteps.selectRatesOfTheDollarCurrency();
	}

	@After("@SelectOnlyDollarCurrencyRates")
	public void deleteNewRatesWithBalanceZero() {
		RatesQueries ratesQueries = new RatesQueries();

		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen1,
				"SelectOnlyDollarCurrencyRates");
		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen2,
				"SelectOnlyDollarCurrencyRates");
		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen3,
				"SelectOnlyDollarCurrencyRates");
	}
}
