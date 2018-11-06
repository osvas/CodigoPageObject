package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertFalse;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.Util;

public class EnabledRatesWithFutureComplianceDateDifferentOfTheDollarCurrencySteps {
	@Page
	FirstScreenPage firstScreen;

	public void CheckThatARateWithAFutureComplianceDateCanBeSelected() {
		RunEvents.loadStandBy(1);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		firstScreen.screenShotsPage("listaDeTasas", "TasasHabilitadasFechaCumplimientoFuturaMonedaDiferenteDolar");

		int numberOfPage = 0;
		boolean nextPage = true;
		do {
			numberOfPage++;
			// Se recorren la cantidad de filas que tiene la tabla
			for (int i = 1; i <= firstScreen.rowsOfTheTable.size() - 3; i++) {
				switch (firstScreen.currencyField.get(i).getText().trim()) {
				case "EUR":
					if (Util.compareDates(firstScreen.complianceDate.get(i).getText().trim()) > 0) {
						assertFalse(
								"No se está pudiendo seleccionar una tasa con la moneda euro con la fecha de negociación futura",
								firstScreen.rowsOfTheTable.get(i + 1).getAttribute("class").equals("locked"));
					}
					break;
				case "JPY":
					if (Util.compareDates(firstScreen.complianceDate.get(i).getText().trim()) > 0) {
						assertFalse(
								"No se está pudiendo seleccionar una tasa con la moneda yen con la fecha de negociación futura",
								firstScreen.rowsOfTheTable.get(i + 1).getAttribute("class").equals("locked"));
					}
					break;
				}
			}
			firstScreen.screenShotsPage("tasasDolarBloqueadas" + numberOfPage,
					"TasasHabilitadasFechaCumplimientoFuturaMonedaDiferenteDolar");

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
