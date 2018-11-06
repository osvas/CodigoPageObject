package com.bancolombia.mercadolibreempresas.utilities.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bancolombia.mercadolibreempresas.utilities.Util;

/**
 * @author sfrua
 */
public class BasicDao {

	private static ConnectionDB connectionDB;
	private static String _separator = ",";

	/**
	 * Constructor
	 */
	public BasicDao() {
		connectionDB = new ConnectionDB();
	}

	/**
	 * Crea una consulta sql mediante los parametros
	 * 
	 * @param fields,
	 *            indicando los campos a consultar
	 * @param parent,
	 *            indicando la libreria
	 * @param tableName,
	 *            indicando los archivos y el resto de librerias y archivos
	 * @param condition,
	 *            indicando condicion de busqueda
	 * @param testCaseName,
	 *            Nombre del caso de prueba que se está ejecutando
	 * @return Resultado de la consulta en un vector
	 * @throws SQLException
	 */
	public String[] searchDataFieldsCondition(String fields, String parent, String tableName, String condition,
			String testCaseName) {
		int fieldsLen = countFields(fields);
		String[] resultQuery = new String[fieldsLen];
		Connection connection = connectionDB.connection();

		try {
			if (connection != null) {
				String query = "SELECT " + fields + " FROM " + parent + "." + tableName + " WHERE " + condition;
				Util.loggerInformation("info", query, testCaseName);

				Statement pst = connection.createStatement();
				ResultSet rs = pst.executeQuery(query);

				if (rs.next()) {
					for (int i = 0; i < fieldsLen; i++) {
						resultQuery[i] = String.valueOf(rs.getObject(i + 1));
					}
				} else {
					Util.loggerInformation("warning", "No se encontraron registros", testCaseName);
				}
			}
		} catch (Exception e) {
			Util.loggerInformation("error", "Error al consultar en la base de datos", testCaseName);
			Util.loggerInformation("error", "Error resumen" + e.getCause(), testCaseName);
			e.printStackTrace();
		} finally {
			connectionDB.disconnect();
		}

		return resultQuery;
	}

	/**
	 * 
	 * @param fields
	 * @param parent
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public List<List<String>> searchMultipleDataFieldsCondition(String fields, String parent, String tableName,
			String condition, String testCaseName) {
		Connection connection = connectionDB.connection();
		int fieldsNum = 0;

		List<List<String>> dataRowList = new ArrayList<List<String>>();

		try {
			if (connection != null) {
				String query = "SELECT " + fields + " FROM " + parent + "." + tableName + " WHERE " + condition;
				Util.loggerInformation("info", query, testCaseName);

				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(query);
				ResultSetMetaData rsmd = rs.getMetaData();

				for (int i = 0; rs.next(); i++) {
					List<String> fieldsOnReg = new ArrayList<String>();

					fieldsNum = rsmd.getColumnCount();

					for (int j = 0; j < fieldsNum; j++) {
						fieldsOnReg.add(j, String.valueOf(rs.getObject(j + 1)));
					}

					dataRowList.add(i, fieldsOnReg);
				}
			}
		} catch (SQLException e) {
			Util.loggerInformation("error", "Error al consultar en la base de datos", testCaseName);
			Util.loggerInformation("error", "Error SQL" + e.getErrorCode(), testCaseName);
			Util.loggerInformation("error", "Error resumen" + e.getCause(), testCaseName);
			e.printStackTrace();
		} finally {
			connectionDB.disconnect();
		}

		return dataRowList;
	}

	private int countFields(String fields) {
		int length = 0;

		if (fields.contains(_separator)) {
			String[] fieldAux = fields.split(_separator);
			length = fieldAux.length;
		} else
			length = 1;

		return length;
	}

	/**
	 * Método que se encarga de hacer una actualización a la base de datos
	 * 
	 * @param parent
	 *            La librería que se va a usar
	 * @param tableName
	 *            El archivo que se va a modificar
	 * @param updateSet
	 *            las columnas que se van a actualizar
	 * @param condition
	 *            Condición necesaria para hacer la actualización
	 * @param testCaseName,
	 *            Nombre del caso de prueba que se está ejecutando
	 * @throws SQLException
	 */
	public void updateQryDML(String parent, String tableName, String updateSet, String condition, String testCaseName) {
		Connection connection = connectionDB.connection("CIBLIBRAMD");
		Statement st = null;

		try {
			if (connection != null) {
				String sqlIns = "UPDATE " + parent + "." + tableName + " SET " + updateSet + " WHERE " + condition;
				Util.loggerInformation("info", sqlIns, testCaseName);

				st = connection.createStatement();
				st.execute(sqlIns);
			}
		} catch (SQLException e) {
			Util.loggerInformation("error", "Error al consultar en la base de datos", testCaseName);
			Util.loggerInformation("error", "Error SQL" + e.getErrorCode(), testCaseName);
			Util.loggerInformation("error", "Error resumen" + e.getCause(), testCaseName);
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connectionDB.disconnect();
		}
	}

