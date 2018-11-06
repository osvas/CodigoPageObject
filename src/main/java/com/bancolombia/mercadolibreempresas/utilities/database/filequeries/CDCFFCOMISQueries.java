package com.bancolombia.mercadolibreempresas.utilities.database.filequeries;

import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class CDCFFCOMISQueries {
	private BasicDao basicDao;

	public CDCFFCOMISQueries() {
		basicDao = new BasicDao();
	}

	public String getDebitoComisionConIva(String referenciaDeEnvio, String testCaseName) {
		String[] debito = basicDao.searchDataFieldsCondition("DEBITO", "CDCLIBRAMD", "CDCFFCOMIS",
				"NROCRED = " + referenciaDeEnvio, testCaseName);

		return debito[0];
	}

	public String getTRM(String referenciaDeEnvio, String testCaseName) {
		String[] debito = basicDao.searchDataFieldsCondition("TASACAMB", "CDCLIBRAMD", "CDCFFCOMIS",
				"NROCRED = " + referenciaDeEnvio, testCaseName);

		return debito[0];
	}
}
