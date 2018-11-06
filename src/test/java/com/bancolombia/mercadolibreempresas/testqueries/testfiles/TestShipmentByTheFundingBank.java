package com.bancolombia.mercadolibreempresas.testqueries.testfiles;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CdcffapertQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibffMensaQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibffcomplQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibfflgfndQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.GeneralQueriesToFiles;

public class TestShipmentByTheFundingBank {
	String shippingReference = "025060003405";
	// Valor que tiene la tasa y no el saldo
	String rate = "8712.23";
	String accountNumber = "779390453";
	String currency = "USD";
	private String bankNameInCIBFFCONTR = "";
	private String[] codesBankOfTheRate;

	@Before
	public void getCodesFromCIBFFCONTR() {
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();
		bankNameInCIBFFCONTR = generalQueries.getTheNameOfTheBankInCIBFFCONTR(rate, "Envio banco de fondeo");
		codesBankOfTheRate = generalQueries.getCodeSubCodeFromCIBFFCOBAH(bankNameInCIBFFCONTR,
				"Envio banco de fondeo múltiples tasas");
	}

	@Test
	public void testValidateFileCDCFFAPERT() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CDCFFAPERT-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCode = "";
		String bankSubCode = "";
		CdcffapertQueries cdcffapertQueries = new CdcffapertQueries();

		List<List<String>> data = cdcffapertQueries.getFileData(shippingReference,
				"Envio banco de fondeo múltiples tasas");
		for (List<String> list : data) {
			bankCode = list.get(0).trim();
			bankSubCode = list.get(1).trim();
		}

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFAPERT: " + bankCode.trim() + " - " + bankSubCode.trim()
						+ "; Codigo y subcodigo del archivo CIBFFCOBAH pertenecientes a la tasa de mayor saldo: "
						+ codesBankOfTheRate[0].trim() + " - " + codesBankOfTheRate[1].trim(),
				"Envio banco beneficiario múltiples tasas");

		assertTrue("Los códigos del archivo CIBFFAPERT no coinciden",
				bankCode.trim().equals(codesBankOfTheRate[0].trim()));
		assertTrue("Los subcódigos del archivo CIBFFAPERT no coinciden",
				bankSubCode.trim().equals(codesBankOfTheRate[1].trim()));
	}

	@Test
	public void testValidateFileCIBFFCOMPL() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CIBFFCOMPL-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCode = "";
		String bankSubCode = "";
		CibffcomplQueries cibffcomplQueries = new CibffcomplQueries();

		List<List<String>> data = cibffcomplQueries.getCodeSubcodeInCIBFFCOMPL(shippingReference,
				"Envio banco de fondeo");
		for (List<String> list : data) {
			bankCode = list.get(0).trim();
			bankSubCode = list.get(1).trim();
		}

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFCOMPL: " + bankCode.trim() + " - " + bankSubCode.trim()
						+ "; Codigo y subcodigo del archivo CIBFFCOBAH pertenecientes a la tasa: "
						+ codesBankOfTheRate[0].trim() + " - " + codesBankOfTheRate[1].trim(),
				"Envio banco beneficiario múltiples tasas");

		assertTrue("Los códigos del archivo CIBFFCOMPL no coinciden",
				bankCode.trim().equals(codesBankOfTheRate[0].trim()));
		assertTrue("Los subcódigos del archivo CIBFFCOMPL no coinciden",
				bankSubCode.trim().equals(codesBankOfTheRate[1].trim()));
	}

	@Test
	public void testValidateFileCIBFFLGFND() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CIBFFLGFND-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCode = "";
		String bankSubCode = "";
		CibfflgfndQueries cibfflgfndQueries = new CibfflgfndQueries();

		List<List<String>> data = cibfflgfndQueries.getCodeSubcodeInCIBFFLGFND(rate, "Envio banco de fondeo");
		for (List<String> list : data) {
			bankCode = list.get(0).trim();
			bankSubCode = list.get(1).trim();
		}

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFLGFND: " + bankCode.trim() + " - " + bankSubCode.trim()
						+ "; \n Codigo y subcodigo del archivo CIBFFCOBAH pertenecientes a la tasa: "
						+ codesBankOfTheRate[0].trim() + " - " + codesBankOfTheRate[1].trim(),
				"Envio banco beneficiario múltiples tasas");

		assertTrue("Los códigos del archivo CIBFFLGFND no coinciden",
				bankCode.trim().equals(codesBankOfTheRate[0].trim()));
		assertTrue("Los subcódigos del archivo CIBFFLGFND no coinciden",
				bankSubCode.trim().equals(codesBankOfTheRate[1].trim()));
	}

	@Test
	public void testValidateFileCIBFFMENSA() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CIBFFMENSA-------->",
				"Envio banco beneficiario múltiples tasas");
		CibffMensaQueries CibffmensaQueries = new CibffMensaQueries();
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();

		String swiftCodeMensa = CibffmensaQueries.getSwiftCodeInCIBFFMENSA(shippingReference,
				"Envio banco genérico múltiples tasas");

		String swiftBeneficiary = generalQueries.getTheSwiftCodeOfTheBeneficiaryBankMultipleRates(accountNumber,
				"Envio banco genérico múltiples tasas");

		String[] arraySwiftCodeBankFromCibffbanco = new String[2];
		arraySwiftCodeBankFromCibffbanco[0] = swiftBeneficiary.trim().substring(0, 8);
		arraySwiftCodeBankFromCibffbanco[1] = swiftBeneficiary.trim().substring(8);

		String swiftBeneficiaryWithX = arraySwiftCodeBankFromCibffbanco[0] + "X" + arraySwiftCodeBankFromCibffbanco[1];

		String[] codeSubcodeBeneficiaryBank = generalQueries.getCodeSubcodeCorrespondentFromCIBFFBANCO(
				swiftBeneficiaryWithX, currency, "Envio banco genérico múltiples tasas");

		Util.loggerInformation("info",
				"Código y subcódigo del banco beneficiario (CIBFFBANCO): " + codeSubcodeBeneficiaryBank[0] + " - "
						+ codeSubcodeBeneficiaryBank[1] + "; Código y subcódigo del banco por donde se envió el giro: "
						+ codesBankOfTheRate[0] + " - " + codesBankOfTheRate[1],
				"Envio banco genérico múltiples tasas");

		Util.loggerInformation("info",
				"Código swift del banco beneficiario (CIBFFCTAIN): " + swiftBeneficiary.trim()
						+ "; Código swift almacenado en el CIBFFMENSA: " + swiftCodeMensa,
				"Envio banco genérico múltiples tasas");

		if (!codeSubcodeBeneficiaryBank[0].trim().equals(codesBankOfTheRate[0].trim())
				|| !codeSubcodeBeneficiaryBank[1].trim().equals(codesBankOfTheRate[1].trim())) {
			assertTrue("No está quedando el código swift en el campo 57 del CIBFFMENSA",
					swiftCodeMensa.trim().equals(swiftBeneficiary.trim()));
		} else {
			assertTrue(swiftCodeMensa == null);
		}
	}
}
