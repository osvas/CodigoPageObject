package com.bancolombia.mercadolibreempresas.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.pages.PageObject;

public class HomePageSve extends PageObject {
	@FindBy(xpath = "//*[@id='frameSetPpal']/frame[1]")
	public WebElement frameOne;

	@FindBy(xpath = "//*[@id='frameSetPpal']/frame[2]")
	public WebElement frameTwo;

	@FindBy(name = "iframeDivisas")
	private WebElement frameThree;

	public WebElement internationalCommerce;

	@FindBy(id = "MTbl9")
	public WebElement administrativeModule;

	@FindBy(id = "MTbl3")
	public WebElement internationalTrade;

	@FindBy(id = "pTR34")
	public WebElement receptionOfForeignCurrencies;
	
	@FindBy(id = "lnk34")
	public WebElement receptionOfForeignCurrencies2;

	@FindBy(id = "lnk131")
	public WebElement ownProducts;

	@FindBy(id = "lnk115")
	public WebElement administerProducts;

	private WebElement link;

	private WebElement sendingCurrencies;

	public void switchToFrameOne() throws InterruptedException {
		getDriver().switchTo().defaultContent();
		getDriver().switchTo().frame(frameOne);
	}

	public void switchToFrameTwo() throws InterruptedException {
		getDriver().switchTo().defaultContent();
		getDriver().switchTo().frame(frameTwo);
	}

	public void switchToFrameThree() throws InterruptedException {
		getDriver().switchTo().defaultContent();
		getDriver().switchTo().frame(frameThree);
		RunEvents.loadStandBy(2);
	}

	public WebElement findInternationalCommerce() throws InterruptedException {
		switchToFrameOne();
		internationalCommerce = getDriver().findElement(By.cssSelector("#MTbl3"));
		return internationalCommerce;
	}

	private WebElement findlink() throws InterruptedException {
		switchToFrameTwo();
		new WebDriverWait(getDriver(), 30).until(ExpectedConditions.presenceOfElementLocated(By.id("pTR34")));
		link = getDriver().findElement(By.id("pTR35"));
		return link;
	}

	private WebElement findSendingCurrenciesOption() throws InterruptedException {
		switchToFrameTwo();
		new WebDriverWait(getDriver(), 30).until(ExpectedConditions.presenceOfElementLocated(By.id("mmlink6")));
		sendingCurrencies = getDriver().findElement(By.id("mmlink6"));
		return sendingCurrencies;
	}

	public void selectInternationalCommerce() throws InterruptedException {
		findInternationalCommerce().click();
	}

	public void selectEnvioDivisasOption() throws InterruptedException {
		RunEvents.moveMouse(findlink(), this.getDriver());
		findSendingCurrenciesOption().click();
	}
	
	/**
	 * Método que se llamará desde los Steps para tomar una captura de pantalla en
	 * cualquier momento solicitado
	 * 
	 * @param screenShotName
	 *            Nombre que tendrá la captura de pantalla
	 */
	public void screenShotsPage(String screenShotName, String pathScrenshot) {
		Util.captureScreenshot(this.getDriver(), screenShotName, pathScrenshot + "/");
	}
}
