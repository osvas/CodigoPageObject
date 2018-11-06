package com.bancolombia.mercadolibreempresas.steps.files;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.DatosFinalesTransaccion;
import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.NumeralCambiarioQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CIBFFINTOGQueries;

public class CIBFFINTOG {

	public void validoElArchivoCibffintogDondeSeCobraElGMFConLaDivisa(DatosFinalesTransaccion datosFinales,
			String cobro, String currency, List<EvidenceData> evidence) {
		NumeralCambiarioQueries numeralQueries = new NumeralCambiarioQueries();
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency;
		Util.loggerInformation("info",
				"<-------------Validación en el archivo CIBFFINTOG para saber si hubo cobro del GMF------------->",
				testCaseName);
		CIBFFINTOGQueries intogQueries = new CIBFFINTOGQueries();

		String idUsuarioEnElExterior = intogQueries.getIdentificacionEnElExterior(datosFinales.getReferenciaDeEnvio(),
				testCaseName);
		String idUsuarioCuentaBeneficiario = numeralQueries
				.getIdTitularDeLaCuenta(datosFinales.getCuentaDelBeneficiario(), testCaseName);
		Util.loggerInformation("info",
				"Identificación del beneficiario de la cuenta: " + idUsuarioCuentaBeneficiario.trim()
						+ ", Identificación del usuario en el archivo CIBFFINTOG: " + idUsuarioEnElExterior.trim(),
				testCaseName);

		if (cobro.equals("SI")) {
			assertTrue("No se le hizo el cobro del GMF al cliente", idUsuarioEnElExterior.equals("000000000000000"));
		} else {
			assertTrue("Se le hizo el cobro del GMF al cliente", idUsuarioEnElExterior.trim()
					.equals(Util.addingNumbersToValue(idUsuarioCuentaBeneficiario.trim(), 15, "0")));
		}
	}

}
