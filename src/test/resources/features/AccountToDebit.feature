Feature: Como usuario requiero que se verifique la correcta funcionalidad de la Pantalla cuenta a bebitar 

Background: 
	Given Abro la pagina SVE 
	When Inicio sesion en la sucursal virtual empresas 
	
	
@SinCuentasInscritas 
Scenario: Usuario sin cuentas inscritas 
	And Elijo opcion de envio de divisas 
	And Seleccion Tasas Front 
	And Dar clic en el boton continuar tasas 
	Then Aparece mensaje de alerta por usuario sin cuentas inscritas 
	
	
	
@OnlyAffiliatedAccountsToChannel 
Scenario:
Verificar que en el campo Cuenta a debitar de la pantalla cuenta a debitar solo se muestren las cuentas afiliadas a la SVE 
	And Elijo opcion administrar productos propios 
	And Capturo las cuentas propias que se tienen afiliadas al canal 
	And Elijo opcion de envio de divisas 
	And Seleccion Tasas Front 
	And Dar clic en el boton continuar tasas 
	And Capturo las cuentas del campo cuenta a debitar 
	And Comparo que las cuentas que se muestran en el campo cuenta a debitar sean las que se tienen afiliadas al canal 
    