package com.bancolombia.mercadolibreempresas.testqueries;

import java.util.List;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CdcffapertQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibffMensaQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.CibfflgfndQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.filequeries.GeneralQueriesToFiles;

public class TestFilesQueries {

	@Test
	public void testGetFileData() {
		CdcffapertQueries queries = new CdcffapertQueries();
		List<List<String>> data = queries.getFileData("025060002493", "JunitTest");

		for (List<String> list : data) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).trim());
			}

		}
	}

	@Test
	public void testGetTheNameOfTheBankThroughTheCodeAndTheSubcode() {
		GeneralQueriesToFiles queries = new GeneralQueriesToFiles();
		String nameOfBank = queries.getTheNameOfTheBankThroughTheCodeAndTheSubcodeInCIBFFCOBAH("1116", "464",
				"JunitTest");

		System.out.println(nameOfBank);
	}

	@Test
	public void testGetTheNameOfTheBankInCIBFFCONTR() {
		GeneralQueriesToFiles queries = new GeneralQueriesToFiles();
		String nameOfBank = queries.getTheNameOfTheBankInCIBFFCONTR("5860.93", "JunitTest");

		System.out.println(nameOfBank);
	}

	@Test
	public void testGetCodeSubcodeInCIBFFLGFND() {
		CibfflgfndQueries queries = new CibfflgfndQueries();
		List<List<String>> data = queries.getCodeSubcodeInCIBFFLGFND("1218.25", "JunitTest");

		for (List<String> list : data) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).trim());
			}

		}
	}

	@Test
	public void testGetSwiftCodeInCIBFFMENSA() {
		CibffMensaQueries queries = new CibffMensaQueries();
		String swiftCode = queries.getSwiftCodeInCIBFFMENSA("025060002496", "JunitTest");

		System.out.println(swiftCode);
	}
}
