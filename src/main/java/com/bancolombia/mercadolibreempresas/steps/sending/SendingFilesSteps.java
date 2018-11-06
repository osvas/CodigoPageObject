package com.bancolombia.mercadolibreempresas.steps.sending;

import java.util.Map;

import com.bancolombia.mercadolibreempresas.models.files.Cdcffpagir;
import com.bancolombia.mercadolibreempresas.models.files.Cibffdccns;
import com.bancolombia.mercadolibreempresas.models.files.Cibffmov;
import com.bancolombia.mercadolibreempresas.models.files.GeneratorObjectComparing;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.SendingQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.infosearcher.SearcherFilesByFields;

public class SendingFilesSteps {

	private static SearcherFilesByFields searcher = new SearcherFilesByFields();
	private SendingQueries sendingQueries = new SendingQueries();
	private static GeneratorObjectComparing genObjComparing = new GeneratorObjectComparing();
	private static ValidateSendingFiles validateSendingFiles = new ValidateSendingFiles();

	public void triggerValidateFileCibffmov() {

		String remmitanceNumber = "25060002313";
		String amount = "3550";

		validateFileCibffmov(remmitanceNumber, amount);
	}

	public void validateFileCibffmov(String remmitanceNumber, String amount) {
		Cibffmov cibffmov = genObjComparing.generateComparingObjMov(remmitanceNumber, Double.parseDouble(amount));
		String consdiv = sendingQueries.searchConsDivByNroGiro(Util.addingNumbersToValue(remmitanceNumber, 12, "0"));
		Map<String, String> cibffMovActual = searcher.searchMappedFileByColumns(consdiv, Cibffmov.class.getName());

		validateSendingFiles.validateFileCibffmov(cibffmov, cibffMovActual);
	}

	public void validateFileCibffdccns(String remmitanceNumber, String currentDate, String format) {
		Cibffdccns cibffdccns = genObjComparing.generateComparingObjDccns(remmitanceNumber, currentDate, format);
		String consdiv = sendingQueries.searchConsDivByNroGiro(Util.addingNumbersToValue(remmitanceNumber, 12, "0"));
		Map<String, String> cibffdccnsActual = searcher.searchMappedFileByColumns(consdiv, Cibffdccns.class.getName());

		validateSendingFiles.validateFilecibffdccns(cibffdccns, cibffdccnsActual);
	}

	public void validateFileCibffpagir(String remmNumber, String currency, String currentDate, String amount) {

		Cdcffpagir cdcffpagir = genObjComparing.generateComparingObjPagir(remmNumber, currency, currentDate,
				SendingQueries.NIT, Double.parseDouble(amount));
		Map<String, String> mapcdcffpagirActual = searcher.searchMappedFileByColumns(remmNumber,
				Cdcffpagir.class.getName());

		validateSendingFiles.validateFilecdcffpagir(cdcffpagir, mapcdcffpagirActual);

	}
}
