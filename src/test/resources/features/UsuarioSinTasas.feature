Feature: Usuario sin Tasas pactadas. 

Background: 
	Given Abro la pagina SVE 
	When Inicio sesion en la sucursal virtual empresas 
	
@SinTasas 
Scenario: Usuario sin tasas pactadas 
	And Igresar a CIB a inactivar las tasas de envio 
	And Elijo opcion de envio de divisas 
	Then Aparece mensaje de alerta por usuario sin tasas