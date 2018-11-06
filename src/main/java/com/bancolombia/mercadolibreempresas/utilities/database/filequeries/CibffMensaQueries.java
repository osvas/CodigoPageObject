package com.bancolombia.mercadolibreempresas.utilities.database.filequeries;

import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class CibffMensaQueries {
	private BasicDao basicDao;

	public CibffMensaQueries() {
		this.basicDao = new BasicDao();
	}

	/**
	 * Método que retorna el código swift de la tabla CIBFFMENSA
	 * 
	 * @param rate
	 *            valor de la tasa negociada
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String getSwiftCodeInCIBFFMENSA(String shippingReference, String testCaseName) {
		String[] swiftCode = basicDao.searchDataFieldsCondition("CONTENIDO", "CIBLIBRAMD", "CIBFFMENSA",
				"NROCRED = '" + shippingReference + "' AND CAMPO = 57", testCaseName);

		return swiftCode[0];
	}

	public String[] searchInCibffMensaByField(String nrocred, int field) {
		return basicDao.searchDataFieldsCondition("TIPOMSG,NROCRED,CAMPO,SECUENCIA,CONTENIDO", "CIBLIBRAMD",
				"CIBFFMENSA", "TIPOMSG = '103' AND NROCRED = '" + Util.addingNumbersToValue(nrocred, 12, "0")
						+ "' AND CAMPO = '" + field + "'",
				"Consulta archivo cibffmensa");
	}

}
