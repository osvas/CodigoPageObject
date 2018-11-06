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

public class TestShipmentBeneficiaryBankWithMultipleRates {
	String shippingReference = "025060003400";
	String accountNumber = "779390453";
	String swiftCodeBeneficiary = "SNBKUS3AXXX";
	String currency = "USD";

	@Before
	public void setDataSwiftCode() {
		String[] arraySwiftCodeBeneficiaryBank = new String[2];
		arraySwiftCodeBeneficiaryBank[0] = swiftCodeBeneficiary.substring(0, 8);
		arraySwiftCodeBeneficiaryBank[1] = swiftCodeBeneficiary.substring(8);
		swiftCodeBeneficiary = arraySwiftCodeBeneficiaryBank[0] + "X" + arraySwiftCodeBeneficiaryBank[1];
	}

	@Test
	public void testValidateFileCDCFFAPERT() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CDCFFAPERT-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCodeApert = "";
		String bankSubCodeApert = "";
		String bankCodeBanco = "";
		String bankSubCodeBanco = "";
		CdcffapertQueries cdcffapertQueries = new CdcffapertQueries();
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();

		List<List<String>> data = cdcffapertQueries.getFileData(shippingReference,
				"Envio banco de fondeo múltiples tasas");
		for (List<String> list : data) {
			bankCodeApert = list.get(0).trim();
			bankSubCodeApert = list.get(1).trim();
		}

		String[] codesFromCIBFFBANCO = generalQueries.getCodeSubcodeFromCIBFFBANCO(swiftCodeBeneficiary,
				"Envio banco beneficiario múltiples tasas");

		bankCodeBanco = codesFromCIBFFBANCO[0];
		bankSubCodeBanco = codesFromCIBFFBANCO[1];

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFAPERT: " + bankCodeApert.trim() + " - " + bankSubCodeApert.trim()
						+ "; Codigo y subcodigo del archivo CIBFFBANCO: " + bankCodeBanco.trim() + " - "
						+ bankSubCodeBanco.trim(),
				"Envio banco beneficiario múltiples tasas");

		assertTrue("Los códigos del banco no coinciden", bankCodeApert.trim().equals(bankCodeBanco.trim()));
		assertTrue("Los subcódigos del banco no coinciden", bankSubCodeApert.trim().equals(bankSubCodeBanco.trim()));
	}

	@Test
	public void testValidateFileCIBFFCOMPL() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CIBFFCOMPL-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCodeApert = "";
		String bankSubCodeApert = "";
		String bankCodeBanco = "";
		String bankSubCodeBanco = "";
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();
		CibffcomplQueries cibffcomplQueries = new CibffcomplQueries();

		List<List<String>> data = cibffcomplQueries.getCodeSubcodeInCIBFFCOMPL(shippingReference,
				"Envio banco de fondeo múltiples tasas");
		for (List<String> list : data) {
			bankCodeApert = list.get(0).trim();
			bankSubCodeApert = list.get(1).trim();
		}

		String[] codesFromCIBFFBANCO = generalQueries.getCodeSubcodeFromCIBFFBANCO(swiftCodeBeneficiary,
				"Envio banco beneficiario múltiples tasas");

		bankCodeBanco = codesFromCIBFFBANCO[0];
		bankSubCodeBanco = codesFromCIBFFBANCO[1];

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFCOMPL: " + bankCodeApert.trim() + " - " + bankSubCodeApert.trim()
						+ "; Codigo y subcodigo del archivo CIBFFBANCO: " + bankCodeBanco.trim() + " - "
						+ bankSubCodeBanco.trim(),
				"Envio banco beneficiario múltiples tasas");

		assertTrue("Los códigos del banco no coinciden", bankCodeApert.trim().equals(bankCodeBanco.trim()));
		assertTrue("Los subcódigos del banco no coinciden", bankSubCodeApert.trim().equals(bankSubCodeBanco.trim()));
	}

	@Test
	public void testValidateFileCIBFFLGFND() {
		Util.loggerInformation("info", "<--------Probando datos del archivo CIBFFLGFND-------->",
				"Envio banco beneficiario múltiples tasas");
		String bankCodeApert = "";
		String bankSubCodeApert = "";
		String bankCodeBanco = "";
		String bankSubCodeBanco = "";
		GeneralQueriesToFiles generalQueries = new GeneralQueriesToFiles();
		CibfflgfndQueries cibfflgfndQueries = new CibfflgfndQueries();

		List<List<String>> data = cibfflgfndQueries.getCodeSubcodeInCIBFFLGFNDWithMultipleRates(shippingReference,
				"Envio banco de fondeo múltiples tasas");
		for (List<String> list : data) {
			bankCodeApert = list.get(0).trim();
			bankSubCodeApert = list.get(1).trim();
		}

		String[] codesFromCIBFFBANCO = generalQueries.getCodeSubcodeFromCIBFFBANCO(swiftCodeBeneficiary,
				"Envio banco beneficiario múltiples tasas");

		bankCodeBanco = codesFromCIBFFBANCO[0];
		bankSubCodeBanco = codesFromCIBFFBANCO[1];

		Util.loggerInformation("info",
				"Codigo y subcodigo del archivo CIBFFLGFND: " + bankCodeApert.trim() + " - " + bankSubCodeApert.trim()
						+ "; Codigo y subcodigo del archivo CIBFFBANCO: " + bankCodeBanco.trim() + " - "
						+ bankSubCodeBanco.trim(),
				"Envio banco beneficiario múltiples tasas");

		assertTrue("Los códigos del banco no coinciden", bankCodeApert.trim().equals(bankCodeBanco.trim()));
		assertTrue("Los subcódigos del banco no coinciden", bankSubCodeApert.trim().equals(bankSubCodeBanco.trim()));
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

		Util.loggerInformation("info", "Código swift del banco beneficiario: " + swiftBeneficiary,
				"Envio banco genérico múltiples tasas");

		if (swiftCodeMensa == null) {
			Util.loggerInformation("info", "Código swift almacenado en el archivo CIBFFMENSA: ' '",
					"Envio banco genérico múltiples tasas");
		} else {
			Util.loggerInformation("info", "Código swift almacenado en el archivo CIBFFMENSA: " + swiftCodeMensa,
					"Envio banco genérico múltiples tasas");
		}

		assertTrue(
				"Se está guardando el código swift del beneficiario en el archivo CIBFFMENSA, es un error debido a que no se debe de mostrar ahí ya que el banco corresponsal es el mismo banco del beneficiario",
				swiftCodeMensa == null);
	}
}
