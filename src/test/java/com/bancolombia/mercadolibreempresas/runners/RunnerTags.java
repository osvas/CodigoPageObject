package com.bancolombia.mercadolibreempresas.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		features = "src/test/resources/features/recepcion.feature",
		tags = "@Recepcion",
		snippets = SnippetType.CAMELCASE,
		glue = "com.bancolombia.mercadolibreempresas.definitions"
)
public class RunnerTags {

}
