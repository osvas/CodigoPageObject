package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.AccountToDebitPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.OwnAccountsAffiliatedWithTheChannelPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;

import net.thucydides.core.annotations.Step;

public class AccountToDebitSteps {

	@Page
	AccountToDebitPage accountToDebitPage;

	@Page
	OwnAccountsAffiliatedWithTheChannelPage ownAccountsAffiliatedWithTheChannelPage;

	String[] arrayOwnAccounts;
	// String[] subArrayOwnAccounts;
	String[] arrayAccountsToDebit;

	// Selección de una tasa en la pantalla de tasas

	@Step
	public void selectionOfRates() {
		RunEvents.loadStandBy(7);
		// Acá se invoca el tercer Iframe
		accountToDebitPage.swichToIframe();
		// Por medio de un for each se busca los check box de la tabla de tasas y se
		// seleccionan
		for (WebElement checkbox : accountToDebitPage.checkboxOfTable) {
			checkbox.click();
		}
	}

	/**
	 * Dar clic en el botón continuar de la pagina de tasas
	 */
	@Step
	public void clickButtonContinueRates() {
		accountToDebitPage.buttonContinueRates.click();

	}

	/**
	 * Verificar que el mensaje que aparece en pantalla si corresponda al definido
	 * funcionalmente para cuando un usuario no tienen cuentas internacionales
	 * inscritas
	 * 
	 * @author Carolina Galeano
	 */
	@Step
	public void checkWarningMessageWithoutRegisteredInternationalAccounts() {
		RunEvents.loadStandBy(8);
		assertTrue(
				"No está saliendo la validación de que no se tienen cuentas internacionales inscritas, ya que esta saliendo este mensaje: "
						+ accountToDebitPage.warningMessageWithoutRegisteredInternationalAccounts.getText(),
				accountToDebitPage.warningMessageWithoutRegisteredInternationalAccounts.getText()
						.equals("No tiene cuentas afiliadas para esta transacción."));
		assertTrue(
				"No se está mostrando mensaje de alerta cuando un usuario no tiene cuentas internacionales inscritas",
				accountToDebitPage.warningMessageWithoutRegisteredInternationalAccounts.isDisplayed());

		String claseNoCuentasInscritas = accountToDebitPage.warningDivMessage.getAttribute("class");
		String[] arregloclaseNoCuentasInscritas = claseNoCuentasInscritas.split(" ");
		boolean mensajeEncontrado = false;

		for (int i = 0; i < arregloclaseNoCuentasInscritas.length; i++) {
			System.out.println("Este es el valor del arreglo: " + arregloclaseNoCuentasInscritas[i]);
			switch (arregloclaseNoCuentasInscritas[i]) {
			case "Icono-Advertencia":
				mensajeEncontrado = true;
				break;
			}
			if (mensajeEncontrado) {
				break;
			}
			assertTrue(
					"No está bien el mensaje de alerta cuando un usuario no tiene cuentas internacionales inscritas, ya que se está mostrando como un error y no como una advertencia.",
					mensajeEncontrado);
		}

	}

	@Step
	public void captureOfOwnAccounts() {
		RunEvents.loadStandBy(4);
		arrayOwnAccounts = new String[ownAccountsAffiliatedWithTheChannelPage.ownAccounts.size()];
		int i = 0;
		for (WebElement ownAccounts : ownAccountsAffiliatedWithTheChannelPage.ownAccounts) {
			ownAccounts.getText();
			String unscriptedAccounts = ownAccounts.getText().replace("-", "");
			arrayOwnAccounts[i] = unscriptedAccounts;
			System.out.println(arrayOwnAccounts[i]);

		}

		ownAccountsAffiliatedWithTheChannelPage.screenShotsPage("CuentasPropiasAfiliadasAlCanal",
				"EvidenciasPantallaCuentaADebitar");

	}

	@Step
	public void listOfAccountsToDebit() {
		RunEvents.loadStandBy(6);
		arrayAccountsToDebit = new String[accountToDebitPage.listOfAccountsToDebit.size()];
		int i = 0;

		for (WebElement accountsToDebit : accountToDebitPage.listOfAccountsToDebit) {
			arrayAccountsToDebit[i] = accountsToDebit.getText().substring(0, 11);
			System.out.println(arrayAccountsToDebit[i]);
		}

		int cont = 1;
		for (WebElement list : accountToDebitPage.listOfAccountsToDebit) {
			list.click();
			RunEvents.loadStandBy(1);
			if (!list.getText().equals("Seleccionar")) {
				accountToDebitPage.screenShotsPage("ListaDeCuentasADebitar" + cont, "EvidenciasPantallaCuentaADebitar");
			}
			cont++;
		}
	}

	public void compareAccountsToDebitWithChannelAffiliates() {
		int totalPosicionesVector = 0;
		for (int i = 0; i < arrayAccountsToDebit.length; i++) {
			for (int j = 0; j < arrayOwnAccounts.length; j++) {
				if (arrayAccountsToDebit[i].equals(arrayOwnAccounts[j])) {
					totalPosicionesVector = totalPosicionesVector + 1;
				}

			}

		}
		assertTrue("Las cuentas afiliadas en el canal no son las mismas que se listan en el campo cuenta a debitar",
				arrayOwnAccounts.length == totalPosicionesVector);
	}

}
