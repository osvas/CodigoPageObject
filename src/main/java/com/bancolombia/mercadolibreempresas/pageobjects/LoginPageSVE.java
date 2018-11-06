package com.bancolombia.mercadolibreempresas.pageobjects;

import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;


@DefaultUrl("https://svebc.qa04.todo1.com/SVE/control/BoleTransactional.bancolombia")
public class LoginPageSVE extends PageObject {
	@FindBy(id = "COMPANYID")
	public WebElementFacade nitField;

	@FindBy(id = "CLIENTID")
	public WebElementFacade usernameField;

	@FindBy(id = "USERPASS")
	public WebElementFacade password;

	@FindBy(id = "titulo_sve")
	public WebElementFacade titleSVE;

	@FindBy(xpath = "//input[@value='Aceptar']")
	public WebElement buttonSubmit;

	public LoginPageSVE(WebDriver driver) {
		super(driver);
	}

	public WebElementFacade getTitleSVE() {
		return titleSVE;
	}

	public WebDriver getWebDriver() {
		return this.getDriver();
	}

	public void clickPasswordField() {
		password.click();
	}

	public void textUserNameInfo(String nit, String username) {
		nitField.sendKeys(nit);
		usernameField.sendKeys(username);
	}

	public void clickOnSubmit() {
		buttonSubmit.click();
	}

}
