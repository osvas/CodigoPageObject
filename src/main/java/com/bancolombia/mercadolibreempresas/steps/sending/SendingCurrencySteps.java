package com.bancolombia.mercadolibreempresas.steps.sending;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.models.DatosFinalesTransaccion;
import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.models.SecondScreen;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.FourthScreenPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.SecondScreenPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.ThirdScreenPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.TransactionStatusPage;
import com.bancolombia.mercadolibreempresas.steps.commission.ClienteExisteOurBenshaSteps;
import com.bancolombia.mercadolibreempresas.steps.commission.ClienteNoExisteOurBenshaSteps;
import com.bancolombia.mercadolibreempresas.steps.commission.CobroValorCopSteps;
import com.bancolombia.mercadolibreempresas.steps.commission.ComisionGourSteps;
import com.bancolombia.mercadolibreempresas.steps.numeralcambiario.NumeralCambiarioSteps;
import com.bancolombia.mercadolibreempresas.utilities.GenerateExcelWithSwiftCodesPagen;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.bancofondeo.SetUpBeneficiaryBankQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.bancofondeo.SetUpFundingBankQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;

import net.thucydides.core.annotations.Steps;

public class SendingCurrencySteps {
	@Page
	FirstScreenPage firstScreen;

	@Page
	SecondScreenPage secondScreen;

	@Page
	ThirdScreenPage thirdScreen;

	@Page
	FourthScreenPage fourthScreenPage;

	@Page
	TransactionStatusPage transactionStatusPage;

	@Steps
	NumeralCambiarioSteps numeralSteps;

	private ClienteExisteOurBenshaSteps clienteExiste;
	private ClienteNoExisteOurBenshaSteps clienteNoExiste;
	private ComisionGourSteps gourSteps;
	private String globalCurrency = "";
	private String amountAndCurrency = "";
	float highestBalance = 0.00f;
	float balanceValue = 0.00f;
	String accountNumber = "";
	String balanceValueText = "";
	String numeralCambiario = "";

	public SendingCurrencySteps() {
		clienteExiste = new ClienteExisteOurBenshaSteps();
		clienteNoExiste = new ClienteNoExisteOurBenshaSteps();
		gourSteps = new ComisionGourSteps();
	}

	public void iSelectACurrencyRate(String currency, List<EvidenceData> evidence) {
		globalCurrency = currency.toUpperCase();
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + globalCurrency;
		RunEvents.loadStandBy(5);
		RunEvents.scrollUpDown(firstScreen.getDriver(), "400");
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		firstScreen.screenShotsPage("lista de tasas", testCaseName);

		// Se recorren la cantidad de filas que tiene la tabla
		int numberOfPage = 0;
		boolean nextPage = true;
		boolean finishSelectRates = false;
		do {
			numberOfPage++;
			// Se recorren la cantidad de filas que tiene la tabla
			for (int i = 1; i <= firstScreen.rowsOfTheTable.size() - 3; i++) {
				switch (currency.toUpperCase()) {
				case "USD":
					finishSelectRates = selectDollarRate(i);
					break;

				case "JPY":
					finishSelectRates = selectYenRate(i);
					break;

				case "EUR":
					finishSelectRates = selectEuroRate(i);
					break;
				case "MXN":
					finishSelectRates = selectMexicanPesosRate(i);
					break;
				case "CAD":
					finishSelectRates = selectCanadianDollarRate(i);
					break;
				}
				if (finishSelectRates) {
					nextPage = false;
					break;
				}
			}
			firstScreen.screenShotsPage("tasa seleccionada" + numberOfPage, testCaseName);
			boolean existNextPage = false;

			for (WebElement elemento : firstScreen.elementsOfThePager) {
				if (elemento.getAttribute("class").contains("NavRight") && nextPage) {
					existNextPage = true;
				}
			}

			if (existNextPage) {
				firstScreen.NextButtonOfThePager.click();
				RunEvents.loadStandBy(2);
			} else {
				nextPage = false;
			}
		} while (nextPage);

		firstScreen.buttonContinueRates.click();
	}

	public void iSelectTheDataOfTheAccounts(List<EvidenceData> evidence) {
		seleccionarCuentas(evidence);
		secondScreen.continueButton.click();
	}

