package com.bancolombia.mercadolibreempresas.utilities.database.bancofondeo;

import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class SetUpGenericBankQueries {
	private BasicDao basicDao;
	private static String nit;

	public SetUpGenericBankQueries() {
		this.basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
	}

	/**
	 * Método que obtiene el código swift de un banco a través del número de la
	 * cuenta
	 * 
	 * @param accountNumber
	 *            número de la cuenta con la cual se buscará el código swift
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return Se retorna el código swift en un string
	 */
	public String getSwiftCodeBeneficiaryBank(String accountNumber, String testCaseName) {
		String[] swiftCode = basicDao.searchDataFieldsCondition("CTASWIF", "CIBLIBRAMD", "CIBFFCTAIN",
				"CTAIDCO LIKE '%" + nit + "'", testCaseName);

		return swiftCode[0];
	}

	/**
	 * Método que obtiene el listado de códigos swift que están registrados en el
	 * CIBFFPAGEN
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return Se retorna lista con los códigos swift del CIBFFPAGEN
	 */
	public List<List<String>> getSwiftCodesFromCibffpagen(String testCaseName) {
		return basicDao.searchMultipleDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN = 4 AND CODFUN LIKE '%BCONOCOM%'", testCaseName);
	}

	/**
	 * Actualización del código swift en el CIBFFPAGEN para que el banco no sea
	 * filial
	 * 
	 * @param swiftCode
	 *            código swift que se está buscando
	 * @param swiftCodeToReplace
	 *            código swift que se insertará
	 * @param testCaseName
	 *            nombre del caso de prueba
	 */
	public void updateCibffpagen(String swiftCode, String swiftCodeToReplace, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "CAMALF = '" + swiftCodeToReplace + "'",
				"CODCAN = 4 AND CODFUN LIKE '%BCONOCOM%' AND CAMALF LIKE '%" + swiftCode + "%'", testCaseName);
	}

	/**
	 * Método que busca el método de pago de una tasa en específico
	 * 
	 * @param valueOfRate
	 *            valor del mayor de los saldos seleccionados
	 * @return se retorna la forma de pago USD
	 */
	public String getThePaymentMethod(String valueOfRate, String testCaseName) {
		String[] paymentMethod = basicDao.searchDataFieldsCondition("FORMAPAGOU", "CIBLIBRAMD", "CIBFFCONTR",
				"IDCONTRAPA LIKE '%" + nit + "' AND SALDO = " + valueOfRate, testCaseName);

		return paymentMethod[0];
	}

	/**
	 * Método para obtener el código y el subcódigo del banco a través de la forma
	 * de pago USD
	 * 
	 * @param paymentMethod
	 *            forma de pago USD
	 * @param testCaseName
	 *            nombre del caso de prueba
	 * @return Se retorna un arreglo con el código y el subcódigo del banco
	 */
	public String[] getCodeSubCodeFromCibffcobah(String paymentMethod, String testCaseName) {
		String[] codeSubcode = basicDao.searchDataFieldsCondition("CHCODBCO, CHSUBCOD", "CIBLIBRAMD", "CIBFFCOBAH",
				"CHNOMBCO = '" + paymentMethod + "'", testCaseName);

		return codeSubcode;
	}

	/**
	 * Método que obtiene el código swfit del CIBFFBANCO a través del código y el
	 * subcódigo del banco
	 * 
	 * @param code
	 *            código del banco
	 * @param subCode
	 *            subcódigo del banco
	 * @param testCaseName
	 *            nombre del caso de prueba
	 * @return Se retorna el código swift del banco que se encuentra en CIBFFBANCO
	 */
	public String getSwiftCodeFromCibffbanco(String code, String subCode, String testCaseName) {
		String[] swiftCode = basicDao.searchDataFieldsCondition("CODSWIFT", "CIBLIBRAMD", "CIBFFBANCO",
				"CODBCO=" + code + " AND SUBCODBCO=" + subCode, testCaseName);

		return swiftCode[0];
	}
}
