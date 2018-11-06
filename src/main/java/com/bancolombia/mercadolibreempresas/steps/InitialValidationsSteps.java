package com.bancolombia.mercadolibreempresas.steps;

import java.sql.SQLException;

import com.bancolombia.mercadolibreempresas.utilities.database.InitialValidationsQueries;

public class InitialValidationsSteps {
	InitialValidationsQueries queries = new InitialValidationsQueries();

	public void updateCIBNotAvailable() {
		// queries.setCIBNotAvailable();
	}

	public void checkMessageCIBIsNotAvailable() {

	}

	public void updateCIBIsAvailable() {
		// queries.setCIBIsAvailable();
	}

	public void addUserToTheControlList() {
		// queries.addUserToTheControlList();
	}

	public void checkMessageUserInControlList() {

	}

	public void removeUserFromControlList() throws SQLException {
		// queries.RemoveUserFromControlList();
	}
}
