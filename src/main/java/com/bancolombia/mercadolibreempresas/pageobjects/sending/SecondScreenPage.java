package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class SecondScreenPage extends PageObject {

	// Se definen objetos
	// Cuenta a debitar

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[1]/div[2]/select/option[2]")
	public WebElement listAccountsDebit;

	// Cuentas de beneficiario
	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[3]/div[2]/select/option")
	public List<WebElement> listAccountsBeneficiary;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[1]/div[2]/select")
	public WebElement selectAccounts;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[1]/div[2]/select/option")
	public List<WebElement> listAccountsToDebit;

	@FindBy(name = "inputAmmountValue")
	public WebElement amountAndCurrencyInput;

	@FindBy(xpath = "//button[contains(text(),'Continuar')]")
	public WebElement continueButton;

	@FindBy(xpath = "(//input[@name='numeralsChecks'])[1]")
	public WebElement commissionOurRadioButton;

	@FindBy(xpath = "(//input[@name='numeralsChecks'])[2]")
	public WebElement commissionBenShaRadioButton;

	@FindBy(xpath = "(//input[@name='numeralsChecks'])[3]")
	public WebElement commissionGOurRadioButton;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[8]/div[10]/textarea")
	public WebElement messageToTheBeneficiaryWithAllCommissionsTextArea;

	@FindBy(xpath = "//textarea[@placeholder='Ingrese máximo 140 caracteres']")
	public WebElement messageToTheBeneficiaryWithTwoCommissionsTextArea;

	@FindBy(xpath = "//*[@id='frameSetPpal']/frame[2]]")
	public WebElement scrollIframe;

	@FindBy(name = "iframeDivisas]")
	public WebElement currencyIframe;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[8]/div[3]/div[2]/p")
	public WebElement totalAmountInPesos;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[8]/div[3]/div[2]/p")
	public WebElement factorValue;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[8]/div[4]/div[2]/p")
	public WebElement totalAmountInPesos2;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[8]/div[1]/div[2]/p")
	public WebElement totalTasa;

	@FindBy(xpath = "//p[@refreshsession]")
	public WebElement commissionTarifa;

	@FindBy(xpath = "//div[@class='textCurrency']")
	public WebElement commission;

	private static String FIRST_SELECTED_ACCOUNT;

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

	public String getFirstBeneficiaryAccount() {
		FIRST_SELECTED_ACCOUNT = listAccountsBeneficiary.get(1).getText();
		return listAccountsBeneficiary.get(1).getText();
	}

	public static String getSavedFirstSelectedAccount() {
		return FIRST_SELECTED_ACCOUNT;
	}
}