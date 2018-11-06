package com.bancolombia.mercadolibreempresas.definitions.recepcion;

import java.util.List;

import com.bancolombia.mercadolibreempresas.models.EvidenceData;
import com.bancolombia.mercadolibreempresas.utilities.database.recepcion.RecepcionQueries;

import cucumber.api.java.en.Given;

public class RecepcionDefinitions {
	@Given("^Ingreso giro con la divisa \"([^\"]*)\"$")
	public void ingresoGiroConLaDivisa(String divisa, List<EvidenceData> evidence) {
		String testCaseName = evidence.get(0).getNameOfDirectory() + " " + divisa.toUpperCase();
		RecepcionQueries queries = new RecepcionQueries();
		// Se eliminan los giros existentes
		queries.borrarGirosDelCliente(testCaseName);
		queries.insertarGiros(divisa, 1, testCaseName);
	}
}
