package com.bancolombia.mercadolibreempresas.models.files;

public class Cdcffpagir {
	
	private String ofcorig = "25";  
	private String nroGiro;
	private String tipoGiro = "2";
	private String convenio = "N";
	private String nit;	      
	private String fecApert; 
	private double valorApe;
	private String monedApe; 
	private String consdiv;
	private String ofCcio = "25";
	private double divisas;
	private double efectivo = 0;
	private double cheque = 0;
	private String nroTrans = "";
	public String getOfcorig() {
		return ofcorig;
	}
	
	public void setOfcorig(String ofcorig) {
		this.ofcorig = ofcorig;
	}
	
	public String getNroGiro() {
		return nroGiro;
	}
	
	public void setNroGiro(String nroGiro) {
		this.nroGiro = nroGiro;
	}
	
	public String getTipoGiro() {
		return tipoGiro;
	}
	
	public void setTipoGiro(String tipoGiro) {
		this.tipoGiro = tipoGiro;
	}
	
	public String getConvenio() {
		return convenio;
	}
	
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	
	public String getNit() {
		return nit;
	}
	
	public void setNit(String nit) {
		this.nit = nit;
	}
	
	public String getFecApert() {
		return fecApert;
	}
	
	public void setFecApert(String fecApert) {
		this.fecApert = fecApert;
	}
	
	public double getValorApe() {
		return valorApe;
	}
	
	public void setValorApe(double valorApe) {
		this.valorApe = valorApe;
	}
	
	public String getMonedApe() {
		return monedApe;
	}
	
	public void setMonedApe(String monedApe) {
		this.monedApe = monedApe;
	}
	
	public String getConsdiv() {
		return consdiv;
	}
	
	public void setConsdiv(String consdiv) {
		this.consdiv = consdiv;
	}
	
	public String getOfCcio() {
		return ofCcio;
	}
	
	public void setOfCcio(String ofCcio) {
		this.ofCcio = ofCcio;
	}
	
	public double getDivisas() {
		return divisas;
	}
	
	public void setDivisas(double divisas) {
		this.divisas = divisas;
	}
	
	public double getEfectivo() {
		return efectivo;
	}
	
	public void setEfectivo(double efectivo) {
		this.efectivo = efectivo;
	}
	
	public double getCheque() {
		return cheque;
	}
	
	public void setCheque(double cheque) {
		this.cheque = cheque;
	}
	
	public String getNroTrans() {
		return nroTrans;
	}
	
	public void setNroTrans(String nroTrans) {
		this.nroTrans = nroTrans;
	}
}