	public void seleccionarCuentas(List<EvidenceData> evidence) {
		String accountBeneficiaryText = "";

		String[] accountArray;
		RunEvents.loadStandBy(5);
		for (WebElement accountToDebit : secondScreen.listAccountsToDebit) {
			if (!accountToDebit.getText().trim().equals("Seleccionar")) {
				accountToDebit.click();
				break;
			}
		}

		for (WebElement accountBeneficiary : secondScreen.listAccountsBeneficiary) {
			if (!accountBeneficiary.getText().trim().equals("Seleccionar")) {
				accountBeneficiaryText = accountBeneficiary.getText().trim();
				accountArray = accountBeneficiaryText.split(" ");
				accountNumber = accountArray[0];
				accountBeneficiary.click();
				break;
			}
		}

		amountAndCurrency = "" + Util.randomNumber(3);
		secondScreen.amountAndCurrencyInput.sendKeys(amountAndCurrency);
		secondScreen.screenShotsPage("Seleccion de cuentas con divisa",
				evidence.get(0).getNameOfDirectory() + " " + globalCurrency.toUpperCase());
	}

	public void iCheckTheCOPValue(String currency, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + globalCurrency;
		RatesQueries rates = new RatesQueries();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		String nit = PropertiesReader.getInstance().getProperty("user.nit");

		String amountTasa = rates.getTasaOfTheRate("USD", nit, testCaseName);
		amountTasa = amountTasa.replace("$", "");

		String textAmountInPesosFront, textAmountFactor;
		if (currency.toUpperCase().equals("USD")) {
			textAmountInPesosFront = secondScreen.totalAmountInPesos.getText();
			textAmountFactor = secondScreen.factorValue.getText();
		} else {
			textAmountInPesosFront = secondScreen.totalAmountInPesos2.getText();
			textAmountFactor = secondScreen.factorValue.getText();
		}

		secondScreen.screenShotsPage("Monto valor COP", testCaseName);

		textAmountInPesosFront = Util.quitarCaracteresACadenasConValores(textAmountInPesosFront);
		textAmountFactor = Util.quitarCaracteresACadenasConValores(textAmountFactor);

		double valorDoubleTasa = Util.redondearDecimales(Double.parseDouble(amountTasa), 2);
		double valorDoubleFront = Util.redondearDecimales(Double.parseDouble(textAmountInPesosFront), 2);
		double valorDoubleFactor = Double.parseDouble(textAmountFactor);

		CobroValorCopSteps cobro = new CobroValorCopSteps();

		cobro.iCheckTheCOPValue(valorDoubleTasa, valorDoubleFront, valorDoubleFactor, currency, amountAndCurrency,
				evidence);

	}

	public void iSelectTheCommission(String commission, List<EvidenceData> evidence) {
		seleccionarComision(commission, evidence);

		secondScreen.continueButton.click();
	}

	public void iValidTheCommission(String commission, String tarifa, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + globalCurrency.toUpperCase();
		CommissionQueries commissionQueries = new CommissionQueries();
		Util.loggerInformation("info", "<----------Validación de la comisión en la segunda pantalla---------->",
				testCaseName);
		seleccionarComision(commission, evidence);
		SecondScreen commissionScreen = new SecondScreen();

		String textAmountInPesosFront;
		String textAmountFactor = "";
		String textTasa = secondScreen.totalTasa.getText();
		if (globalCurrency.toUpperCase().equals("USD")) {
			textAmountInPesosFront = secondScreen.totalAmountInPesos.getText();
		} else {
			textAmountFactor = secondScreen.factorValue.getText();
			textAmountInPesosFront = secondScreen.totalAmountInPesos2.getText();
		}

		String textCommissionTarifa = secondScreen.commissionTarifa.getText();
		String textCommission = secondScreen.commission.getText();

		commissionScreen.setTasa(textTasa);
		commissionScreen.setAmountInPesos(textAmountInPesosFront);
		commissionScreen.setFactor(textAmountFactor);
		commissionScreen.setCommissionTarifa(textCommissionTarifa);
		commissionScreen.setCommission(textCommission);

		List<List<String>> obtenerCliente = commissionQueries.searchUserInTheCentralCommission(testCaseName);

		// Se revisa si el tipo de comisión es GOUR
		if (commission.toUpperCase().equals("GOUR")) {
			// Si la comisión es GOUR entonces el valor que llega por parámetro no es la
			// tarifa sino el estado del parámetro (activa - desactivado)
			String estadoComneggour = tarifa;
			gourSteps.validoLaComisionConElParametroCOMNEGGOUR(commissionScreen, globalCurrency, estadoComneggour,
					evidence);
			// Si la comisión no es GOUR entonces se debe de revisar si el cliente está o no
			// en central de comisiones
		} else if (obtenerCliente.isEmpty()) {
			clienteNoExiste.validoParaElClienteNoExisteLaComisionDeTarifa(commissionScreen, commission, globalCurrency,
					evidence);
		} else {
			clienteExiste.iValidTheCommissionSecondScreen(commissionScreen, globalCurrency, tarifa, evidence);
		}

		secondScreen.continueButton.click();
	}

