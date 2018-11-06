#Author: sfrua@bancolombia.com.co
Feature: Envío de divisas al exterior

  @BancoGenerico
  Scenario Outline: Preparacion envio con banco de beneficiario como banco generico
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>"
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
    And Selecciono los datos de las cuentas
    And Establezco banco homologado como banco filial para realizar envio con parametro comision "<parametroComision>" y moneda "<divisa>"
    And Selecciono la comision "<comision>"
    And Selecciono un numeral cambiario
    And Verifico que los datos ingresados sean los correctos para el envio

    Examples: 
      | divisa | comision | parametroComision |
      | USD    | OUR      |        00         |

  @BancoGenerico
  Scenario: Preparacion envio con banco de beneficiario como banco generico
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas con usuario aprobador
    And Reviso los datos de la cuenta que voy a aprobar
    And Apruebo el envio
    Then Valido datos banco banco generico en archivo "CDCFFAPERT"
    And Valido datos banco banco generico en archivo "CIBFFCOMPL"
    And Valido datos banco banco generico en archivo "CIBFFLGFND"
    And Valido datos banco banco generico en archivo "CIBFFMENSA"
