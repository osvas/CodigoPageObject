#Author: <ogvasque@bancolombia.com.co>
Feature: Validación de envío de divisas banco de fondeo

  @EnvioPorLaTasaUnicaTasa
  Scenario Outline: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | EnvioPorLaTasaUnicaTasa |
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | EnvioPorLaTasaUnicaTasa |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory         |
      | imagen      | EnvioPorLaTasaUnicaTasa |
    And Preparo los datos de envio banco de fondeo unica tasa
    And Selecciono la comision "<comision>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | EnvioPorLaTasaUnicaTasa |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory         |
      | imagen      | EnvioPorLaTasaUnicaTasa |
    And Verifico que los datos ingresados sean los correctos para el envio
      | nameOfImage | nameOfDirectory         |
      | imagen      | EnvioPorLaTasaUnicaTasa |

    Examples: 
      | divisa | comision |
      | EUR    | OUR      |

  @EnvioPorLaTasaVariasTasas
  Scenario Outline: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso varias tasas con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory           |
      | imagen      | EnvioPorLaTasaVariasTasas |
    And Elijo opcion de envio de divisas
    And Selecciono varias tasas de moneda dolar
      | nameOfImage | nameOfDirectory           |
      | imagen      | EnvioPorLaTasaVariasTasas |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory           |
      | imagen      | EnvioPorLaTasaVariasTasas |
    And Preparo los datos de envio banco de fondeo multiples tasas
    And Selecciono la comision "<comision>"
      | nameOfImage | nameOfDirectory           |
      | imagen      | EnvioPorLaTasaVariasTasas |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory           |
      | imagen      | EnvioPorLaTasaVariasTasas |
    And Verifico que los datos ingresados sean los correctos para el envio
      | nameOfImage | nameOfDirectory           |
      | imagen      | EnvioPorLaTasaVariasTasas |

    Examples: 
      | divisa | comision |
      | USD    | OUR      |

  @EnvioBancoBeneficiarioVariasTasas
  Scenario Outline: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso varias tasas con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory                   |
      | imagen      | EnvioBancoBeneficiarioVariasTasas |
    And Elijo opcion de envio de divisas
    And Selecciono varias tasas de moneda dolar
      | nameOfImage | nameOfDirectory                   |
      | imagen      | EnvioBancoBeneficiarioVariasTasas |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory                   |
      | imagen      | EnvioBancoBeneficiarioVariasTasas |
    And Preparo los datos de envio banco del beneficiario multiples tasas
    And Selecciono la comision "<comision>"
      | nameOfImage | nameOfDirectory                   |
      | imagen      | EnvioBancoBeneficiarioVariasTasas |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory                   |
      | imagen      | EnvioBancoBeneficiarioVariasTasas |
    And Verifico que los datos ingresados sean los correctos para el envio
      | nameOfImage | nameOfDirectory                   |
      | imagen      | EnvioBancoBeneficiarioVariasTasas |

    Examples: 
      | divisa | comision |
      | USD    | OUR      |

  @EnvioBancoGenericoVariasTasas
  Scenario Outline: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso varias tasas con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory               |
      | imagen      | EnvioBancoGenericoVariasTasas |
    And Elijo opcion de envio de divisas
    And Selecciono varias tasas de moneda dolar
      | nameOfImage | nameOfDirectory               |
      | imagen      | EnvioBancoGenericoVariasTasas |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory               |
      | imagen      | EnvioBancoGenericoVariasTasas |
    And Preparo los datos de envio banco generico multiples tasas
    And Selecciono la comision "<comision>"
      | nameOfImage | nameOfDirectory               |
      | imagen      | EnvioBancoGenericoVariasTasas |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory               |
      | imagen      | EnvioBancoGenericoVariasTasas |
    And Verifico que los datos ingresados sean los correctos para el envio
      | nameOfImage | nameOfDirectory               |
      | imagen      | EnvioBancoGenericoVariasTasas |

    Examples: 
      | divisa | comision |
      | USD    | OUR      |

  @FinalizarEnvio
  Scenario: Envío de divisas
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas con usuario aprobador
    And Reviso los datos de la cuenta que voy a aprobar
      | nameOfImage                | nameOfDirectory             |
      | Detalles de la transaccion | EnvioPorLaTasaUnicaTasa EUR |
    And Apruebo el envio
      | nameOfImage                         | nameOfDirectory             |
      | Detalles de la aprobación del envío | EnvioPorLaTasaUnicaTasa EUR |