	private void seleccionarComision(String commission, List<EvidenceData> evidence) {
		RunEvents.loadStandBy(1);
		switch (commission.toUpperCase()) {
		case "OUR":
			secondScreen.commissionOurRadioButton.click();
			break;
		case "BEN/SHA":
			secondScreen.commissionBenShaRadioButton.click();
			break;
		case "GOUR":
			secondScreen.commissionGOurRadioButton.click();
			break;
		}
		RunEvents.loadStandBy(2);
		switch (globalCurrency.toUpperCase()) {
		case "USD":
			secondScreen.messageToTheBeneficiaryWithAllCommissionsTextArea.click();
			secondScreen.messageToTheBeneficiaryWithAllCommissionsTextArea
					.sendKeys("Este es un mensaje al beneficiario automatizado");
			break;

		default:
			secondScreen.messageToTheBeneficiaryWithTwoCommissionsTextArea.click();
			secondScreen.messageToTheBeneficiaryWithTwoCommissionsTextArea
					.sendKeys("Este es un mensaje al beneficiario automatizado");
			break;
		}

		secondScreen.screenShotsPage("Seleccion de comision con divisa",
				evidence.get(0).getNameOfDirectory() + " " + globalCurrency.toUpperCase());
	}

	public void iSelectACurrencyExchange(List<EvidenceData> evidence) {
		RunEvents.loadStandBy(2);
		for (WebElement exchangeNumber : thirdScreen.selectExchangeNumber) {
			if (!exchangeNumber.getText().trim().equals("Seleccionar")) {
				exchangeNumber.click();
				break;
			}
		}
		RunEvents.loadStandBy(1);
		numeralCambiario = thirdScreen.numeralSeleccionado.getText().trim();
		firstScreen.getDriver().switchTo().parentFrame();
		RunEvents.loadStandBy(1);
		RunEvents.scrollUpDown(firstScreen.getDriver(), "220");
		thirdScreen.screenShotsPage("Seleccion de numeral cambiario con divisa",
				evidence.get(0).getNameOfDirectory() + " " + globalCurrency.toUpperCase());
		RunEvents.loadStandBy(2);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		thirdScreen.continueButton.click();
	}

	public void iVerifyThatTheEnteredDataAreCorrectForTheShipment(List<EvidenceData> evidence) {
		confirmarEnvio(evidence);
		fourthScreenPage.confirmButton.click();
		RunEvents.loadStandBy(8);
	}

