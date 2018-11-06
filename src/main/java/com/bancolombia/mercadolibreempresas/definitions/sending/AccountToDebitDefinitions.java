package com.bancolombia.mercadolibreempresas.definitions.sending;

import java.sql.SQLException;

import com.bancolombia.mercadolibreempresas.steps.sending.AccountToDebitSteps;
import com.bancolombia.mercadolibreempresas.utilities.database.InitialValidationsQueries;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class AccountToDebitDefinitions {

	@Steps
	AccountToDebitSteps accountToDebitSteps;

	/**
	 * Cambiar el estado de las cuentas internacionales inscritas a pendiente
	 * 
	 * @throws SQLException
	 */
	@Before("@SinCuentasInscritas")
	public void PendingStateIntoAccountsToDebit() throws SQLException {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		queries.setPendingStateIntoAccountsToDebit("SinCuentasInscritas");
	}

	@When("^Seleccion Tasas Front$")
	public void selectionOfRates() {
		accountToDebitSteps.selectionOfRates();
	}

	@When("^Dar clic en el boton continuar tasas$")
	public void clickButtonContinueRates() {
		accountToDebitSteps.clickButtonContinueRates();

	}

	@Then("^Aparece mensaje de alerta por usuario sin cuentas inscritas$")
	public void checkWarningMessage() {
		accountToDebitSteps.checkWarningMessageWithoutRegisteredInternationalAccounts();

	}

	@When("^Capturo las cuentas propias que se tienen afiliadas al canal$")
	public void CaptureOwnAccountsThatAreAffiliatedWithTheChannel() {
		accountToDebitSteps.captureOfOwnAccounts();

	}

	@When("^Capturo las cuentas del campo cuenta a debitar")
	public void accountCaptureOfTheAccountFieldToBeDebited() {
		accountToDebitSteps.listOfAccountsToDebit();
	}

	@When("^Comparo que las cuentas que se muestran en el campo cuenta a debitar sean las que se tienen afiliadas al canal$")
	public void compareAffiliatedAccountsWithAccountsToBeDebited() {

	}

	/**
	 * Cambiar el estado de las cuentas internacionales inscritas a activas
	 * 
	 * @throws SQLException
	 */
	@After("@SinCuentasInscritas")
	public void activateRegisteredAccounts() throws SQLException {
		InitialValidationsQueries queries = new InitialValidationsQueries();
		queries.setActivateRegisteredAccounts("SinCuentasInscritas");
	}

}
