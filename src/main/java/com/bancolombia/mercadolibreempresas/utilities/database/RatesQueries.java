package com.bancolombia.mercadolibreempresas.utilities.database;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;

/**
 * Clase encargada de realizar las consultas necesarias para las tasas
 * 
 * @author Carolina Galeano <cghoyos@bancolombia.com.co>
 *
 */
public class RatesQueries {

	private BasicDao basicDao;
	private static String NIT;
	private static String NIT_TYPE;

	/**
	 * Método constructor
	 */
	public RatesQueries() {
		this.basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		NIT = PropertiesReader.getInstance().getProperty("user.nit");
		NIT_TYPE = PropertiesReader.getInstance().getProperty("user.nitType");
	}

	/**
	 * Método que actualiza las tasas a estado inactivo
	 * 
	 * @param cedula
	 *            número de cédula que se necesita para saber a qué usuario se le
	 *            inactivan las tasas
	 * @param estado
	 *            Estado el que debe de tener la tasa (Activo - Inactivo)
	 * 
	 * @param testCaseName
	 *            Nombre del caso de prueba que la está ejecutando
	 */
	public void inactivateRates(String cedula, String estado, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFCONTR", "ESTADO = '" + estado + "'",
				"IDCONTRAPA LIKE '%" + cedula + "' AND TIPOOPERAC = 'V'", testCaseName);
	}

	/**
	 * Método que busca las tasas activas con saldo de un cliente
	 * 
	 * @param cedula
	 *            número de cédula que se necesita para saber a qué usuario se le
	 *            hace la consulta
	 * @return
	 * @throws SQLException
	 */
	public String[] getRatesWithBalance(String cedula, String currency, String testCaseName) {
		return basicDao
				.searchDataFieldsCondition("FECHANEGOC, FECHACUMPL, HORANEGOCI, MONEDA, MONTO, SALDO, TASA, FACTORCONV",
						"CIBLIBRAMD", "CIBFFCONTR", "TIPOOPERAC = 'V' AND IDCONTRAPA LIKE '%" + cedula
								+ "' and SALDO > 0 AND MONEDA = '" + currency + "' ORDER BY FECHANEGOC ASC",
						testCaseName);
	}

	/**
	 * Método que inserta en la base de datos tasas con la fecha actual para un
	 * usuario, tasa y divisa específica
	 * 
	 * @param nit
	 *            número de identificación del usuario
	 * @param saldo
	 *            saldo que se desea ingresar a la tasa
	 * @param idContigen
	 *            valor único de la tasa
	 * 
	 * @param currency
	 *            divisa a la que se le asignará la tasa
	 * 
	 * @param futureNegotiationDate
	 *            fecha de negociación que tendrá la tasa
	 */
	public void insertNewDolarRate(String nit, String tipoNit, String saldo, String idContigen, String testCaseName,
			String negotiationDate, String valueOfTheRate, String typeOfOperation) {
		basicDao.insertAllFieldsQuery("CIBLIBRAMD", "CIBFFCONTR", "'" + nit + "', '" + tipoNit + "', " + negotiationDate
				+ ", 081032, '" + typeOfOperation + "', 'USD', " + saldo + ".00, " + valueOfTheRate + ", 00001.00000, "
				+ negotiationDate
				+ ", 0000000000, 'BANCOLOMBIA', 'DEBITO.CUENTA', 'MAVISAZA', 'MESA DE DINERO PYME', 'OTC', '          ', 200000.00, 'FX SPOT T+0 ', 'S', 'ACTIVO', "
				+ idContigen + ", " + saldo + ", 'PRUEBA UNITARIAS ID CONTINGENCIA'", testCaseName);
	}

	public void insertNewDolarRateWithCustomBank(String nit, String tipoNit, String saldo, String idContigen,
			String testCaseName, String negotiationDate, String valueOfTheRate, String nameOfTheBank) {
		basicDao.insertAllFieldsQuery("CIBLIBRAMD", "CIBFFCONTR", "'" + nit + "', '" + tipoNit + "', " + negotiationDate
				+ ", 081032, 'V', 'USD', " + saldo + ".00, " + valueOfTheRate + ", 00001.00000, " + negotiationDate
				+ ", 0000000000, '" + nameOfTheBank
				+ "', 'DEBITO.CUENTA', 'MAVISAZA', 'MESA DE DINERO PYME', 'OTC', '          ', 200000.00, 'FX SPOT T+0 ', 'S', 'ACTIVO', "
				+ idContigen + ", " + saldo + ", 'PRUEBA UNITARIAS ID CONTINGENCIA'", testCaseName);
	}

	public void insertDifferentRatesToDollar(String nit, String tipoNit, String saldo, String testCaseName,
			String negotiationDate, String currency, String pataIdentification, String conversionFactor,
			String valueOfTheRate, String typeOfOperation) {
		basicDao.insertAllFieldsQuery("CIBLIBRAMD", "CIBFFCONTR",
				"'" + nit + "', '" + tipoNit + "', " + negotiationDate + ", 081032, '" + typeOfOperation + "', '"
						+ currency + "', " + saldo + ".00, " + valueOfTheRate + ", " + conversionFactor + ", "
						+ negotiationDate
						+ ", 0000000000, 'BANCOLOMBIA', 'DEBITO.CUENTA', 'MAVISAZA', 'MESA DE DINERO PYME', 'OTC', '"
						+ pataIdentification + "', 200000.00, 'FX SPOT T+0 ', 'S', 'ACTIVO', " + pataIdentification
						+ ", " + saldo + ".00, 'PRUEBA UNITARIAS ID CONTINGENCIA'",
				testCaseName);
	}

	/**
	 * Método que elimina las tasas que se crearon durante la prueba que tienen
	 * saldo cero
	 * 
	 * @param nit
	 *            número de identificación del usuario
	 * @param idContigen
	 *            número único de la tasa
	 */
	public void deleteRateWithBalanceZero(String nit, String idContigen, String testCaseName) {
		basicDao.deleteRecords("CIBLIBRAMD", "CIBFFCONTR", "IDCONTRAPA = '" + nit + "' AND IDCONTIGEN = " + idContigen,
				testCaseName);
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> searchAllTasas() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String currentDate = simpleDateFormat.format(today);

		String[] data = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODFUN = 'PARMDIASVE' AND CODCAN = 04", "Parametros dias de vigencia");
		String diaSVE = data[0];

		String subqueryFechaMinima = "(SELECT MIN(FECHAS.FECHACUMPL) AS FECHASHABILES FROM " + "(Select FECHACUMPL "
				+ "FROM CIBLIBRAMD.CIBFFCONTR WHERE FECHACUMPL <= '" + currentDate + "' "
				+ "GROUP BY FECHACUMPL ORDER BY FECHACUMPL DESC " + "FETCH FIRST " + diaSVE + " ROWS ONLY) FECHAS)";

		String conditionTasasAll = "FECHACUMPL >= " + subqueryFechaMinima + " AND IDCONTRAPA LIKE '%" + NIT
				+ "' AND TIPONIT = " + NIT_TYPE
				+ "  AND ESTADO = 'ACTIVO' AND SALDO > 0 AND ((MONEDA = 'USD' AND IDPATAUSD = '') OR (MONEDA <> 'USD' AND IDPATAUSD <> '')) AND TIPOOPERAC = 'V'";

		List<List<String>> listTasas = basicDao.searchMultipleDataFieldsCondition(
				"FECHACUMPL, HORANEGOCI, MONEDA, MONTO,SALDO,TASA,FACTORCONV,FORMAPAGOU", "CIBLIBRAMD", "CIBFFCONTR",
				conditionTasasAll, "Listado de tasas segun condiciones");

		return listTasas;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> searchAllTasas(String currency) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String currentDate = simpleDateFormat.format(today);

		String[] data = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODFUN = 'PARMDIASVE' AND CODCAN = 04", "Parametros dias de vigencia");
		String diaSVE = data[0];

		String subqueryFechaMinima = "(SELECT MIN(FECHAS.FECHACUMPL) AS FECHASHABILES FROM " + "(Select FECHACUMPL "
				+ "FROM CIBLIBRAMD.CIBFFCONTR WHERE FECHACUMPL <= '" + currentDate + "' "
				+ "GROUP BY FECHACUMPL ORDER BY FECHACUMPL DESC " + "FETCH FIRST " + diaSVE + " ROWS ONLY) FECHAS)";

		String conditionPata;

		if (currency.equals("USD")) {
			conditionPata = "IDPATAUSD = ''";
		} else {
			conditionPata = "IDPATAUSD <> ''";
		}

		String conditionTasasAll = "FECHACUMPL >= " + subqueryFechaMinima + " AND IDCONTRAPA LIKE '%" + NIT
				+ "' AND TIPONIT = " + NIT_TYPE + "  AND ESTADO = 'ACTIVO' AND SALDO > 0 AND (MONEDA = '" + currency
				+ "' AND " + conditionPata + ") AND TIPOOPERAC = 'V'";

		List<List<String>> listTasas = basicDao.searchMultipleDataFieldsCondition(
				"FECHACUMPL, HORANEGOCI, MONEDA, MONTO,SALDO,TASA,FACTORCONV,FORMAPAGOU", "CIBLIBRAMD", "CIBFFCONTR",
				conditionTasasAll, "Listado de tasas segun condiciones");

		return listTasas;
	}

	/**
	 * Método que elimina todas las tasas de un usuario en específico
	 * 
	 * @param nit
	 *            del usuario que se va a eliminar
	 * @param testCaseName
	 *            nombre del caso de prueba que se está ejecutando
	 */
	public void deleteAllUserRates(String nit, String testCaseName) {
		basicDao.deleteRecords("CIBLIBRAMD", "CIBFFCONTR", "IDCONTRAPA LIKE '%" + nit + "'", testCaseName);
	}

	public List<List<String>> getAllTheDollarRatesWithoutPata(String nit, String testCaseName) {

		return basicDao
				.searchMultipleDataFieldsCondition("MONEDA, IDPATAUSD", "CIBLIBRAMD", "CIBFFCONTR",
						"IDCONTRAPA LIKE '%" + nit
								+ "' AND IDPATAUSD = '' AND TIPOOPERAC = 'V' AND MONEDA = 'USD' AND SALDO > 0",
						testCaseName);
	}

	public List<List<String>> getAllTheDollarRatesWithPata(String nit, String testCaseName) {

		return basicDao
				.searchMultipleDataFieldsCondition("MONEDA, IDPATAUSD", "CIBLIBRAMD", "CIBFFCONTR",
						"IDCONTRAPA LIKE '%" + nit
								+ "' AND IDPATAUSD <> '' AND TIPOOPERAC = 'V' AND MONEDA = 'USD' AND SALDO > 0",
						testCaseName);
	}

	public String getTasaOfTheRate(String currency, String nit, String testCaseName) {
		String[] tasa = basicDao.searchDataFieldsCondition("TASA", "CIBLIBRAMD", "CIBFFCONTR",
				"IDCONTRAPA LIKE '%" + nit + "' AND MONEDA = '" + currency + "'", testCaseName);

		return tasa[0];
	}
}
