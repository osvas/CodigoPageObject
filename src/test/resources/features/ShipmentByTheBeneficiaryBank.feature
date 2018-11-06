#Author: ogvasque@bancolombia.com.co
Feature: Envío de divisas al exterior

  @IniciarEnvio
  Scenario Outline: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>"
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
    And Selecciono los datos de las cuentas
    And Establezco banco filial igual al beneficiario para realizar envio para numero comision "<parametroComision>"
    And Selecciono la comision "<comision>"
    And Selecciono un numeral cambiario
    And Verifico que los datos ingresados sean los correctos para el envio

    Examples: 
      | divisa | comision | parametroComision |
      | USD    | OUR      |                00 |

  @TerminarEnvio
  Scenario: Envío de divisas
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas con usuario aprobador
    And Reviso los datos de la cuenta que voy a aprobar
    And Apruebo el envio
    Then Valido los datos de fonde en el archivo CDCFFAPERT
