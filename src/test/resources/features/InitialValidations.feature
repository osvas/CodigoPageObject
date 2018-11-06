Feature: Validaciones Iniciales 

Background: 
	Given Abro la pagina SVE 
	When Inicio sesion en la sucursal virtual empresas 
	#And Elijo opcion de envio de divisas 
	
@CIBNotAvailable 
Scenario: Validación de servicio CIB no está disponible 
	And Se pone CIB en estado no disponible 
	Then Se valida que se muestre el mensaje correspondiente de cib no disponible 
	And Se pone CIB en estado disponible 
	
@UserInControlList 
Scenario: Validación de usuario en listas de control 
	And Se ingresa al usuario de SVE en listas de control 
	Then Se valida que se muestre el mensaje correspondiente del usuario en listas de control 
	And Se retira al usuario de SVE de las listas de control