package com.bancolombia.mercadolibreempresas.models.files;

import com.bancolombia.mercadolibreempresas.models.businessrules.Application;
import com.bancolombia.mercadolibreempresas.models.businessrules.OptionsMld;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.CurrencyQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.SendingQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.UserInfoQueries;

public class GeneratorObjectComparing {

	private SendingQueries sendingQueries;
	private CurrencyQueries currencyQueries;
	private UserInfoQueries userInfoQueries;

	public GeneratorObjectComparing() {
		sendingQueries = new SendingQueries();
		currencyQueries = new CurrencyQueries();
		userInfoQueries = new UserInfoQueries();
	}

	public Cibffmov generateComparingObjMov(String remmitanceNum, double amount) {

		String consdiv = sendingQueries.searchConsDivByNroGiro(remmitanceNum);
		Cibffmov cibffmov = new Cibffmov();

		cibffmov.setmdCnsReg(consdiv);
		cibffmov.setMdCodTrn(OptionsMld.ENVIO.toString());
		cibffmov.setMdCodReg(OptionsMld.ENVIO.toString());
		cibffmov.setMdCnsTrm(Application.SVE.toString());
		cibffmov.setmdValor(amount);

		return cibffmov;
	}

	public Cdcffpagir generateComparingObjPagir(String remmitanceNum, String currency, String currentDate, String nit,
			double amount) {
		Cdcffpagir cdcffpagir = new Cdcffpagir();

		cdcffpagir.setNroGiro(Util.addingNumbersToValue(remmitanceNum, 12, "0"));
		cdcffpagir.setNit(nit);
		cdcffpagir.setFecApert(currentDate);
		cdcffpagir.setValorApe(amount);
		cdcffpagir.setMonedApe(searchCodeCurrency(currency));
		cdcffpagir.setDivisas(amount);

		return cdcffpagir;
	}

	public Cibffdccns generateComparingObjDccns(String remmitanceNum, String currentDate, String format) {
		String consdiv = sendingQueries.searchConsDivByNroGiro(remmitanceNum);
		Cibffdccns cibffdccns = new Cibffdccns();

		String[] array_date = Util.specifiedDate(currentDate, format);

		cibffdccns.setoffccio("25");
		cibffdccns.setanoDcl(array_date[0]);
		cibffdccns.setmesDcl(array_date[1]);
		cibffdccns.setdiaDcl(array_date[2]);
		cibffdccns.setconsecu(consdiv);
		cibffdccns.settipoDcl("5");
		cibffdccns.setnitDcl(Util.addingNumbersToValue(UserInfoQueries.NIT, 11, "0"));
		cibffdccns.settipoNit(UserInfoQueries.TIPO_NIT);
		cibffdccns.setnombre(searchUserName());

		return cibffdccns;
	}

	private String searchCodeCurrency(String currency) {

		String[] currencyInfo = currencyQueries.getAllDetailsOnCurrenciesByCurrency(currency);
		String currencyCode = currencyInfo[0];

		return currencyCode;
	}

	private String searchUserName() {
		String[] userInfo = userInfoQueries.searchUserInCname("Consulta info usuario");
		String userName = userInfo[1];

		return userName.trim();
	}
}
