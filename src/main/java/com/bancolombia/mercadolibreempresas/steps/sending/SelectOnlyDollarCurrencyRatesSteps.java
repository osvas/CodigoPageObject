package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;

public class SelectOnlyDollarCurrencyRatesSteps {
	@Page
	FirstScreenPage firstScreen;

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
					assertFalse("No se está pudiendo seleccionar una moneda de tipo dólar",
							firstScreen.rowsOfTheTable.get(i + 1).getAttribute("class").equals("locked"));
					if (i == 1) {
						firstScreen.firstInputCheckBox.click();
					} else {
						firstScreen.inputCheckBox.get(i - 1).click();
					}

					break;

				default:
					assertTrue("Se está pudiendo seleccionar monedas diferente a dólar",
							firstScreen.rowsOfTheTable.get(i + 1).getAttribute("class").equals("locked"));
					break;
				}
			}
			firstScreen.screenShotsPage("tasasSeleccionadasDolar" + numberOfPage, "SeleccionarTasasMonedaDolar");
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
	}
}
