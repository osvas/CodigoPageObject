#Author: ogvasque@bancolombia.com.co
Feature: Numerales Cambiarios
  Feature donde se realizarán los casos de pruebas relacionados a los numerales cambiarios

  @NumeralCambiario
  Scenario Outline: Numeral cambiario con o sin cobro
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>" para "<tipoOperacion>"
      | nameOfImage | nameOfDirectory   |
      | imagen      | Numeral Cambiario |
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
      | nameOfImage | nameOfDirectory   |
      | imagen      | Numeral Cambiario |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory   |
      | imagen      | Numeral Cambiario |
    And Selecciono la comision "<comision>"
      | nameOfImage | nameOfDirectory   |
      | imagen      | Numeral Cambiario |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory   |
      | imagen      | Numeral Cambiario |
    And Preparo los datos para cobro de GMF "<cobro>"
      | nameOfImage | nameOfDirectory   |
      | imagen      | Numeral Cambiario |
    And Verifico que los datos ingresados sean los correctos para el envio
      | nameOfImage | nameOfDirectory   |
      | imagen      | Numeral Cambiario |

    Examples: 
      | divisa | cobro | comision | tipoOperacion |
      | USD    | SI    | OUR      | VENTA         |

  @NumeralCambiario
  Scenario Outline: Envío de divisas
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas con usuario aprobador
    And Reviso los datos de la cuenta que voy a aprobar
      | nameOfImage                | nameOfDirectory       |
      | Detalles de la transaccion | Numeral Cambiario USD |
    And Apruebo el envio
      | nameOfImage                         | nameOfDirectory       |
      | Detalles de la aprobación del envío | Numeral Cambiario USD |
    Then Valido el archivo cibffintog donde "<cobro>" se cobra el GMF con la divisa "<divisa>"
      | nameOfImage                         | nameOfDirectory   |
      | Detalles de la aprobación del envío | Numeral Cambiario |

    Examples: 
      | divisa | cobro |
      | USD    | SI    |
