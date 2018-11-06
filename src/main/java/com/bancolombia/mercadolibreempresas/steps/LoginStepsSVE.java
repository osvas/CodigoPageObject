package com.bancolombia.mercadolibreempresas.steps;

import org.fluentlenium.core.annotation.Page;

import com.bancolombia.mercadolibreempresas.pageobjects.LoginPageSVE;
import com.bancolombia.mercadolibreempresas.utilities.PropertiesReader;
import com.bancolombia.mercadolibreempresas.utilities.RunEvents;

import net.thucydides.core.annotations.Step;

import com.bancolombia.mercadolibreempresas.pageobjects.Keyboard;

public class LoginStepsSVE {

	@Page
	private LoginPageSVE loginPage;
	private Keyboard keyboard;

	@Step
	public void openSVE() {
		loginPage.open();
		keyboard = new Keyboard(loginPage.getDriver());
	}

	@Step
	public void clickPasswordField() {
		loginPage.clickPasswordField();
	}

	@Step
	public void textUserNameInfo(String nit, String username) {
		loginPage.nitField.sendKeys(nit);
		loginPage.usernameField.sendKeys(username);
		// loginPage.textUserNameInfo(nit, username);
	}

	@Step
	public void clickOnSubmit() throws InterruptedException {
		RunEvents.loadStandBy(2);
		loginPage.buttonSubmit.click();
		// loginPage.clickOnSubmit();
	}

	@Step
	public void enterPasswordOnField(String password) throws InterruptedException {
		keyboard.setLoginPage(loginPage);
		keyboard.enterPassword(password);
	}

	@Step
	public void singInSVE(String nit, String username, String password) throws InterruptedException {
		loginPage.textUserNameInfo(nit, username);
		loginPage.clickPasswordField();
		keyboard.setLoginPage(loginPage);
		keyboard.enterPassword(password);
		loginPage.clickOnSubmit();
	}

	@Step
	public void singInSVE() throws InterruptedException {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		String nit = PropertiesReader.getInstance().getProperty("user.nit");
		String username = PropertiesReader.getInstance().getProperty("user.name");
		String password = PropertiesReader.getInstance().getProperty("user.password");

		loginPage.textUserNameInfo(nit, username);
		loginPage.clickPasswordField();
		keyboard.setLoginPage(loginPage);
		keyboard.enterPassword(password);
		loginPage.clickOnSubmit();
	}
	
	@Step
	public void logInWithApprovingUser() {
		PropertiesReader.initInstanceByEnum(PropertiesReader.PropertiesEnum.UserData.toString());
		String nit = PropertiesReader.getInstance().getProperty("user.nit");
		String username = PropertiesReader.getInstance().getProperty("approvinguser.name");
		String password = PropertiesReader.getInstance().getProperty("approvinguser.password");

		loginPage.textUserNameInfo(nit, username);
		loginPage.clickPasswordField();
		keyboard.setLoginPage(loginPage);
		keyboard.enterPassword(password);
		loginPage.clickOnSubmit();
	}
}
