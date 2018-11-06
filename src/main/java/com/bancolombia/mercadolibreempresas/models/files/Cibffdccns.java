package com.bancolombia.mercadolibreempresas.models.files;

public class Cibffdccns {
	public static final String OFCORIG = "25"; //Esta parametrizado en el PAGEN
	private String anoDcl;
	private String mesDcl; 
	private String diaDcl;
	private String consecu;
	private String tipoDcl;
	private String ofccio = "25";
	private String nitDcl;
	private String tipoNit;
	private String nombre;
	
	public void setanoDcl(String anoDcl) {
		this.anoDcl = anoDcl;
	}
	
	public void setmesDcl(String mesDcl) {
		this.mesDcl = mesDcl;
	}
	
	public void setdiaDcl(String diaDcl) {
		this.diaDcl = diaDcl;
	}
	
	public void setconsecu(String consecu) {
		this.consecu = consecu;
	}
	
	public void settipoDcl(String tipoDcl) {
		this.tipoDcl = tipoDcl;
	}
	
	public void setoffccio(String offccio) {
		this.ofccio = offccio;
	}
	
	public void setnitDcl(String nitDcl) {
		this.nitDcl = nitDcl;
	}
	
	public void settipoNit(String tipoNit) {
		this.tipoNit = tipoNit;
	}
	
	public void setnombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnoDcl() {
		return anoDcl;
	}

	public String getMesDcl() {
		return mesDcl;
	}

	public String getDiaDcl() {
		return diaDcl;
	}

	public String getConsecu() {
		return consecu;
	}

	public String getTipoDcl() {
		return tipoDcl;
	}

	public String getOfccio() {
		return ofccio;
	}

	public String getNitDcl() {
		return nitDcl;
	}

	public String getTipoNit() {
		return tipoNit;
	}

	public String getNombre() {
		return nombre;
	} 
}
