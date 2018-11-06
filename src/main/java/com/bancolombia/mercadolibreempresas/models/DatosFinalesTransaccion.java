package com.bancolombia.mercadolibreempresas.models;

public class DatosFinalesTransaccion {
	private String transaccion;
	private String cuentaADebitar;
	private String nombreDelOrdenante;
	private String nombreDelBeneficiario;
	private String nombrePersonalizado;
	private String cuentaDelBeneficiario;
	private String codigoSwiftBancoBeneficiario;
	private String montoYMoneda;
	private String tasa;
	private String montoEnPesosValorAEnviar;
	private String gastosBancoEnExterior;
	private String comisionDeEnvio;
	private String montoTotalADebitar;
	private String numeralCambiario;
	private String MensajeAlBeneficiario;
	private String Factor;
	private String referenciaDeEnvio;

	public String getReferenciaDeEnvio() {
		return referenciaDeEnvio;
	}

	public void setReferenciaDeEnvio(String referenciaDeEnvio) {
		this.referenciaDeEnvio = referenciaDeEnvio;
	}

	public String getFactor() {
		return Factor;
	}

	public void setFactor(String factor) {
		Factor = factor;
	}

	public String getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(String transaccion) {
		this.transaccion = transaccion;
	}

	public String getCuentaADebitar() {
		return cuentaADebitar;
	}

	public void setCuentaADebitar(String cuentaADebitar) {
		this.cuentaADebitar = cuentaADebitar;
	}

	public String getNombreDelOrdenante() {
		return nombreDelOrdenante;
	}

	public void setNombreDelOrdenante(String nombreDelOrdenante) {
		this.nombreDelOrdenante = nombreDelOrdenante;
	}

	public String getNombreDelBeneficiario() {
		return nombreDelBeneficiario;
	}

	public void setNombreDelBeneficiario(String nombreDelBeneficiario) {
		this.nombreDelBeneficiario = nombreDelBeneficiario;
	}

	public String getNombrePersonalizado() {
		return nombrePersonalizado;
	}

	public void setNombrePersonalizado(String nombrePersonalizado) {
		this.nombrePersonalizado = nombrePersonalizado;
	}

	public String getCuentaDelBeneficiario() {
		return cuentaDelBeneficiario;
	}

	public void setCuentaDelBeneficiario(String cuentaDelBeneficiario) {
		this.cuentaDelBeneficiario = cuentaDelBeneficiario;
	}

	public String getCodigoSwiftBancoBeneficiario() {
		return codigoSwiftBancoBeneficiario;
	}

	public void setCodigoSwiftBancoBeneficiario(String codigoSwiftBancoBeneficiario) {
		this.codigoSwiftBancoBeneficiario = codigoSwiftBancoBeneficiario;
	}

	public String getMontoYMoneda() {
		return montoYMoneda;
	}

	public void setMontoYMoneda(String montoYMoneda) {
		this.montoYMoneda = montoYMoneda;
	}

	public String getTasa() {
		return tasa;
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public String getMontoEnPesosValorAEnviar() {
		return montoEnPesosValorAEnviar;
	}

	public void setMontoEnPesosValorAEnviar(String montoEnPesosValorAEnviar) {
		this.montoEnPesosValorAEnviar = montoEnPesosValorAEnviar;
	}

	public String getGastosBancoEnExterior() {
		return gastosBancoEnExterior;
	}

	public void setGastosBancoEnExterior(String gastosBancoEnExterior) {
		this.gastosBancoEnExterior = gastosBancoEnExterior;
	}

	public String getComisionDeEnvio() {
		return comisionDeEnvio;
	}

	public void setComisionDeEnvio(String comisionDeEnvio) {
		this.comisionDeEnvio = comisionDeEnvio;
	}

	public String getMontoTotalADebitar() {
		return montoTotalADebitar;
	}

	public void setMontoTotalADebitar(String montoTotalADebitar) {
		this.montoTotalADebitar = montoTotalADebitar;
	}

	public String getNumeralCambiario() {
		return numeralCambiario;
	}

	public void setNumeralCambiario(String numeralCambiario) {
		this.numeralCambiario = numeralCambiario;
	}

	public String getMensajeAlBeneficiario() {
		return MensajeAlBeneficiario;
	}

	public void setMensajeAlBeneficiario(String mensajeAlBeneficiario) {
		MensajeAlBeneficiario = mensajeAlBeneficiario;
	}

}
