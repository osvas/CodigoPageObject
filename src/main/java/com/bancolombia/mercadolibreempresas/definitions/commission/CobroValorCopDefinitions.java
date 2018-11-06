package com.bancolombia.mercadolibreempresas.definitions.commission;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.steps.sending.SendingCurrencySteps;

import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class CobroValorCopDefinitions {
	@Steps
	private SendingCurrencySteps sending;

	@Then("^valido el valor cop de la divisa \"([^\"]*)\"$")
	public void iCheckTheCOPValue(String currency, List<EvidenceData> evidence) {
		sending.iCheckTheCOPValue(currency, evidence);
	}
}
