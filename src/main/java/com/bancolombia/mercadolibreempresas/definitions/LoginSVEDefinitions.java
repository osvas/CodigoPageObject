package com.bancolombia.mercadolibreempresas.definitions;

import com.bancolombia.mercadolibreempresas.steps.LoginStepsSVE;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class LoginSVEDefinitions {
	@Steps
	private LoginStepsSVE loginSteps;

	@Given(value = "^Abro la pagina SVE$")
	public void openPageSVE() {
		loginSteps.openSVE();
	}

	@When(value = "^Inicio sesion en la sucursal virtual empresas$")
	public void signIn() throws InterruptedException {
		loginSteps.singInSVE();
	}
	
	@When(value = "^Inicio sesion en la sucursal virtual empresas con usuario aprobador$")
	public void logInWithApprovingUser() {
		loginSteps.logInWithApprovingUser();
	}
}
