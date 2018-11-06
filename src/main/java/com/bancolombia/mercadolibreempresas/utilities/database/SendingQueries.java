package com.bancolombia.mercadolibreempresas.utilities.database;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader.PropertiesEnum;
import com.bancolombia.mercadolibreempresas.utilities.Util;

/**
 * Clase para realizar consultas necesarias para la funcionalidad de Envio
 * 
 *
 */
public class SendingQueries {

	private BasicDao basicDao;
	public static String NIT;
	public static String TIPO_NIT;
	private String[] resultQry;
	private List<List<String>> resultQryList;
	private static final Logger LOGGER = Logger.getLogger(SendingQueries.class.getName());

	public SendingQueries() {
		basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesEnum.UserData.toString());
		NIT = PropertiesReader.getInstance().getProperty("user.nit");
		TIPO_NIT = PropertiesReader.getInstance().getProperty("user.nitType");
	}

	/**
	 * 
	 * @param cedula
	 * @return
	 * @throws SQLException
	 */
	public String dinamycPassword(String cedula, String testCaseName) {
		String condition = "rrn(b) = (SELECT rrn(a) from COMLIBRAMD.COMFFLGEGN a where trim(substr(a.MENSAJE, 183, 15)) = '"
				+ cedula + "' ORDER BY rrn(A) desc fetch first rows only ) +3";
		String[] qry = basicDao.searchDataFieldsCondition("substr(b.MENSAJE,181,6)", "COMLIBRAMD", "COMFFLGEGN b",
				condition, testCaseName);
		String dynamicKey = qry[0];

		return dynamicKey;
	}

	/**
	 * 
	 * @param nrocred
	 * @return
	 */
	public List<List<String>> searchInCibffMensa(String nrocred) {
		nrocred = Util.addingNumbersToValue(nrocred, 12, "0");

		resultQryList = basicDao.searchMultipleDataFieldsCondition("TIPOMSG,NROCRED,CAMPO,SECUENCIA,CONTENIDO",
				"CIBLIBRAMD", "CIBFFMENSA", "TIPOMSG = '103' AND  NROCRED  LIKE '%" + nrocred + "'",
				"Consulta archic MENSA");

		return resultQryList;
	}

	/**
	 * 
	 * @param nrocred
	 * @return
	 */
	public String[] searchCdcffapertMember(String nrocred) {
		nrocred = Util.addingNumbersToValue(nrocred, 12, "0");
		resultQry = basicDao.searchDataFieldsCondition("*", "CDCLIBRAMD", "MEMBERAPERT", "NROCCR = '" + nrocred + "'",
				"Consulta archivo de apertura (miembro)");

		return resultQry;
	}

	/**
	 * 
	 * @param remitanceNumber
	 * @return
	 */
	public String searchConsDivByNroGiro(String remitanceNumber) {
		remitanceNumber = Util.addingNumbersToValue(remitanceNumber, 12, "0");

		resultQry = basicDao.searchDataFieldsCondition("CONSDIV", "CDCLIBRAMD", "CDCFFPAGIR",
				"NROGIRO ='" + remitanceNumber + "'", "Consulta del consecutivo mendainte numero de giro");

		String consDiv = resultQry[0];
		return consDiv;
	}

	/**
	 * Metodo encargado de buscar todos los bancos corresponsales
	 * 
	 * @return
	 */
	public List<List<String>> searchAllCorrespondantBanks() {
		return basicDao.searchMultipleDataFieldsCondition("CODBCO,SUBCODBCO,CODSWIFT,NOMBREBCO", "CIBLIBRAMD",
				"CIBFFBANCO", "FAX = 'SI' AND MONEDA1 <> ''", "Buscar todos los bancos coresponsales");
	}

	/**
	 * Metodo encargado de buscar el codigo, subcodigo,codigo swift y nombre banco
	 * segun moneda y codigo swift
	 * 
	 * @param swiftCode
	 * @param currency
	 * @return
	 */
	public String[] searchCorrespondantBankBySwiftCodeAndCurrency(String swiftCode, String currency) {
		resultQry = basicDao.searchDataFieldsCondition("CODBCO,SUBCODBCO,CODSWIFT,NOMBREBCO", "CIBLIBRAMD",
				"CIBFFBANCO",
				"FAX = 'SI' AND MONEDA1 ='" + currency.trim() + "' AND CODSWIFT LIKE '" + swiftCode.trim() + "%'",
				"Consulta si existe banco correponsal");

		return resultQry;
	}

	/**
	 * 
	 * @param codbank
	 * @param subbank
	 * @return
	 */
	public String[] searchCorrespondantBankByCodeBankAndSubCode(String codbank, String subbank) {
		resultQry = basicDao.searchDataFieldsCondition("CODBCO,SUBCODBCO,CODSWIFT,NOMBREBCO", "CIBLIBRAMD",
				"CIBFFBANCO", "FAX = 'SI' AND CODBCO ='" + codbank + "' AND SUBCODBCO = '" + subbank + "'",
				"Info banco corresponsal");

		return resultQry;
	}

	/**
	 * 
	 * @param currency
	 * @return
	 */
	public List<List<String>> searchCorrespondantBankByCurrency(String currency) {
		return basicDao.searchMultipleDataFieldsCondition("CODBCO,SUBCODBCO,CODSWIFT,NOMBREBCO", "CIBLIBRAMD",
				"CIBFFBANCO", "FAX = 'SI' AND MONEDA1 ='" + currency + "'", "Bancos corresponsales por moneda");
	}

	/**
	 * 
	 * @param currency
	 * @return
	 */
	public String[] searchCibffpagenGeneralBankByCurrency(String currency) {
		resultQry = basicDao.searchDataFieldsCondition("CODCAN,CODFUN,CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODFUN ='CIBGIRE" + currency + "' AND CODCAN = '04'", "Banco generico por moneda");

		return resultQry;
	}

	public String[] searchCibffcpto(String motive) {

		resultQry = basicDao.searchDataFieldsCondition("CPTOBIC, CLASFCPTO, DETALLE, CPTOBCOREP", "CIBLIBRAMD",
				"CIBFFCPTO", "CPTOBIC = '" + motive + "'", "Consulta archivo conceptos por codigo motivo");
		return resultQry;
	}

	public String searchExchangeNumeralExceptionGmf() {
		resultQry = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN =03 AND CODFUN = 'CIBNUMEXCE'", "Consulta numerales excentos de cobro gmf");
		String exceptions = resultQry[0];
		return exceptions;
	}

	public String findTheNameOfAUserThroughTheirIdentification(String testCaseName) {
		BasicDao basicDao = new BasicDao();
		String[] name = basicDao.searchDataFieldsCondition("CNNAME", "VISIONR", "CNAME",
				"CNNOSS = '" + Util.addingNumbersToValue(NIT, 15, "0") + "'", testCaseName);

		return name[0];
	}

	public String[] findBeneficiaryAccountData(String testCaseName) {
		BasicDao basicDao = new BasicDao();
		String[] data = basicDao.searchDataFieldsCondition("CTABENE, CTANCTA, CTASWIF", "CIBLIBRAMD", "CIBFFCTAIN",
				"CTANCTA LIKE '%779390453' AND CTAIDCO = '" + Util.addingNumbersToValue(NIT, 15, "0")
						+ "' AND CTACANA = 4",
				testCaseName);

		return data;
	}

	public void updateGenericoBank(String currency, String infoGeneric, String testCaseName) {

		String genericBankParam = "CIBGIRE" + currency;

		LOGGER.info("Actualizando banco generico " + infoGeneric + " en parametro " + genericBankParam);
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "CAMALF = '" + infoGeneric + "'",
				"CODCAN =04 AND CODFUN = '" + genericBankParam + "'", testCaseName);
	}

	public String[] searchInfoBankBySwiftCode(String swiftCode) {
		return basicDao.searchDataFieldsCondition("BICCD,BANKNAME", "CIBLIBRAMD", "CIBFFBICCD",
				"BICCD = '" + swiftCode + "'", "Consulta codigo swift en biccd");
	}

	public String[] searchHomologatedBankInfo(String description) {
		return basicDao.searchDataFieldsCondition("CHCODBCO,CHSUBCOD,CHNOMBCO", "CIBLIBRAMD", "CIBFFCOBAH",
				"CHNOMBCO = '" + description.trim() + "'", "Consultando banco homologado");
	}

	public void insertCorrespondantBank(String codbco, String subcodbco, String codswift, String bankName, String city,
			String country, String currency) {
		String values = "" + codbco + "," + subcodbco + ", '" + codswift + "', '" + bankName + "', '400" + codbco
				+ Util.addingNumbersToValue(subcodbco, 3, "0") + "'," + "'SI', '" + currency + "', 'SI','" + city
				+ "', '" + country + "', 'SI'";

		String fields = "CODBCO,SUBCODBCO,CODSWIFT,NOMBREBCO,CONTACTO, CTA1, MONEDA1, CTA2, CIUDAD, PAIS, FAX";

		basicDao.insertAllFieldsQuery("CIBLIBRAMD", "CIBFFBANCO", fields, values, "Insertando banco corresponsal");

	}

	/**
	 * 
	 * @param codbank
	 * @param subbank
	 * @return
	 */
	public String[] searchCorrespondantBankByCodeBankAndSubCode(String codbank, String subbank, String currency) {
		resultQry = basicDao.searchDataFieldsCondition(
				"CODBCO,SUBCODBCO,CODSWIFT,NOMBREBCO", "CIBLIBRAMD", "CIBFFBANCO", "FAX = 'SI' AND CODBCO ='" + codbank
						+ "' AND SUBCODBCO = '" + subbank + "' AND MONEDA1 = '" + currency + "'",
				"Info banco corresponsal");

		return resultQry;
	}
}
