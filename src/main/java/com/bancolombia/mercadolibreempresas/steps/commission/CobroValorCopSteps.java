package com.bancolombia.mercadolibreempresas.steps.commission;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.utilities.Util;

public class CobroValorCopSteps {

	public void iCheckTheCOPValue(double valorDoubleTasa, double valorDoubleFront, double valorDoubleFactor,
			String currency, String amountAndCurrency, List<EvidenceData> evidence) {
		double valueInCOP;
		if (currency.toUpperCase().equals("USD")) {
			valueInCOP = valorDoubleTasa * Double.parseDouble(amountAndCurrency);
		} else {
			valueInCOP = Double.parseDouble(amountAndCurrency) / valorDoubleFactor;
			valueInCOP = Util.redondearDecimales(valueInCOP, 2) * valorDoubleTasa;
		}

		valueInCOP = Util.fijarNumero(valueInCOP, 0);

		Util.loggerInformation("info", "Valor esperado: " + valueInCOP + " ||| Valor obtenido: " + valorDoubleFront,
				evidence.get(0).getNameOfDirectory() + " " + currency);
		assertTrue("Los valores no concuerdan ya que no da el mismo precio del valor en COP. Valor esperado: "
				+ valueInCOP + ". Valor obtenido: " + valorDoubleFront, valueInCOP == valorDoubleFront);
	}

}
