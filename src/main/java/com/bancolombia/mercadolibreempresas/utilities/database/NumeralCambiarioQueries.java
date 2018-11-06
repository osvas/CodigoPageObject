package com.bancolombia.mercadolibreempresas.utilities.database;

import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;

public class NumeralCambiarioQueries {
	private BasicDao basicDao;
	private static String nit;

	public NumeralCambiarioQueries() {
		basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
	}

	/**
	 * Método que retorna la respuesta si la cuenta del beneficiario es del mismo
	 * titular (1 = Sí, 2 = No)
	 * 
	 * @param numeroCuenta
	 *            número de la cuenta que se va a buscar
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String getMismoTitularCuenta(String numeroCuenta, String testCaseName) {
		String[] mismoTitular = basicDao.searchDataFieldsCondition("CTAIDPB", "CIBLIBRAMD", "CIBFFCTAIN",
				"CTAIDCO LIKE '%" + nit + "' AND CTANCTA LIKE '%" + numeroCuenta + "%'", testCaseName);

		return mismoTitular[0].trim();
	}

	public void updateMismoTitularCuenta(String mismoTitular, String numeroCuenta, String documentoBeneficiario,
			String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFCTAIN",
				"CTAIDPB = " + mismoTitular + ", CTAIDBE = '" + documentoBeneficiario + "'",
				"CTAIDCO LIKE '%" + nit + "' AND CTANCTA LIKE '%" + numeroCuenta + "'", testCaseName);
	}

	public String getIdTitularDeLaCuenta(String numeroCuenta, String testCaseName) {
		String[] titular = basicDao.searchDataFieldsCondition("CTAIDBE", "CIBLIBRAMD", "CIBFFCTAIN",
				"CTAIDCO LIKE '%" + nit + "' AND CTANCTA LIKE '%" + numeroCuenta + "'", testCaseName);

		return titular[0];
	}

	/**
	 * Método para obtener los números de los numerales cambiarios registrados en el
	 * CIBFFPAGEN que están excentos
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String getNumeralesExcentosEnCIBFFPAGEN(String testCaseName) {
		String[] numerales = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN=04 AND CODFUN LIKE '%CIBNUMEXCE%'", testCaseName);

		return numerales[0].trim();
	}

	/**
	 * Método que obtiene el cobro GMF del archivo CIBFFINTOG para saber si cobra el
	 * numeral cambiario o no
	 * 
	 * @param numeroDeGiro
	 *            número del giro con el cual se busca después de realizar el envío
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String getCobroGMF(String numeroDeGiro, String testCaseName) {
		String[] cobro = basicDao.searchDataFieldsCondition("IDENTIFEX", "CIBLIBRAMD", "CIBFFINTOG",
				"NROODPGIR = '" + numeroDeGiro + "'", testCaseName);

		return cobro[0].trim();
	}

	/**
	 * Método que obtiene los numerales cambiarios que están registrados en el
	 * backend y se muestran en el frontend
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public List<List<String>> getNumeralesCambiariosHabilitados(String testCaseName) {
		List<List<String>> numerales = basicDao.searchMultipleDataFieldsCondition("CODDNUM", "CIBLIBRAMD", "CIBFFDESNU",
				"TPERNUM = 'J' AND TTRNNUM = 'D' AND CANANUM = 04 AND FORMNUM = 5 ORDER BY CODDNUM ASC", testCaseName);

		return numerales;
	}

	/**
	 * Método que actualiza los numerales cambiarios que están registrados en el
	 * CIBFFPAGEN como excentos del cobro del GMF.
	 * 
	 * @param numeralCambiario
	 *            numeral cambiario que se va a agregar
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void updateNumeralesCambiariosDelPagen(String numeralCambiario, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "CAMALF = '" + numeralCambiario + "'",
				"CODCAN=04 AND CODFUN LIKE '%CIBNUMEXCE%'", testCaseName);
	}
}
