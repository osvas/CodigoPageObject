package com.bancolombia.mercadolibreempresas.testqueries;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.steps.sending.VerifyAccountsBeneficiarySteps;
import com.bancolombia.mercadolibreempresas.utilities.database.CibBeneficiaryAccountsQueries;

public class TestCibBeneficiaryAccounts {

	VerifyAccountsBeneficiarySteps verifyAccountsBeneficiarySteps;

	@Test
	public void testCibBeneficiaryAccounts() throws SQLException {

		CibBeneficiaryAccountsQueries testBeneficiaryActive = new CibBeneficiaryAccountsQueries();

		List<List<String>> resultado = testBeneficiaryActive.getActiveBeneficiaryAccounts();
		int cont = 0;
		String consulta[] = new String[resultado.size()];
		for (List<String> list : resultado) {

			consulta[cont] = "la cuenta es " + list.get(0).trim() + " - " + list.get(1).trim();
			cont++;

		}
		for (int i = 0; i < consulta.length; i++) {

			System.out.println("valor del listado:" + consulta[i]);
			// consulta[i]= consulta + "la consulta" +consulta[i];
		}

	}

}
