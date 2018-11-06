#Author: ogvasque@bancolombia.com.co
Feature: Rececpción de divisas
  Característica encargada de cubrir los flujos de la recepción de divisas del exterior

  @Recepcion
  Scenario Outline: Title of your scenario
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso giro con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory |
      | imagen      | Recepcion       |
    And Ingreso tasa con la divisa "<divisa>" para "<tipoOperacion>"
      | nameOfImage | nameOfDirectory |
      | imagen      | Recepcion       |

    Examples: 
      | divisa | tipoOperacion |
      | USD    | COMPRA        |
