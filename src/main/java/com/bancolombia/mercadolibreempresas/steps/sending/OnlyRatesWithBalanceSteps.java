package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertFalse;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.FirstScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;

public class OnlyRatesWithBalanceSteps {
	@Page
	FirstScreenPage firstScreen;

	public void checkThatThereAreNoRatesWithoutBalance() {
		RunEvents.loadStandBy(1);
		firstScreen.getDriver().switchTo().frame(firstScreen.frameMld);
		RunEvents.loadStandBy(2);
		firstScreen.buttonOrderBalanceRatesFromLowestToHighest.click();
		RunEvents.loadStandBy(5);

		for (WebElement balance : firstScreen.amountField) {
			assertFalse("Se encontraron tasas sin saldo", balance.getText().equals("0.00"));
		}
	}
}