	public void verificarCobroComisionYTarifa(String commission, String tarifa, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + globalCurrency.toUpperCase();
		confirmarEnvio(evidence);
		CommissionQueries commissionQueries = new CommissionQueries();
		Util.loggerInformation("info",
				"<-----------Validación del cobro en la comisión de la cuarta pantalla----------->", testCaseName);
		DatosFinalesTransaccion finalScreen = new DatosFinalesTransaccion();
		List<WebElement> labelsLeftList = fourthScreenPage.listOfLabelsOnTheLeft;
		List<WebElement> labelsRightList = fourthScreenPage.listOfLabelsOnTheRight;

		int cont = 0;
		for (WebElement list : labelsLeftList) {
			if (list.getText().trim().equals("Monto y moneda:")) {
				finalScreen.setMontoYMoneda(labelsRightList.get(cont).getText().trim());
			}

			if (list.getText().trim().equals("Tasa:")) {
				finalScreen.setTasa(labelsRightList.get(cont).getText().trim());
			}

			if (list.getText().trim().equals("Factor:")) {
				finalScreen.setFactor(labelsRightList.get(cont).getText().trim());
			}

			if (list.getText().trim().equals("Monto en pesos del valor a enviar:")) {
				finalScreen.setMontoEnPesosValorAEnviar(labelsRightList.get(cont).getText().trim());
			}

			if (list.getText().trim().equals("Gastos de banco en el exterior:")) {
				finalScreen.setGastosBancoEnExterior(labelsRightList.get(cont).getText().trim());
			}

			if (list.getText().trim().equals("Comisión de envío:")) {
				finalScreen.setComisionDeEnvio(labelsRightList.get(cont).getText().trim());
			}

			if (list.getText().trim().equals("Monto total a debitar:")) {
				finalScreen.setMontoTotalADebitar(labelsRightList.get(cont).getText().trim());
			}
			cont = cont + 1;
		}

		List<List<String>> obtenerCliente = commissionQueries.searchUserInTheCentralCommission(testCaseName);

		// Se revisa si el tipo de comisión es GOUR
		if (commission.toUpperCase().equals("GOUR")) {
			// Si la comisión es GOUR entonces el valor que llega por parámetro no es la
			// tarifa sino el estado del parámetro (activo - inactivo)
			String estadoComneggour = tarifa;
			gourSteps.verificoLaComisionConElParametroCOMNGGOUR(finalScreen, globalCurrency, estadoComneggour,
					evidence);
			// Si la comisión no es GOUR entonces se debe de revisar si el cliente está o no
			// en central de comisiones
		} else if (obtenerCliente.isEmpty()) {
			clienteNoExiste.verificoQueLosCobrosClienteNoExisteDeLaComisionYTarifaSeanLosCorrectosParaElEnvio(
					finalScreen, commission, globalCurrency, evidence);
		} else {
			clienteExiste.verificarCobroComisionYTarifa(finalScreen, commission, tarifa, globalCurrency, evidence);
		}

		fourthScreenPage.confirmButton.click();
		RunEvents.loadStandBy(5);
	}

	public void confirmarEnvio(List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + globalCurrency.toUpperCase();
		RunEvents.loadStandBy(2);
		firstScreen.getDriver().switchTo().parentFrame();
		RunEvents.loadStandBy(1);
		RunEvents.scrollUpDown(firstScreen.getDriver(), "300");
		thirdScreen.screenShotsPage("Se verifican los datos del envío", testCaseName);
		RunEvents.loadStandBy(1);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);

