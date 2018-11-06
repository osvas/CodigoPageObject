package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bancolombia.mercadolibreempresas.utilities.RunEvents;

import net.serenitybdd.core.pages.PageObject;

public class TasasPage extends PageObject {
	// Definición de los objetos
	@FindBy(xpath = "//div[@id='Wrap-Cont']/div[5]/div/div/p")
	public WebElement textoTasasInactivas;

	@FindBy(xpath = "/html/body/div[48]/table/tbody/tr[3]/td/table/tbody/tr/td/iframe")
	private WebElement frameMld;
	
	@FindBy(xpath = "//*[@id=\"Wrap-Cont\"]/div[5]/div/div/div")
	public WebElement iconoAdvertencia;

	public void initMld() throws InterruptedException {
		RunEvents.loadStandBy(1);
		this.getDriver().switchTo().frame(frameMld);
		RunEvents.loadStandBy(2);
		
		System.out.println("Texto de las clases: " + iconoAdvertencia.getAttribute("class"));
		
	}
	
	

}
