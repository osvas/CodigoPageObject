package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.Util;

public class DisabledRatesWithFutureComplianceDateOfTheDollarCurrencySteps {
	@Page
	FirstScreenPage firstScreen;

	public void CheckThatARateWithAFutureTradingDateCanNotBeSelected() {
		RunEvents.loadStandBy(1);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		firstScreen.screenShotsPage("listaDeTasas", "TasasBloqueadasFechaCumplimientoFuturaMonedaDolar");

		int numberOfPage = 0;
		boolean nextPage = true;
		do {
			numberOfPage++;
			// Se recorren la cantidad de filas que tiene la tabla
			for (int i = 1; i <= firstScreen.rowsOfTheTable.size() - 3; i++) {
				switch (firstScreen.currencyField.get(i).getText().trim()) {
				case "USD":
					if (Util.compareDates(firstScreen.complianceDate.get(i).getText().trim()) > 0) {
						assertTrue("Se está pudiendo seleccionar más monedas de cualquier tipo incluida de dolar",
								firstScreen.rowsOfTheTable.get(i + 1).getAttribute("class").equals("locked"));
					} else {
						assertFalse("No se está pudiendo seleccionar una moneda con la fecha de negociación correcta",
								firstScreen.rowsOfTheTable.get(i + 1).getAttribute("class").equals("locked"));
					}
					break;
				}
			}
			firstScreen.screenShotsPage("tasasDolarBloqueadas" + numberOfPage,
					"TasasBloqueadasFechaCumplimientoFuturaMonedaDolar");

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
