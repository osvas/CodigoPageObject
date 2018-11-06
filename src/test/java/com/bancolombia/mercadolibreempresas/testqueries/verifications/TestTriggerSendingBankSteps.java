package com.bancolombia.mercadolibreempresas.testqueries.verifications;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.steps.sending.SendingBankSteps;
import com.bancolombia.mercadolibreempresas.utilities.database.SendingQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.SimonCommissionQueries;

public class TestTriggerSendingBankSteps {

	private static SimonCommissionQueries commissionQueries;
	private static SendingBankSteps sendingBankSteps;
	private static SendingQueries sendingQueries;

	@Before
	public void initTest() {
		commissionQueries = new SimonCommissionQueries();
		sendingBankSteps = new SendingBankSteps();
		sendingQueries = new SendingQueries();
	}

	@Test
	public void testSettingUpDataForBeneficiaryAsSubsidiary() {
		String beneficiaryAccount = "xxxxxxxxxxxxxxxx - maria fernanda";
		sendingBankSteps.setBeneficiaryBankAsSubsidiaryForSending("01", beneficiaryAccount);
		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(beneficiaryAccount.split("-")[1],
				"Consulta codigo swift del beneficiario");
		String[] infoSubsidiary = commissionQueries.searchBankNoCommissionBySwiftCode(codeSwiftBenef,
				"Consulta  Codigo swift");
		commissionQueries.updateBankNoCommission("01", "XXXXXXXXXX", "Modificando banco filial");
		assertEquals(codeSwiftBenef, infoSubsidiary[1]);
	}

	@Test
	public void testSettingUpDataForHomolagatedBankAsSubsidiary() {
		String currency = "USD";
		String bconocom = "01";
		String beneficiaryAccount = "xxxxxxxxxxxxxxxx - maria fernanda";
		sendingBankSteps.setHomologatedBankAsSubsidiaryByCurrencyForSending(currency, bconocom, beneficiaryAccount);
		sendingQueries.searchCibffpagenGeneralBankByCurrency(currency);
	}

	@Test
	public void testSettingUpDataForHomologatedBankAsFoundingBank() {
		String currency = "USD";
		String bconocom = "01";
		String beneficiaryAccount = "xxxxxxxxxxxxxxxx - maria fernanda";
		sendingBankSteps.setHomologatedBankAsSendingBankForSending(currency, bconocom, beneficiaryAccount);
	}

}
