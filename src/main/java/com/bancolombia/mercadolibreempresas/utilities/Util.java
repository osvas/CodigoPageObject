package com.bancolombia.mercadolibreempresas.utilities;

import java.io.File;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Clase que se usa para crear métodos genéricos, los cuales se estarán llamando
 * muy amenudo a lo largo de la aplicación
 * 
 * @author Óscar Germán Vásquez <ogvasque@bancolombia.com.co>
 *
 */
public class Util {

	/**
	 * Metodo de utileria para adicionar números como prefijo de un valor (Como Nit
	 * o consecutivo)
	 * 
	 * @author Simon Felipe Rua Vargas <sfruas@bancolombia.com.co>
	 * @param value
	 *            Se recibe un valor de entrada como un Nit o un consecutivo para
	 *            adicionarle los ceros
	 * @param lengthField
	 *            Se recibe la longitud del campo en el archivo
	 * 
	 * @param valueToAdd
	 *            Se recibe el número tipo cadena que se desea agregar al valor
	 * @return
	 */
	public static String addingNumbersToValue(String value, int lengthField, String valueToAdd) {
		String numbers = "";

		for (int i = 0; i < lengthField - value.length(); i++) {
			numbers = numbers + valueToAdd;
		}

		String valueWithNumbers = numbers.concat(value);

		return valueWithNumbers;
	}

	/**
	 * Método encargado de generar un número aleatorio entre 0 y 999999999
	 * dependiendo del caso que sea enviado
	 * 
	 * @author Óscar Gérmán Vásquez <ogvasque@bancolombia.com.co>
	 * @param quantity
	 *            Se recibe un número entero entre 1 y 4 para saber la cantidad de
	 *            dígitos que va a tener el número aleatorio.
	 * @return
	 */
	public static int randomNumber(int quantity) {
		int randomNum = 0;
		switch (quantity) {
		case 1:
			randomNum = 0 + (int) (Math.random() * 9);
			break;
		case 2:
			randomNum = 0 + (int) (Math.random() * 99);
			break;
		case 3:
			randomNum = 0 + (int) (Math.random() * 999);
			break;
		case 4:
			randomNum = 0 + (int) (Math.random() * 9999);
			break;
		case 5:
			randomNum = 0 + (int) (Math.random() * 99999);
			break;
		case 6:
			randomNum = 0 + (int) (Math.random() * 999999);
			break;
		case 7:
			randomNum = 0 + (int) (Math.random() * 9999999);
			break;
		case 8:
			randomNum = 0 + (int) (Math.random() * 99999999);
			break;
		case 9:
			randomNum = 0 + (int) (Math.random() * 999999999);
			break;
		}

		return randomNum;
	}

	/**
	 * Método que genera la fecha actual.
	 * 
	 * @return String, Se retorna un String con el siguinte formato de la fecha:
	 *         yyyyMMdd
	 */
	public static String getFechaActual() {
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd");
		return formateador.format(ahora);
	}

