package com.bancolombia.mercadolibreempresas.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/AccountToDebit.feature", tags = "@OnlyAffiliatedAccountsToChannel", glue="com.bancolombia.mercadolibreempresas.definitions")

public class AccountToDebit {

}
