package com.bancolombia.mercadolibreempresas.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class RunEvents {
	public static void executeScriptByElement(WebElement element, String script, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(script, element);
	}

	/**
	 * Método encargado de hacer bajar o subir la pantalla
	 * 
	 * @param driver
	 *            el driver que se está manejando en ese momento
	 * @param pixels
	 *            cantidad de pixeles que se debe de bajar (positivo para bajar,
	 *            negativo para subir)
	 */
	public static void scrollUpDown(WebDriver driver, String pixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + pixels + ")");
	}

	public static void moveMouse(WebElement element, WebDriver driver) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}

	public static void loadStandBy(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver switchDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
		return driver;
	}

	public static boolean elementPresent(By by, WebDriver driver) {
		return driver.findElement(by).isDisplayed();
	}

}
