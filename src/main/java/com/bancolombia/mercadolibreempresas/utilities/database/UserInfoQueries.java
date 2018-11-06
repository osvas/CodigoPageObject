package com.bancolombia.mercadolibreempresas.utilities.database;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader.PropertiesEnum;

public class UserInfoQueries {

	private BasicDao basicDao;
	public static String NIT;
	public static String TIPO_NIT;

	public UserInfoQueries() {
		basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesEnum.UserData.toString());
		NIT = PropertiesReader.getInstance().getProperty("user.nit");
		TIPO_NIT = PropertiesReader.getInstance().getProperty("user.nitType");
	}

	public String[] searchUserInCname(String testCaseName) {
		return basicDao.searchDataFieldsCondition("CNNAMK,CNNAME,CNNOSS,CNCDTI,CNNOBP", "VISIONR", "CNAME",
				"CNNOSS LIKE '%" + NIT + "'", testCaseName);
	}
}
