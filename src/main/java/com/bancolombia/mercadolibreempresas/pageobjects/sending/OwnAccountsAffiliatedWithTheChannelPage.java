package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.pages.PageObject;

public class OwnAccountsAffiliatedWithTheChannelPage extends PageObject {

	// Definición de los objetos
	@FindBy(xpath = "/html/body/table/tbody/tr[4]/td[2]/form[1]/table/tbody/tr[1]/td/table/tbody/tr/td[1]/div")
	public List<WebElement> ownAccounts;

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
