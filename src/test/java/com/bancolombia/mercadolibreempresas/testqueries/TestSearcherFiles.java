package com.bancolombia.mercadolibreempresas.testqueries;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.models.files.Cdcffpagir;
import com.bancolombia.mercadolibreempresas.models.files.Cibffdccns;
import com.bancolombia.mercadolibreempresas.models.files.Cibffentml;
import com.bancolombia.mercadolibreempresas.models.files.Cibffmov;
import com.bancolombia.mercadolibreempresas.utilities.database.infosearcher.SearcherFilesByFields;

public class TestSearcherFiles {

	private static SearcherFilesByFields searcher;
	private static Map<String, String> map;

	@Before
	public void init() {
		searcher = new SearcherFilesByFields();
	}

	@Test
	public void testSearcherFilesByFieldsInCibffmov() {
		String consecu = "12819";
		map = searcher.searchMappedFileByColumns(consecu, Cibffmov.class.getName());

		assertEquals(true, map.size() > 0);
	}

	@Test
	public void testSearcherFilesByFieldsInCibffentml() {
		String consecu = "12819";
		map = searcher.searchMappedFileByColumns(consecu, Cibffentml.class.getName());

		assertEquals(true, map.size() > 0);
	}

	@Test
	public void testSearcFileByFieldsInCibffdccns() {
		String consecu = "12819";
		map = searcher.searchMappedFileByColumns(consecu, Cibffdccns.class.getName());

		assertEquals(true, map.size() > 0);
	}

	@Test
	public void testSearchFileByFieldsInCdcffpagir() {
		String consecu = "025060002313";
		map = searcher.searchMappedFileByColumns(consecu, Cdcffpagir.class.getName());

		assertEquals(true, map.size() > 0);
	}

}
