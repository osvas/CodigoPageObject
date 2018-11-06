package com.bancolombia.mercadolibreempresas.steps.files;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.DatosFinalesTransaccion;
import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CDCFFCOMISQueries;

public class CDCFFCOMIS {

	public void validoArchivoCdcffcomisParaComisionTarifaYDivisa(String commission, String tarifa, String currency,
			DatosFinalesTransaccion datosFinales, List<EvidenceData> evidence) {
		CDCFFCOMISQueries comis = new CDCFFCOMISQueries();
		CommissionQueries commissionQuery = new CommissionQueries();
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + currency.toUpperCase();
		Util.loggerInformation("info", "<-----------------Validación archivo CDCFFCOMIS----------------->",
				testCaseName);

		double debito = Double
				.parseDouble(comis.getDebitoComisionConIva(datosFinales.getReferenciaDeEnvio(), testCaseName));

		double trm = Double.parseDouble(comis.getTRM(datosFinales.getReferenciaDeEnvio(), testCaseName));

		double trmBackend = commissionQuery.searchValueTrmToday(testCaseName);

		double valueTarifa = 0.0;

		if (!commission.equals("GOUR")) {
			switch (tarifa.toLowerCase()) {
			case "plena":
				valueTarifa = commissionQuery.getTarifaPlena(testCaseName);
				break;
			case "especial":
				valueTarifa = commissionQuery.getTarifaSpecial(testCaseName);
				break;
			case "minima":
				valueTarifa = commissionQuery.getTarifaMinima(testCaseName);
				break;
			case "especial2":
				valueTarifa = commissionQuery.getTarifaSpecial(testCaseName);
				break;
			}
		} else {
			// Se obtiene el valor de la tarifa plena de la comisión dependiendo de su
			// estado (activo, inactivo)
			if (tarifa.toLowerCase().equals("activo")) {
				valueTarifa = commissionQuery.getTarifaPlenaParametroCOMNEGGOUR(testCaseName);
			} else {
				valueTarifa = commissionQuery.getTarifaPlenaUsuarioGenerico(testCaseName);
			}
		}

		double comisionEnPesos = valueTarifa * trmBackend;
		double ivaComision = comisionEnPesos * commissionQuery.getIva(testCaseName);
		double totalComision = ivaComision + comisionEnPesos;
		String[] textIvaFront = datosFinales.getComisionDeEnvio().split("\\$");
		double ivaFront = Double.parseDouble(Util.quitarCaracteresACadenasConValores(textIvaFront[1]));

		Util.loggerInformation("info",
				"Valor de la comisión sin iva: $" + ivaFront + ". Valor de la comisión en el archivo CDCFFCOMIS: $"
						+ debito + ". Valor total de la comisión esperado: $" + Util.fijarNumero(totalComision, 0),
				testCaseName);

		Util.loggerInformation("info",
				"TRM en el archivo CDCFFCOMIS: $" + trm + ". Valor de la TRM esperada es: $" + trmBackend,
				testCaseName);

		assertTrue(debito == Util.fijarNumero(totalComision, 0));
		assertTrue(trmBackend == trm);
	}

}
