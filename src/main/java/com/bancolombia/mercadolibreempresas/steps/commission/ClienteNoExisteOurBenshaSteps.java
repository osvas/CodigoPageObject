package com.bancolombia.mercadolibreempresas.steps.commission;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fluentlenium.core.annotation.Page;

import com.bancolombia.mercadolibreempresas.models.DatosFinalesTransaccion;
import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.models.SecondScreen;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.SecondScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;

public class ClienteNoExisteOurBenshaSteps {

	CommissionQueries commissionQuery;

	@Page
	SecondScreenPage secondScreen;

	public ClienteNoExisteOurBenshaSteps() {
		commissionQuery = new CommissionQueries();
		secondScreen = new SecondScreenPage();
	}

	public void PreparoLosDatosClienteNoExisteParaLaDivisa(String currency, String rebate, String accountNumber,
			List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		Util.loggerInformation("info",
				"<--------Preparación de los datos para cliente en central de comisiones y comisión OUR-BEN/SHA-------->",
				testCaseName);

		commissionQuery.deleteUserInTheCentralCommission(testCaseName);

		// Se obtiene el código swift del banco beneficiario
		List<List<String>> swiftCodeBeneficiaryBank = commissionQuery.getSwiftCodeBeneficiaryBank(testCaseName);

		// Se obtiene el listado de códigos swift del pagen
		List<List<String>> swiftCodesFromPagen = commissionQuery.getSwiftCodesFromCibffpagen(testCaseName);

		// Se revisa si existe el código del beneficiario en el listado del pagen
		for (List<String> listPagen : swiftCodesFromPagen) {
			for (String swiftCodePagen : listPagen) {
				for (List<String> listBeneficiary : swiftCodeBeneficiaryBank) {
					for (String swiftCodeBeneficiary : listBeneficiary) {
						// Se remueve el código del beneficiario del pagen si existe.
						if (swiftCodePagen.trim().equals(swiftCodeBeneficiary.trim())) {
							Util.loggerInformation("info",
									"Código swift del pagen que se va a remover para evitar que sea filial: "
											+ swiftCodePagen.trim(),
									testCaseName);
							commissionQuery.updateCibffpagen(swiftCodeBeneficiary.trim(), "PPPPPPPPPPP",
									"Envio banco de fondeo con multiples tasas");
						}
					}
				}
			}
		}

		commissionQuery.updateRebat(rebate, testCaseName);
	}

	public void validoParaElClienteNoExisteLaComisionDeTarifa(SecondScreen commissionScreen, String commission,
			String currency, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		double tarifaPlena;
		double trm = commissionQuery.searchValueTrmToday(testCaseName);
		String rebat = commissionQuery.getRebat(testCaseName);

		if (rebat.toUpperCase().equals("OFF") || commission.toUpperCase().equals("OUR")) {
			tarifaPlena = commissionQuery.getTarifaPlenaUsuarioGenerico(testCaseName);
		} else {
			tarifaPlena = commissionQuery.getTarifaPlenaParametroComnbensha(testCaseName);
		}

		Util.loggerInformation("info",
				"Valor de la tarifa plena en el front: " + Double.parseDouble(commissionScreen.getCommissionTarifa())
						+ ", valor de la tarifa en el back: " + tarifaPlena,
				testCaseName);

		assertTrue("", Double.parseDouble(commissionScreen.getCommissionTarifa()) == tarifaPlena);

		double commissionWithTrm = Double.parseDouble(commissionScreen.getCommissionTarifa()) * trm;
		String commissionFront = Util.quitarCaracteresACadenasConValores(commissionScreen.getCommission());
		Util.loggerInformation("info", "valor de la comisión en el front: " + commissionFront
				+ ", Valor de la comisión esperado: " + commissionWithTrm, testCaseName);
		assertTrue("", Double.parseDouble(commissionFront) == commissionWithTrm);
	}

