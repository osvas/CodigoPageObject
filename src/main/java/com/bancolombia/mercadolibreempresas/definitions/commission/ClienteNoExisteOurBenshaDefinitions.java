package com.bancolombia.mercadolibreempresas.definitions.commission;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.steps.sending.SendingCurrencySteps;
import com.bancolombia.mercadolibreempresas.steps.sending.TransactionStatusSteps;

import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class ClienteNoExisteOurBenshaDefinitions {
	@Steps
	private TransactionStatusSteps transactionSteps;

	@Steps
	SendingCurrencySteps sending;

	@When("^Preparo los datos cliente no existe para la divisa \"([^\"]*)\" y rebate \"([^\"]*)\"$")
	public void PreparoLosDatosClienteNoExisteParaLaDivisa(String currency, String rebate,
			List<EvidenceData> evidence) {
		sending.PreparoLosDatosClienteNoExisteParaLaDivisa(currency.toUpperCase(), rebate.toUpperCase(), evidence);
	}

	@When("^Valido para el cliente no existe la comision \"([^\"]*)\" de tarifa \"([^\"]*)\"$")
	public void validoParaElClienteNoExisteLaComisionDeTarifa(String commission, String tarifa,
			List<EvidenceData> evidence) {
		sending.iValidTheCommission(commission, tarifa, evidence);
	}

	@When("^Verifico que los cobros cliente no existe de la comision \"([^\"]*)\" y tarifa \"([^\"]*)\" sean los correctos para el envio$")
	public void verificoQueLosCobrosClienteNoExisteDeLaComisionYTarifaSeanLosCorrectosParaElEnvio(String commission,
			String tarifa, List<EvidenceData> evidence) {
		sending.verificarCobroComisionYTarifa(commission, tarifa, evidence);
	}
}