	/**
	 * Método encargado de hacer insert a una tabla con todos los campos
	 * 
	 * @param parent
	 *            La librería que se va a usar
	 * @param tableName
	 *            El archivo donde se va a ingresar el registro
	 * @param values
	 *            Valores que se van a ingresar en el archivo
	 * @param testCaseName,
	 *            Nombre del caso de prueba que se está ejecutando
	 */
	public void insertAllFieldsQuery(String parent, String tableName, String values, String testCaseName) {
		Connection connection = connectionDB.connection("CIBLIBRAMD");
		Statement st = null;

		try {
			if (connection != null) {

				String query = "INSERT INTO " + parent + "." + tableName + " VALUES(" + values + ")";
				Util.loggerInformation("info", query, testCaseName);

				st = connection.createStatement();
				st.execute(query);
			}
		} catch (SQLException e) {
			Util.loggerInformation("error", "Error al insertar en la base de datos", testCaseName);
			Util.loggerInformation("error", "Error SQL" + e.getErrorCode(), testCaseName);
			Util.loggerInformation("error", "Error resumen" + e.getCause(), testCaseName);
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connectionDB.disconnect();
		}
	}

	/**
	 * Método encargado de eliminar registros
	 * 
	 * @param parent
	 *            Nombre de la librería
	 * @param tableName
	 *            Nombre del archivo
	 * @param condition
	 *            valores necesarios del where
	 * @param testCaseName,
	 *            Nombre del caso de prueba que se está ejecutando
	 */
	public void deleteRecords(String parent, String tableName, String condition, String testCaseName) {
		Connection connection = connectionDB.connection("CIBLIBRAMD");
		Statement st = null;

		try {
			if (connection != null) {
				String query = "DELETE FROM " + parent + "." + tableName + " WHERE " + condition;
				Util.loggerInformation("info", query, testCaseName);

				st = connection.createStatement();
				st.execute(query);
			}
		} catch (SQLException e) {
			Util.loggerInformation("error", "Error al eliminar los datos", testCaseName);
			Util.loggerInformation("error", "Error SQL" + e.getErrorCode(), testCaseName);
			Util.loggerInformation("error", "Error resumen" + e.getCause(), testCaseName);
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connectionDB.disconnect();
		}
	}

	/**
	 * Método encargado de hacer insert a una tabla con todos los campos
	 * 
	 * @param parent
	 *            La librería que se va a usar
	 * @param tableName
	 *            El archivo donde se va a ingresar el registro
	 * @param values
	 *            Valores que se van a ingresar en el archivo
	 * @param testCaseName,
	 *            Nombre del caso de prueba que se está ejecutando
	 */
	public void insertAllFieldsQuery(String parent, String tableName, String columns, String values,
			String testCaseName) {
		Connection connection = connectionDB.connection("CIBLIBRAMD");
		Statement st = null;

		try {
			if (connection != null) {
				String query = "INSERT INTO " + parent + "." + tableName + "(" + columns + ") VALUES(" + values + ")";
				Util.loggerInformation("info", query, testCaseName);

				st = connection.createStatement();
				st.execute(query);
			}
		} catch (SQLException e) {
			Util.loggerInformation("error", "Error al insertar en la base de datos", testCaseName);
			Util.loggerInformation("error", "Error SQL" + e.getErrorCode(), testCaseName);
			Util.loggerInformation("error", "Error resumen" + e.getCause(), testCaseName);
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connectionDB.disconnect();
		}
	}
}