	/**
	 * Método encargado de hacer una captura de pantalla
	 * 
	 * @author Óscar Gérmán Vásquez <ogvasque@bancolombia.com.co>
	 * @param driver
	 *            Driver del navegador necesario para tomar la captura de pantalla
	 *            en el momento solicitado
	 * @param screenshotName
	 *            Nombre que tendrá la imagen cuando sea creada
	 * @param pathScrenshot
	 *            Nombre del directorio donde se guardará la imagen, todas tendrán
	 *            una ruta inicial de ./Screenshots/
	 */
	public static void captureScreenshot(WebDriver driver, String screenshotName, String pathScrenshot) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./Screenshots/" + pathScrenshot + screenshotName + ".png"));
		} catch (Exception e) {
			loggerInformation("fatal", "Error realizando la captura de pantalla" + e.getMessage(),
					"Captura de pantalla");
		}
	}

	/**
	 * Método encargado de crear un log de tipo html, el cual puede ser llamado
	 * desde cualquier parte del proyecto, recibe tres parámetros necesarios para
	 * generar la información necesaria.
	 * 
	 * @author Óscar Gérmán Vásquez <ogvasque@bancolombia.com.co>
	 * 
	 * @param typeOfLog
	 *            Es el tipo de log que se necesita generar, es un String que espera
	 *            las siguientes opciones ("info", "error", "fatal", "warning")
	 * @param message
	 *            Es el mensaje que se mostrará en el log del reporte generado
	 * @param testCaseName
	 *            Es el nombre de la prueba que se está haciendo, es importante que
	 *            toda la prueba que lo invoca tenga siempre el mismo nombre para
	 *            que el reporte sea bien coherente.
	 */
	public static void loggerInformation(String typeOfLog, String message, String testCaseName) {
		Logger loggerLocal = Logger.getLogger(testCaseName);
		PropertyConfigurator.configure("properties/log4j.properties");

		switch (typeOfLog) {
		case "info":
			loggerLocal.info(message);
			break;

		case "error":
			loggerLocal.error(message);
			break;

		case "fatal":
			loggerLocal.fatal(message);
			break;

		case "warning":
			loggerLocal.warn(message);
			break;

		default:
			loggerLocal.info(message);
			break;
		}
	}

	/**
	 * Método que adiciona o resta días calendario a la fecha actual
	 * 
	 * @param days
	 *            número de días que se modificará la fecha (número positivo para
	 *            agregar días, número negativo para restarle días).
	 * @return
	 */
	public static String addOrSubtractDaysToTheCurrentDate(int days) {
		Date fechaActual = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.add(Calendar.DATE, days);

		SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd");
		fechaActual = calendar.getTime();
		return formateador.format(fechaActual);
	}

	public static int compareDates(String dateToCompare) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = formatter.parse(dateToCompare);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date currentDate = new Date();

		return date.compareTo(currentDate);
	}

	/**
	 * Metodo de utileria para identificar los atributos de una clase y retornarlos
	 * en un solo String seperado por comas
	 * 
	 * @author Simon Felipe Rua Vargas <sfruas@bancolombia.com.co>
	 * @param objectClass
	 *            recibe la clase
	 * @return
	 */
	public static String inspectColumns(Class<?> objectClass, String split) {
		Field[] fields = objectClass.getDeclaredFields();
		String separatedCommaFields = "";

		for (Field field : fields) {
			separatedCommaFields = separatedCommaFields.concat(field.getName().concat(split));
		}
		// Ignoro ultima coma
		separatedCommaFields = separatedCommaFields.substring(0, separatedCommaFields.length() - 1);

		return separatedCommaFields;
	}

	public static String getDataFromSplittedValueByPosition(String value, String regex, int position) {
		String[] valueSplitted = value.split(regex);

		return valueSplitted[position];
	}

	public static Map<String, String> generateMapDataByFields(String fields, String seperation, String[] arrayData) {

		Map<String, String> mapDataByFields = new HashMap<>();

		if (arrayData[0] != null) {

			String[] fieldsKey = fields.split(seperation);

			for (int i = 0; i < fieldsKey.length; i++)
				mapDataByFields.put(fieldsKey[i], arrayData[i]);
		}

		return mapDataByFields;
	}

	public static String[] specifiedDate(String date_str, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;

		try {
			date = sdf.parse(date_str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();

		if (date != null)
			calendar.setTime(date);

		String year = "" + calendar.get(Calendar.YEAR);
		String month = "" + (calendar.get(Calendar.MONTH) + 1);
		String day = "" + calendar.get(Calendar.DATE);

		String today_arr[] = new String[3];

		today_arr[0] = year;
		today_arr[1] = month;
		today_arr[2] = day;

		return today_arr;
	}

	/**
	 * Método que redondea un valor double a un número de decimales específico
	 * 
	 * @param valorInicial
	 *            Valor double que se modificará
	 * @param numeroDecimales
	 *            Número de decimales que tendrá el valor que se modificará
	 * @return
	 */
	public static double redondearDecimales(double valorInicial, int numeroDecimales) {
		double parteEntera, resultado;
		resultado = valorInicial;
		parteEntera = Math.floor(resultado);
		resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
		resultado = Math.round(resultado);
		resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
		return resultado;
	}

	/**
	 * Método que fija un número haciendo que se haga un redondeo hacia arriba o
	 * hacia abajo según sea el caso
	 * 
	 * @param numero
	 *            Número double que se modificará
	 * @param digitos
	 *            Número de dígitos que tendrá el valor
	 * @return
	 */
	public static double fijarNumero(double numero, int digitos) {
		double resultado;
		resultado = numero * Math.pow(10, digitos);
		resultado = Math.round(resultado);
		resultado = resultado / Math.pow(10, digitos);
		return resultado;
	}

	/**
	 * Método que quita el caracter ($) y el (,) de un valor obtenido del front
	 * 
	 * @param cadena
	 *            cadena a la cual se le desea quitar los caracteres extraños
	 * @return
	 */
	public static String quitarCaracteresACadenasConValores(String cadena) {
		cadena = cadena.replace(",", "");
		cadena = cadena.replace("$", "");
		cadena = cadena.replace(" ", "");
		cadena = cadena.replace("+", "");
		cadena = cadena.replace("IVA", "");
		cadena = cadena.replace("MXN", "");
		cadena = cadena.replace("USD", "");
		cadena = cadena.replace("EUR", "");

		return cadena;
	}
}
