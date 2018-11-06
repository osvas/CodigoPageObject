package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertTrue;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.database.InitialValidationsQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.PriorityInErrorMessagesQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

import net.thucydides.core.annotations.Step;

public class PriorityInErrorMessagesSteps {
	@Page
	FirstScreenPage firstScreen;

	PriorityInErrorMessagesQueries priorityQueries = new PriorityInErrorMessagesQueries();
	InitialValidationsQueries initialQueries = new InitialValidationsQueries();
	RatesQueries ratesQueries = new RatesQueries();

	@Step
	public void checkMessageFirstUserInCheckList() {
		RunEvents.loadStandBy(1);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		validationsUserInChecklist();
		checkIfThereAreRatesEnabled("UsuarioEnListasDeControlDePrimero");
	}

	@Step
	public void checkMessageSecondUserInCheckList() {
		RunEvents.loadStandBy(1);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		validationsUserInChecklist();
		checkIfThereAreRatesEnabled("UsuarioEnListasDeControlDeSegundo");
	}

	public void validationsUserInChecklist() {
		String[] messageInCIB = priorityQueries.getMessageOfUserInChecklist("PriorityInErrorMessages");
		firstScreen.screenShotsPage("UsuarioEnListasDeControl", "PriorityInErrorMessages");
		assertTrue("No está correcto el texto de usuario en listas de control",
				firstScreen.textMessageError.getText().equals(messageInCIB[0]));
		assertTrue("No está saliendo el mensaje como tipo error!",
				firstScreen.iconType.getAttribute("class").contains("Icono-Error"));
	}

	@Step
	public void checkMessageCIBIsNotAvailableFirst() {
		RunEvents.loadStandBy(2);
		firstScreen.screenShotsPage("CIBNoDisponible", "PriorityInErrorMessages");
		validationsCIBIsNotAvailable();
		checkIfThereAreRatesEnabled("CIBNoDisponibleDePrimero");
	}

	@Step
	public void checkMessageCIBIsNotAvailableSecond() {
		RunEvents.loadStandBy(2);
		firstScreen.screenShotsPage("CIBNoDisponible", "PriorityInErrorMessages");
		validationsCIBIsNotAvailable();
		checkIfThereAreRatesEnabled("CIBNoDisponibleDeSegundo");
	}

	public void validationsCIBIsNotAvailable() {
		String[] messageInCIB = priorityQueries.getMessageOfCibIsNotAvailable("PriorityInErrorMessages");
		assertTrue("No está correcto el texto con CIB no disponible",
				firstScreen.textMessageError.getText().equals(messageInCIB[0]));
		assertTrue("No está saliendo el mensaje como tipo error!",
				firstScreen.iconType.getAttribute("class").contains("Icono-Error"));
	}

	@Step
	public void checkMessageUserWithoutFeesFirst() {
		RunEvents.loadStandBy(1);
		firstScreen.screenShotsPage("CIBNoDisponible", "PriorityInErrorMessages");
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		validationsUserWithoutFees();
		firstScreen.screenShotsPage("UsuarioSinTasasDePrimero", "PriorityInErrorMessages");
	}

	@Step
	public void checkMessageUserWithoutFeesSecond() {
		RunEvents.loadStandBy(1);
		firstScreen.screenShotsPage("CIBNoDisponible", "PriorityInErrorMessages");
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		validationsUserWithoutFees();
		firstScreen.screenShotsPage("UsuarioSinTasasDeSegundo", "PriorityInErrorMessages");
	}

	public void validationsUserWithoutFees() {
		RunEvents.loadStandBy(2);
		firstScreen.screenShotsPage("UsuarioSinTasas", "PriorityInErrorMessages");
		String[] messageInCIBAttentionLines = priorityQueries.getMessageOfAttentionLines("PriorityInErrorMessages");
		String[] messageInCIBUserWithoutRates = priorityQueries.getMessageOfUserWithoutRates("PriorityInErrorMessages");

		assertTrue("No está correcto el texto de usuario sin tasas",
				firstScreen.textMessageError.getText().equals(messageInCIBUserWithoutRates[0]));
		assertTrue("No está saliendo el mensaje como tipo alerta!",
				firstScreen.iconType.getAttribute("class").contains("Icono-Advertencia"));
		assertTrue("No está saliendo el mensaje que indica cómo negociar tasas",
				firstScreen.textMessageErrorWithoutFees.getText().equals(messageInCIBAttentionLines[0]));
	}

	public void checkIfThereAreRatesEnabled(String testCaseName) {
		// Se recorren la cantidad de filas que tiene la tabla
		int numberOfPage = 0;
		boolean nextPage = true;
		do {
			numberOfPage++;
			// Se recorren la cantidad de filas que tiene la tabla
			for (int i = 1; i <= firstScreen.rowsOfTheTableWithErrorMessage.size() - 3; i++) {
				System.out.println("Está revisando las filas!!!");
				assertTrue("Se encontraron tasas habilitadas",
						firstScreen.rowsOfTheTableWithErrorMessage.get(i + 1).getAttribute("class").equals("locked"));
			}
			firstScreen.screenShotsPage(testCaseName + numberOfPage, "PriorityInErrorMessages");
			boolean existNextPage = false;

			for (WebElement elemento : firstScreen.elementsOfThePagerWithErrorMessage) {
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
	}
}
