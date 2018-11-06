package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bancolombia.mercadolibreempresas.utilities.RunEvents;
import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.pages.PageObject;

public class AccountToDebitPage extends PageObject {

	// Definición de los objetos

	@FindBy(xpath = "/html/body/div[48]/table/tbody/tr[3]/td/table/tbody/tr/td/iframe")
	public WebElement frameMld;

	// Check box de los registros de la pantalla de tasas
	@FindBy(xpath = "/html/body/my-app/app-rate/div/div/div[5]/div/table/tbody/tr/td[1]/input")
	public List<WebElement> checkboxOfTable;

	// Boton continuar de la pantalla de tasas
	@FindBy(xpath = "//div[@id='Wrap-Cont']/div[7]/div/button")
	public WebElement buttonContinueRates;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[3]/div/div/div")
	public WebElement warningDivMessage;

	// Mensaje de alerta sin cuentas inscritas
	@FindBy(xpath = "//div[@id='Wrap-Cont']/div[3]/div/div/p")
	public WebElement warningMessageWithoutRegisteredInternationalAccounts;

	// lista de cuentas del campo Cuenta a debitar
	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[1]/div[2]/select/option")
	public List<WebElement> listOfAccountsToDebit;

	@FindBy(xpath = "//*[@id='Wrap-Cont']/div[4]/div[1]/div[2]/select")
	public WebElement selectAccountToDebit;

	public void swichToIframe() {
		RunEvents.loadStandBy(1);
		this.getDriver().switchTo().frame(frameMld);
		RunEvents.loadStandBy(2);

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
