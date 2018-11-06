package com.bancolombia.mercadolibreempresas.utilities.database;

public class PriorityInErrorMessagesQueries {
	private static final String USER_CODE_IN_CONTROL_LISTS = "104";
	private static final String CIB_IS_NOT_AVAILABLE = "382";
	private static final String USER_WITHOUT_RATES = "107";

	public void setFirstUserInControlList() {
		System.out.println("Hizo la primer consulta");
		BasicDao basicDao = new BasicDao();
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 1",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALLIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 2",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALDIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 3",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALCTR'", "PriorityInErrorMessages");
	}

	public void setSecondUserInControlList() {
		System.out.println("Hizo la segunda consulta");
		BasicDao basicDao = new BasicDao();
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 2",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALLIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 1",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALDIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 3",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALCTR'", "PriorityInErrorMessages");
	}

	public void setFirstCIBIsNotAvailable() {
		BasicDao basicDao = new BasicDao();
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 2",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALLIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 1",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALDIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 3",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALCTR'", "PriorityInErrorMessages");
	}

	public void setSecondCIBIsNotAvailable() {
		BasicDao basicDao = new BasicDao();
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 1",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALLIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 2",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALDIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 3",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALCTR'", "PriorityInErrorMessages");
	}

	public void setFirstUserWithoutFees() {
		BasicDao basicDao = new BasicDao();
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 3",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALLIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 2",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALDIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 1",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALCTR'", "PriorityInErrorMessages");
	}

	public void setSecondUserWithoutFees() {
		BasicDao basicDao = new BasicDao();
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 3",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALLIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 1",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALDIS'", "PriorityInErrorMessages");
		basicDao.updateQryDML("CIBLIBRAMD", "CIBFFVALGN", "VCONSECU = 2",
				"VCANAL = '04' AND VTIPOPER = 'N' AND VPGM = 'CIBOVALCTR'", "PriorityInErrorMessages");
	}

	public String[] getMessageOfAttentionLines(String testCaseName) {
		BasicDao basicDao = new BasicDao();
		String[] resultado = basicDao.searchDataFieldsCondition("CAMALF", "CIBLIBRAMD", "CIBFFPAGEN",
				"CODCAN = 4 AND CODFUN = 'LINORISVE'", testCaseName);
		return resultado;
	}

	public String[] getMessageOfUserInChecklist(String testCaseName) {
		BasicDao basicDao = new BasicDao();
		String[] resultado = basicDao.searchDataFieldsCondition("MENS2SVRME", "WWWLIBRAMD", "WWWFFMENS2",
				"MENS2CODTR = '0236' AND MENS2CODME='" + USER_CODE_IN_CONTROL_LISTS + "'", testCaseName);

		return resultado;
	}

	public String[] getMessageOfUserWithoutRates(String testCaseName) {
		BasicDao basicDao = new BasicDao();
		String[] resultado = basicDao.searchDataFieldsCondition("MENS2SVRME", "WWWLIBRAMD", "WWWFFMENS2",
				"MENS2CODTR = '0236' AND MENS2CODME='" + USER_WITHOUT_RATES + "'", testCaseName);

		return resultado;
	}

	public String[] getMessageOfCibIsNotAvailable(String testCaseName) {
		BasicDao basicDao = new BasicDao();
		String[] resultado = basicDao.searchDataFieldsCondition("MENS2SVRME", "WWWLIBRAMD", "WWWFFMENS2",
				"MENS2CODTR = '0236' AND MENS2CODME='" + CIB_IS_NOT_AVAILABLE + "'", testCaseName);

		return resultado;
	}
}
