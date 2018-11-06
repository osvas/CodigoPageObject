package com.bancolombia.mercadolibreempresas.utilities.database.commission;

import java.util.Calendar;
import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

/**
 * Clase encargada de realizar la consultas sobre valores de calculo de envio y
 * sus parametros.
 * 
 * @author Simon Felipe Rua Vargas <sfrua@bancolombia.com.co>
 *
 */

public class SimonCommissionQueries {

	private BasicDao basicDao;
	private String[] resultQry;
	private static String ID;

	/**
	 * Metodo constructor
	 */
	public SimonCommissionQueries() {
		basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		ID = PropertiesReader.getInstance().getProperty("user.nit");
	}

	public double searchPercentageCommission(String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN =04 AND CODFUN IN('PORCOMISVP')", testCaseName);
		double percentage = (Double.parseDouble(resultQry[0]) / 100) / 100;

		return percentage;
	}

	public double searchInsuranceValue(String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN =04 AND CODFUN IN('SEGUROGOUR')", testCaseName);
		double insuranceValue = Double.parseDouble(resultQry[0]) / 100;
		return insuranceValue;
	}

	public double searchGreatestValue(String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN =04 AND CODFUN IN('VLRCOMMAXI')", testCaseName);
		double greatestValue = Double.parseDouble(resultQry[0]) / 100;
		return greatestValue;
	}

	public double searchLowestValue(String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN =04 AND CODFUN IN('VLRCOMMINI')", testCaseName);
		double lowestValue = Double.parseDouble(resultQry[0]) / 100;
		return lowestValue;
	}

	public double searchValueInUsdSentAmount(String consDiv, String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("TOTUSD", "CIBLIBRAMD", "CIBFFDCL05",
				"NUMERODCL = '" + consDiv + "'", testCaseName);
		double totalUsd = Double.parseDouble(resultQry[0]);
		return totalUsd;
	}

	public double searchValueIva(String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN =04 AND CODFUN IN('BUSCIVACDC')", testCaseName);
		double valueIva = Double.parseDouble(resultQry[0]) / 100;
		return valueIva;
	}

	public double searchValueTrmToday(String testCaseName) {
		Calendar calendar = Calendar.getInstance();
		String field = "CAVALCAM" + calendar.get(Calendar.DATE);
		String year = "" + calendar.get(Calendar.YEAR);
		String month = "" + (calendar.get(Calendar.MONTH) + 1);

		resultQry = basicDao.searchDataFieldsCondition(field, "stalibra", "staffcamb",
				"CAANOACT =" + year + " and CAMESACT = " + month + " and CACODCAM = '31'", testCaseName);
		double trm = Double.parseDouble(resultQry[0]);

		return trm;
	}

	public String searchConsDivByNroGiro(String remitanceNumber, String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("CONSDIV", "CDCLIBRAMD", "CDCFFPAGIR",
				"NROGIRO ='" + remitanceNumber + "'", testCaseName);

		String consDiv = resultQry[0];
		return consDiv;
	}

	public double searchDebit(String remmitanceNumber, String testCaseName) {

		resultQry = basicDao.searchDataFieldsCondition("DEBITO", "CDCLIBRAMD", "CDCFFCOMIS",
				"NROCRED = '" + remmitanceNumber + "'", testCaseName);
		String commissionDebitStr = resultQry[0];
		double commissionDebit;

		if (commissionDebitStr == null || commissionDebitStr.isEmpty())
			commissionDebit = 0;
		else
			commissionDebit = Double.parseDouble(resultQry[0]);

		return commissionDebit;
	}

	public String searchSwiftCodeBeneficiary(String beneficiary, String document, String testCaseName) {
		beneficiary = beneficiary.trim();
		String UpperBeneficiary = beneficiary.toUpperCase();
		String Condition = "CTAIDCO LIKE '%" + document + "' AND UPPER(CTADCOR) LIKE '%" + UpperBeneficiary + "%'";
		String[] qry = null;

		qry = basicDao.searchDataFieldsCondition("CTASWIF", "CIBLIBRAMD", "CIBFFCTAIN", Condition, testCaseName);

		String codeSwift = qry[0].trim();

		return codeSwift;
	}

