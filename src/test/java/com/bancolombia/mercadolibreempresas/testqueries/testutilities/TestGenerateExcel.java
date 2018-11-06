package com.bancolombia.mercadolibreempresas.testqueries.testutilities;

import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.GenerateExcelWithSwiftCodesPagen;
import com.bancolombia.mercadolibreempresas.utilities.LeerExcel;

public class TestGenerateExcel {

	@Test
	public void testGenerateExcel() {
		String[][] data = new String[1][2];
		data[0][0] = "AAAAAAAAAAAA";
		data[0][1] = "BBBBBBBBBBBBB";
		try {
			GenerateExcelWithSwiftCodesPagen.exportarExcel(1, 2, data, "PruebaOscar");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLeerExcel() {
		String[][] dataExcel = LeerExcel.leerArchivoExcel(1, 2, "PruebaOscar", "Codigos Swift CIBFFPAGEN");

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.println(dataExcel[i][j]);
			}
		}
	}
}
