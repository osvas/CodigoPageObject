package com.bancolombia.mercadolibreempresas.utilities;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class LeerExcel {
	// ruta del documento a leer
	private static String[][] cargarValores;

	public static String[][] leerArchivoExcel(int rows, int columns, String nameOfFile, String nameOfSheet) {
		String documentoDir = "./" + nameOfFile + ".xls";
		cargarValores = new String[rows][columns];
		try {
			// Se crea una referencia al documento excel
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(documentoDir));
			// indicamos la hoja que queremos leer
			HSSFSheet sheet = workbook.getSheet(nameOfSheet);
			// recorremos filas
			int fila = 0;
			int columna = 0;
			for (Row row : sheet) {
				columna = 0;
				for (Cell cell : row) {
					if (cell != null) {
						try {
							cargarValores[fila][columna] = "" + cell.getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
							e = null;
						}
					}
					columna++;
				}
				fila++;
			}
		} catch (Exception e) {
			System.out.println("Error! " + e);
		}
		return cargarValores;
	}
}
