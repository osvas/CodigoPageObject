package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class TransactionHistoryPage extends PageObject {
	@FindBy(linkText = "Ejecutada")
	public List<WebElement> estadosLink;
	
	@FindBy(xpath = "/html/body/table/tbody/tr[4]/td[2]/form[1]/table[1]/tbody/tr[2]/td[4]/a")
	public WebElement historicoUltimaTransaccion;

	@FindBy(xpath = "//tr[16]/td[2]")
	public WebElement changeDeclarationNumber;

	@FindBy(xpath = "//tr[17]/td[2]")
	public WebElement shippingReference;

	@FindBy(xpath = "/html/body/table/tbody/tr[4]/td[2]/form/table/tbody/tr[3]/td/table/tbody/tr/td[1]/div/b")
	public List<WebElement> labelsTransaction;

	@FindBy(xpath = "/html/body/table/tbody/tr[4]/td[2]/form/table/tbody/tr[3]/td/table/tbody/tr/td[2]")
	public List<WebElement> textsTransaction;

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
