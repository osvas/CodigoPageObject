package com.bancolombia.mercadolibreempresas.testqueries.verifications;

import org.junit.Before;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.steps.sending.TransactionStatusSteps;

public class TestTriggerTransactionStatusSteps {

	private static TransactionStatusSteps transactionStatusSteps;
	
	String customName = "vidal";
	String shippingNumber = "025060002598";//"025060002581";
	
	@Before
	public void initTest() {
		 transactionStatusSteps = new TransactionStatusSteps();
	}
	
	@Test
	public void testBeneficiaryAsSubsidiaryBankCDCFFAPERT() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.iValidateDataSendingBankAsBeneficiaryBankInCDCFFAPERT();
	}
	
	@Test
	public void testBeneficiaryAsSubsidiaryBankCIBFFCOMPL() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.iValidateDataSendingBankAsBeneficiaryBankInCIBFFCOMPL();
	}
	
	@Test
	public void testBeneficiaryAsSubsidiaryBankCIBFFLGFND() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.setAmountAndCurrency("925.00 EUR");
		transactionStatusSteps.iValidateDataSendingBankAsBeneficiaryForFoundingBankInCIBFFLGFND();
	}
	
	@Test
	public void testBeneficiaryAsSubsidiaryBankCIBFFMENSA() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.iValidateDataSendingBankAsBeneficiaryBankInCIBFFMENSA();
	}
	
	@Test
	public void testGenericBankUsdInCDCFFAPERT() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.iValidateDataSendingBankByGenericBankInFile("CDCFFAPERT");
	}
	
	@Test
	public void testGenericBankUsdInCIBFFCOMPL() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.iValidateDataSendingBankByGenericBankInFile("CIBFFCOMPL");
	}
	
	@Test
	public void testGenericBankUsdInCIBFFLGFND() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.setAmountAndCurrency("598.00 MXN");
		transactionStatusSteps.iValidateDataSendingBankByGenericBankInFile("CIBFFLGFND");
	}
	
	@Test
	public void testGenericBankUsdInCIBFFMENSA() {
		transactionStatusSteps.setCustomName(customName);
		transactionStatusSteps.setShippingNumber(shippingNumber);
		transactionStatusSteps.iValidateDataSendingBankByGenericBankInFile("CIBFFMENSA");
	}
}
