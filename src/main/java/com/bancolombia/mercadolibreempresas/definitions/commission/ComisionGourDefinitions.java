package com.bancolombia.mercadolibreempresas.definitions.commission;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.steps.commission.ComisionGourSteps;
import com.bancolombia.mercadolibreempresas.steps.sending.SendingCurrencySteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class ComisionGourDefinitions {
	@Steps
	ComisionGourSteps gourSteps;

	@Steps
	SendingCurrencySteps sending;

	@When("^Preparo los datos comision GOUR para la divisa \"([^\"]*)\" y el parametro COMNEGGOUR \"([^\"]*)\"$")
	public void preparoLosDatosComisionGOURParaLaDivisaYElParametroCOMNEGGOUR(String currency, String estadoParametro,
			List<EvidenceData> evidence) {
		sending.preparoLosDatosComisionGOURParaLaDivisaYElParametroCOMNEGGOUR(currency, estadoParametro, evidence);
	}

	@When("^Valido la comision con el parametro COMNEGGOUR \"([^\"]*)\"$")
	public void validoLaComisionConElParametroCOMNEGGOUR(String estadoComneggour, List<EvidenceData> evidence) {
		sending.iValidTheCommission("GOUR", estadoComneggour, evidence);
	}

	@Then("^Verifico la comision con el parametro COMNGGOUR \"([^\"]*)\"$")
	public void verificoLaComisionConElParametroCOMNEGGOUR(String estadoComneggour, List<EvidenceData> evidence) {
		sending.verificarCobroComisionYTarifa("GOUR", estadoComneggour, evidence);
	}
}
