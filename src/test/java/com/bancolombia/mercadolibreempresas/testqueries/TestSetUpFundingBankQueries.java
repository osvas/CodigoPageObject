package com.bancolombia.mercadolibreempresas.testqueries;

import java.util.List;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.bancofondeo.SetUpFundingBankQueries;

public class TestSetUpFundingBankQueries {

	@Test
	public void testGetSwiftCodeBeneficiaryBank() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		System.out.println(queries.getSwiftCodeBeneficiaryBank("779390453", "JunitTest").trim());
	}

	@Test
	public void testGetPagenData() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		List<List<String>> swiftCodes = queries.getSwiftCodesFromCibffpagen("JunitTest");
		for (List<String> list : swiftCodes) {
			for (int j = 0; j < list.size(); j++) {
				System.out.println(list.get(j).trim());
			}
		}
	}

	@Test
	public void testUpdateCibffpagen() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		queries.updateCibffpagen("YYYYYYYYYYYY", "CHASCOBBXXXX", "JUnitTest");
	}

	@Test
	public void testGetThePaymentMethod() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		System.out.println(queries.getThePaymentMethod("8529943.00", "JUnitTest").trim());
	}

	@Test
	public void testGetCodeSubCodeFromCibffcobah() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		String[] codeSubcodes = queries.getCodeSubCodeFromCibffcobah("TRANS.BANK OF NY", "JUnitTest");

		for (int i = 0; i < codeSubcodes.length; i++) {
			System.out.println(codeSubcodes[i].trim());
		}
	}

	@Test
	public void testGetSwiftCodeFromCibffbanco() {
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();
		System.out.println(queries.getSwiftCodeFromCibffbanco("1044", "473", "JUnitTest"));
	}
}
