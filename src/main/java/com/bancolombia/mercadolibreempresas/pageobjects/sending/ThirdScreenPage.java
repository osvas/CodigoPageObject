package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class ThirdScreenPage extends PageObject {

	@FindBy(xpath = "//button[contains(text(),'Continuar')]")
	public WebElement continueButton;

	@FindBy(xpath = "//*[@id='frameSetPpal']/frame[1]]")
	public WebElement iframeOne;

	@FindBy(xpath = "//*[@id='frameSetPpal']/frame[2]]")
	public WebElement iframeTwo;

	@FindBy(xpath = "//iframe[@class='iframeSize']]")
	public WebElement iframeThree;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div/select/option")
	public List<WebElement> selectExchangeNumber;

	@FindBy(xpath = "//div[@class='numeralListItemSelected']/h4/strong")
	public WebElement numeralSeleccionado;

	@FindBy(xpath = "//textarea[@id='remarks']")
	public WebElement observations;

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