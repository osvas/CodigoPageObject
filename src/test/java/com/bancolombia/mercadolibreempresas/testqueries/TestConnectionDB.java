package com.bancolombia.mercadolibreempresas.testqueries;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.ConnectionDB;

public class TestConnectionDB {
	private static ConnectionDB connectionDB;
	private Connection connection;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		connectionDB = new ConnectionDB();
	}

	@Test
	public void testExistConnectionDataBaseIseriesDB2() throws SQLException {
		connection = connectionDB.connection();

		boolean actual = connection != null;

		connectionDB.disconnect();

		assertEquals(true, actual);
	}

	@Test
	public void testConnectionIsOpened() throws SQLException {
		connection = connectionDB.connection();

		boolean actual = connection.isClosed();

		connectionDB.disconnect();

		assertEquals(false, actual);
	}

	@Test
	public void testConnectionIsClosed() throws SQLException {
		connection = connectionDB.connection();
		connectionDB.disconnect();

		boolean actual = connection.isClosed();

		assertEquals(true, actual);
	}

	@Test
	public void testConnectionSettingLibrary() {
		connection = connectionDB.connection("CIBLIBRAMD");

		boolean actual = connection != null;

		connectionDB.disconnect();

		assertEquals(true, actual);
	}

}
