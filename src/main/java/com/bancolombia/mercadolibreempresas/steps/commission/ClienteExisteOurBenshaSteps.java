package com.bancolombia.mercadolibreempresas.steps.commission;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fluentlenium.core.annotation.Page;

import com.bancolombia.mercadolibreempresas.models.DatosFinalesTransaccion;
import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.models.SecondScreen;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.SecondScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;

public class ClienteExisteOurBenshaSteps {
	CommissionQueries commissionQuery;

	@Page
	SecondScreenPage secondScreen;

	public ClienteExisteOurBenshaSteps() {
		commissionQuery = new CommissionQueries();
		secondScreen = new SecondScreenPage();
	}

	public void iPrepareTheDataForTheCommissionRate(String tarifa, String currency, String accountNumber,
			List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		Util.loggerInformation("info",
				"<--------Preparación de los datos para cliente en central de comisiones y comisión OUR-BEN/SHA-------->",
				testCaseName);
		// se busca el usuario en la central de comisiones
		List<List<String>> userData = commissionQuery.searchUserInTheCentralCommission(testCaseName);

		// Si el usuario no existe entonces se creará un registro para que exista en la
		// central de comisiones
		if (userData.size() < 1) {
			commissionQuery.insertUserInTheCentralCommission(testCaseName);
		}

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

		// Se alistan los datos para el cobro de la comisión según se necesite en la
		// prueba.
		switch (tarifa.toLowerCase()) {
		case "plena":
			commissionQuery.updateTarifaInTheCentralCommission("10", "35", "20", testCaseName);
			break;
		case "especial":
			commissionQuery.updateTarifaInTheCentralCommission("20", "15", "10", testCaseName);
			break;
		case "minima":
			commissionQuery.updateTarifaInTheCentralCommission("20", "28", "30", testCaseName);
			break;
		case "especial2":
			commissionQuery.updateTarifaInTheCentralCommission("30", "28", "28", testCaseName);
			break;
		}
	}

	public void validoElValorParaLaComisionTarifaYDivisa(String tarifa, String currency, List<EvidenceData> evidence) {
		RatesQueries rates = new RatesQueries();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		String nit = PropertiesReader.getInstance().getProperty("user.nit");

		String amountTasa = rates.getTasaOfTheRate("USD", nit,
				evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase());
		amountTasa = amountTasa.replace("$", "");

		String textAmountInPesosFront, textAmountFactor;
		if (currency.toUpperCase().equals("USD")) {
			textAmountInPesosFront = secondScreen.totalAmountInPesos.getText();
			textAmountFactor = secondScreen.factorValue.getText();
		} else {
			textAmountInPesosFront = secondScreen.totalAmountInPesos2.getText();
			textAmountFactor = secondScreen.factorValue.getText();
		}
		textAmountInPesosFront = textAmountInPesosFront.replace(",", "");
		textAmountInPesosFront = textAmountInPesosFront.replace("$", "");
		textAmountInPesosFront = textAmountInPesosFront.replace(" ", "");

		textAmountFactor = textAmountFactor.replace(",", "");
		textAmountFactor = textAmountFactor.replace("$", "");
		textAmountFactor = textAmountFactor.replace(" ", "");

		// double valorDoubleTasa =
		// Util.redondearDecimales(Double.parseDouble(amountTasa), 2);
		// double valorDoubleFront =
		// Util.redondearDecimales(Double.parseDouble(textAmountInPesosFront), 2);
		// double valorDoubleFactor = Double.parseDouble(textAmountFactor);

	}

	public double calcularValorTarifa(String tarifa, List<EvidenceData> evidence, String currency) {
		double valueTarifa = 0.0;

		switch (tarifa.toLowerCase()) {
		case "plena":
			valueTarifa = commissionQuery.getTarifaPlena(evidence.get(0).getNameOfDirectory() + " " + currency);
			break;
		case "especial":
			valueTarifa = commissionQuery.getTarifaSpecial(evidence.get(0).getNameOfDirectory() + " " + currency);
			break;
		case "minima":
			valueTarifa = commissionQuery.getTarifaMinima(evidence.get(0).getNameOfDirectory() + " " + currency);
			break;
		case "especial2":
			valueTarifa = commissionQuery.getTarifaSpecial(evidence.get(0).getNameOfDirectory() + " " + currency);
			break;
		}

		return valueTarifa;
	}

	public void iValidTheCommissionSecondScreen(SecondScreen commissionScreen, String currency, String tarifa,
			List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		double valueTarifa = calcularValorTarifa(tarifa, evidence, currency);

		double trm = commissionQuery.searchValueTrmToday(testCaseName);

		Util.loggerInformation("info",
				"Valor de la tarifa " + tarifa + " en el front: "
						+ Double.parseDouble(commissionScreen.getCommissionTarifa())
						+ ", valor de la tarifa en el back: " + valueTarifa,
				testCaseName);
		assertTrue("", Double.parseDouble(commissionScreen.getCommissionTarifa()) == valueTarifa);

		double commission = Double.parseDouble(commissionScreen.getCommissionTarifa()) * trm;
		String commissionFront = Util.quitarCaracteresACadenasConValores(commissionScreen.getCommission());
		Util.loggerInformation("info", "valor de la comisión en el front: " + commissionFront
				+ ", Valor de la comisión esperado: " + commission, testCaseName);
		assertTrue("", Double.parseDouble(commissionFront) == commission);
	}

	/**
	 * Método que verifica el cobro de la comisión al cliente en la cuarta pantalla
	 * 
	 * @param finalScreen
	 *            modelo de la cuarta pantalla para obtener los datos del front y
	 *            poder hacer los cálculos
	 * @param commission
	 *            tipo de comisión con la que se está trabajando (OUR, BEN/SHA)
	 * @param tarifa
	 *            tipo de tarifa que se está aplicando al cliente (plena, especial,
	 *            minima, especial2)
	 * @param currency
	 *            divisa con la cual se está realizando la prueba
	 * @param evidence
	 *            lista con el nombre de la carpeta donde se guardará la evidencia
	 *            tomada.
	 */
	public void verificarCobroComisionYTarifa(DatosFinalesTransaccion finalScreen, String commission, String tarifa,
			String currency, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		// Se obtiene el valor de la tarifa según sea el caso plena, especial o mínima
		double valueTarifa = calcularValorTarifa(tarifa, evidence, currency);

		// Se obtiene el valor de la trm
		double trm = commissionQuery.searchValueTrmToday(testCaseName);

		// Se obtiene el valor de la comisión en pesos
		double comisionEnPesos = valueTarifa * trm;
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

	public void validoElValorParaLaComisionTarifaYDivisa(DatosFinalesTransaccion datosFinales, String tarifa,
			String currency, String commission, List<EvidenceData> evidence) {
		verificarCobroComisionYTarifa(datosFinales, commission, tarifa, currency, evidence);
	}

}
