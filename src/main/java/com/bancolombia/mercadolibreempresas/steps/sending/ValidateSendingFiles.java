package com.bancolombia.mercadolibreempresas.steps.sending;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.logging.Logger;

import com.bancolombia.mercadolibreempresas.models.files.Cdcffpagir;
import com.bancolombia.mercadolibreempresas.models.files.Cibffdccns;
import com.bancolombia.mercadolibreempresas.models.files.Cibffmov;


public class ValidateSendingFiles {

	private static final Logger LOGGER = Logger.getLogger(ValidateSendingFiles.class.getName());

	public void validateFileCibffmov(Cibffmov cibffmov, Map<String, String> cibffMovActual) {

		comparingData("Comparando MDTPOREG", Cibffmov.MDTPOREG, cibffMovActual.get("MDTPOREG"));
		comparingData("Comparando MDCNSREG", cibffmov.getMdCnsReg(), cibffMovActual.get("MDCNSREG"));
		comparingData("Comparando MDRPMREG", Cibffmov.MDPRMREG, cibffMovActual.get("MDPRMREG"));
		comparingData("Comparando MDANTREG", Cibffmov.MDANTREG, cibffMovActual.get("MDANTREG"));
		comparingData("Comparando MDSIGREG", Cibffmov.MDSIGREG, cibffMovActual.get("MDSIGREG"));

		comparingData("Comparando MDCODTRN", cibffmov.getMdCodTrn(), cibffMovActual.get("MDCODTRN"));
		comparingData("Comparando MDCODREG", cibffmov.getMdCodReg(), cibffMovActual.get("MDCODREG"));
		comparingData("Comparando MDCODSTM", Cibffmov.MDCODSTM, cibffMovActual.get("MDCODSTM"));

		comparingData("Comparando MDOFCORG", Cibffmov.MDOFCORG, cibffMovActual.get("MDOFCORG"));
		comparingData("Comparando MDOFCDST", Cibffmov.MDOFCDST, cibffMovActual.get("MDOFCDST"));
		comparingData("Comparando MDCODEST", Cibffmov.MDCODEST, cibffMovActual.get("MDCODEST"));
		comparingData("Comparando MDCNSTRM", cibffmov.getMdCnsTrm(), cibffMovActual.get("MDCNSTRM"));

		comparingData("Comparando MDACMTOT", Cibffmov.MDACMTOT, cibffMovActual.get("MDACMTOT"));
		comparingData("Comparando MDACMSBT", Cibffmov.MDACMSBT, cibffMovActual.get("MDACMSBT"));
		comparingData("Comparando MDINDATR", Cibffmov.MDINDATR, cibffMovActual.get("MDINDATR"));
		comparingData("Comparando MDVALOR", cibffmov.getMdValor(), Double.parseDouble(cibffMovActual.get("MDVALOR")),
				0);
	}

	public void validateFilecibffdccns(Cibffdccns cibffdccns, Map<String, String> mapCibdccnsActual) {

		comparingData("Comparando OFCORIG", Cibffdccns.OFCORIG, mapCibdccnsActual.get("OFCORIG"));
		comparingData("Comparando ANODCL", cibffdccns.getAnoDcl(), mapCibdccnsActual.get("ANODCL"));
		comparingData("Comparando MESDCL", cibffdccns.getMesDcl(), mapCibdccnsActual.get("MESDCL"));
		comparingData("Comparando DIADCL", cibffdccns.getDiaDcl(), mapCibdccnsActual.get("DIADCL"));
		comparingData("Comparando CONSECU", cibffdccns.getConsecu(), mapCibdccnsActual.get("CONSECU"));
		comparingData("Comparando TIPODCL", cibffdccns.getTipoDcl(), mapCibdccnsActual.get("TIPODCL"));
		comparingData("Comparando OFCCIO", cibffdccns.getOfccio(), mapCibdccnsActual.get("OFCCIO"));
		comparingData("Comparando NITDCL", cibffdccns.getNitDcl(), mapCibdccnsActual.get("NITDCL").trim());
		comparingData("Comparando TIPONIT", cibffdccns.getTipoNit(), mapCibdccnsActual.get("TIPONIT").trim());
	}

	public void validateFilecdcffpagir(Cdcffpagir cdcffpagir, Map<String, String> mapcdcffpagirActual) {

		comparingData("Comparando OFCORIG", cdcffpagir.getOfcorig(), mapcdcffpagirActual.get("OFCORIG"));
		comparingData("Comparando NROGIRO", cdcffpagir.getNroGiro(), mapcdcffpagirActual.get("NROGIRO").trim());
		comparingData("Comparando TIPOGIRO", cdcffpagir.getTipoGiro(), mapcdcffpagirActual.get("TIPOGIRO").trim());
		comparingData("Comparando CONVENIO", cdcffpagir.getConvenio(), mapcdcffpagirActual.get("CONVENIO").trim());
		comparingData("Comparando NIT", cdcffpagir.getNit(), mapcdcffpagirActual.get("NIT"));
		comparingData("Comparando FECAPERT", cdcffpagir.getFecApert(), mapcdcffpagirActual.get("FECAPERT"));
		comparingData("Comparando VALORAPE", cdcffpagir.getValorApe(),
				Double.parseDouble(mapcdcffpagirActual.get("VALORAPE")), 0);
		comparingData("Comparando MONEDAPE", cdcffpagir.getMonedApe(), mapcdcffpagirActual.get("MONEDAPE"));
		comparingData("Comparando OFCCIO", cdcffpagir.getOfCcio(), mapcdcffpagirActual.get("OFCCIO"));
		comparingData("Verificando CONSDIV", true, !mapcdcffpagirActual.get("CONSDIV").isEmpty());
		comparingData("Comparando DIVISAS", cdcffpagir.getDivisas(),
				Double.parseDouble(mapcdcffpagirActual.get("DIVISAS")), 0);
		comparingData("Comparando EFECTIVO", cdcffpagir.getEfectivo(),
				Double.parseDouble(mapcdcffpagirActual.get("EFECTIVO")), 0);
		comparingData("Comparando CHEQUE", cdcffpagir.getCheque(),
				Double.parseDouble(mapcdcffpagirActual.get("CHEQUE")), 0);
		comparingData("Comparando NROTRANS", cdcffpagir.getNroTrans().trim(),
				mapcdcffpagirActual.get("NROTRANS").trim());
	}

	private void comparingData(String description, Object expected, Object actual) {
		LOGGER.info(description + " dato comparativo: " + expected + "-dato encontrado:" + actual);
		assertEquals(description, expected, actual);
	}

	private void comparingData(String description, double actual, double expected, double delta) {
		LOGGER.info(description + " dato comparativo: " + expected + "-dato encontrado:" + actual);
		assertEquals(description, expected, actual, 0);
	}

}
