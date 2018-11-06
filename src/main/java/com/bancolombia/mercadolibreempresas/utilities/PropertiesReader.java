package com.bancolombia.mercadolibreempresas.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.jboss.logging.Logger;

/**
 * 
 * @author sfrua
 * 
 *
 */
public class PropertiesReader {
	private static Properties properties;
	private static FileInputStream in;
	private static final Logger LOGGER = Logger.getLogger(PropertiesReader.class.getName());

	public static enum PropertiesEnum {
		IseriesConnection, UserData
	}

	/**
	 * Lee archivo de propiedades e instancia objeto de propiedades para se
	 * utilizado
	 * 
	 * @param propertiesOption
	 * @return
	 */
	public static void initInstanceByEnum(String propertiesOption) {
		if (propertiesOption.equals(PropertiesEnum.IseriesConnection.toString())) {
			initInstance("connectionIseries.properties");
		} else if (propertiesOption.equals(PropertiesEnum.UserData.toString())) {
			initInstance("userData.properties");
		}
	}

	/**
	 * Inicializa objeto de propiedades segun el archivo indicado
	 * 
	 * @param pathFile
	 */
	private static void initInstance(String pathFile) {
		properties = new Properties();

		try {
			in = new FileInputStream(pathFile);
			properties.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			LOGGER.error("Error:" + e.getMessage());
			LOGGER.error("Trace: " + e.getStackTrace());
		} catch (IOException e) {
			LOGGER.error("Error:" + e.getMessage());
			LOGGER.error("Trace: " + e.getStackTrace());
		}
	}

	/**
	 * Retorn objeto propiedades inicializado
	 * 
	 * @return
	 */
	public static Properties getInstance() {
		return properties;
	}

}
