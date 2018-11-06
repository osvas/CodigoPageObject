Feature: Verificar segunda pantalla

Background: 
	Given Abro la pagina SVE 
	And Inicio sesion en la sucursal virtual empresas
	And Elijo opcion de envio de divisas



  @CasoExitoso
  Scenario: verificar las cuentas de beneficiario activas para USD
    Given Se seleccionan solo las tasas de la moneda USD
    And Seleccionar cuenta a debitar
    When Seleccionar Cuentas de beneficiario
    Then Verificar cuentas de beneficiario contra CIB
 
  