	public String searchSwiftCodeBeneficiary(String beneficiary, String testCaseName) {
		beneficiary = beneficiary.trim();
		String UpperBeneficiary = beneficiary.toUpperCase();
		String Condition = "CTAIDCO LIKE '%" + ID + "' AND UPPER(CTADCOR) LIKE '%" + UpperBeneficiary + "%'";
		String[] qry = null;

		qry = basicDao.searchDataFieldsCondition("CTASWIF", "CIBLIBRAMD", "CIBFFCTAIN", Condition, testCaseName);

		String codeSwift = qry[0].trim();

		return codeSwift;
	}

	public List<List<String>> searchBankNoCommission(String testCaseName) {
		List<List<String>> banksNoCommissions = basicDao.searchMultipleDataFieldsCondition("CODFUN,CAMALF",
				"CIBLIBRAMD", "CIBFFPAGEN", "CODFUN LIKE 'BCONOCOM%' AND CODCAN = 04", testCaseName);
		return banksNoCommissions;
	}

	public String[] searchBankNoCommissionBySwiftCode(String swiftCode, String testCaseName) {
		return basicDao.searchDataFieldsCondition("CODFUN,CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODFUN LIKE 'BCONOCOM%' AND CODCAN = 04 AND CAMALF LIKE '%" + swiftCode + "%'", testCaseName);

	}

	public String searchBankNoCommissionByParameter(String paramNum, String testCaseName) {
		resultQry = basicDao.searchDataFieldsCondition("CODFUN,CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODFUN = 'BCONOCOM" + paramNum + "'", testCaseName);

		String codeSwiftBankNoComm = resultQry[1];

		return codeSwiftBankNoComm;
	}

	public double searchIvaValueCDCGOS(String testCaseName) {
		String[] iva = basicDao.searchDataFieldsCondition("NOMBRE", "CDCLIBRAMD", "CDCFFCDGOS",
				"CODIGO = 'COM' AND LISTA = 'IVA'", testCaseName);
		double ivaDbl = Double.parseDouble(iva[0]) / 100;
		return ivaDbl;

	}

	public String[] searchCdcffcomis(String remmitanceNumber, String testCaseName) {
		String fields = "NROCRED,NROCCR,NIT,TIPOID,ANOCOM,MESCOM,DIACOM,TASACAMB,DEBITO";
		String[] qry = basicDao.searchDataFieldsCondition(fields, "CDCLIBRAMD", "CDCFFCOMIS",
				"NROCRED = '" + remmitanceNumber + "'", testCaseName);
		return qry;
	}

	public void updateHighestValueCommission(String value, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "SET CAMALF = '" + value + "'",
				"CODCAN =04 AND CODFUN IN('VLRCOMMAXI')", testCaseName);
	}

	public void updateLowestValueCommission(String value, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "SET CAMALF = '" + value + "'",
				"CODCAN =04 AND CODFUN IN('VLRCOMMINI')", testCaseName);
	}

	public void updateBankNoCommission(String bconocomNumber, String swiftCode, String testCaseName) {
		String bankNoComm = "BCONOCOM" + bconocomNumber;

		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "CAMALF = '" + swiftCode + "'",
				"CODCAN =04 AND CODFUN = '" + bankNoComm + "'", testCaseName);
	}

	public double searchValueTrmSpecificDate(String day, String month, String year) {
		String field = "CAVALCAM" + day;

		resultQry = basicDao.searchDataFieldsCondition(field, "stalibra", "staffcamb",
				"CAANOACT =" + year + " and CAMESACT = " + month + " and CACODCAM = '31'", "");
		double trm = Double.parseDouble(resultQry[0]);

		return trm;
	}

	public void updateInsuranceOurCommission(String value, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "SET CAMALF = '" + value + "'",
				"CODCAN =04 AND CODFUN IN('SEGUROGOUR')", testCaseName);
	}

	public void updatePercentageCommission(String value, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "SET CAMALF = '" + value + "'",
				"CODCAN =04 AND CODFUN IN('PORCOMISVP')", testCaseName);
	}

	public void removeBankNoCommissionBySwiftCode(String newSwiftCode, String swiftCode, String testCaseName) {
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFPAGEN", "CAMALF = '" + newSwiftCode + "'",
				"CODCAN =04 AND CODFUN LIKE 'BCONOCOM%' AND CAMALF = '" + swiftCode + "'", testCaseName);
	}

}