		fourthScreenPage.screenShotsPage("Envio pendiente de aprobacion", testCaseName);
	}

	private boolean selectDollarRate(int i) {
		boolean finishSelectRates = false;

		switch (firstScreen.currencyField.get(i).getText().trim()) {
		case "USD":
			getBalanceValue(i);
			if (i == 1) {
				firstScreen.firstInputCheckBox.click();
			} else {
				firstScreen.inputCheckBox.get(i - 1).click();
			}
			finishSelectRates = true;
			break;
		}
		return finishSelectRates;
	}

	private void getBalanceValue(int i) {
		balanceValueText = firstScreen.amountField.get(i).getText().trim();
		balanceValueText = balanceValueText.replace(",", "");
		balanceValue = Float.parseFloat(balanceValueText);
	}

	private boolean selectYenRate(int i) {
		boolean finishSelectRates = false;
		switch (firstScreen.currencyField.get(i).getText().trim()) {
		case "JPY":
			getBalanceValue(i);
			if (i == 1) {
				firstScreen.firstInputCheckBox.click();
			} else {
				firstScreen.inputCheckBox.get(i - 1).click();
			}
			finishSelectRates = true;
			break;
		}
		return finishSelectRates;
	}

	private boolean selectEuroRate(int i) {
		boolean finishSelectRates = false;
		switch (firstScreen.currencyField.get(i).getText().trim()) {
		case "EUR":
			getBalanceValue(i);
			if (i == 1) {
				firstScreen.firstInputCheckBox.click();
			} else {
				firstScreen.inputCheckBox.get(i - 1).click();
			}
			finishSelectRates = true;
			break;
		}
		return finishSelectRates;
	}

	private boolean selectMexicanPesosRate(int i) {
		boolean finishSelectRates = false;
		switch (firstScreen.currencyField.get(i).getText().trim()) {
		case "MXN":
			getBalanceValue(i);
			if (i == 1) {
				firstScreen.firstInputCheckBox.click();
			} else {
				firstScreen.inputCheckBox.get(i - 1).click();
			}
			finishSelectRates = true;
			break;
		}
		return finishSelectRates;
	}

	private boolean selectCanadianDollarRate(int i) {
		boolean finishSelectRates = false;
		switch (firstScreen.currencyField.get(i).getText().trim()) {
		case "CAD":
			getBalanceValue(i);
			if (i == 1) {
				firstScreen.firstInputCheckBox.click();
			} else {
				firstScreen.inputCheckBox.get(i - 1).click();
			}
			finishSelectRates = true;
			break;
		}
		return finishSelectRates;
	}

	public void iSelectSeveralRatesOfTheDollarCurrency(List<EvidenceData> evidence) {
		globalCurrency = "USD";
		RunEvents.loadStandBy(3);
		RunEvents.scrollUpDown(firstScreen.getDriver(), "400");
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		firstScreen.screenShotsPage("Lista de tasas", evidence.get(0).getNameOfDirectory() + " " + globalCurrency);
		int countOfRates = 0;

		float balanceValue = 0.00f;
		String balanceValueText = "";
		DecimalFormatSymbols separadoresDecimal = new DecimalFormatSymbols();
		separadoresDecimal.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("#.00", separadoresDecimal);

		// Se recorren la cantidad de filas que tiene la tabla
		int numberOfPage = 0;
		boolean nextPage = true;
		do {
			numberOfPage++;
			// Se recorren la cantidad de filas que tiene la tabla
			for (int i = 1; i <= firstScreen.rowsOfTheTable.size() - 3; i++) {
				switch (firstScreen.currencyField.get(i).getText().trim()) {
				case "USD":
					balanceValueText = firstScreen.amountField.get(i).getText().trim();
					balanceValueText = balanceValueText.replace(",", "");
					balanceValue = Float.parseFloat(balanceValueText);
					if (balanceValue > highestBalance) {
						highestBalance = balanceValue;
					}

					if (i == 1) {
						firstScreen.firstInputCheckBox.click();
					} else {
						firstScreen.inputCheckBox.get(i - 1).click();
					}
					countOfRates = countOfRates + 1;
					break;
				}
			}
			firstScreen.screenShotsPage("tasasSeleccionadasDolar" + numberOfPage,
					"Envio banco de fondeo con multiples tasas");
			boolean existNextPage = false;

			for (WebElement elemento : firstScreen.elementsOfThePager) {
				if (elemento.getAttribute("class").contains("NavRight")) {
					existNextPage = true;
				}
			}

			if (existNextPage && countOfRates < 2) {
				firstScreen.NextButtonOfThePager.click();
				RunEvents.loadStandBy(2);
			} else {
				nextPage = false;
			}
		} while (nextPage);
		Util.loggerInformation("info", "El monto más alto es el: " + format.format(highestBalance),
				"Envio banco de fondeo con multiples tasas");
		firstScreen.buttonContinueRates.click();
	}

	public void iPrepareShippingDataBankFundingRates() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		String swiftCodeBeneficiaryBank = queries.getSwiftCodeBeneficiaryBank(accountNumber,
				"Envio banco de fondeo tasas");

		List<List<String>> swiftCodesFromPagen = queries.getSwiftCodesFromCibffpagen("Envio banco de fondeo tasas");

		String swiftCodeBeneficiaryBankPagen = "";
		String[][] swiftCodesToSave = new String[1][2];
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).trim().equals(swiftCodeBeneficiaryBank)) {
					swiftCodeBeneficiaryBankPagen = list.get(i).trim();
					System.out.println("Código swift del pagen que se va a remover para evitar que sea filial: "
							+ swiftCodeBeneficiaryBankPagen);
					queries.updateCibffpagen(swiftCodeBeneficiaryBank, "PPPPPPPPPPP",
							"Envio banco de fondeo con multiples tasas");
				}
			}
		}
		swiftCodesToSave[0][0] = swiftCodeBeneficiaryBankPagen;
		String paymentMethod = queries.getThePaymentMethod("" + balanceValue,
				"Envio banco de fondeo con multiples tasas");
		Util.loggerInformation("info",
				"Método de pago USD de la tasa por la cual se va a enviar el giro: " + paymentMethod,
				"Envio banco de fondeo con multiples tasas");

		String[] codeSubCode = queries.getCodeSubCodeFromCibffcobah(paymentMethod.trim(),
				"Envio banco de fondeo con multiples tasas");
		String code = codeSubCode[0];
		String subCode = codeSubCode[1];

		String swiftCodeBankFromCibffbanco = queries.getSwiftCodeFromCibffbanco(code, subCode,
				"Envio banco de fondeo con multiples tasas");
		Util.loggerInformation("info",
				"Código swift de la tasa encontrado en el CIBFFBANCO: " + swiftCodeBankFromCibffbanco.trim(),
				"Envio banco de fondeo con multiples tasas");
		String[] arraySwiftCodeBankFromCibffbanco = new String[2];
		arraySwiftCodeBankFromCibffbanco[0] = swiftCodeBankFromCibffbanco.trim().substring(0, 8);
		arraySwiftCodeBankFromCibffbanco[1] = swiftCodeBankFromCibffbanco.trim().substring(9);

		String swiftCodeRateBankFromCibffbancoToCompare = arraySwiftCodeBankFromCibffbanco[0]
				+ arraySwiftCodeBankFromCibffbanco[1];

		Util.loggerInformation("error",
				"código swift de la tasa obtenida del CIBFFBANCO sin la X de más: "
						+ swiftCodeRateBankFromCibffbancoToCompare
						+ "\n código swift de la tasa obtenida del CIBFFBANCO con la X: " + swiftCodeBankFromCibffbanco,
				"Envio banco de fondeo con multiples tasas");

		Util.loggerInformation("info",
				"Código swift de la tasa encontrado en el CIBFFBANCO para modificarlo en caso de encontrarse en el pagen: "
						+ swiftCodeBankFromCibffbanco.trim(),
				"Envio banco de fondeo con multiples tasas");

		String swiftCodeRateBankPagen = "";
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).trim().equals(swiftCodeRateBankFromCibffbancoToCompare.trim())) {
					swiftCodeRateBankPagen = list.get(i).trim();
					Util.loggerInformation("info",
							"Código swift que se va a quitar del pagen para que la tasa no sea filial: "
									+ swiftCodeRateBankPagen.trim(),
							"Envio banco de fondeo con multiples tasas");
					queries.updateCibffpagen(swiftCodeRateBankFromCibffbancoToCompare.trim(), "ZZZZZZZZZZZ",
							"Envio banco de fondeo con multiples tasas");
				}
			}
		}
		swiftCodesToSave[0][1] = swiftCodeRateBankPagen;

		try {
			GenerateExcelWithSwiftCodesPagen.exportarExcel(1, 2, swiftCodesToSave, "banco de fondeo multiples tasas");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void iPrepareShippingDataBankMultipleFundingRates() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		String swiftCodeBeneficiaryBank = queries.getSwiftCodeBeneficiaryBank(accountNumber,
				"Envio banco de fondeo con multiples tasas");

		List<List<String>> swiftCodesFromPagen = queries
				.getSwiftCodesFromCibffpagen("Envio banco de fondeo con multiples tasas");

		String swiftCodeBeneficiaryBankPagen = "";
		String[][] swiftCodesToSave = new String[1][2];
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).trim().equals(swiftCodeBeneficiaryBank)) {
					swiftCodeBeneficiaryBankPagen = list.get(i).trim();
					Util.loggerInformation("info",
							"Código swift del pagen que se va a remover para evitar que sea filial: "
									+ swiftCodeBeneficiaryBankPagen,
							"Envio banco de fondeo con multiples tasas");
					queries.updateCibffpagen(swiftCodeBeneficiaryBank, "PPPPPPPPPPP",
							"Envio banco de fondeo con multiples tasas");
				}
			}
		}
		swiftCodesToSave[0][0] = swiftCodeBeneficiaryBankPagen;

		String paymentMethod = queries.getThePaymentMethod("" + highestBalance,
				"Envio banco de fondeo con multiples tasas");
		Util.loggerInformation("info",
				"Método de pago USD de la tasa por la cual se va a enviar el giro: " + paymentMethod,
				"Envio banco de fondeo con multiples tasas");

		String[] codeSubCode = queries.getCodeSubCodeFromCibffcobah(paymentMethod.trim(),
				"Envio banco de fondeo con multiples tasas");
		String code = codeSubCode[0];
		String subCode = codeSubCode[1];

		String swiftCodeBankFromCibffbanco = queries.getSwiftCodeFromCibffbanco(code, subCode,
				"Envio banco de fondeo con multiples tasas");

		Util.loggerInformation("info",
				"Código swift de la tasa encontrado en el CIBFFBANCO: " + swiftCodeBankFromCibffbanco.trim(),
				"Envio banco de fondeo con multiples tasas");

		String[] arraySwiftCodeBankFromCibffbanco = new String[2];
		arraySwiftCodeBankFromCibffbanco[0] = swiftCodeBankFromCibffbanco.trim().substring(0, 8);
		arraySwiftCodeBankFromCibffbanco[1] = swiftCodeBankFromCibffbanco.trim().substring(9);

		String swiftCodeRateBankFromCibffbancoToCompare = arraySwiftCodeBankFromCibffbanco[0]
				+ arraySwiftCodeBankFromCibffbanco[1];

		Util.loggerInformation("info",
				"código swift de la tasa encontrada en cibffbanco para saber si la tasa es filial: "
						+ swiftCodeRateBankFromCibffbancoToCompare,
				"Envio banco de fondeo con multiples tasas");

		swiftCodesFromPagen = queries.getSwiftCodesFromCibffpagen("Envio banco de fondeo con multiples tasas");

		String swiftCodeRateBankPagen = "";
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).trim().equals(swiftCodeRateBankFromCibffbancoToCompare.trim())) {
					swiftCodeRateBankPagen = list.get(i).trim();
					Util.loggerInformation("info",
							"Código swift que se va a quitar del pagen para que la tasa no sea filial: "
									+ swiftCodeRateBankPagen.trim(),
							"Envio banco de fondeo con multiples tasas");
					queries.updateCibffpagen(swiftCodeRateBankFromCibffbancoToCompare.trim(), "ZZZZZZZZZZZ",
							"Envio banco de fondeo con multiples tasas");
				}
			}
		}
		swiftCodesToSave[0][1] = swiftCodeRateBankPagen;

		try {
			GenerateExcelWithSwiftCodesPagen.exportarExcel(1, 2, swiftCodesToSave, "banco de fondeo multiples tasas");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void iPrepareBeneficiaryDataBankMultipleRates() {
		SetUpBeneficiaryBankQueries queries = new SetUpBeneficiaryBankQueries();
		String swiftCodeBeneficiaryBank = queries.getSwiftCodeBeneficiaryBank(accountNumber,
				"Envio banco beneficiario con multiples tasas");
		Util.loggerInformation("info", "Swift code del banco beneficiario: " + swiftCodeBeneficiaryBank,
				"Envio banco beneficiario con multiples tasas");

		String[][] swiftCodesToSave = new String[2][1];

		List<List<String>> swiftCodesFromPagen = queries
				.getSwiftCodesFromCibffpagen("Envio banco beneficiario con multiples tasas");

		String swiftCodeBeneficiaryBankPagen = "";
		boolean swiftCodeFound = false;
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Lista de los códigos en el pagen: " + list.get(i).trim());
				if (list.get(i).trim().equals(swiftCodeBeneficiaryBank.trim())) {
					swiftCodeFound = true;
				} else {
					swiftCodeBeneficiaryBankPagen = list.get(i).trim();
				}
			}
		}

		if (!swiftCodeFound) {
			swiftCodeBeneficiaryBankPagen = swiftCodesFromPagen.get(0).get(0).trim();
			queries.updateCibffpagen(swiftCodeBeneficiaryBankPagen.trim(), swiftCodeBeneficiaryBank.trim(),
					"Envio banco beneficiario con multiples tasas");
		}

		swiftCodesToSave[0][0] = swiftCodeBeneficiaryBankPagen;
		swiftCodesToSave[1][0] = swiftCodeBeneficiaryBank;

		try {
			GenerateExcelWithSwiftCodesPagen.exportarExcel(2, 1, swiftCodesToSave,
					"Banco beneficiario multiples tasas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void iPrepareGenericDataBankMultipleRates() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		String swiftCodeBeneficiaryBank = queries.getSwiftCodeBeneficiaryBank(accountNumber,
				"Envio banco generico con multiples tasas");
		List<List<String>> swiftCodesFromPagen = queries
				.getSwiftCodesFromCibffpagen("Envio banco generico con multiples tasas");

		String swiftCodeBeneficiaryBankPagen = "";
		String[][] swiftCodesToSave = new String[1][2];
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).trim().equals(swiftCodeBeneficiaryBank.trim())) {

					swiftCodeBeneficiaryBankPagen = list.get(i).trim();
					queries.updateCibffpagen(swiftCodeBeneficiaryBank.trim(), "PPPPPPPPPPPP",
							"Envio banco generico con multiples tasas");
				}
			}
		}
		swiftCodesToSave[0][0] = swiftCodeBeneficiaryBankPagen;

		String paymentMethod = queries.getThePaymentMethod("" + highestBalance,
				"Envio banco generico con multiples tasas");

		String[] codeSubCode = queries.getCodeSubCodeFromCibffcobah(paymentMethod,
				"Envio banco generico con multiples tasas");
		String code = codeSubCode[0];
		String subCode = codeSubCode[1];

		String swiftCodeBankFromCibffbanco = queries.getSwiftCodeFromCibffbanco(code, subCode,
				"Envio banco generico con multiples tasas");

		String[] arraySwiftCodeBankFromCibffbanco = new String[2];
		arraySwiftCodeBankFromCibffbanco[0] = swiftCodeBankFromCibffbanco.trim().substring(0, 8);
		arraySwiftCodeBankFromCibffbanco[1] = swiftCodeBankFromCibffbanco.trim().substring(9);

		String swiftCodeRateBankFromCibffbancoToCompare = arraySwiftCodeBankFromCibffbanco[0]
				+ arraySwiftCodeBankFromCibffbanco[1];

		String swiftCodeGenericBankPagen = "";
		boolean swiftCodeFound = false;
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).trim().equals(swiftCodeRateBankFromCibffbancoToCompare.trim())) {
					swiftCodeFound = true;
				} else {
					swiftCodeGenericBankPagen = list.get(i).trim();
				}
			}
		}
		swiftCodesToSave[0][1] = swiftCodeGenericBankPagen;

		if (!swiftCodeFound) {
			queries.updateCibffpagen(swiftCodeGenericBankPagen.trim(), swiftCodeRateBankFromCibffbancoToCompare.trim(),
					"Envio banco generico con multiples tasas");
		}

		try {
			GenerateExcelWithSwiftCodesPagen.exportarExcel(1, 2, swiftCodesToSave, "banco generico multiples tasas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void iPrepareTheDataForTheCommissionRate(String tarifa, String currency, List<EvidenceData> evidence) {
		clienteExiste.iPrepareTheDataForTheCommissionRate(tarifa, currency, accountNumber, evidence);
	}

	public void validoElValorParaLaComisionTarifaYDivisa(String tarifa, String currency, List<EvidenceData> evidence) {
		clienteExiste.validoElValorParaLaComisionTarifaYDivisa(tarifa, currency, evidence);
	}

	public void preparoLosDatosClienteNoExisteParaLaComisionTarifaYDivisa(String commission, String currency,
			String rebate, List<EvidenceData> evidence) {

	}

	public void PreparoLosDatosClienteNoExisteParaLaDivisa(String currency, String rebate,
			List<EvidenceData> evidence) {
		clienteNoExiste.PreparoLosDatosClienteNoExisteParaLaDivisa(currency, rebate, accountNumber, evidence);

	}

	public void preparoLosDatosComisionGOURParaLaDivisaYElParametroCOMNEGGOUR(String currency, String estadoParametro,
			List<EvidenceData> evidence) {
		seleccionarCuentas(evidence);
		gourSteps.preparoLosDatosComisionGOURParaLaDivisaYElParametroCOMNEGGOUR(currency, estadoParametro,
				accountNumber, evidence);
		secondScreen.continueButton.click();
	}

	public void preparoLosDatosParaCobroDeGMF(String cobro, List<EvidenceData> evidence) {
		numeralSteps.preparoLosDatosParaCobroDeGMF(cobro, globalCurrency, accountNumber, numeralCambiario, evidence);
	}

}
