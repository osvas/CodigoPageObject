package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class FourthScreenPage extends PageObject {

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[1]")
	public WebElement transactionTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[2]")
	public WebElement accountToDebitTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[3]")
	public WebElement nameOfThePayerTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[4]")
	public WebElement nameOfTheBeneficiaryTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[5]")
	public WebElement customNameTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[6]")
	public WebElement beneficiaryAccountTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[7]")
	public WebElement swiftBankBeneficiaryCodeTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[9]")
	public WebElement amountAndCurrencyTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[10]")
	public WebElement rateTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[11]")
	public WebElement amountInPesosTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[12]")
	public WebElement expensesOfTheBankAbroadTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[13]")
	public WebElement shippingCommissionTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[14]")
	public WebElement totalAmountToDebitTitle;

	@FindBy(xpath = "(//div[@class='ResumenHalfTwo'])[15]")
	public WebElement exchangeAndValueNumeralTitle;

	@FindBy(xpath = "//td[2]/div")
	public WebElement successfullyCreatedAccountText;

	// @FindBy(name = "(//div[@class='ResumenHalfTwo'])[15]")
	// public WebElement finishButton;

	@FindBy(xpath = "//button[contains(text(),'Confirmar')]")
	public WebElement confirmButton;

	@FindBy(xpath = "//input[@name='regresar']")
	public WebElement finishButton;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div/div[2]/div/div[1]")
	public List<WebElement> listOfLabelsOnTheLeft;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div/div[2]/div/div[2]")
	public List<WebElement> listOfLabelsOnTheRight;

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
