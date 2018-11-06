package com.bancolombia.mercadolibreempresas.pageobjects.sending;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.serenitybdd.core.pages.PageObject;

public class TransactionStatusPage extends PageObject {
	@FindBy(xpath = "//table[@id='estadoTransacciones']/tbody/tr")
	public List<WebElement> transactionList;

	@FindBy(id = "checkbox")
	public List<WebElement> checkboxList;

	@FindBy(id = "aprobar")
	public WebElement approveButton;
}
