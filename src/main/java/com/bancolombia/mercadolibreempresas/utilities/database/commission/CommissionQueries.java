package com.bancolombia.mercadolibreempresas.utilities.database.commission;

import java.util.Calendar;
import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

public class CommissionQueries {
	private BasicDao basicDao;
	private static String nit;
	private static String nitType;

	public CommissionQueries() {
		basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		nitType = PropertiesReader.getInstance().getProperty("user.nitType");
	}

	/**
	 * Método encargado de buscar si el usuario está en la central de comisiones
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public List<List<String>> searchUserInTheCentralCommission(String testCaseName) {
		return basicDao.searchMultipleDataFieldsCondition("*", "CDCLIBRAMD", "CDCFFCOMCL",
				"NIT = " + nit + " AND TIPOCOM = '196'", testCaseName);
	}

	/**
	 * Método encargado de insertar el usuairo en la central de comisiones
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void insertUserInTheCentralCommission(String testCaseName) {
		basicDao.insertAllFieldsQuery("CDCLIBRAMD", "CDCFFCOMCL",
				nit + ", " + nitType + ", " + "99, 196, 2, 10, 20, 30", testCaseName);
	}

	/**
	 * Método encargado de eliminar el usuario de la central de comisiones
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void deleteUserInTheCentralCommission(String testCaseName) {
		basicDao.deleteRecords("CDCLIBRAMD", "CDCFFCOMCL", "NIT = " + nit + " AND TIPOCOM ='196'", testCaseName);
	}

	/**
	 * Método encargado de actualizar las tarifas de un usuario en la central de
	 * comisiones
	 * 
	 * @param full
	 *            Tarifa plena
	 * @param special
	 *            Tarifa especial
	 * @param minimum
	 *            Tarifa mínima
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void updateTarifaInTheCentralCommission(String full, String special, String minimum, String testCaseName) {
		basicDao.updateQryDML("CDCLIBRAMD", "CDCFFCOMCL",
				"PLENA='" + full + "',ESPECIAL='" + special + "' ,MINIMA='" + minimum + "' ",
				"NIT = " + nit + " AND TIPOCOM ='196'", testCaseName);
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
	public List<List<String>> getSwiftCodeBeneficiaryBank(String testCaseName) {
		List<List<String>> swiftCodes = basicDao.searchMultipleDataFieldsCondition("CTASWIF", "CIBLIBRAMD",
				"CIBFFCTAIN", "CTAIDCO LIKE '%" + nit + "'", testCaseName);

		return swiftCodes;
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
				"CODCAN = 04 AND CODFUN LIKE '%BCONOCOM%' AND CAMALF LIKE '%" + swiftCode + "%'", testCaseName);
	}

	/**
	 * Método para buscar la TRM del día
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double searchValueTrmToday(String testCaseName) {
		Calendar calendar = Calendar.getInstance();
		String field = "CAVALCAM" + calendar.get(Calendar.DATE);
		String year = "" + calendar.get(Calendar.YEAR);
		String month = "" + (calendar.get(Calendar.MONTH) + 1);

		String[] resultQry = basicDao.searchDataFieldsCondition(field, "STALIBRA", "STAFFCAMB",
				"CAANOACT =" + year + " and CAMESACT = " + month + " and CACODSWI ='TRM'", testCaseName);

		return Double.parseDouble(resultQry[0]);
	}

	/**
	 * Método para obtener la tarifa plena
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double getTarifaPlena(String testCaseName) {
		String[] tarifaPlena = basicDao.searchDataFieldsCondition("PLENA", "CDCLIBRAMD", "CDCFFCOMCL",
				"NIT = '" + nit + "' AND TIPOCOM ='196'", testCaseName);

		return (Double.parseDouble(tarifaPlena[0]));
	}

	/**
	 * Método para obtener la tarifa expecial
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double getTarifaSpecial(String testCaseName) {
		String[] tarifaSpecial = basicDao.searchDataFieldsCondition("ESPECIAL", "CDCLIBRAMD", "CDCFFCOMCL",
				"NIT = '" + nit + "' AND TIPOCOM ='196'", testCaseName);

		return (Double.parseDouble(tarifaSpecial[0]));
	}

	/**
	 * Método para obtener la tarifa mínima
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double getTarifaMinima(String testCaseName) {
		String[] tarifaMinima = basicDao.searchDataFieldsCondition("MINIMA", "CDCLIBRAMD", "CDCFFCOMCL",
				"NIT = '" + nit + "' AND TIPOCOM ='196'", testCaseName);

		return (Double.parseDouble(tarifaMinima[0]));
	}

	/**
	 * Método para obtener el IVA parametrizado
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double getIva(String testCaseName) {
		String[] iva = basicDao.searchDataFieldsCondition("NOMBRE", "CDCLIBRAMD", "CDCFFCDGOS",
				"LISTA = 'IVA' AND CODIGO = ' '", testCaseName);

		return (Double.parseDouble(iva[0]) / 100);
	}

	/**
	 * Método para obtener el estado del REBAT
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String getRebat(String testCaseName) {
		String[] rebat = basicDao.searchDataFieldsCondition("NOMBRE", "CDCLIBRAMD", "CDCFFCDGOS",
				"LISTA = 'CDC' AND CODIGO ='REBAT'", testCaseName);

		return rebat[0].trim();
	}

	public void updateRebat(String estado, String testCaseName) {
		basicDao.updateQryDML("CDCLIBRAMD", "CDCFFCDGOS", "NOMBRE = '" + estado + "'",
				"LISTA = 'CDC' AND CODIGO ='REBAT'", testCaseName);
	}

	/**
	 * Método para obtener la tarifa plena del usuario genérico
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double getTarifaPlenaUsuarioGenerico(String testCaseName) {
		String[] tarifaPlena = basicDao.searchDataFieldsCondition("PLENA", "CDCLIBRAMD", "CDCFFCOMCL",
				"NIT = '999999999999' AND TIPOCOM ='196' AND TIPOID = 9", testCaseName);

		return (Double.parseDouble(tarifaPlena[0]));
	}

	/**
	 * Método para obtener la tarifa plena con el parámetro comnbensha
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double getTarifaPlenaParametroComnbensha(String testCaseName) {
		String[] tarifaPlena = basicDao.searchDataFieldsCondition("PGRDVLR4", "CIBLIBRAMD", "CIBFFDPGRL",
				"PGRDPARM LIKE '%COMNBENSHA%'", testCaseName);

		return (Double.parseDouble(tarifaPlena[0]) / 100);
	}

	/**
	 * Método para obtener el código del banco
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @param bankName
	 *            nombre del banco
	 * @return
	 */
	public String getCodeBankFromCIBFFBANCO(String testCaseName, String codigoSwift) {
		String[] codeBank = basicDao.searchDataFieldsCondition("CODBCO", "CIBLIBRAMD", "CIBFFBANCO",
				"CODSWIFT = '" + codigoSwift + "'", testCaseName);

		return codeBank[0].trim();
	}

