package com.bancolombia.mercadolibreempresas.utilities.database;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;

public class CurrencyQueries {

	private BasicDao basicDao;
	private String[] resultQry;
	public static String ID;
	public static String TIPO_NIT;

	public CurrencyQueries() {
		basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		ID = PropertiesReader.getInstance().getProperty("user.nit");
		TIPO_NIT = PropertiesReader.getInstance().getProperty("user.nitType");
	}

	/**
	 * Consulta que retorna el detalle de las divisas
	 * 
	 * @return
	 */
	public String[] getAllDetailsOnCurrenciesByCurrency(String currency) {

		resultQry = basicDao.searchDataFieldsCondition("CODMOND,DESCORT,DESLARG", "CIBLIBRAMD", "CIBFFMONMD",
				"DESCORT = '" + currency + "'", "Descripcion moneda");

		return resultQry;
	}

	/**
	 * Consulta que retorna el detalle de las divisas
	 * 
	 * @return
	 */
	public String[] getAllInfoCountryCurrencyByCountry(String countryCode) {

		resultQry = basicDao.searchDataFieldsCondition("PMCODPAI,PMPAISCO,PMCODMON", "CIBLIBRAMD", "CIBFFPAMON",
				"PMCODPAI = '" + countryCode + "'", "Descripcion pais");

		return resultQry;
	}
}