	public void verificoQueLosCobrosClienteNoExisteDeLaComisionYTarifaSeanLosCorrectosParaElEnvio(
			DatosFinalesTransaccion finalScreen, String commission, String currency, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		double tarifaPlena;
		double trm = commissionQuery.searchValueTrmToday(testCaseName);
		String rebat = commissionQuery.getRebat(testCaseName);

		if (rebat.toUpperCase().equals("OFF") || commission.toUpperCase().equals("OUR")) {
			tarifaPlena = commissionQuery.getTarifaPlenaUsuarioGenerico(testCaseName);
		} else {
			tarifaPlena = commissionQuery.getTarifaPlenaParametroComnbensha(testCaseName);
		}

		// Se obtiene el valor de la comisión en pesos
		double comisionEnPesos = tarifaPlena * trm;
		// Se obtiene la comisión en pesos del front para luego compararla con el valor
		// obtenido del backend
		String[] arrayComisionEnPesosFront = finalScreen.getComisionDeEnvio().split("\\$");
		String comisionEnPesosFront = arrayComisionEnPesosFront[1];
		comisionEnPesosFront = Util.quitarCaracteresACadenasConValores(comisionEnPesosFront);

		Util.loggerInformation("info", "Valor de la comisión encontrada en el front: " + comisionEnPesosFront
				+ ", Valor esperado de la comisión: " + comisionEnPesos, testCaseName);

		assertTrue("", Double.parseDouble(comisionEnPesosFront) == comisionEnPesos);

		// Se obtiene el valor del iva de la comisión
		double ivaComision = comisionEnPesos * commissionQuery.getIva(testCaseName);
		// Valor total de la comisión con el IVA incluido
		double totalComision = ivaComision + comisionEnPesos;

		Util.loggerInformation("info", "Valor del iva: " + (commissionQuery.getIva(testCaseName) * 100) + "%",
				testCaseName);
		Util.loggerInformation("info", "Valor de la comisión con iva incluído: " + ivaComision, testCaseName);

		// Se obtiene el monto que se está enviando al beneficiario
		double montoEnvio = Double.parseDouble(Util.quitarCaracteresACadenasConValores(finalScreen.getMontoYMoneda()));

		double valueInCOP;
		if (currency.toUpperCase().equals("USD")) {
			// Si la divisa es dólar, entonces se multiplica la tasa por el monto que se
			// está enviando
			valueInCOP = Double.parseDouble(Util.quitarCaracteresACadenasConValores(finalScreen.getTasa()))
					* montoEnvio;
		} else {
			// Si la divisa es diferente a dólar, entonces se divide el monto que se está
			// enviando entre el factor de la tasa
			valueInCOP = montoEnvio
					/ Double.parseDouble(Util.quitarCaracteresACadenasConValores(finalScreen.getFactor()));
			// Al valor anterio obtenido se le redondea a dos decimales y se multiplica por
			// el valor de la tasa pactada
			valueInCOP = Util.redondearDecimales(valueInCOP, 2)
					* Double.parseDouble(Util.quitarCaracteresACadenasConValores(finalScreen.getTasa()));
		}

		// Se finaliza el valor redondeando el número por encima o por debajo según los
		// decimales que tenga, (la idea es dejar que los decimales queden .00)
		valueInCOP = Util.fijarNumero(valueInCOP, 0);

		Util.loggerInformation("info",
				"Monto en pesos del valor a enviar: $"
						+ Double.parseDouble(
								Util.quitarCaracteresACadenasConValores(finalScreen.getMontoEnPesosValorAEnviar()))
						+ ", Monto esperado a enviar: $" + valueInCOP,
				testCaseName);

		assertTrue(Double.parseDouble(
				Util.quitarCaracteresACadenasConValores(finalScreen.getMontoEnPesosValorAEnviar())) == valueInCOP);

		// Se calcula en monto total que se va a debitar de la cuenta del beneficiario y
		// se redondea por encima o por debajo según los decimales
		double montoTotalADebitar = Util.fijarNumero(totalComision + valueInCOP, 0);

		Util.loggerInformation("info",
				"Monto total a debitar del front: $"
						+ Double.parseDouble(Util.quitarCaracteresACadenasConValores(
								Util.quitarCaracteresACadenasConValores(finalScreen.getMontoTotalADebitar())))
						+ ", Monto total esperado para debitar: $" + montoTotalADebitar,
				testCaseName);

		assertTrue(Double.parseDouble(
				Util.quitarCaracteresACadenasConValores(finalScreen.getMontoTotalADebitar())) == montoTotalADebitar);
	}

}
