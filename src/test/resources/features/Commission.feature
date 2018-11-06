#Author: ogvasque@bancolombia.com.co
Feature: Cobro de comisiones

  @CobroValorCOP
  Scenario Outline: Cálculo valor COP
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory |
      | imagen      | cobro valor COP |
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
      | nameOfImage | nameOfDirectory |
      | imagen      | cobro valor COP |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory |
      | imagen      | cobro valor COP |
    Then valido el valor cop de la divisa "<divisa>"
      | nameOfImage | nameOfDirectory |
      | imagen      | cobro valor COP |

    Examples: 
      | divisa | comision |
      | USD    | OUR      |
      | EUR    | OUR      |
      | MXN    | OUR      |

  @ClienteExisteOUR-BENSHA
  Scenario Outline: Cálculo valor de la comisión por tarifa
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |
    And Preparo los datos cliente existe para la comision tarifa "<tarifa>" y divisa "<divisa>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |
    And Valido la comision "<comision>" de tarifa "<tarifa>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |
    And Verifico que los cobros de la comision "<comision>" y tarifa "<tarifa>" sean los correctos para el envio
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |

    Examples: 
      | divisa | comision | tarifa    |
      | EUR    | OUR      | especial2 |

  @ClienteExisteOUR-BENSHA
  Scenario Outline: Envío de divisas
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas con usuario aprobador
    And Reviso los datos de la cuenta que voy a aprobar
      | nameOfImage                | nameOfDirectory             |
      | Detalles de la transaccion | ClienteExisteOUR-BENSHA EUR |
    Then Valido el valor para la comision "<comision>", tarifa "<tarifa>" y divisa "<divisa>"
      | nameOfImage | nameOfDirectory             |
      | imagen      | ClienteExisteOUR-BENSHA EUR |
    Then Valido archivo cdcffcomis para comision "<comision>", tarifa "<tarifa>" y divisa "<divisa>"
      | nameOfImage | nameOfDirectory         |
      | imagen      | ClienteExisteOUR-BENSHA |

    Examples: 
      | divisa | comision | tarifa    |
      | EUR    | OUR      | especial2 |

  @ClienteNoExisteOUR-BENSHA
  Scenario Outline: Cálculo valor de la comisión por tarifa
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory           |
      | imagen      | ClienteNoExisteOUR-BENSHA |
    And Preparo los datos cliente no existe para la divisa "<divisa>" y rebate "<rebate>"
      | nameOfImage | nameOfDirectory           |
      | imagen      | ClienteNoExisteOUR-BENSHA |
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
      | nameOfImage | nameOfDirectory           |
      | imagen      | ClienteNoExisteOUR-BENSHA |
    And Selecciono los datos de las cuentas
      | nameOfImage | nameOfDirectory           |
      | imagen      | ClienteNoExisteOUR-BENSHA |
    And Valido para el cliente no existe la comision "<comision>" de tarifa "<tarifa>"
      | nameOfImage | nameOfDirectory           |
      | imagen      | ClienteNoExisteOUR-BENSHA |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory           |
      | imagen      | ClienteNoExisteOUR-BENSHA |
    And Verifico que los cobros cliente no existe de la comision "<comision>" y tarifa "<tarifa>" sean los correctos para el envio
      | nameOfImage | nameOfDirectory           |
      | imagen      | ClienteNoExisteOUR-BENSHA |

    Examples: 
      | divisa | comision | tarifa | rebate |
      | USD    | OUR      | plena  | OFF    |

  @ComisionGOUR
  Scenario Outline: Cálculo valor de la comisión GOUR
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas
    And Ingreso tasa con la divisa "<divisa>"
      | nameOfImage | nameOfDirectory |
      | imagen      | ComisionGOUR    |
    And Elijo opcion de envio de divisas
    And Selecciono una tasa de moneda "<divisa>"
      | nameOfImage | nameOfDirectory |
      | imagen      | ComisionGOUR    |
    And Preparo los datos comision GOUR para la divisa "<divisa>" y el parametro COMNEGGOUR "<estadoParametro>"
      | nameOfImage | nameOfDirectory |
      | imagen      | ComisionGOUR    |
    And Valido la comision con el parametro COMNEGGOUR "<estadoParametro>"
      | nameOfImage | nameOfDirectory |
      | imagen      | ComisionGOUR    |
    And Selecciono un numeral cambiario
      | nameOfImage | nameOfDirectory |
      | imagen      | ComisionGOUR    |
    Then Verifico la comision con el parametro COMNGGOUR "<estadoParametro>"
      | nameOfImage | nameOfDirectory |
      | imagen      | ComisionGOUR    |

    Examples: 
      | divisa | estadoParametro | comision |
      | USD    | activo          | GOUR     |

  @ComisionGOUR
  Scenario Outline: Envío de divisas
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas con usuario aprobador
    And Reviso los datos de la cuenta que voy a aprobar
      | nameOfImage                | nameOfDirectory  |
      | Detalles de la transaccion | ComisionGOUR USD |
    Then Valido el valor para la comision "<comision>", tarifa "<estadoParametro>" y divisa "<divisa>"
      | nameOfImage | nameOfDirectory  |
      | imagen      | ComisionGOUR USD |
    Then Valido archivo cdcffcomis para comision "<comision>", tarifa "<estadoParametro>" y divisa "<divisa>"
      | nameOfImage | nameOfDirectory  |
      | imagen      | ComisionGOUR USD |

    Examples: 
      | divisa | comision | estadoParametro |
      | USD    | GOUR     | activo          |
