package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;

public class DollarRatesWithoutPataShouldBeShownSteps {
	@Page
	FirstScreenPage firstScreen;

	public void validateDollarRatesWithoutPata(String nit) {
		RunEvents.loadStandBy(5);
		RunEvents.scrollUpDown(firstScreen.getDriver(), "400");
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);

		// Se recorren la cantidad de filas que tiene la tabla
		int numberOfPage = 0;
		int numberOfDollarRatesInTheFrontend = 0;
		boolean nextPage = true;
		do {
			numberOfPage++;
			// Se recorren la cantidad de filas que tiene la tabla
			for (int i = 1; i <= firstScreen.rowsOfTheTable.size() - 3; i++) {
				switch (firstScreen.currencyField.get(i).getText().trim()) {
				case "USD":
					numberOfDollarRatesInTheFrontend++;
					break;
				}
			}
			firstScreen.screenShotsPage("tasas de la pagina " + numberOfPage, "Tasas Dolar sin PATA");
			boolean existNextPage = false;

			for (WebElement elemento : firstScreen.elementsOfThePager) {
				if (elemento.getAttribute("class").contains("NavRight") && nextPage) {
					existNextPage = true;
				}
			}

			if (existNextPage) {
				firstScreen.NextButtonOfThePager.click();
				RunEvents.loadStandBy(4);
			} else {
				nextPage = false;
			}
		} while (nextPage);

		RatesQueries ratesQueries = new RatesQueries();
		List<List<String>> listOfRatesWitoutPata = ratesQueries.getAllTheDollarRatesWithoutPata(nit,
				"Tasas Dolar sin PATA");

		int numberOfDollarRatesInTheBackend = listOfRatesWitoutPata.size();

		assertTrue("No está coincidiendo la cantidad de datos que hay en el backend con las que hay en el frontend",
				numberOfDollarRatesInTheFrontend == numberOfDollarRatesInTheBackend);
	}
}
