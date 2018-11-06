package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.support.FindBy;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import java.util.List;

import org.openqa.selenium.WebElement;

public class FirstScreenPage extends PageObject {
	@FindBy(xpath = "//*[@id='iframeDivisas']")
	public WebElement frameMld;

	@FindBy(name = "TransactionFrame")
	public WebElement frameTwo;

	@FindBy(xpath = "(//a[contains(text(),'Up')])[6]")
	public WebElement buttonOrderBalanceRatesFromLowestToHighest;

	@FindBy(xpath = "//div[@id='Wrap-Cont']/div[5]/div/table/tbody/tr/td[6]")
	public List<WebElement> amountField;

	@FindBy(xpath = "//div[@id='Wrap-Cont']/div[5]/div/table/tbody/tr/td[3]")
	public List<WebElement> complianceDate;

	@FindBy(xpath = "(//input[@name='rateItemCheck'])")
	public List<WebElement> inputCheckBox;

	@FindBy(xpath = "//div[@id='Wrap-Cont']/div[5]/div/table/tbody/tr/td[5]")
	public List<WebElement> currencyField;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[5]/div/table/tbody/tr")
	public List<WebElement> rowsOfTheTable;

	@FindBy(xpath = "//*[@id=\"Wrap-Cont\"]/div[6]/div/table/tbody/tr")
	public List<WebElement> rowsOfTheTableWithErrorMessage;

	@FindBy(name = "rateItemCheck")
	public WebElement firstInputCheckBox;

	@FindBy(linkText = "Right")
	public WebElement NextButtonOfThePager;

	@FindBy(xpath = "//*[@id=\"Wrap-Cont\"]/div[5]/div/table/tbody/tr[13]/td/div/div")
	public List<WebElement> elementsOfThePager;

	@FindBy(xpath = "//div[@id='Wrap-Cont']/div[7]/div/button")
	public WebElement buttonContinueRates;

	@FindBy(xpath = "//*[@id=\"Wrap-Cont\"]/div[6]/div/table/tbody/tr[13]/td/div/div")
	public List<WebElement> elementsOfThePagerWithErrorMessage;

	@FindBy(xpath = "//*[@id=\"Wrap-Cont\"]/div[5]/div/div/p")
	public WebElement textMessageError;

	@FindBy(xpath = "//*[@id=\"Wrap-Cont\"]/div[5]/div/p")
	public WebElement textMessageErrorWithoutFees;

	@FindBy(xpath = "//*[@id=\"Wrap-Cont\"]/div[5]/div/div/div")
	public WebElement iconType;

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
