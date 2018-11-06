package com.bancolombia.mercadolibreempresas.steps;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.pageobjects.HomePageSve;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;

import net.thucydides.core.annotations.Step;

public class HomePageSteps {

	@Page
	private HomePageSve homePageSve;

	@Step
	public void selectInternationalCommerce() throws InterruptedException {
		homePageSve.selectInternationalCommerce();
	}

	@Step
	public void selectEnvioDivisasOption() throws InterruptedException {
		RunEvents.loadStandBy(6);
		homePageSve.selectInternationalCommerce();
		homePageSve.selectEnvioDivisasOption();
		RunEvents.loadStandBy(6);
	}

	public HomePageSve getHomePageSve() {
		return homePageSve;
	}

	public void selectAdministrarProductosPropiosOption() {
		RunEvents.loadStandBy(4);
		selectFrame(homePageSve.frameOne);
		homePageSve.administrativeModule.click();
		RunEvents.loadStandBy(1);
		selectFrame(homePageSve.frameTwo);
		RunEvents.moveMouse(homePageSve.ownProducts, homePageSve.getDriver());
		RunEvents.loadStandBy(2);
		homePageSve.administerProducts.click();
		RunEvents.loadStandBy(5);
	}

	public void selectFrame(WebElement frame) {
		homePageSve.getDriver().switchTo().defaultContent();
		homePageSve.getDriver().switchTo().frame(frame);
		RunEvents.loadStandBy(2);
	}

	public void iSelectTheCurrencyReceptionOption() {
		RunEvents.loadStandBy(4);
		selectFrame(homePageSve.frameOne);
		homePageSve.internationalTrade.click();
		selectFrame(homePageSve.frameTwo);
		homePageSve.screenShotsPage("Selección de recepción de divisas", "Selección de menú");
		homePageSve.receptionOfForeignCurrencies.click();
		RunEvents.loadStandBy(5);
	}

}