	/**
	 * Método para obtener el código de la divisa que se esté usando
	 * 
	 * @param currency
	 *            nombre corto de la divisa
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public String getCurrencyCodeFromCIBFFMONMD(String currency, String testCaseName) {
		String[] currencyCode = basicDao.searchDataFieldsCondition("CODMOND", "CIBLIBRAMD", "CIBFFMONMD",
				"DESCORT = '" + currency + "'", testCaseName);

		return currencyCode[0].trim();
	}

	/**
	 * Método para obtener el código del CDGOS, para verificar si existe ya en el
	 * archivo
	 * 
	 * @param codes
	 *            código que se buscará (primeros cuatro son el código del banco,
	 *            los otros dos son el código de la moneda)
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public List<List<String>> getCodeVGourFromCDCFFCDGOS(String codes, String testCaseName) {
		List<List<String>> code = basicDao.searchMultipleDataFieldsCondition("NOMBRE", "CDCLIBRAMD", "CDCFFCDGOS",
				"NOMBRE = '" + codes + "'", testCaseName);

		return code;
	}

	/**
	 * Método que inserta el código del banco y la moneda en el archivo CDGOS
	 * 
	 * @param code
	 *            código que se insertará
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void insertCodeVGourIntoCDCFFCDGOS(String code, String testCaseName) {
		basicDao.insertAllFieldsQuery("CDCLIBRAMD", "CDCFFCDGOS", "'RBT', 'VGOUR', " + code, testCaseName);
	}

	/**
	 * Método para verificar si existe el parámetro COMNEGGOUR
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public List<List<String>> getParametroCOMNEGGOUR(String testCaseName) {

		return basicDao.searchMultipleDataFieldsCondition("*", "CIBLIBRAMD", "CIBFFDPGRL",
				"PGRDAPLI = 'CDCRB' AND PGRDPARM = 'COMNEGGOUR'", testCaseName);
	}

	/**
	 * Método para obtener el valor de la tarifa plena si el parámetro COMNEGGOUR
	 * existe
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 * @return
	 */
	public double getTarifaPlenaParametroCOMNEGGOUR(String testCaseName) {
		String[] tarifaPlena = basicDao.searchDataFieldsCondition("PGRDVLR4", "CIBLIBRAMD", "CIBFFDPGRL",
				"PGRDAPLI = 'CDCRB' AND PGRDPARM = 'COMNEGGOUR'", testCaseName);

		return (Double.parseDouble(tarifaPlena[0]) / 100);
	}

	/**
	 * Método para insertar el parámetro COMNEGOUR en caso de que no exista
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void insertParametroCOMNEGGOUR(String testCaseName) {
		basicDao.insertAllFieldsQuery("CIBLIBRAMD", "CIBFFDPGRL",
				"'CDCRB', 'COMIS', 'COMNEGGOUR', '/GOUR/                        ','                                         ','                     ', 5500, 6500, 7500,'           ', 0, 0,'           ', 0, 0",
				testCaseName);
	}

	/**
	 * Método para eliminar el registro con el parámetro COMNEGGOUR
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void deleteParametroCOMNEGGOUR(String testCaseName) {
		basicDao.deleteRecords("CIBLIBRAMD", "CIBFFDPGRL", "PGRDAPLI = 'CDCRB' AND PGRDPARM = 'COMNEGGOUR'",
				testCaseName);
	}
}
