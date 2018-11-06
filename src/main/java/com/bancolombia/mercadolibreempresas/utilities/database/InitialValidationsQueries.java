package com.bancolombia.mercadolibreempresas.utilities.database;

import java.sql.SQLException;
import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;

public class InitialValidationsQueries {
	BasicDao basicDao = new BasicDao();
	private static String nit;

	/**
	 * Método constructor
	 */
	public InitialValidationsQueries() {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
	}

	/**
	 * Método que pone el servicio de CIB no disponible
	 */
	public void setCIBNotAvailable(String testCaseName) {
		basicDao.updateQryDML("CDCLIBRAMD", "CDCFFCDGOS", "NOMBRE = 'N'", "LISTA = 'DIS' AND NOMBRE = 'S'",
				testCaseName);
	}

	/**
	 * Método que pone el servicio de CIB disponible
	 */
	public void setCIBIsAvailable(String testCaseName) {
		basicDao.updateQryDML("CDCLIBRAMD", "CDCFFCDGOS", "NOMBRE = 'S'", "LISTA = 'DIS' AND NOMBRE = 'N'",
				testCaseName);
	}

	/**
	 * Método para validar el estado en se se encuentra el servicio de CIB
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> getCIBStatus(String testCaseName) throws SQLException {
		return basicDao.searchMultipleDataFieldsCondition("*", "CDCLIBRAMD", "CDCFFCDGOS", "LISTA = 'DIS'",
				testCaseName);
	}

	/**
	 * Método para buscar un usuario en listas de control
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String[] searchUserInControlList(String testCaseName) throws SQLException {
		String fields = "LPE.PNUMEDOC ,LPE.PTIPODOC,LLI.LCATEG,LKA.CCODCAT,LMN.MNIVEL";
		return basicDao.searchDataFieldsCondition(fields, "BVCLIBRAMD",
				"BVCL2CTLPE LPE, BVCLIBRAMD.BvcffCtlLi LLI,BVCLIBRAMD.BVCFFCTLKA LKA, BVCLIBRAMD.BVCFFCTLMN LMN",
				"LPE.PLLAVE = LLI.LLLAVE AND LLI.LESTADO = 'V' AND  LLI.LCATEG = LKA.CCODCAT AND LKA.CMENSAJE = LMN.MCODMEN AND LPE.PNUMEDOC LIKE '%"
						+ nit + "'",
				testCaseName);
	}

	/**
	 * Método que busca un usuario específico que está en listas de control y
	 * actualiza el nit con el del usuario de prueba
	 */
	public void addUserToTheControlList(String testCaseName) {
		String fullNit = Util.addingNumbersToValue(nit, 15, "0");
		String condition = "PNUMEDOC LIKE '%12345678%' AND PLLAVE = '1292029'";
		basicDao.updateQryDML("BVCLIBRAMD", "BVCL2CTLPE", "PNUMEDOC = '" + fullNit + "'", condition, testCaseName);
	}

	/**
	 * Método que remueve el usuario de prueba de listas de control y pone uno que
	 * ya se encontraba en listas
	 */
	public void removeUserFromControlList(String testCaseName) {
		String condition = "PNUMEDOC LIKE '%" + nit + "%' AND PLLAVE = '1292029'";
		basicDao.updateQryDML("BVCLIBRAMD", "BVCL2CTLPE", "PNUMEDOC = '000000012345678'", condition, testCaseName);
	}

	/**
	 * Método que pone las cuentas internacionales inscritas en estado pendiente
	 */
	public void setPendingStateIntoAccountsToDebit(String testCaseName) {
		String condition = "CTAIDCO LIKE '%" + nit + "'";
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFCTAIN", "CTASTS1 = 'V'", condition, testCaseName);

	}

	/**
	 * Método que pone las cuentas internacionales inscritas en estado activo
	 */
	public void setActivateRegisteredAccounts(String testCaseName) {
		String condition = "CTAIDCO LIKE '%" + nit + "'";
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFCTAIN", "CTASTS1 = 'A'", condition, testCaseName);

	}
}
