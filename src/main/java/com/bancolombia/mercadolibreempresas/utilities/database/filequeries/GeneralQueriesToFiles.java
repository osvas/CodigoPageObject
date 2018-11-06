package com.bancolombia.mercadolibreempresas.utilities.database.filequeries;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class GeneralQueriesToFiles {
	private BasicDao basicDao;
	private String nit;

	public GeneralQueriesToFiles() {
		this.basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		this.nit = PropertiesReader.getInstance().getProperty("user.nit");
	}

	/**
	 * Método que retorna el nombre del banco que se busca en la tabla CIBFFCOBAH a
	 * través del código y el subcódigo
	 * 
	 * @param code
	 *            código del banco
	 * @param subCode
	 *            subcódigo del banco
	 * @param testCaseName
	 *            nombre del caso de prueba que lo ejecuta
	 * @return
	 */
	public String getTheNameOfTheBankThroughTheCodeAndTheSubcodeInCIBFFCOBAH(String code, String subCode,
			String testCaseName) {
		String[] bankName = basicDao.searchDataFieldsCondition("CHNOMBCO", "CIBLIBRAMD", "CIBFFCOBAH",
				"CHCODBCO=" + code + " AND CHSUBCOD=" + subCode, testCaseName);

		return bankName[0];
	}

	/**
	 * Método que retorna el nombre del banco que está en la tabla CIBFFCONTR a
	 * través de la tasa y el nit
	 * 
	 * @param rate
	 *            valor de la tasa con la que se buscará la tasa en la base de datos
	 * @param nit
	 *            número de identificación del usuario dueño de la tasa
	 * @param testCaseName
	 *            nombre del caso de prueba que lo ejecuta
	 * @return
	 */
	public String getTheNameOfTheBankInCIBFFCONTR(String rate, String testCaseName) {
		String[] bankName = basicDao.searchDataFieldsCondition("FORMAPAGOU", "CIBLIBRAMD", "CIBFFCONTR",
				"IDCONTRAPA LIKE '%" + nit + "' AND TASA = " + rate, testCaseName);

		return bankName[0];
	}

	public String getTheNameOfTheBankInCIBFFCONTRMultipleRates(String rate, String testCaseName) {
		String[] bankName = basicDao.searchDataFieldsCondition("FORMAPAGOU", "CIBLIBRAMD", "CIBFFCONTR",
				"IDCONTRAPA LIKE '%" + nit + "' AND MONTO = " + rate, testCaseName);

		return bankName[0];
	}

	public String getTheSwiftCodeOfTheBeneficiaryBankMultipleRates(String accountNumber, String testCaseName) {
		String[] swiftCode = basicDao.searchDataFieldsCondition("CTASWIF", "CIBLIBRAMD", "CIBFFCTAIN",
				"CTAIDCO LIKE '%" + nit + "' AND CTANCTA LIKE '%" + accountNumber + "'", testCaseName);

		return swiftCode[0].trim();
	}

	public String[] getCodeSubcodeFromCIBFFBANCO(String swiftCode, String testCaseName) {
		String[] codes = basicDao.searchDataFieldsCondition("CODBCO, SUBCODBCO", "CIBLIBRAMD", "CIBFFBANCO",
				"CODSWIFT = '" + swiftCode + "'", testCaseName);

		return codes;
	}

	/**
	 * Método que obtiene el código y el subcódigo del banco corresponsal con Fax=SI
	 * y la divisa solicitada
	 * 
	 * @param swiftCode
	 *            código swift con el que se buscarán los datos
	 * @param currency
	 *            divisa que se debe de traer dependiendo del tipo del giro
	 * @param testCaseName
	 *            nombre que tendrá el caso de prueba
	 * @return
	 */
	public String[] getCodeSubcodeCorrespondentFromCIBFFBANCO(String swiftCode, String currency, String testCaseName) {
		String[] codes = basicDao.searchDataFieldsCondition("CODBCO, SUBCODBCO", "CIBLIBRAMD", "CIBFFBANCO",
				"CODSWIFT = '" + swiftCode + "' AND MONEDA1='" + currency + "' AND FAX='SI'", testCaseName);

		return codes;
	}

	public String getCodeSubcodeGenericBankFromPagen(String testCaseName) {
		String[] genericBank = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN =04 AND CODFUN LIKE '%CIBGIREUSD%'", testCaseName);

		return genericBank[0];
	}

	public String[] getCodeSubCodeBankFromCIBFFCTAIN(String accountNumber, String testCaseName) {
		String[] ctainBankCodes = basicDao.searchDataFieldsCondition("CTABANK, CTASUBB", "CIBLIBRAMD", "CIBFFCTAIN",
				"CTAIDCO LIKE '%" + nit + "' AND CTANCTA LIKE '%" + accountNumber + "'", testCaseName);

		return ctainBankCodes;
	}

	public String[] getCodeSubCodeFromCIBFFCOBAH(String nameBank, String testCaseName) {
		String[] codes = basicDao.searchDataFieldsCondition("CHCODBCO, CHSUBCOD", "CIBLIBRAMD", "CIBFFCOBAH",
				"CHNOMBCO ='" + nameBank + "'", testCaseName);

		return codes;
	}
}
