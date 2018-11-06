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

public class TestShipmentGenericBankWithMultipleRates {
	String shippingReference = "025060003402";
	String accountNumber = "779390453";
	private String genericCode = "";
	private String genericSubCode = "";
	String currency = "USD";

	@Before
	public void findCodSubcodeshipment() {
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();
		String genericBank = generalQueries.getCodeSubcodeGenericBankFromPagen("Envio banco genérico múltiples tasas");
		genericBank = genericBank.replaceAll("\\s{2,}", " ");
		String[] arrayGenericBank = genericBank.split(" ");
		genericCode = arrayGenericBank[0].trim();
		genericSubCode = arrayGenericBank[1].trim();
	}

	@Test
	public void testValidateFileCDCFFAPERT() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CDCFFAPERT-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCodeApert = "";
		String bankSubCodeApert = "";
		CdcffapertQueries cdcffapertQueries = new CdcffapertQueries();

		List<List<String>> data = cdcffapertQueries.getFileData(shippingReference,
				"Envio banco genérico múltiples tasas");
		for (List<String> list : data) {
			bankCodeApert = list.get(0).trim();
			bankSubCodeApert = list.get(1).trim();
		}

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFAPERT: " + bankCodeApert.trim() + " - " + bankSubCodeApert.trim()
						+ "; Codigo y subcodigo del banco genérico: " + genericCode.trim() + " - "
						+ genericSubCode.trim(),
				"Envio banco genérico múltiples tasas");

		assertTrue("Los códigos del banco no coinciden", bankCodeApert.trim().equals(genericCode.trim().trim()));
		assertTrue("Los subcódigos del banco no coinciden", bankSubCodeApert.trim().equals(genericSubCode.trim()));
	}

	@Test
	public void testValidateFileCIBFFCOMPL() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CIBFFCOMPL-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCodeApert = "";
		String bankSubCodeApert = "";
		CibffcomplQueries cibffcomplQueries = new CibffcomplQueries();

		List<List<String>> data = cibffcomplQueries.getCodeSubcodeInCIBFFCOMPL(shippingReference,
				"Envio banco de fondeo múltiples tasas");
		for (List<String> list : data) {
			bankCodeApert = list.get(0).trim();
			bankSubCodeApert = list.get(1).trim();
		}

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFCOMPL: " + bankCodeApert.trim() + " - " + bankSubCodeApert.trim()
						+ "; Codigo y subcodigo del banco genérico: " + genericCode.trim() + " - "
						+ genericSubCode.trim(),
				"Envio banco genérico múltiples tasas");

		assertTrue("Los códigos del banco no coinciden", bankCodeApert.trim().equals(genericCode.trim().trim()));
		assertTrue("Los subcódigos del banco no coinciden", bankSubCodeApert.trim().equals(genericSubCode.trim()));
	}

	@Test
	public void testValidateFileCIBFFLGFND() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CIBFFLGFND-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCodeApert = "";
		String bankSubCodeApert = "";
		CibfflgfndQueries cibfflgfndQueries = new CibfflgfndQueries();

		List<List<String>> data = cibfflgfndQueries.getCodeSubcodeInCIBFFLGFNDWithMultipleRates(shippingReference,
				"Envio banco de fondeo múltiples tasas");
		for (List<String> list : data) {
			bankCodeApert = list.get(0).trim();
			bankSubCodeApert = list.get(1).trim();
		}

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFLGFND: " + bankCodeApert.trim() + " - " + bankSubCodeApert.trim()
						+ "; Codigo y subcodigo del banco genérico: " + genericCode.trim() + " - "
						+ genericSubCode.trim(),
				"Envio banco genérico múltiples tasas");

		assertTrue("Los códigos del banco no coinciden", bankCodeApert.trim().equals(genericCode.trim().trim()));
		assertTrue("Los subcódigos del banco no coinciden", bankSubCodeApert.trim().equals(genericSubCode.trim()));
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
						+ genericCode + " - " + genericSubCode,
				"Envio banco genérico múltiples tasas");

		if (swiftCodeMensa == null) {
			Util.loggerInformation("info", "Código swift almacenado en el archivo CIBFFMENSA: ' '",
					"Envio banco genérico múltiples tasas");
		} else {
			Util.loggerInformation("info", "Código swift almacenado en el archivo CIBFFMENSA: " + swiftCodeMensa,
					"Envio banco genérico múltiples tasas");
		}
		Util.loggerInformation("info", "Códgio swift del banco beneficiario: " + swiftBeneficiary,
				"Envio banco genérico múltiples tasas");

		if (!codeSubcodeBeneficiaryBank[0].trim().equals(genericCode.trim())
				|| !codeSubcodeBeneficiaryBank[1].trim().equals(genericSubCode.trim())) {
			assertTrue("No está quedando el código swift en el campo 57 del CIBFFMENSA",
					swiftCodeMensa.trim().equals(swiftBeneficiary.trim()));
		} else {
			assertTrue(swiftCodeMensa == null);
		}
	}
}
