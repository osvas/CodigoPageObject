package com.bancolombia.mercadolibreempresas.utilities.database.filequeries;

import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class CibffcomplQueries {
	private BasicDao basicDao;

	public CibffcomplQueries() {
		this.basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
	}

	/**
	 * Método que retorna el código y el subcódigo guardado en la tabla CIBFFLGFND
	 * 
	 * @param rate
	 *            valor de la tasa negociada
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public List<List<String>> getCodeSubcodeInCIBFFCOMPL(String shippingReference, String testCaseName) {
		return basicDao.searchMultipleDataFieldsCondition("CODBCO, SUBCODBCO", "CIBLIBRAMD", "CIBFFCOMPL",
				"NROCRED LIKE '%" + shippingReference + "%' ", testCaseName);
	}

	/**
	 * Método que retorna el código y el subcódigo guardado en la tabla CIBFFLGFND
	 * 
	 * @param rate
	 *            valor de la tasa negociada
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String[] getCodeSubcodeInCIBFFCOMPLUniqueRow(String shippingReference, String testCaseName) {
		return basicDao.searchDataFieldsCondition("CODBCO, SUBCODBCO", "CIBLIBRAMD", "CIBFFCOMPL",
				"NROCRED LIKE '%" + shippingReference + "%' ", testCaseName);
	}
}
