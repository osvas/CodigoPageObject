package com.bancolombia.mercadolibreempresas.utilities;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class GenerateExcelWithSwiftCodesPagen {
	public static void exportarExcel(int rows, int columns, String[][] data, String nombreArchivo) throws Exception {
		// ruta donde se creará el archivo
		String rutaArchivo = "./" + nombreArchivo + ".xls";
		// Se crea el objeto de tipo File con la ruta del archivo
		File archivoXLS = new File(rutaArchivo);

		/* Si el archivo existe se elimina */
		if (archivoXLS.exists()) {
			archivoXLS.delete();
		}

		/* Se crea el archivo */
		archivoXLS.createNewFile();

		// Se crea el libro de excel usando el objeto de tipo Workbook
		Workbook libro = new HSSFWorkbook();
		// Se inicializa el flujo de datos con el archivo xls
		FileOutputStream archivo = new FileOutputStream(archivoXLS);

		/*
		 * Utilizamos la clase Sheet para crear una nueva hoja de trabajo dentro del
		 * libro que creamos anteriormente
		 */
		Sheet hoja = libro.createSheet("Codigos Swift CIBFFPAGEN");

		// Hacemos un ciclo para inicializar los valores de las filas
		for (int fila = 0; fila < rows; fila++) {
			// Se crea la fila
			Row row = hoja.createRow(fila);
			// Número de columnas que tendrá la fila actual
			for (int column = 0; column < columns; column++) {
				// Se crea la celda a partir de la columna actual
				Cell cell = row.createCell(column);
				cell.setCellValue(data[fila][column]);
			}
		}

		// Se escribe en el libro
		libro.write(archivo);
		// Se cierra el flujo de datos
		archivo.close();
	}
}
