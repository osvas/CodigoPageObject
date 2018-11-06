package com.bancolombia.mercadolibreempresas.testqueries;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.SendingQueries;

public class TestSendingQueries {

	SendingQueries sendingQueries = new SendingQueries();

	@Test
	public void testFindTheNameOfAUserThroughTheirIdentification() {
		String name = sendingQueries.findTheNameOfAUserThroughTheirIdentification("JUnit");
		System.out.println("Nombre del usuario:" + name);
	}

	@Test
	public void testFindBeneficiaryAccountData() {
		String[] data = sendingQueries.findBeneficiaryAccountData("JUnit");

		for (int i = 0; i < data.length; i++) {
			System.out.println("valores: " + data[i].trim());
		}
	}

	@Test
	public void testInsertBeneficiary() {
		String codbco = "2940";
		String subcodbco = "231";
		String codswift = "BKENGB2LCON";
		String bankName = "BANK OF ENGLAND";
		String city = "YORK";
		String country = "INGLATERRA";
		String currency = "EUR";
		sendingQueries.insertCorrespondantBank(codbco, subcodbco, codswift, bankName, city, country, currency);
	}

	@Test
	public void testSearchGenericBankByCurrency() {
		sendingQueries.searchCibffpagenGeneralBankByCurrency("USD");
	}
}
