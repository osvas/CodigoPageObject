package com.bancolombia.mercadolibreempresas.models.files;

import com.bancolombia.mercadolibreempresas.models.businessrules.Application;
import com.bancolombia.mercadolibreempresas.models.businessrules.OptionsMld;

public class Cibffmov {
	public static String MDTPOREG = "1";
	private String mdCnsReg = "";
	public static String MDPRMREG = "0";
	public static String MDANTREG = "0";
	public static String MDSIGREG = "0";
	private String mdCodTrn;
	private String mdCodReg;
	public static String MDCODSTM = "999";
	public static String MDOFCORG = "25";
	public static String MDOFCDST = "25";
	public static String MDCODEST = "WS";
	private String mdCnsTrm;
	public static String MDACMTOT = "0";
	public static String MDACMSBT = "0";
	public static String MDINDATR = "1";
	private double mdValor;// valor moneda origen trama entrada

	public void setmdCnsReg(String mdCnsReg) {
		this.mdCnsReg = mdCnsReg;
	}

	public void setMdCodTrn(String function) {
		if (function.equals(OptionsMld.ENVIO.toString())) {
			mdCodTrn = "64";
		} else if (function.equals(OptionsMld.RECEPCION.toString())) {
			mdCodTrn = "56";
		}
	}

	public void setMdCodReg(String function) {
		if (function.equals(OptionsMld.ENVIO.toString())) {
			mdCodReg = "94";
		} else if (function.equals(OptionsMld.RECEPCION.toString())) {
			mdCodReg = "01";
		}
	}

	public void setMdCnsTrm(String app) {

		if (app.equals(Application.SVE.toString())) {
			mdCnsTrm = "997";
		} else if (app.equals(Application.SVP.toString())) {
			mdCnsTrm = "998";
		}
	}

	public void setmdValor(double originValue) {
		mdValor = originValue;
	}

	public String getMdCnsReg() {
		return mdCnsReg;
	}

	public String getMdCodTrn() {
		return mdCodTrn;
	}

	public String getMdCodReg() {
		return mdCodReg;
	}

	public String getMdCnsTrm() {
		return mdCnsTrm;
	}

	public double getMdValor() {
		return mdValor;
	}
}
