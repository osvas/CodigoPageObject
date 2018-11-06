package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class DetailOfTheTransactionPage extends PageObject {
	@FindBy(xpath = "//tr[3]/td/table/tbody/tr[2]/td[2]")
	public WebElement accountToDebit;

	@FindBy(xpath = "//tr[3]/td/table/tbody/tr[4]/td[2]")
	public WebElement nameOfTheBeneficiary;

	@FindBy(xpath = "//tr[3]/td/table/tbody/tr[5]/td[2]")
	public WebElement customName;

	@FindBy(xpath = "//tr[5]/td[2]")
	public WebElement accountOfTheBeneficiary;

	@FindBy(xpath = "//tr[6]/td[2]")
	public WebElement SWIFTCodeOfTheBeneficiaryBank;

	@FindBy(xpath = "//tr[8]/td[2]")
	public WebElement amountAndCurrency;

	@FindBy(xpath = "//tr[9]/td[2]")
	public WebElement rate;

	@FindBy(xpath = "//tr[10]/td[2]")
	public WebElement factor;

	@FindBy(xpath = "//tr[11]/td[2]")
	public WebElement amountInPesosOfTheValueToSend;

	@FindBy(xpath = "//tr[12]/td[2]")
	public WebElement typeOfCommission;

	@FindBy(xpath = "//tr[14]/td[2]")
	public WebElement totalAmountToDebit;

	@FindBy(name = "regresar")
	public WebElement regresarButton;

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
