package com.bancolombia.mercadolibreempresas.utilities.database.filequeries;

import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class CibfflgfndQueries {
	private BasicDao basicDao;
	private String nit;

	public CibfflgfndQueries() {
		this.basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		this.nit = PropertiesReader.getInstance().getProperty("user.nit");
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
	public List<List<String>> getCodeSubcodeInCIBFFLGFND(String rate, String testCaseName) {
		return basicDao.searchMultipleDataFieldsCondition("BANCOENV, SUBBCOENV", "CIBLIBRAMD", "CIBFFLGFND",
				"NRODOCCLTE = " + nit + " AND TASAAPLIC = " + rate, testCaseName);
	}

	public List<List<String>> getCodeSubcodeInCIBFFLGFNDWithMultipleRates(String shippingReference,
			String testCaseName) {
		return basicDao.searchMultipleDataFieldsCondition("BANCOENV, SUBBCOENV", "CIBLIBRAMD", "CIBFFLGFND",
				"NRODOCCLTE = " + nit + " AND NROGIRO LIKE '%" + shippingReference + "%'", testCaseName);
	}

	/**
	 * Método que retorna el código y el subcódigo guardado de la tasa en la tabla
	 * CIBFFLGFND
	 * 
	 * @param rate
	 *            valor de la tasa negociada
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String[] getCodeSubcodeInCIBFFLGFNDByRemmitanceNumber(String nrogiro, String testCaseName) {
		return basicDao.searchDataFieldsCondition("BANCONEG, SUBBCONEG", "CIBLIBRAMD", "CIBFFLGFND",
				"NRODOCCLTE = " + nit + " AND NROGIRO = '" + Util.addingNumbersToValue(nrogiro, 16, "0") + "'",
				testCaseName);
	}

	/**
	 * Método que retorna el código y el subcódigo del banco de fondeo, guardado en
	 * la tabla CIBFFLGFND
	 * 
	 * @param rate
	 *            valor de la tasa negociada
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String[] getCodeSubcodeInCIBFFLGFNDByRemmNumberForSendingBank(String nrogiro, String testCaseName) {
		return basicDao.searchDataFieldsCondition("BANCOENV,SUBBCOENV", "CIBLIBRAMD", "CIBFFLGFND",
				"NRODOCCLTE = " + nit + " AND NROGIRO = '" + Util.addingNumbersToValue(nrogiro, 16, "0") + "'",
				testCaseName);
	}
}
