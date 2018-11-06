package com.bancolombia.mercadolibreempresas.models.files;

import com.bancolombia.mercadolibreempresas.models.businessrules.OptionsMld;

public class Cibffentml {

	public static String OFCORIG = "25";
	public static String USUARIO;// 998 SVP 997 SVE
	public static String CODOPER;// 56 (recepción) 64 (Envió)
	public static String CODTRAN;// 01 (Recepción) 94(Envió)
	public static String CONSREG;// N Numero registro movimiento CIBFFMOV
	public static String NIT;// Numero documento del cliente
	public static String TIPONIT;// Tipo identificación cliente en cib
	public static String CREDITO;// Valor transacción en pesos
	public static String CHEQUE = "0.00";// 0
	public static String SAP = "0.00";// 0
	public static String SEBRA = "0.00";// 0
	public static String CARTERA = "0.00";// 0
	public static String IMPTOS = "0.00";// 0
	public static String TESORERIA = "0.00";// 0
	public static String OTRAS = "0.00";// 0
	public static String EFECTIVO = "0.00";// 0
	public static String PAGOACH = "0.00";// 0

	public static void setUsuario(String app) {
		if (app.equals("SVP")) {
			USUARIO = "998";
		} else if (app.equals("SVE")) {
			USUARIO = "997";
		}
	}

	public static void setCodOper(String function) {
		if (function.equals(OptionsMld.ENVIO.toString())) {
			CODOPER = "64";
		} else if (function.equals(OptionsMld.RECEPCION.toString())) {
			CODOPER = "56";
		}
	}

	public static void setCodTran(String function) {
		if (function.equals(OptionsMld.ENVIO.toString())) {
			CODTRAN = "94";
		} else if (function.equals(OptionsMld.RECEPCION.toString())) {
			CODTRAN = "01";
		}
	}

	public static void setCONSREG(String cONSREG) {
		CONSREG = cONSREG;
	}

	public static void setNIT(String nit) {
		NIT = nit;
	}

	public static void setTIPONIT(String nitType) {
		TIPONIT = nitType;
	}

	public static void setCREDITO(String credito) {
		CREDITO = credito;
	}
}
