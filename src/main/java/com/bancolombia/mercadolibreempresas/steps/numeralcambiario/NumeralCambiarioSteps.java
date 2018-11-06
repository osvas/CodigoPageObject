package com.bancolombia.mercadolibreempresas.steps.numeralcambiario;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.NumeralCambiarioQueries;

public class NumeralCambiarioSteps {

	public void preparoLosDatosParaCobroDeGMF(String cobro, String globalCurrency, String numeroCuenta,
			String numeralCambiario, List<EvidenceData> evidence) {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		String nit = PropertiesReader.getInstance().getProperty("user.nit");
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + globalCurrency.toUpperCase();
		Util.loggerInformation("info",
				"<-------------Preparación de los datos para el cobro o no cobro GMF------------->", testCaseName);

		NumeralCambiarioQueries numeralQueries = new NumeralCambiarioQueries();
		if (cobro.equals("SI")) {
			// Como se va a hacer cobro entonces se debe de poner que la cuenta no es del
			// mismo titular
			numeralQueries.updateMismoTitularCuenta("2", numeroCuenta, "", testCaseName);
			// Se remueven los numerales cambiarios para que garantizar el cobro GMF
			numeralQueries.updateNumeralesCambiariosDelPagen("", testCaseName);
			Util.loggerInformation("info", "Se actualizó la cuenta para hacer el cobro del GMF.", testCaseName);
		} else {
			// Como no se va a hacer cobro entonces se pone la cuenta como del mismo titular
			numeralQueries.updateMismoTitularCuenta("1", numeroCuenta, Util.addingNumbersToValue(nit, 15, "0"),
					testCaseName);
			// Se debe de agregar el numeral cambiario a PAGEN ya que es obligatorio que
			// esté ahí para que no se haga cobro
			numeralQueries.updateNumeralesCambiariosDelPagen(numeralCambiario, testCaseName);
			Util.loggerInformation("info",
					"Numeral cambiario que será agregado al CIBFFPAGEN para quedar excento: " + numeralCambiario,
					testCaseName);
			Util.loggerInformation("info", "Se actualizó la cuenta para no hacer el cobro del GMF.", testCaseName);

		}

		Util.loggerInformation("info", "Numerales cambiarios registrados en el CIBFFPAGEN para ser excentos: "
				+ numeralQueries.getNumeralesExcentosEnCIBFFPAGEN(testCaseName), testCaseName);
	}

}
