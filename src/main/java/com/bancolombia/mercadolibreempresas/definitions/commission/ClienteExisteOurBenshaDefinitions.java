package com.bancolombia.mercadolibreempresas.definitions.commission;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.steps.sending.SendingCurrencySteps;
import com.bancolombia.mercadolibreempresas.steps.sending.TransactionStatusSteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class ClienteExisteOurBenshaDefinitions {
	@Steps
	private TransactionStatusSteps transactionSteps;

	@Steps
	SendingCurrencySteps sending;

	@When("^Preparo los datos cliente existe para la comision tarifa \"([^\"]*)\" y divisa \"([^\"]*)\"$")
	public void iPrepareTheDataForTheCommissionRate(String tarifa, String currency, List<EvidenceData> evidence) {
		sending.iPrepareTheDataForTheCommissionRate(tarifa, currency, evidence);
	}

	@When("^Valido la comision \"([^\"]*)\" de tarifa \"([^\"]*)\"$")
	public void iValidTheCommission(String commission, String tarifa, List<EvidenceData> evidence) {
		sending.iValidTheCommission(commission, tarifa, evidence);
	}

	@When("^Verifico que los cobros de la comision \"([^\"]*)\" y tarifa \"([^\"]*)\" sean los correctos para el envio$")
	public void verificoQueLosCobrosDeLaComisionYTarifaSeanLosCorrectosParaElEnvio(String commission, String tarifa,
			List<EvidenceData> evidence) {
		sending.verificarCobroComisionYTarifa(commission, tarifa, evidence);
	}

	@Then("^Valido el valor para la comision \"([^\"]*)\", tarifa \"([^\"]*)\" y divisa \"([^\"]*)\"$")
	public void validoElValorParaLaComisionTarifaYDivisa(String commission, String tarifa, String currency,
			List<EvidenceData> evidence) {
		transactionSteps.validoElValorParaLaComisionTarifaYDivisa(commission, tarifa, currency, evidence);
	}

	@Then("^Valido archivo cdcffcomis para comision \"([^\"]*)\", tarifa \"([^\"]*)\" y divisa \"([^\"]*)\"$")
	public void validoArchivoCdcffcomisParaComisionTarifaYDivisa(String commission, String tarifa, String currency,
			List<EvidenceData> evidence) {
		transactionSteps.validoArchivoCdcffcomisParaComisionTarifaYDivisa(commission, tarifa, currency, evidence);
	}
}
