package com.bancolombia.mercadolibreempresas.utilities.database.bancofondeo;

import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class SetUpBeneficiaryBankQueries {
	private BasicDao basicDao;
	private static String nit;

	public SetUpBeneficiaryBankQueries() {
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
				"CTAIDCO LIKE '%" + nit + "' AND CTANCTA LIKE '%" + accountNumber + "%'", testCaseName);

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
}
