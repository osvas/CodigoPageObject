Feature: Verificar Cuentas Beneficiario CIB

  Background: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Elijo opcion de envio de divisas

  @RegisteredBeneficiaryAccounts
  Scenario: Verificar en CIB que el usuario tenga cuentas de beneficiario inscritas
    When seleccionar una tasa tipo moneda "USD"
    Then Verificar Cuentas Beneficiario
