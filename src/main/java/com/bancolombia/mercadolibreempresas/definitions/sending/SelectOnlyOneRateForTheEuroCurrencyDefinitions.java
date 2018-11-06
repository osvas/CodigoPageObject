package com.bancolombia.mercadolibreempresas.definitions.sending;

import java.sql.SQLException;

import com.bancolombia.mercadolibreempresas.steps.sending.SelectOnlyOneRateForTheEuroCurrencySteps;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class SelectOnlyOneRateForTheEuroCurrencyDefinitions {
	@Steps
	private SelectOnlyOneRateForTheEuroCurrencySteps currencySteps;

	private String nit, idContigen1, idContigen2, idContigen3, nitType;

	@Before("@SelectOnlyOneRateForTheEuroCurrency")
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
				idContigen1, "SelectOnlyOneRateForTheEuroCurrency", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");

		idContigen2 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen2 = Util.addingNumbersToValue(idContigen2, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, Util.randomNumber(4) + "",
				idContigen2, "SelectOnlyOneRateForTheEuroCurrency", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");

		idContigen3 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen3 = Util.addingNumbersToValue(idContigen3, 10, "1");
		ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, Util.randomNumber(4) + "",
				idContigen3, "SelectOnlyOneRateForTheEuroCurrency", Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "V");
	}

	@Then("^Se selecciona tasa de la moneda euro$")
	public void selectRatesOfTheEuroCurrency() {
		currencySteps.selectRatesOfTheEuroCurrency();
	}

	@After("@SelectOnlyOneRateForTheEuroCurrency")
	public void deleteNewRatesWithBalanceZero() {
		RatesQueries ratesQueries = new RatesQueries();

		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen1,
				"SelectOnlyOneRateForTheEuroCurrency");
		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen2,
				"SelectOnlyOneRateForTheEuroCurrency");
		ratesQueries.deleteRateWithBalanceZero(Util.addingNumbersToValue(nit, 15, "0"), idContigen3,
				"SelectOnlyOneRateForTheEuroCurrency");
	}
}
