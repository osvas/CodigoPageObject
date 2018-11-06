package com.bancolombia.mercadolibreempresas.models.files;

public class Cibffdcl05 {
	public static final String OFCORIG = "25";
	public String numeroDcl; //consdiv
	public String anoDcl;
	public String mesDcl;
	public String diaDcl;
	public String anoGrb;
	public String mesGrb;
	public String diaGrb;
	public String tipoDcl;
	public static final String TIPOOPER = "1";
	public String tiponit = "3";
	public String operacion = "0";
	public String nit;
	public String nombre;
	public String telefono;
	public String direccion = "";
	public String ciudad = "";
	public String mongiro;
	public double vlrmongir;
	public String cambioUsd;
	public String totUsd;
	public static final String CODTERM = "997"; // 998 SVP 997 SVE
	

	public  void setnumeroDcl(String numeroDcl) {
		this.numeroDcl = numeroDcl;
	}
	
	public  void setanoDcl(String anoDcl) {
		this.anoDcl = anoDcl;
	}
	
	public  void setmesDcl(String mesDcl) {
		this.mesDcl = mesDcl;
	}
	
	public  void setdiaDcl(String diaDcl) {
		this.diaDcl = diaDcl;
	}
	

	public  void setanoGrb(String anoGrb) {
		this.anoGrb = anoGrb;
	}
	

	public  void setmesGrb(String mesGrb) {
		this.mesGrb = mesGrb;
	}

	public  void setdiaGrb(String diaGrb) {
		this.diaGrb = diaGrb;
	}

	public  void settipoDcl(String tipoDcl) {
		this.tipoDcl = tipoDcl;
	}

	public  void setnit(String nit) {
		this.nit = nit;
	}

	public  void setnombre(String nombre) {
		this.nombre = nombre;
	}

	public  void setmongiro(String mongiro) {
		this.mongiro = mongiro;
	}

	public  void setvlrmongir(double vlrmongir) {
		this.vlrmongir = vlrmongir;
	}

	public  void setcambioUsd(String cambioUsd) {
		this.cambioUsd = cambioUsd;
	}

	public  void settotUsd(String totUsd) {
		this.totUsd = totUsd;
	}

	public  void settelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
}
