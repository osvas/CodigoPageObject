package com.bancolombia.mercadolibreempresas.utilities.database.recepcion;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;

public class RecepcionQueries {
	private BasicDao basicDao;
	private static String nit;
	private static String tipoNit;

	public RecepcionQueries() {
		this.basicDao = new BasicDao();
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		nit = PropertiesReader.getInstance().getProperty("user.nit");
		tipoNit = PropertiesReader.getInstance().getProperty("user.nitType");
	}

	/**
	 * Método que inserta giros que llegan del exterior
	 * 
	 * @param divisa
	 *            divisa con la cual se va a trabajar
	 * 
	 * @param cantidadDeGiros
	 *            cantidad de giros que se necesitan crear
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void insertarGiros(String divisa, int cantidadDeGiros, String testCaseName) {
		CommissionQueries commissionQuery = new CommissionQueries();
		// Número del nit completo con los ceros que lo anteceden
		String nitCompleto = Util.addingNumbersToValue(nit, 13, "0");
		// Se obtiene la fecha actual para ingresar cuándo se recibió el giro
		String fechaActual = Util.getFechaActual();
		String anoActual = fechaActual.substring(0, 4);
		String mesActual = fechaActual.substring(4, 6);
		String diaActual = fechaActual.substring(6, 8);

		// Se obtiene la fecha anterior para insertar el día de emisión de la orden el
		// valor que se pasa por parámetro es el número de días que se desea disminuir
		// la fecha
		String fechaAnterior = Util.addOrSubtractDaysToTheCurrentDate(-1);
		String diaAnterior = fechaAnterior.substring(6, 8);

		// Se obtiene el código de la moneda con la cual se está haciendo el envío
		String codigoDivisa = commissionQuery.getCurrencyCodeFromCIBFFMONMD(divisa, testCaseName);
		if (Integer.parseInt(codigoDivisa) >= 1 && Integer.parseInt(codigoDivisa) <= 9) {
			codigoDivisa = Util.addingNumbersToValue(codigoDivisa, 2, "0");
		}

		for (int i = 0; i < cantidadDeGiros; i++) {
			// Número de referencia único para poder ingresar el giro
			String numeroReferencia = Util.addingNumbersToValue("" + Util.randomNumber(9), 12, "3");
			// Campos orden de pago y Saldo por negociar donde se ingresa un valor de 3
			// cifras
			String ordenPagoYSaldoPorNegociar = "" + Util.randomNumber(3);

			basicDao.insertAllFieldsQuery("CIBLIBRAMD", "CIBFFORPND",
					"025, 250, 025, 555, 'AUTO" + numeroReferencia + "'" + "," + nitCompleto + "," + tipoNit
							+ ", 'OPERADORA DE VIAJES Y TURISMO DESTINO ANTIOQUIA S A S',"
							+ "'/ES1500495176112912000287 GOMEZ SAINZ JOSE MANUEL CL INTERGO',"
							+ "0002607451, 0000000000, 1077, 0302, " + codigoDivisa + ", " + ordenPagoYSaldoPorNegociar
							+ ".00, " + ordenPagoYSaldoPorNegociar + ".00," + anoActual + ", " + mesActual + ", "
							+ diaActual + ", 00000, ' ', 'N', 'N', 'N', 0, '   ', 000,000," + "'            ', "
							+ anoActual + ", " + mesActual + ", " + diaAnterior
							+ ", 0000, 00, 00, 0.00, 0.00, 000040679286"
							+ "004, '1', 'A', 'A', '          ', '          ', ' ', ' ', ' ', 0",
					testCaseName);
		}

	}

	/**
	 * Método que borra todos los registros de giros de un cliente
	 * 
	 * @param testCaseName
	 *            nombre del caso de prueba que lo está ejecutando
	 */
	public void borrarGirosDelCliente(String testCaseName) {
		basicDao.deleteRecords("CIBLIBRAMD", "CIBFFORPND", "NIT = " + nit, testCaseName);
	}
}
