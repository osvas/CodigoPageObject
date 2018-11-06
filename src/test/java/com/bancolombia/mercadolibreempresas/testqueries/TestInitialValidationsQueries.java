package com.bancolombia.mercadolibreempresas.testqueries;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.InitialValidationsQueries;

public class TestInitialValidationsQueries {
	InitialValidationsQueries queries = new InitialValidationsQueries();

	@Test
	public void testUpdateCIBNotAvailable() throws SQLException {
		queries.setCIBNotAvailable("JUnitTest");
	}

	@Test
	public void testUpdateCIBIsAvailable() throws SQLException {
		queries.setCIBIsAvailable("JUnitTest");
	}

	@Test
	public void testCIBStatus() throws SQLException {
		List<List<String>> query = queries.getCIBStatus("JUnitTest");
		for (List<String> list : query) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}

	@Test
	public void testSearchUserInControlLists() throws SQLException {
		String[] query = queries.searchUserInControlList("JUnitTest");
		for (int i = 0; i < query.length; i++) {
			System.out.println(query[i]);
		}
	}

	@Test
	public void testAddUserToTheControlLists() throws SQLException {
		queries.addUserToTheControlList("JUnitTest");
	}

	@Test
	public void testRemoveUserFromControlList() throws SQLException {
		queries.removeUserFromControlList("JUnitTest");
	}

	@Test
	public void testPendingStateIntoAccountsToDebit() throws SQLException {
		queries.setPendingStateIntoAccountsToDebit("JUnitTest");
	}

	@Test
	public void testActivateRegisteredAccounts() throws SQLException {
		queries.setActivateRegisteredAccounts("JUnitTest");
	}

}
