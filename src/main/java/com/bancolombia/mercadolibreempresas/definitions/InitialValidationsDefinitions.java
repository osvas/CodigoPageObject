package com.bancolombia.mercadolibreempresas.definitions;

import java.sql.SQLException;

import com.bancolombia.mercadolibreempresas.steps.InitialValidationsSteps;
import com.bancolombia.mercadolibreempresas.utilities.database.InitialValidationsQueries;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class InitialValidationsDefinitions {
	@Steps
	InitialValidationsSteps steps = new InitialValidationsSteps();

	@Before("@UserInControlList")
	public void setUpUserList() throws SQLException {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		queries.addUserToTheControlList("UserInControlList");
	}

	@Before("@CIBNotAvailable")
	public void setUpCIB() throws SQLException {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		queries.setCIBNotAvailable("CIBNotAvailable");
	}

	@When("^Se pone CIB en estado no disponible$")
	public void updateCIBNotAvailable() {
		steps.updateCIBNotAvailable();
	}

	@Then("^Se valida que se muestre el mensaje correspondiente de cib no disponible$")
	public void checkMessageCIBIsNotAvailable() {

	}

	@Then("^Se pone CIB en estado disponible$")
	public void updateCIBIsAvailable() {
		steps.updateCIBNotAvailable();
	}

	@When("^Se ingresa al usuario de SVE en listas de control$")
	public void addUserToTheControlList() {
		steps.addUserToTheControlList();
	}

	@Then("^Se valida que se muestre el mensaje correspondiente del usuario en listas de control$")
	public void checkMessageUserInControlList() {
		steps.checkMessageUserInControlList();
	}

	@Then("^Se retira al usuario de SVE de las listas de control$")
	public void removeUserFromControlList() throws SQLException {
		// steps.removeUserFromControlList();
	}

	@After("@UserInControlList")
	public void endTestControlList() throws SQLException {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		queries.removeUserFromControlList("UserInControlList");
	}

	@After("@CIBNotAvailable")
	public void endTestCIB() throws SQLException {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		queries.removeUserFromControlList("CIBNotAvailable");
	}
}
