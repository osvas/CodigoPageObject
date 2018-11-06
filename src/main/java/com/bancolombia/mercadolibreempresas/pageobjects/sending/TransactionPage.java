package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.bancolombia.mercadolibreempresas.utilities.Util;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class TransactionPage extends PageObject {
	@FindBy(name = "TransactionFrame")
	public WebElement transactionFrame;

	@FindBy(linkText = "Pendiente de Aprobación")
	public List<WebElement> pendienteAprobacionLink;

	@FindBy(id = "checkbox")
	public List<WebElement> checkboxList;

	@FindBy(id = "aprobar")
	public WebElement approveButton;

	@FindBy(id = "aceptar")
	public WebElement aceptarButton;

	@FindBy(name = "Terminar")
	public WebElement terminarButton;

	@FindBy(linkText = "Ver Histórico de Transacciones")
	public WebElement historicoLink;

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
