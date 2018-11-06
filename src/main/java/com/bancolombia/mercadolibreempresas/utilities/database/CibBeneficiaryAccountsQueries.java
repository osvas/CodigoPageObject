package com.bancolombia.mercadolibreempresas.utilities.database;

import java.sql.SQLException;
import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;

public class CibBeneficiaryAccountsQueries {
	BasicDao basicDao = new BasicDao();
	private static String nit;

	/**
	 * Método constructor
	 */
	public CibBeneficiaryAccountsQueries() {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
	}

	public List<List<String>> getActiveBeneficiaryAccounts() throws SQLException {
		return basicDao.searchMultipleDataFieldsCondition("CTANCTA, CTADCOR", "CIBLIBRAMD", "CIBFFCTAIN",
				"CTAIDCO ='000000032256183'and CTACMON ='01'", "verifyAccountsBeneficiary");
	}
	
	/**
	 * Busca 
	 * 
	 * @param beneficiary
	 * @param document
	 * @return
	 */
	public String[] searchActiveAccountBeneficiaryByUser(String beneficiary)
	{
        beneficiary = beneficiary.trim();
        String UpperBeneficiary = beneficiary.toUpperCase();
        String Condition = "CTAIDCO LIKE '%"+nit+"' AND UPPER(CTADCOR) LIKE '%"+UpperBeneficiary+"%'";
        String[] qry = null;
        
		qry = basicDao.searchDataFieldsCondition("CTAPAIS,CTANMON,CTASUBB,CTADSCB,CTADCOR,CTAIDBE,CTAIDPB,CTASTS1", "CIBLIBRAMD", "CIBFFCTAIN", Condition,"Informacion envio cuenta inscrita");
		
		return qry;
	}
}
