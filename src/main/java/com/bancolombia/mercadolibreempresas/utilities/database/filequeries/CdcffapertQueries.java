package com.bancolombia.mercadolibreempresas.utilities.database.filequeries;

import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class CdcffapertQueries {
	private BasicDao basicDao;

	/**
	 * Método constructor
	 */
	public CdcffapertQueries() {
		this.basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());

	}

	public List<List<String>> getFileData(String shippingReference, String testCaseName) {
		return basicDao.searchMultipleDataFieldsCondition("CODBCO, SUBCODBCO", "CDCLIBRAMD", "CDCFFAPERT",
				"NROCCR LIKE '%" + shippingReference + "%'", testCaseName);
	}
}
