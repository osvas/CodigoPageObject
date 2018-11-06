package com.bancolombia.mercadolibreempresas.definitions.sending;

import com.bancolombia.mercadolibreempresas.steps.sending.SendingBankSteps;
import com.bancolombia.mercadolibreempresas.steps.sending.TransactionStatusSteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class SendingBankDefinitions {
	
	@Steps
	private SendingBankSteps sendingBankSteps;
	
	@Steps
	private TransactionStatusSteps transactionSteps;

	@When("^Establezco banco filial igual al beneficiario para realizar envio para numero comision \"([^\"]*)\"$")
	public void setBeneficiaryBankAsSubsidiaryBank(String bconocomNum) {
		sendingBankSteps.setBeneficiaryBankAsSubsidiary(bconocomNum);
	}
	
	@When("^Establezco banco homologado como banco filial para realizar envio con parametro comision \"([^\"]*)\" y moneda \"([^\"]*)\"$")
	public void setGenericBank(String bconocomNum, String currency) {
		sendingBankSteps.setHomologatedBankAsSubsidiaryByCurrency(currency, bconocomNum);
	}
	
	@When("^Establezco banco de fondeo de tasa para realizar envio para parametro comision \"([^\"]*)\" y moneda \"([^\"]*)\"$")
	public void setFoundingBank(String bconocomNum, String currency) {
		sendingBankSteps.setHomologatedBankAsSendingBank(currency, bconocomNum);
	}
	
	
	@Then("^Valido datos banco de fondeo igual banco beneficiario CDCFFAPERT$")
	public void iValidateDataFoundingBankAsBeneficiaryBankCDCFFAPERT() {
		  transactionSteps.iValidateDataSendingBankAsBeneficiaryBankInCDCFFAPERT();
	}
	
	@Then("^Valido datos banco de fondeo igual banco beneficiario CIBFFCOMPL$")
	public void iValidateDataFoundingBankAsBeneficiaryBankCIBFFCOMPL() {
		  transactionSteps.iValidateDataSendingBankAsBeneficiaryBankInCIBFFCOMPL();
	}
	
	@Then("^Valido datos banco de fondeo igual banco beneficiario CIBFFLGFND$")
	public void iValidateDataFoundingBankAsBeneficiaryBankCIBFFLGFND() {
		  transactionSteps.iValidateDataSendingBankAsBeneficiaryBankInCIBFFLGFND();
	}
	
	@Then("^Valido datos banco de fondeo igual banco beneficiario CIBFFMENSA$")
	public void iValidateDataFoundingBankAsBeneficiaryBankCIBFFMENSA() {
		  transactionSteps.iValidateDataSendingBankAsBeneficiaryBankInCIBFFMENSA();
	}
	
	@Then("^Valido datos banco banco generico en archivo \"([^\"]*)\"$")
	public void iValidateDataFoundingGenericBank(String file) {
		  transactionSteps.iValidateDataSendingBankByGenericBankInFile(file);
	}
}
