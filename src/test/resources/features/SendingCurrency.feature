#Author: ogvasque@bancolombia.com.co
Feature: Envío de divisas al exterior
  Yo como usuario del banco
  quiero tener la posibilidad de realizar
  un envío de divisas al exterior desde la
  sucursal virtual empresas.

  Background: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas

  @EnvioDolar
  Scenario Outline: Envío de divisas
    And Ingreso tasa con la divisa "<divisa>"
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
    And Selecciono los datos de las cuentas
    And Selecciono la comision "<tipoComision>"
    And Selecciono un numeral cambiario
    And Verifico que los datos ingresados sean los correctos para el envio

    Examples: 
      | divisa | tipoComision |
      | MXN    | OUR          |
      | USD    | OUR          |
      | CAD    | BEN/SHA      |
