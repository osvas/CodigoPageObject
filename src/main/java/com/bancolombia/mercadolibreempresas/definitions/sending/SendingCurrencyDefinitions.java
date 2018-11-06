package com.bancolombia.mercadolibreempresas.definitions.sending;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.steps.sending.SendingCurrencySteps;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class SendingCurrencyDefinitions {
	@Steps
	private SendingCurrencySteps sending;

	@When("^Ingreso tasa con la divisa \"([^\"]*)\" para \"([^\"]*)\"$")
	public void ingresoTasaConLaDivisaPara(String currency, String tipoOperacion, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		String nit = PropertiesReader.getInstance().getProperty("user.nit");
		String nitType = PropertiesReader.getInstance().getProperty("user.nitType");
		RatesQueries ratesQueries = new RatesQueries();
		Util.loggerInformation("info",
				"<-----------Ingreso de tasas necesarias para la ejecución de la prueba----------->", testCaseName);

		ratesQueries.deleteAllUserRates(nit, testCaseName);

		String idContigen1 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen1 = Util.addingNumbersToValue(idContigen1, 10, "1");

		String idContigen2 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen2 = Util.addingNumbersToValue(idContigen2, 10, "1");

		String pataIdentification = "" + Util.randomNumber(9) + Util.randomNumber(1);
		pataIdentification = Util.addingNumbersToValue(pataIdentification, 10, "1");

		if (tipoOperacion.toUpperCase().equals("VENTA")) {
			tipoOperacion = "V";
		} else {
			tipoOperacion = "C";
		}

		if (!currency.toUpperCase().equals("USD")) {
			ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
					"" + Util.randomNumber(7), testCaseName, Util.getFechaActual(), "USD", pataIdentification,
					"00001.00000", Util.randomNumber(4) + ".00", tipoOperacion);
		}

		switch (currency.toUpperCase()) {
		case "USD":
			ratesQueries.insertNewDolarRate(Util.addingNumbersToValue(nit, 15, "0"), nitType, "" + Util.randomNumber(7),
					idContigen1, testCaseName, Util.getFechaActual(), Util.randomNumber(4) + "." + Util.randomNumber(2),
					tipoOperacion);
			break;

		case "JPY":
			ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
					"" + Util.randomNumber(7), testCaseName, Util.getFechaActual(), "JPY", pataIdentification,
					"00115.98564", "" + Util.randomNumber(4), tipoOperacion);
			break;

		case "EUR":
			ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
					"" + Util.randomNumber(7), testCaseName, Util.getFechaActual(), "EUR", pataIdentification,
					"00000.80158", "" + Util.randomNumber(4), tipoOperacion);
			break;

		case "MXN":
			ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
					"" + Util.randomNumber(7), testCaseName, Util.getFechaActual(), "MXN", pataIdentification,
					"00014.96874", "" + Util.randomNumber(4), tipoOperacion);
			break;
		case "CAD":
			ratesQueries.insertDifferentRatesToDollar(Util.addingNumbersToValue(nit, 15, "0"), nitType,
					"" + Util.randomNumber(7), testCaseName, Util.getFechaActual(), "CAD", pataIdentification,
					"00014.96874", "" + Util.randomNumber(4), tipoOperacion);
			break;
		}
	}

	@When("^Ingreso varias tasas con la divisa \"([^\"]*)\"$")
	public void ingresoVariasTasasConLaDivisa(String currency, List<EvidenceData> evidence) {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		String nit = PropertiesReader.getInstance().getProperty("user.nit");
		String nitType = PropertiesReader.getInstance().getProperty("user.nitType");
		RatesQueries ratesQueries = new RatesQueries();

		ratesQueries.deleteAllUserRates(nit, evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase());

		String idContigen = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen = Util.addingNumbersToValue(idContigen, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");
		ratesQueries.insertNewDolarRateWithCustomBank(nit, nitType, "" + Util.randomNumber(7), idContigen,
				evidence.get(0).getNameOfDirectory(), Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "TRANS.DEUTSCHE BANK");

		String idContigen2 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen = Util.addingNumbersToValue(idContigen, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");
		ratesQueries.insertNewDolarRateWithCustomBank(nit, nitType, "" + Util.randomNumber(7), idContigen2,
				evidence.get(0).getNameOfDirectory(), Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "TRANS.BANK OF NY");

		String idContigen3 = "" + Util.randomNumber(9) + Util.randomNumber(1);
		idContigen = Util.addingNumbersToValue(idContigen, 10, "1");
		nit = Util.addingNumbersToValue(nit, 15, "0");
		ratesQueries.insertNewDolarRateWithCustomBank(nit, nitType, "" + Util.randomNumber(7), idContigen3,
				evidence.get(0).getNameOfDirectory(), Util.getFechaActual(),
				Util.randomNumber(4) + "." + Util.randomNumber(2), "TRANS.BANK OF NY");
	}

	@When("^Selecciono una tasa de moneda \"([^\"]*)\"$")
	public void iSelectACurrencyRate(String currency, List<EvidenceData> evidence) {
		sending.iSelectACurrencyRate(currency, evidence);
	}

	@When("^Selecciono varias tasas de moneda dolar")
	public void iSelectSeveralRatesOfTheDollarCurrency(List<EvidenceData> evidence) {
		sending.iSelectSeveralRatesOfTheDollarCurrency(evidence);
	}

	@When("^Selecciono los datos de las cuentas$")
	public void iSelectTheDataOfTheAccounts(List<EvidenceData> evidence) {
		sending.iSelectTheDataOfTheAccounts(evidence);
	}

	@When("^Selecciono la comision \"([^\"]*)\"$")
	public void iSelectTheCommission(String commission, List<EvidenceData> evidence) {
		sending.iSelectTheCommission(commission, evidence);
	}

	@When("^Selecciono un numeral cambiario$")
	public void iSelectACurrencyExchange(List<EvidenceData> evidence) {
		sending.iSelectACurrencyExchange(evidence);
	}

	@When("^Verifico que los datos ingresados sean los correctos para el envio$")
	public void iVerifyThatTheEnteredDataAreCorrectForTheShipment(List<EvidenceData> evidence) {
		sending.iVerifyThatTheEnteredDataAreCorrectForTheShipment(evidence);
	}

	@When("^Preparo los datos de envio banco de fondeo multiples tasas")
	public void iPrepareShippingDataBankMultipleFundingRates() {
		sending.iPrepareShippingDataBankMultipleFundingRates();
	}

	@When("^Preparo los datos de envio banco de fondeo unica tasa")
	public void iPrepareShippingDataBankFundingRates() {
		sending.iPrepareShippingDataBankFundingRates();
	}

	@When("^Preparo los datos de envio banco del beneficiario multiples tasas")
	public void iPrepareBeneficiaryDataBankMultipleRates() {
		sending.iPrepareBeneficiaryDataBankMultipleRates();
	}

	@When("^Preparo los datos de envio banco generico multiples tasas")
	public void iPrepareGenericDataBankMultipleRates() {
		sending.iPrepareGenericDataBankMultipleRates();
	}

}
