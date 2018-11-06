package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.models.DatosFinalesTransaccion;
import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.DetailOfTheTransactionPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.TransactionHistoryPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.TransactionPage;
import com.bancolombia.mercadolibreempresas.steps.commission.ClienteExisteOurBenshaSteps;
import com.bancolombia.mercadolibreempresas.steps.commission.ComisionGourSteps;
import com.bancolombia.mercadolibreempresas.steps.files.CDCFFCOMIS;
import com.bancolombia.mercadolibreempresas.steps.files.CIBFFINTOG;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.CibBeneficiaryAccountsQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.SendingQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.SimonCommissionQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CdcffapertQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibffMensaQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibffcomplQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibfflgfndQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.GeneralQueriesToFiles;

public class TransactionStatusSteps {
	@Page
	TransactionPage transactionPage;

	@Page
	DetailOfTheTransactionPage detailTransactionPage;

	@Page
	TransactionHistoryPage historyPage;

	CommissionQueries commissionQuery = new CommissionQueries();
	ClienteExisteOurBenshaSteps clienteExiste = new ClienteExisteOurBenshaSteps();
	private ComisionGourSteps gourSteps = new ComisionGourSteps();

	private static final String CONCAT_CORRESPONDANT = "X";
	Double tarifaPlena = 0.0;
	Double tarifaEspecial = 0.0;
	Double tarifaMinima = 0.0;

	String accountToDebit, nameOfTheBeneficiary, customName, accountOfTheBeneficiary, SWIFTCodeOfTheBeneficiaryBank,
			amountAndCurrency, rate, factor, amountInPesosOfTheValueToSend, typeOfCommission, totalAmountToDebit,
			changeDeclarationNumber, shippingReference;

	public void iReviewTheDataOfTheAccountThatIAmGoingToApprove(List<EvidenceData> evidence) {
		transactionPage.getDriver().switchTo().frame(transactionPage.transactionFrame);
		RunEvents.loadStandBy(3);
		transactionPage.pendienteAprobacionLink.get(0).click();
		RunEvents.loadStandBy(3);

		RunEvents.scrollUpDown(detailTransactionPage.getDriver(), "400");
		detailTransactionPage.screenShotsPage(evidence.get(0).getNameOfImage(), evidence.get(0).getNameOfDirectory());

		detailTransactionPage.regresarButton.click();
	}

