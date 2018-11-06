package com.bancolombia.mercadolibreempresas.testqueries;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.utilities.database.commission.CommissionQueries;

public class TestCommissionQueries {
	private static CommissionQueries commission;

	@Before
	public void initTest() {
		commission = new CommissionQueries();
	}

	@Test
	public void testSearchUserInTheCentralCommission() {
		List<List<String>> user = commission.searchUserInTheCentralCommission("JUNITTEST");

		if (user.isEmpty()) {
			System.out.println("entró");
		} else {
			System.out.println("no entró");
		}

		for (List<String> list : user) {
			for (String string : list) {
				System.out.println("Valor encontrado: " + string);
			}
		}
	}

	@Test
	public void testInsertUserInTheCentralCommission() {
		commission.insertUserInTheCentralCommission("JUNITTEST");
	}

	@Test
	public void testUpdateUserInTheCentralCommission() {
		commission.updateTarifaInTheCentralCommission("40", "50", "60", "JUNITTEST");
	}

	@Test
	public void testDeleteUserInTheCentralCommission() {
		commission.deleteUserInTheCentralCommission("JUNITTEST");
	}

	@Test
	public void testSearchValueTrmToday() {
		System.out.println(commission.searchValueTrmToday("JUNITTEST"));
	}

	@Test
	public void testGetTarifaPlena() {
		System.out.println(commission.getTarifaPlena("JUNITTEST"));
	}

	@Test
	public void testGetTarifaSpecial() {
		System.out.println(commission.getTarifaSpecial("JUNITTEST"));
	}

	@Test
	public void testGetTarifaMinima() {
		System.out.println(commission.getTarifaMinima("JUNITTEST"));
	}

	@Test
	public void testGetIva() {
		System.out.println(commission.getIva("JUNITTEST"));
	}

	@Test
	public void testGetTarifaPlenaUsuarioGenerico() {
		System.out.println(commission.getTarifaPlenaUsuarioGenerico("JUNITTEST"));
	}

	@Test
	public void testGetTarifaPlenaParametroComnbensha() {
		System.out.println(commission.getTarifaPlenaParametroComnbensha("JUNITTEST"));
	}

	@Test
	public void testGetCodeBankFromCIBFFBANCO() {
		String codigoSwift = "BKTRUS33XXX";
		String nuevoCodigo = codigoSwift.substring(0, 8) + "X" + codigoSwift.substring(8);
		System.out.println(commission.getCodeBankFromCIBFFBANCO("JUNITTEST", nuevoCodigo));
	}

	@Test
	public void testGetCurrencyCodeFromCIBFFMONMD() {
		System.out.println(commission.getCurrencyCodeFromCIBFFMONMD("USD", "JUNITTEST"));
	}

	@Test
	public void testGetCodeVGourFromCDCFFCDGOS() {
		List<List<String>> code = commission.getCodeVGourFromCDCFFCDGOS("111616", "JUNITTEST");
		System.out.println(code);
		if (code.isEmpty()) {
			System.out.println("Está vacío");
		} else {
			System.out.println("Está lleno");
		}
	}

	@Test
	public void testInsertCodeVGourIntoCDCFFCDGOS() {
		commission.insertCodeVGourIntoCDCFFCDGOS("000001", "JUNITTEST");
	}

	@Test
	public void testGetTarifaPlenaParametroCOMNEGGOUR() {
		System.out.println(commission.getTarifaPlenaParametroCOMNEGGOUR("JUNITTEST"));
	}

	@Test
	public void testInsertParametroCOMNEGGOUR() {
		commission.insertParametroCOMNEGGOUR("JUNITTEST");
	}

	@Test
	public void testDeleteParametroCOMNEGGOUR() {
		commission.deleteParametroCOMNEGGOUR("JUNITTEST");
	}

	@Test
	public void testGetParametroCOMNEGGOUR() {
		List<List<String>> datos = commission.getParametroCOMNEGGOUR("JUNITTEST");

		if (datos.isEmpty()) {
			System.out.println("Está vacío");
		} else {
			System.out.println("Está lleno");
		}
	}
}
