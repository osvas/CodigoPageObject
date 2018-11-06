package com.bancolombia.mercadolibreempresas.steps.commission;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fluentlenium.core.annotation.Page;

import com.bancolombia.mercadolibreempresas.models.DatosFinalesTransaccion;
import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.models.SecondScreen;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.SecondScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.bancofondeo.SetUpFundingBankQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;

public class ComisionGourSteps {
	CommissionQueries commissionQuery;
	Util util;

	@Page
	SecondScreenPage secondScreen;

	public ComisionGourSteps() {
		commissionQuery = new CommissionQueries();
		secondScreen = new SecondScreenPage();
		util = new Util();
	}

	/**
	 * Método que prepara los datos para realizar un envío con la comisión GOUR, se
	 * pone como banco corresponsal el banco del beneficiario, el rebate se pone en
	 * ONN, y se agrega en en CIBFFCDGOS el banco. Todos son datos necesarios para
	 * que se haga el cobro de la comisión.
	 * 
	 * @param currency
	 *            divisa que se está usando para la prueba
	 * @param estadoParametro
	 *            estado que tiene el parámetro COMNEGGOUR (activo - inactivo)
	 * @param accountNumber
	 *            número de la cuenta a la cual se va a enviar el giro
	 * @param evidence
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void preparoLosDatosComisionGOURParaLaDivisaYElParametroCOMNEGGOUR(String currency, String estadoParametro,
			String accountNumber, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		Util.loggerInformation("info",
				"<--------Preparación de los datos para comisión GOUR y banco corresponsal el de la tasa-------->",
				testCaseName);
		SetUpFundingBankQueries queries = new SetUpFundingBankQueries();

		// Se obtiene el código swift del beneficiario
		String codigoSwiftBeneficiario = queries.getSwiftCodeBeneficiaryBank(accountNumber, testCaseName);
		Util.loggerInformation("info", "Swift code del banco beneficiario: " + codigoSwiftBeneficiario, testCaseName);

		// Se obtiene el listado de códigos swift del pagen
		List<List<String>> swiftCodesFromPagen = commissionQuery.getSwiftCodesFromCibffpagen(testCaseName);

		String swiftCodeBeneficiaryBankPagen = "";
		boolean swiftCodeFound = false;
		// Se recorre el listado de códigos swift del pagen para revisar si se encuentra
		// el del beneficiario
		for (List<String> list : swiftCodesFromPagen) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).trim().equals(codigoSwiftBeneficiario.trim())) {
					swiftCodeFound = true;
				} else {
					swiftCodeBeneficiaryBankPagen = list.get(i).trim();
				}
			}
		}

		// Si no se encuentra el código swift del beneficiario entonces se agrega a la
		// lista.
		if (!swiftCodeFound) {
			swiftCodeBeneficiaryBankPagen = swiftCodesFromPagen.get(0).get(0).trim();
			queries.updateCibffpagen(swiftCodeBeneficiaryBankPagen.trim(), codigoSwiftBeneficiario.trim(),
					testCaseName);
		}

		// Se pone el Rebate en "ONN"
		commissionQuery.updateRebat("ONN", testCaseName);

		// Se obtiene el código de la moneda con la cual se está haciendo el envío
		String codigoDivisa = commissionQuery.getCurrencyCodeFromCIBFFMONMD(currency, testCaseName);
		if (Integer.parseInt(codigoDivisa) >= 1 && Integer.parseInt(codigoDivisa) <= 9) {
			codigoDivisa = Util.addingNumbersToValue(codigoDivisa, 2, "0");
		}
		// Se obtiene el código del banco beneficiario
		String codigoDelBanco = commissionQuery.getCodeBankFromCIBFFBANCO(testCaseName,
				codigoSwiftBeneficiario.substring(0, 8) + "X" + codigoSwiftBeneficiario.substring(8));
		Util.loggerInformation("info",
				"código del banco y la divisa que se debe de encontrar en el archivo CDCFFCDGOS: " + codigoDelBanco
						+ codigoDivisa,
				testCaseName);

		// Se busca si en el archivo CDCFFCDGOS ya existe el registro de ese banco con
		// esa moneda
		List<List<String>> codigoBancoCdgos = commissionQuery.getCodeVGourFromCDCFFCDGOS(codigoDelBanco + codigoDivisa,
				testCaseName);
		// Si no se encuentran registros entonces se crea uno
		if (codigoBancoCdgos.isEmpty()) {
			Util.loggerInformation("info",
					"Se agrega el código del banco y la moneda al archivo CDCFFCDGOS para que la condición del GOUR se cumpla.",
					testCaseName);
			commissionQuery.insertCodeVGourIntoCDCFFCDGOS(codigoDelBanco + codigoDivisa, testCaseName);
		}

		// Se captura el parámetro si está activo o no para agregarlo o quitarlo según
		// sea el caso de prueba
		switch (estadoParametro.toLowerCase()) {
		case "activo":
			List<List<String>> datosDelParametro = commissionQuery.getParametroCOMNEGGOUR(testCaseName);
			if (datosDelParametro.isEmpty()) {
				commissionQuery.insertParametroCOMNEGGOUR(testCaseName);
			}
			break;

		default:
			commissionQuery.deleteParametroCOMNEGGOUR(testCaseName);
			break;
		}
	}

	/**
	 * Método que valida la segunda pantalla donde se hace el cobro de la comisión
	 * al cliente
	 * 
	 * @param commissionScreen
	 *            valores capturados de la segunda pantalla
	 * @param currency
	 *            divisa con la que se está realizando la prueba
	 * @param estadoComneggour
	 *            estado del parámetro COMNEGGOUR (activo - inactivo)
	 * @param evidence
	 *            nombre del caso de prueba que se está ejecutando
	 */
	public void validoLaComisionConElParametroCOMNEGGOUR(SecondScreen commissionScreen, String currency,
			String estadoComneggour, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		double tarifaPlena;
		double trm = commissionQuery.searchValueTrmToday(testCaseName);

		if (estadoComneggour.toLowerCase().equals("activo")) {
			tarifaPlena = commissionQuery.getTarifaPlenaParametroCOMNEGGOUR(testCaseName);
		} else {
			tarifaPlena = commissionQuery.getTarifaPlenaUsuarioGenerico(testCaseName);
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

	/**
	 * Método que valida los datos de la comisión GOUR en la cuarta pantalla
	 * 
	 * @param finalScreen
	 *            datos capturados de la cuarta pantalla
	 * @param currency
	 *            divisa con la que se está trabajando
	 * @param estadoComneggour
	 *            estado del parámetro COMNEGOUR, (activo, inactivo)
	 * @param evidence
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void verificoLaComisionConElParametroCOMNGGOUR(DatosFinalesTransaccion finalScreen, String currency,
			String estadoComneggour, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		double tarifaPlena;
		double trm = commissionQuery.searchValueTrmToday(testCaseName);

		// Se obtiene el valor de la tarifa plena de la comisión dependiendo de su
		// estado (activo, inactivo)
		if (estadoComneggour.toLowerCase().equals("activo")) {
			tarifaPlena = commissionQuery.getTarifaPlenaParametroCOMNEGGOUR(testCaseName);
		} else {
			tarifaPlena = commissionQuery.getTarifaPlenaUsuarioGenerico(testCaseName);
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

	/**
	 * Método que valida los cobros que se hicieron al cliente cuando ya se ha
	 * aprobado el envío.
	 * 
	 * @param datosFinales
	 *            datos finales capturados en la última pantalla del aprobado
	 * @param currency
	 *            divisa con la cual se está realizando la prueba
	 * @param estadoComneggour
	 *            estado del parámetro COMNEGOUR
	 * @param evidence
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void validoElValorParaLaComisionTarifaYDivisaParametroCOMNGGOUR(DatosFinalesTransaccion datosFinales,
			String currency, String estadoComneggour, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		// Se obtiene el valor de la tarifa según sea el caso plena, especial o mínima
		double valueTarifa;

		// Se obtiene el valor de la tarifa plena de la comisión dependiendo de su
		// estado (activo, inactivo)
		if (estadoComneggour.toLowerCase().equals("activo")) {
			valueTarifa = commissionQuery.getTarifaPlenaParametroCOMNEGGOUR(testCaseName);
		} else {
			valueTarifa = commissionQuery.getTarifaPlenaUsuarioGenerico(testCaseName);
		}

		// Se obtiene el valor de la trm
		double trm = commissionQuery.searchValueTrmToday(testCaseName);

		// Se obtiene el valor de la comisión en pesos
		double comisionEnPesos = valueTarifa * trm;
		// Se obtiene la comisión en pesos del front para luego compararla con el valor
		// obtenido del backend
		String[] arrayComisionEnPesosFront = datosFinales.getComisionDeEnvio().split("\\$");
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
		Util.loggerInformation("info", "Valor del iva de la comisión: " + ivaComision, testCaseName);

		// Se obtiene el monto que se está enviando al beneficiario
		double montoEnvio = Double.parseDouble(Util.quitarCaracteresACadenasConValores(datosFinales.getMontoYMoneda()));

		double valueInCOP;
		if (currency.toUpperCase().equals("USD")) {
			// Si la divisa es dólar, entonces se multiplica la tasa por el monto que se
			// está enviando
			valueInCOP = Double.parseDouble(Util.quitarCaracteresACadenasConValores(datosFinales.getTasa()))
					* montoEnvio;
		} else {
			// Si la divisa es diferente a dólar, entonces se divide el monto que se está
			// enviando entre el factor de la tasa
			valueInCOP = montoEnvio
					/ Double.parseDouble(Util.quitarCaracteresACadenasConValores(datosFinales.getFactor()));
			// Al valor anterio obtenido se le redondea a dos decimales y se multiplica por
			// el valor de la tasa pactada
			valueInCOP = Util.redondearDecimales(valueInCOP, 2)
					* Double.parseDouble(Util.quitarCaracteresACadenasConValores(datosFinales.getTasa()));
		}

		// Se finaliza el valor redondeando el número por encima o por debajo según los
		// decimales que tenga, (la idea es dejar que los decimales queden .00)
		valueInCOP = Util.fijarNumero(valueInCOP, 0);

		Util.loggerInformation("info",
				"Monto en pesos del valor a enviar: $"
						+ Double.parseDouble(
								Util.quitarCaracteresACadenasConValores(datosFinales.getMontoEnPesosValorAEnviar()))
						+ ", Monto esperado a enviar: $" + valueInCOP,
				testCaseName);

		assertTrue(Double.parseDouble(
				Util.quitarCaracteresACadenasConValores(datosFinales.getMontoEnPesosValorAEnviar())) == valueInCOP);

		// Se calcula en monto total que se va a debitar de la cuenta del beneficiario y
		// se redondea por encima o por debajo según los decimales
		double montoTotalADebitar = Util.fijarNumero(totalComision + valueInCOP, 0);

		Util.loggerInformation("info",
				"Monto total a debitar del front: $"
						+ Double.parseDouble(Util.quitarCaracteresACadenasConValores(
								Util.quitarCaracteresACadenasConValores(datosFinales.getMontoTotalADebitar())))
						+ ", Monto total esperado para debitar: $" + montoTotalADebitar,
				testCaseName);

		assertTrue(Double.parseDouble(
				Util.quitarCaracteresACadenasConValores(datosFinales.getMontoTotalADebitar())) == montoTotalADebitar);
	}

}
