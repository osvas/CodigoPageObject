package com.bancolombia.mercadolibreempresas.utilities.database.infosearcher;

import java.util.HashMap;
import java.util.Map;

import com.bancolombia.mercadolibreempresas.models.files.Cdcffpagir;
import com.bancolombia.mercadolibreempresas.models.files.Cibffdc053;
import com.bancolombia.mercadolibreempresas.models.files.Cibffdccns;
import com.bancolombia.mercadolibreempresas.models.files.Cibffdcl05;
import com.bancolombia.mercadolibreempresas.models.files.Cibffentml;
import com.bancolombia.mercadolibreempresas.models.files.Cibffmov;
import com.bancolombia.mercadolibreempresas.models.librariesfiles.Parents;
import com.bancolombia.mercadolibreempresas.utilities.SplittingValues;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.BasicDao;

/**
 * 
 * @author simon.rua
 *
 */
public class SearcherFilesByFields {

	private static BasicDao basicDao;

	public SearcherFilesByFields() {
		basicDao = new BasicDao();
	}

	/**
	 * 
	 * @param reference
	 * @return
	 */
	public Map<String, String> searchMappedFileByColumns(String reference, String file) {
		Map<String, String> mapData = new HashMap<String, String>();
		String searchCondition = "";

		if (Cibffdc053.class.getName().equals(file)) {

			searchCondition = "NUMERODCL = " + reference + "";
			mapData = searchFile(searchCondition, Cibffdc053.class, Parents.CIBLIBRAMD.toString());

		} else if (Cibffdcl05.class.getName().equals(file)) {

			searchCondition = "NUMERODCL = '" + reference + "'";
			mapData = searchFile(searchCondition, Cibffdcl05.class, Parents.CIBLIBRAMD.toString());

		} else if (Cibffdccns.class.getName().equals(file)) {

			searchCondition = "CONSECU = '" + reference + "'";
			mapData = searchFile(searchCondition, Cibffdccns.class, Parents.CIBLIBRAMD.toString());

		} else if (Cibffentml.class.getName().equals(file)) {
			searchCondition = "CONSREG = " + reference + "";
			mapData = searchFile(searchCondition, Cibffentml.class, Parents.CIBLIBRAMD.toString());

		} else if (Cdcffpagir.class.getName().equals(file)) {

			searchCondition = "NROGIRO = '" + Util.addingNumbersToValue(reference, 12, "0") + "'";
			mapData = searchFile(searchCondition, Cdcffpagir.class, Parents.CDCLIBRAMD.toString());
		} else if (Cibffmov.class.getName().equals(file)) {

			searchCondition = "MDCNSREG = '" + reference + "'";
			mapData = searchFile(searchCondition, Cibffmov.class, Parents.CIBLIBRAMD.toString());
		}

		return mapData;
	}

	/**
	 * 
	 * @param condition
	 * @param classFile
	 * @param parent
	 * @return
	 */
	private Map<String, String> searchFile(String condition, Class<?> classFile, String parent) {

		String fields = Util.inspectColumns(classFile, SplittingValues.SEPARATOR_FIELDS_ON_QUERIES);
		String fileName = Util.getDataFromSplittedValueByPosition(classFile.getName(), "\\.", 5);
		String[] resultQry;
		resultQry = basicDao.searchDataFieldsCondition(fields.toUpperCase(), parent, fileName.toUpperCase(), condition,
				"Consulta archivo " + classFile.getName().toUpperCase());

		return Util.generateMapDataByFields(fields.toUpperCase(), SplittingValues.SEPARATOR_FIELDS_ON_QUERIES,
				resultQry);
	}

}
