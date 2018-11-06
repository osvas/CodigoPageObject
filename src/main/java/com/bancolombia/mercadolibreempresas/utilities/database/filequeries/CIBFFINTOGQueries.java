package com.bancolombia.mercadolibreempresas.utilities.database.filequeries;

import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class CIBFFINTOGQueries {
	private BasicDao basicDao;

	public CIBFFINTOGQueries() {
		basicDao = new BasicDao();
	}

	public String getIdentificacionEnElExterior(String referenciaDeEnvio, String testCaseName) {
		String[] identificacion = basicDao.searchDataFieldsCondition("IDENTIFEX", "CIBLIBRAMD", "CIBFFINTOG",
				"NROODPGIR = '" + referenciaDeEnvio + "'", testCaseName);

		return identificacion[0].trim();
	}
}
