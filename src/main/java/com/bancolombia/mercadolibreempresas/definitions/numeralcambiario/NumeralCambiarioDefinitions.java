package com.bancolombia.mercadolibreempresas.definitions.numeralcambiario;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.steps.sending.SendingCurrencySteps;
import com.bancolombia.mercadolibreempresas.steps.sending.TransactionStatusSteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class NumeralCambiarioDefinitions {

	@Steps
	SendingCurrencySteps sendingSteps;

	@Steps
	private TransactionStatusSteps transactionSteps;

	@When("^Preparo los datos para cobro de GMF \"([^\"]*)\"$")
	public void preparoLosDatosParaCobroDeGMF(String cobro, List<EvidenceData> evidence) {
		sendingSteps.preparoLosDatosParaCobroDeGMF(cobro.toUpperCase(), evidence);
	}

	@Then("^Valido el archivo cibffintog donde \"([^\"]*)\" se cobra el GMF con la divisa \"([^\"]*)\"$")
	public void validoElArchivoCibffintogDondeSeCobraElGMFConLaDivisa(String cobro, String currency,
			List<EvidenceData> evidence) {
		transactionSteps.validoElArchivoCibffintogDondeSeCobraElGMFConLaDivisa(cobro.toUpperCase(), currency, evidence);
	}
}
