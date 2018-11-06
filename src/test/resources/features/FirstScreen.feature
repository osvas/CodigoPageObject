Feature: Validaciones de la primera pantalla

  Background: 
    Given Abro la pagina SVE
    When Inicio sesion en la sucursal virtual empresas

  @OnlyRatesWithBalance
  Scenario: Validación de tasas con saldo cero
    And Elijo opcion de envio de divisas
    Then No se encuentran tasas con saldo igual a cero

  @SelectOnlyDollarCurrencyRates
  Scenario: Validación de que se puedan seleccionar varias tasas de la moneda dólar
    And Elijo opcion de envio de divisas
    Then Se seleccionan solo las tasas de la moneda dolar

  @SelectOnlyOneRateForTheEuroCurrency
  Scenario: Validación de que se pueda seleccionar sólo una tasa de la modena EUR
    And Elijo opcion de envio de divisas
    Then Se selecciona tasa de la moneda euro

  @SelectOnlyOneRateForTheJPYCurrency
  Scenario: Validación de que se pueda seleccionar sólo una tasa de la modena JPY
    And Elijo opcion de envio de divisas
    Then Se selecciona tasa de la moneda yen

  @DisabledRatesWithFutureComplianceDateOfTheDollarCurrency
  Scenario: Validación de que no se pueda seleccionar una tasa de moneda dólar con fecha de negociación futura
    And Elijo opcion de envio de divisas
    Then Se valida que no se pueda seleccionar la moneda dolar por tener fecha de negociacion futura

  @EnabledRatesWithFutureTradingDateDifferentOfTheDollarCurrency
  Scenario: Validación de que sí se pueda seleccionar una tasa de moneda diferente a dólar con fecha de negociación futura
    And Elijo opcion de envio de divisas
    Then Se valida que se pueda seleccionar la moneda diferente a dolar con fecha de negociacion futura

  @PriorityInErrorMessagesFirstUserInChecklist
  Scenario: Prioridad del mensaje de error si lista de control está de segundo
    And Elijo opcion de envio de divisas
    Then Se valida que se muestre el primer mensaje parametrizado de listas de control

  @PriorityInErrorMessagesSecondUserInChecklist
  Scenario: Prioridad del mensaje de error si lista de control está de segundo
    And Elijo opcion de envio de divisas
    Then Se valida que se muestre el segundo mensaje parametrizado de listas de control

  @PriorityInErrorMessagesFirstCIBIsAvailable
  Scenario: Prioridad del mensaje de error si CIB no está disponible
    And Elijo opcion de envio de divisas
    Then Se valida que se muestre el primer mensaje con CIB no disponible

  @PriorityInErrorMessagesSecondCIBIsAvailable
  Scenario: Prioridad del mensaje de error si CIB no está disponible
    And Elijo opcion de envio de divisas
    Then Se valida que se muestre el segundo mensaje con CIB no disponible

  @PriorityInErrorMessagesFirstUserWithoutRates
  Scenario: Prioridad del mensaje de error si el usuario no tiene tasas disponibles
    And Elijo opcion de envio de divisas
    Then Se valida que se muestre el mensaje de usuario sin tasas cuando esta parametrizado de primero

  @PriorityInErrorMessagesSecondUserWithoutRates
  Scenario: Prioridad del mensaje de error si el usuario no tiene tasas disponibles
    And Elijo opcion de envio de divisas
    Then Se valida que se muestre el mensaje de usuario sin tasas cuando esta parametrizado de segundo

  @DollarRatesWithoutPataShouldBeShown
  Scenario: Validación de que las tasas de moneda dólar se muestren si no están unidas a una PATA.
    And Ingreso tasas dolar sin patas
    And Elijo opcion de envio de divisas
    Then Se valida que las tasas dolar sin patas que se encuentren en el backend se muestren en el frontend

  @DollarRatesWithPataShouldNotBeShown
  Scenario: Validación de que las tasas de moneda dólar NO se muestren si están unidas a una PATA.
    And Ingreso tasas dolar con patas
    And Elijo opcion de envio de divisas
    Then Se valida que las tasas dolar con patas que se encuentren en el backend no se muestren en el frontend
