package com.bancolombia.mercadolibreempresas.utilities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader.PropertiesEnum;
import com.bancolombia.mercadolibreempresas.utilities.Util;

/**
 * 
 * @author sfrua Clase implementada para gestionar conexion a la base de datos
 *         Iseries por medio parametros en archivo de configuracion.
 */
public class ConnectionDB {
	private static final String JDBC_CLASS_DRIVER_AS400 = "com.ibm.as400.access.AS400JDBCDriver";
	private String URL = "jdbc:as400://";
	private String URL_LIBRARY = "";
	private String IP_SERVER = "";
	private String USER = "";
	private String PASSWORD = "";
	private Connection connection = null;
	Util util = new Util();

	public ConnectionDB() {
		PropertiesReader.initInstanceByEnum(PropertiesEnum.IseriesConnection.toString());
		IP_SERVER = PropertiesReader.getInstance().getProperty("server.ip");
		USER = PropertiesReader.getInstance().getProperty("username");
		PASSWORD = PropertiesReader.getInstance().getProperty("password");
		URL = URL.concat(IP_SERVER);
		URL_LIBRARY = URL.concat(";libraries=");
	}

	/**
	 * Realiza conexion a la base de datos as400, segun los parametros en el
	 * properties
	 * 
	 * @return Connection
	 */
	public Connection connection() {

		try {
			Class.forName(JDBC_CLASS_DRIVER_AS400);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return connection;
	}

	/**
	 * Realiza connection a la base datos incluyendo una libreria especifica dentro
	 * de isieries
	 * 
	 * @param library
	 * @return Connection
	 */
	public Connection connection(String library) {

		String urlComplete = URL_LIBRARY + library;
		try {
			Class.forName(JDBC_CLASS_DRIVER_AS400);
			connection = DriverManager.getConnection(urlComplete, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return connection;
	}

	/**
	 * Realiza desconexion de la base datos
	 */
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
