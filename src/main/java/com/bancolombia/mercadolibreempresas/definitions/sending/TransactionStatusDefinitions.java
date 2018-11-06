package com.bancolombia.mercadolibreempresas.definitions.sending;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.steps.sending.TransactionStatusSteps;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.SimonCommissionQueries;

import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class TransactionStatusDefinitions {
	@Steps
	private TransactionStatusSteps transactionSteps;

	@When("^Reviso los datos de la cuenta que voy a aprobar$")
	public void iReviewTheDataOfTheAccountThatIAmGoingToApprove(List<EvidenceData> evidence) {
		transactionSteps.iReviewTheDataOfTheAccountThatIAmGoingToApprove(evidence);
	}

	@When("^Apruebo el envio$")
	public void iApproveTheShipment(List<EvidenceData> evidence) {
		transactionSteps.iApproveTheShipment(evidence);
	}

	@Then("^Valido los datos de fonde en el archivo CDCFFAPERT$")
	public void iValidatedTheDataInTheFile() {
		transactionSteps.iValidatedTheDataInTheFileCDCFFAPERT();
		transactionSteps.iValidatedTheDataInTheFileCIBFFCOMPL();
		transactionSteps.iValidatedTheDataInTheFileCIBFFLGFND();
		// transactionSteps.iValidatedTheDataInTheFileCIBFFMENSA();
	}

	@After("@TerminarEnvio")
	public void restoreData() {
		SimonCommissionQueries commissionQueries = new SimonCommissionQueries();
		commissionQueries.updateBankNoCommission("01", "BKTRUS33XXXX", "Actualizacion codigo swift como filial");
	}

	@After("@TerminarEnvioMultiplesTasasBancoDeFondeo")
	public void restoreDataMultipleRatesFundingBank() {
		/*
		 * SetUpFundingBankQueries queries = new SetUpFundingBankQueries(); String[][]
		 * dataExcel = LeerExcel.leerArchivoExcel(1, 2,
		 * "banco de fondeo multiples tasas", "Codigos Swift CIBFFPAGEN");
		 * 
		 * queries.updateCibffpagen("PPPPPPPPPPPP", dataExcel[0][0],
		 * "Envio banco de fondeo con multiples tasas");
		 * queries.updateCibffpagen("ZZZZZZZZZZZZ", dataExcel[0][1],
		 * "Envio banco de fondeo con multiples tasas");
		 */
	}

	@After("@TerminarEnvioMultiplesTasasBancoDelBeneficiario")
	public void restoreDataMultipleRatesBeneficiaryBank() {
		/*
		 * SetUpBeneficiaryBankQueries queries = new SetUpBeneficiaryBankQueries();
		 * String[][] dataExcel = LeerExcel.leerArchivoExcel(2, 1,
		 * "Banco beneficiario multiples tasas", "Codigos Swift CIBFFPAGEN");
		 * 
		 * queries.updateCibffpagen(dataExcel[1][0], dataExcel[0][0],
		 * "Envio banco de fondeo con multiples tasas");
		 */
	}

	@After("@TerminarEnvioMultiplesTasasBancoGenerico")
	public void restoreDataMultipleRatesGenericBank() {
		/*
		 * SetUpBeneficiaryBankQueries queries = new SetUpBeneficiaryBankQueries();
		 * String[][] dataExcel = LeerExcel.leerArchivoExcel(2, 1,
		 * "Banco beneficiario multiples tasas", "Codigos Swift CIBFFPAGEN");
		 * 
		 * queries.updateCibffpagen(dataExcel[1][0], dataExcel[0][0],
		 * "Envio banco de fondeo con multiples tasas");
		 */
	}

}
