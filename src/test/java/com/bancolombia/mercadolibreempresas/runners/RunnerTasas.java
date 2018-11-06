package com.bancolombia.mercadolibreempresas.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/UsuarioSinTasas.feature", tags = "@SinTasas", glue="com.bancolombia.mercadolibreempresas.definitions")

public class RunnerTasas {
	

}
