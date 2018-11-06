package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.pageobjects.sending.SecondScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.database.CibBeneficiaryAccountsQueries;

public class VerifyAccountsBeneficiarySteps {

	@Page
	SecondScreenPage secondScreenPage;
	@Page
	FirstScreenPage firstScreen;

	CibBeneficiaryAccountsQueries cibBeneficiaryAccountsQueries;

	public void selectRatesOfTheDollarCurrency() {
		RunEvents.loadStandBy(1);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		firstScreen.screenShotsPage("listaDeTasas", "SeleccionarTasasMonedaDolar");
		// Se recorren la cantidad de filas que tiene la tabla
		int numberOfPage = 0;
		boolean nextPage = true;
		do {
			numberOfPage++;
			// Se recorren la cantidad de filas que tiene la tabla
			for (int i = 1; i <= firstScreen.rowsOfTheTable.size() - 3; i++) {
				switch (firstScreen.currencyField.get(i).getText().trim()) {
				case "USD":
					// asegura que las tasas no esten bloqueadas para poder seleccionarlas
					assertFalse("No se está pudiendo seleccionar una moneda de tipo dólar",
							firstScreen.rowsOfTheTable.get(i + 1).getAttribute("class").equals("locked"));
					if (i == 1) {
						firstScreen.firstInputCheckBox.click();
						nextPage = false;
					} else {
						firstScreen.inputCheckBox.get(i - 1).click();
						nextPage = false;
					}

					break;

				}
				// si es falso salga de la lista
				if (!nextPage) {
					firstScreen.screenShotsPage("tasasSeleccionadasDolar" + numberOfPage, "verifyAccountsBeneficiary");
					break;
				}
			}
			firstScreen.screenShotsPage("tasasSeleccionadasDolar" + numberOfPage, "verifyAccountsBeneficiary");
			boolean existNextPage = false;

			for (WebElement elemento : firstScreen.elementsOfThePager) {
				if (elemento.getAttribute("class").contains("NavRight")) {
					existNextPage = true;
				}
			}

			if (existNextPage) {
				firstScreen.NextButtonOfThePager.click();
				RunEvents.loadStandBy(2);
			} else {
				nextPage = false;
			}
		} while (nextPage);

		firstScreen.buttonContinueRates.click();
		firstScreen.screenShotsPage("tasasSeleccionadasDolar" + numberOfPage, "verifyAccountsBeneficiary");
	}

	public void selectAccountDebit() {
		RunEvents.loadStandBy(5);
		secondScreenPage.listAccountsDebit.click();
	}

	public void selectAccountBeneficary() throws SQLException {

		CibBeneficiaryAccountsQueries beneficiaryActive = new CibBeneficiaryAccountsQueries();

		List<List<String>> resultado = beneficiaryActive.getActiveBeneficiaryAccounts();
		int cont = 0;
		String consulta[] = new String[resultado.size()];
		for (List<String> list : resultado) {

			consulta[cont] = list.get(0).trim() + " - " + list.get(1).trim();
			cont++;

		}

		int assertsCont = 0;
		int contadorCuentas = 0;

		for (WebElement accountsBeneficiary : secondScreenPage.listAccountsBeneficiary) {
			RunEvents.loadStandBy(4);
			accountsBeneficiary.click();
			secondScreenPage.screenShotsPage("cuentasBeneficiario" + contadorCuentas, "Cuentas de beneficiario");
			contadorCuentas++;
			// recorrer cuentas de CIB
			for (int i = 0; i < consulta.length; i++) {
				if (!accountsBeneficiary.getText().equals("Seleccionar")) {
					// Con este metodo de java tolowercase permite cambiar a minuscula si encuentra
					// en mayuscula
					if (accountsBeneficiary.getText().toLowerCase().equals(consulta[i].toLowerCase())) {
						assertsCont++;
					}
				}

			}
		}
		assertTrue("No se encontraron las mismas cuentas en CIB a las que están en el front",
				(secondScreenPage.listAccountsBeneficiary.size() - 1) == assertsCont);
	}

}
