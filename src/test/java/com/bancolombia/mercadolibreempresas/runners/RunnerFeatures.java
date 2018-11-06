package com.bancolombia.mercadolibreempresas.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
//@CucumberOptions(features = "src/test/resources/features/SecondScreen.feature",tags = "@CasoExitoso", glue = "com.bancolombia.mercadolibreempresas.definitions")
@CucumberOptions(features = "src/test/resources/features/loginSVE.feature",tags = "@loginSve", glue = "com.bancolombia.mercadolibreempresas.definitions")
public class RunnerFeatures {

}
