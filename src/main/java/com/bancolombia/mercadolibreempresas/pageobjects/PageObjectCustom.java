package com.bancolombia.mercadolibreempresas.pageobjects;

//Comentario de ejercicio
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class PageObjectCustom {
	protected WebDriver driver;

	public PageObjectCustom(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}

	public void setWebDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getWebDriverCustom() {
		return driver;
	}
}