	public void aprobarEnvio(List<EvidenceData> evidence, String currency) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		RunEvents.loadStandBy(4);
		transactionPage.checkboxList.get(0).click();
		transactionPage.approveButton.click();
		RunEvents.loadStandBy(2);
		transactionPage.aceptarButton.click();
		RunEvents.loadStandBy(2);
		transactionPage.terminarButton.click();
		RunEvents.loadStandBy(2);
		transactionPage.historicoLink.click();
		RunEvents.loadStandBy(1);
		historyPage.screenShotsPage("Lista de históricos aprobados", testCaseName);
		historyPage.historicoUltimaTransaccion.click();
		RunEvents.loadStandBy(2);
		RunEvents.scrollUpDown(historyPage.getDriver(), "300");
		historyPage.screenShotsPage(evidence.get(0).getNameOfImage(), testCaseName);
	}

	public void iApproveTheShipment(List<EvidenceData> evidence) {
		RunEvents.loadStandBy(4);
		transactionPage.checkboxList.get(0).click();
		transactionPage.approveButton.click();
		RunEvents.loadStandBy(2);
		transactionPage.aceptarButton.click();
		RunEvents.loadStandBy(2);
		transactionPage.terminarButton.click();
		RunEvents.loadStandBy(15);
		transactionPage.historicoLink.click();
		RunEvents.loadStandBy(4);
		historyPage.screenShotsPage("Lista de históricos aprobados", evidence.get(0).getNameOfDirectory());
		historyPage.historicoUltimaTransaccion.click();
		RunEvents.loadStandBy(2);
		RunEvents.scrollUpDown(historyPage.getDriver(), "300");
		historyPage.screenShotsPage(evidence.get(0).getNameOfImage(), evidence.get(0).getNameOfDirectory());
	}

	public void iValidatedTheDataInTheFileCDCFFAPERT() {

		String bankCode = "";
		String bankSubCode = "";
		CdcffapertQueries cdcffapertQueries = new CdcffapertQueries();
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();

		List<List<String>> data = cdcffapertQueries.getFileData(shippingReference, "Envio banco de fondeo");
		for (List<String> list : data) {
			bankCode = list.get(0).trim();
			bankSubCode = list.get(1).trim();
		}

		String bankNameInCIBFFCOBAH = generalQueries.getTheNameOfTheBankThroughTheCodeAndTheSubcodeInCIBFFCOBAH(
				bankCode, bankSubCode, "Envio banco de fondeo");

		String bankNameInCIBFFCONTR = generalQueries.getTheNameOfTheBankInCIBFFCONTR(rate, "Envio banco de fondeo");

		assertTrue("El nombre del banco del fondeo no es el mismo que sale en el CDCFFAPERT",
				bankNameInCIBFFCOBAH.trim().equals(bankNameInCIBFFCONTR.trim()));
	}

	public void iValidatedTheDataInTheFileCIBFFCOMPL() {

		String bankCode = "";
		String bankSubCode = "";
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();
		CibffcomplQueries cibffcomplQueries = new CibffcomplQueries();

		List<List<String>> data = cibffcomplQueries.getCodeSubcodeInCIBFFCOMPL(shippingReference,
				"Envio banco de fondeo");
		for (List<String> list : data) {
			bankCode = list.get(0).trim();
			bankSubCode = list.get(1).trim();
		}

		String bankNameInCIBFFCOBAH = generalQueries.getTheNameOfTheBankThroughTheCodeAndTheSubcodeInCIBFFCOBAH(
				bankCode, bankSubCode, "Envio banco de fondeo");

		String bankNameInCIBFFCONTR = generalQueries.getTheNameOfTheBankInCIBFFCONTR(rate, "Envio banco de fondeo");
		Util.loggerInformation("info",
				"Nombre del banco en la tasa: " + bankNameInCIBFFCONTR
						+ ". Nombre del banco encontrado en el CIBFFCOMPL: " + bankNameInCIBFFCOBAH,
				"Envio banco de fondeo");
		// assertTrue("El nombre del banco del fondeo no es el mismo que sale en el
		// CIBFFCOMPL",
		// bankNameInCIBFFCOBAH.trim().equals(bankNameInCIBFFCONTR.trim()));
	}

	public void iValidatedTheDataInTheFileCIBFFLGFND() {
		String bankCode = "";
		String bankSubCode = "";
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();
		CibfflgfndQueries cibfflgfndQueries = new CibfflgfndQueries();

		List<List<String>> data = cibfflgfndQueries.getCodeSubcodeInCIBFFLGFND(rate, "Envio banco de fondeo");
		for (List<String> list : data) {
			bankCode = list.get(0).trim();
			bankSubCode = list.get(1).trim();
		}

		String bankNameInCIBFFCOBAH = generalQueries.getTheNameOfTheBankThroughTheCodeAndTheSubcodeInCIBFFCOBAH(
				bankCode, bankSubCode, "Envio banco de fondeo");

		String bankNameInCIBFFCONTR = generalQueries.getTheNameOfTheBankInCIBFFCONTR(rate, "Envio banco de fondeo");

		assertTrue("El nombre del banco del fondeo no es el mismo que sale en el CIBFFLGFND",
				bankNameInCIBFFCOBAH.trim().equals(bankNameInCIBFFCONTR.trim()));
	}

	// Beneficiary As SubsidiaryBank
	public void iValidateDataSendingBankAsBeneficiaryBankInCDCFFAPERT() {

		String bankCodeExpected = "";
		String bankSubCodeExpected = "";
		SimonCommissionQueries commissionQueries = new SimonCommissionQueries();
		CibBeneficiaryAccountsQueries cibBeneficiaryAccountsQueries = new CibBeneficiaryAccountsQueries();
		SendingQueries sendingQueries = new SendingQueries();

		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(customName,
				"Consulta codigo swift del beneficiario");

		String codswiftXAdded = codeSwiftBenef.substring(0, 8) + CONCAT_CORRESPONDANT
				+ codeSwiftBenef.substring(8, codeSwiftBenef.length());

		String InfoInscriptedAccount[] = cibBeneficiaryAccountsQueries.searchActiveAccountBeneficiaryByUser(customName);

		String[] correspondantBank = sendingQueries.searchCorrespondantBankBySwiftCodeAndCurrency(codswiftXAdded,
				InfoInscriptedAccount[1]);

		bankCodeExpected = correspondantBank[0].trim();
		bankSubCodeExpected = correspondantBank[1].trim();

		String[] expectedBankInfo = { bankCodeExpected, bankSubCodeExpected };

		validateCodeBankAndSubCodeBankCDCFFAPERT("El banco de fondeo fue diferente al banco del beneficiario",
				expectedBankInfo);
	}

	public void iValidateDataSendingBankAsBeneficiaryBankInCIBFFCOMPL() {

		SimonCommissionQueries commissionQueries = new SimonCommissionQueries();
		CibBeneficiaryAccountsQueries cibBeneficiaryAccountsQueries = new CibBeneficiaryAccountsQueries();
		SendingQueries sendingQueries = new SendingQueries();
		CibffcomplQueries cibffcomplQueries = new CibffcomplQueries();

		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(customName,
				"Consulta codigo swift del beneficiario");

		String codswiftXAdded = codeSwiftBenef.substring(0, 8) + CONCAT_CORRESPONDANT
				+ codeSwiftBenef.substring(8, codeSwiftBenef.length());

		String InfoInscriptedAccount[] = cibBeneficiaryAccountsQueries.searchActiveAccountBeneficiaryByUser(customName);

		String[] correspondantBank = sendingQueries.searchCorrespondantBankBySwiftCodeAndCurrency(codswiftXAdded,
				InfoInscriptedAccount[1]);

		String[] bankInfoExpected = { correspondantBank[0].trim(), correspondantBank[1].trim() };

		String[] cibffcomplInfo = cibffcomplQueries.getCodeSubcodeInCIBFFCOMPLUniqueRow(shippingReference,
				"Envio banco de fondeo igual banco beneficiario");

		String[] bankInfoActual = { cibffcomplInfo[0].trim(), cibffcomplInfo[1].trim() };

		Util.loggerInformation("info",
				"Codigo banco y subbanco esperados en CIBFFCOMPL: " + bankInfoExpected[0] + "-" + bankInfoExpected[1],
				"Banco beneficiario como filial bancaria");
		Util.loggerInformation("info", "Codigo banco y subbanco arrojados por cib en CIBFFCOMPL: " + bankInfoActual[0]
				+ "-" + bankInfoActual[1], "Banco beneficiario como filial bancaria");

		validateCodeBankAndSubCodeBankCIBFFCOMPL("El banco de fondeo fue diferente al banco del beneficiario archivo",
				bankInfoExpected);
	}

	public void iValidateDataSendingBankAsBeneficiaryBankInCIBFFLGFND() {

		SendingQueries sendingQueries = new SendingQueries();
		RatesQueries ratesQueries = new RatesQueries();

		String[] amountAndCurrency_arr = amountAndCurrency.split("\\s+");
		List<List<String>> allRatesByCurrency = ratesQueries.searchAllTasas(amountAndCurrency_arr[1]);

		String wayOfPayment = allRatesByCurrency.get(0).get(7);

		String[] homologatedBankInfo = sendingQueries.searchHomologatedBankInfo(wayOfPayment.trim());

		String[] correspondantBank = sendingQueries.searchCorrespondantBankByCodeBankAndSubCode(homologatedBankInfo[0],
				homologatedBankInfo[1], amountAndCurrency_arr[1]);

		String[] bankInfoExpected = { correspondantBank[0].trim(), correspondantBank[1].trim() };

		validateCodeBankAndSubCodeBankCIBFFLGFND("El banco de fondeo fue diferente al banco del beneficiario archivo",
				bankInfoExpected);
	}

	public void iValidateDataSendingBankAsBeneficiaryForFoundingBankInCIBFFLGFND() {

		SendingQueries sendingQueries = new SendingQueries();
		SimonCommissionQueries commissionQueries = new SimonCommissionQueries();
		CibBeneficiaryAccountsQueries cibBeneficiaryAccountsQueries = new CibBeneficiaryAccountsQueries();

		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(customName,
				"Consulta codigo swift del beneficiario");

		String codswiftXAdded = codeSwiftBenef.substring(0, 8) + CONCAT_CORRESPONDANT
				+ codeSwiftBenef.substring(8, codeSwiftBenef.length());

		String InfoInscriptedAccount[] = cibBeneficiaryAccountsQueries.searchActiveAccountBeneficiaryByUser(customName);

		String[] correspondantBank = sendingQueries.searchCorrespondantBankBySwiftCodeAndCurrency(codswiftXAdded,
				InfoInscriptedAccount[1]);

		String[] bankInfoExpected = { correspondantBank[0].trim(), correspondantBank[1].trim() };

		validateCodeBankAndSubCodeBankCIBFFLGFND("El banco de fondeo fue diferente al banco del beneficiario archivo",
				bankInfoExpected);
	}

	public void iValidateDataSendingBankAsBeneficiaryBankInCIBFFMENSA() {

		CibffMensaQueries cibffMensaQueries = new CibffMensaQueries();
		String[] mensaInfoField57 = cibffMensaQueries.searchInCibffMensaByField(shippingReference, 57);

		assertTrue("Campo 57 existe en el CIBIFFMENSA con informacion" + mensaInfoField57[4],
				mensaInfoField57[0] == null);
	}

	public void iValidateDataSendingBankCIBFFMENSA() {

		CibffMensaQueries cibffMensaQueries = new CibffMensaQueries();
		SimonCommissionQueries commissionQueries = new SimonCommissionQueries();

		String[] mensaInfoField57 = cibffMensaQueries.searchInCibffMensaByField(shippingReference, 57);

		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(customName,
				"Consulta codigo swift del beneficiario");

		Util.loggerInformation("info", "Codigo swift en campo 57 esperado para CIBFFMENSA " + codeSwiftBenef,
				"Verificacion campo 57 Mensa");

		Util.loggerInformation("info", "Codigo swift en campo 57 consultado en CIBFFEMSNA " + mensaInfoField57[4],
				"Verificacion campo 57 Mensa");

		assertEquals("Campo 57 es el incorrecto" + mensaInfoField57[4], codeSwiftBenef.trim(),
				mensaInfoField57[4].trim());
	}

	// Generic Bank
	public void iValidateDataSendingBankByGenericBankInFile(String file) {

		CibBeneficiaryAccountsQueries cibBeneficiaryAccountsQueries = new CibBeneficiaryAccountsQueries();
		SendingQueries sendingQueries = new SendingQueries();

		String InfoInscriptedAccount[] = cibBeneficiaryAccountsQueries.searchActiveAccountBeneficiaryByUser(customName);

		String[] genericBankInfoPagen = sendingQueries.searchCibffpagenGeneralBankByCurrency(InfoInscriptedAccount[1]);//
		genericBankInfoPagen[2] = genericBankInfoPagen[2].trim().replaceAll("\\s{2,}", " ");

		String[] genericBankInfo = genericBankInfoPagen[2].split("\\s{1,}");

		String[] bankInfoExpected = { genericBankInfo[0], genericBankInfo[1] };

		String message = "El banco de fondeo fue diferente al banco generico para moneda " + InfoInscriptedAccount[1];

		switch (file) {
		case "CDCFFAPERT":
			validateCodeBankAndSubCodeBankCDCFFAPERT(message, bankInfoExpected);
			break;
		case "CIBFFCOMPL":
			validateCodeBankAndSubCodeBankCIBFFCOMPL(message, bankInfoExpected);
			break;
		case "CIBFFLGFND":
			validateCodeBankAndSubCodeBankCIBFFLGFND(message, bankInfoExpected);
			break;
		case "CIBFFMENSA":
			iValidateDataSendingBankCIBFFMENSA();
			break;
		}
	}

	private void validateCodeBankAndSubCodeBankCDCFFAPERT(String message, String[] bankInfoExpected) {

		CdcffapertQueries cdcffapertQueries = new CdcffapertQueries();

		List<List<String>> data = cdcffapertQueries.getFileData(shippingReference, "Envio banco de fondeo");

		String bankCodeActual = "";
		String bankSubCodeActual = "";

		for (List<String> list : data) {
			bankCodeActual = list.get(0).trim();
			bankSubCodeActual = list.get(1).trim();
		}

		String[] bankInfoActual = { bankCodeActual, bankSubCodeActual };

		Util.loggerInformation("info",
				"Codigo banco y subbanco esperados en CDCFFAPERT: " + bankInfoExpected[0] + "-" + bankInfoExpected[1],
				"Banco beneficiario como filial bancaria");
		Util.loggerInformation("info", "Codigo banco y subbanco arrojados por cib en CDCFFAPERT: " + bankInfoActual[0]
				+ "-" + bankInfoActual[1], "Banco beneficiario como filial bancaria");

		assertArrayEquals(message, bankInfoExpected, bankInfoActual);
	}

	private void validateCodeBankAndSubCodeBankCIBFFCOMPL(String message, String[] bankInfoExpected) {

		CibffcomplQueries cibffcomplQueries = new CibffcomplQueries();

		String[] cibffcomplInfo = cibffcomplQueries.getCodeSubcodeInCIBFFCOMPLUniqueRow(shippingReference,
				"Envio banco de fondeo igual banco beneficiario");

		String[] bankInfoActual = { cibffcomplInfo[0].trim(), cibffcomplInfo[1].trim() };

		Util.loggerInformation("info",
				"Codigo banco y subbanco esperados en CIBFFCOMPL: " + bankInfoExpected[0] + "-" + bankInfoExpected[1],
				"Banco beneficiario como filial bancaria");
		Util.loggerInformation("info", "Codigo banco y subbanco arrojados por cib en CIBFFCOMPL: " + bankInfoActual[0]
				+ "-" + bankInfoActual[1], "Banco beneficiario como filial bancaria");

		assertArrayEquals(message, bankInfoExpected, bankInfoActual);
	}

	private void validateCodeBankAndSubCodeBankCIBFFLGFND(String message, String[] bankInfoExpected) {

		CibfflgfndQueries cibfflgfndQueries = new CibfflgfndQueries();

		String[] bankInfoActual = cibfflgfndQueries
				.getCodeSubcodeInCIBFFLGFNDByRemmNumberForSendingBank(shippingReference, "Envio log banco de fondeo");

		Util.loggerInformation("info",
				"Codigo banco y subbanco esperados en CIBFFLGFND: " + bankInfoExpected[0] + "-" + bankInfoExpected[1],
				"Banco beneficiario como filial bancaria");
		Util.loggerInformation("info", "Codigo banco y subbanco arrojados por cib en CIBFFLGFND: " + bankInfoActual[0]
				+ "-" + bankInfoActual[1], "Banco beneficiario como filial bancaria");

		assertArrayEquals(message, bankInfoExpected, bankInfoActual);
	}

	public void setAmountAndCurrency(String amountAndCurrency) {
		this.amountAndCurrency = amountAndCurrency;
	}

	public void setShippingNumber(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public void validoElValorParaLaComisionTarifaYDivisa(String commission, String tarifa, String currency,
			List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		Util.loggerInformation("info",
				"<--------------Validación de los datos mostrados en pantalla del giro enviado-------------->",
				testCaseName);
		// DatosFinalesTransaccion datosFinales = new DatosFinalesTransaccion();
		// Se aprueba el envío
		aprobarEnvio(evidence, currency);

		// Se recorren las etiquetas de la izquierda para obtener los datos de la
		// derecha deseados
		DatosFinalesTransaccion datosFinales = obtenerDatosFinalesDelEnvio();

		CommissionQueries commissionQueries = new CommissionQueries();
		List<List<String>> obtenerCliente = commissionQueries.searchUserInTheCentralCommission(testCaseName);

		// Si la comisión es GOUR, entonces se hacen los cálculos de la comisión.
		if (commission.toUpperCase().equals("GOUR")) {
			// Si la comisión es GOUR entonces el valor que llega por parámetro no es la
			// tarifa sino el estado del parámetro (activo - inactivo)
			String estadoComneggour = tarifa;
			gourSteps.validoElValorParaLaComisionTarifaYDivisaParametroCOMNGGOUR(datosFinales, currency,
					estadoComneggour, evidence);
		} else if (obtenerCliente.isEmpty()) {
			// TODO Se debe de hacer la validación cuando el clinte no existe en la central
			// de comisiones
		} else {
			clienteExiste.validoElValorParaLaComisionTarifaYDivisa(datosFinales, tarifa, currency, commission,
					evidence);
		}

	}

	public void validoArchivoCdcffcomisParaComisionTarifaYDivisa(String commission, String tarifa, String currency,
			List<EvidenceData> evidence) {
		CDCFFCOMIS cdcffcomis = new CDCFFCOMIS();
		DatosFinalesTransaccion datosFinales = obtenerDatosFinalesDelEnvio();
		cdcffcomis.validoArchivoCdcffcomisParaComisionTarifaYDivisa(commission, tarifa, currency, datosFinales,
				evidence);
	}

	public void validoElArchivoCibffintogDondeSeCobraElGMFConLaDivisa(String cobro, String currency,
			List<EvidenceData> evidence) {
		CIBFFINTOG intog = new CIBFFINTOG();
		DatosFinalesTransaccion datosFinales = obtenerDatosFinalesDelEnvio();
		intog.validoElArchivoCibffintogDondeSeCobraElGMFConLaDivisa(datosFinales, cobro, currency, evidence);
	}

	public DatosFinalesTransaccion obtenerDatosFinalesDelEnvio() {
		DatosFinalesTransaccion datosFinales = new DatosFinalesTransaccion();
		List<WebElement> labelsList = historyPage.labelsTransaction;
		List<WebElement> textsList = historyPage.textsTransaction;
		// Se recorren las etiquetas de la izquierda para obtener los datos de la
		// derecha deseados
		int contador = 0;
		for (WebElement list : labelsList) {
			if (list.getText().trim().equals("Código CLABE:") || list.getText().trim().equals("Código IBAN:")
					|| list.getText().trim().equals("Cuenta del beneficiario:")
					|| list.getText().trim().equals("Código TRANSIT:")) {
				datosFinales.setCuentaDelBeneficiario(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Código SWIFT banco beneficiario:")) {
				datosFinales.setCodigoSwiftBancoBeneficiario(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Monto y moneda:")) {
				datosFinales.setMontoYMoneda(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Tasa:")) {
				datosFinales.setTasa(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Factor:")) {
				datosFinales.setFactor(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Referencia de envío:")) {
				String referenciaDeEnvio = textsList.get(contador).getText().trim();
				datosFinales.setReferenciaDeEnvio(referenciaDeEnvio.substring(4, 16));
			}

			if (list.getText().trim().equals("Comisión de envío:")) {
				datosFinales.setComisionDeEnvio((textsList.get(contador).getText().trim()));
			}

			if (list.getText().trim().equals("Monto en pesos del valor a enviar:")) {
				datosFinales.setMontoEnPesosValorAEnviar(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Gastos de banco en el exterior:")) {
				datosFinales.setGastosBancoEnExterior(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Comisión de envío:")) {
				datosFinales.setComisionDeEnvio(textsList.get(contador).getText().trim());
			}

			if (list.getText().trim().equals("Monto total a debitar:")) {
				datosFinales.setMontoTotalADebitar(textsList.get(contador).getText().trim());
			}
			contador = contador + 1;
		}

		return datosFinales;
	}

}
