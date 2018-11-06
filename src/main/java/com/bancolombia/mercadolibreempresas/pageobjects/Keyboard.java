package com.bancolombia.mercadolibreempresas.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.bancolombia.mercadolibreempresas.utilities.RunEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author simon.rua
 *
 */

public class Keyboard extends PageObjectCustom {
	/**
	 * Keyboard Qwerty
	 */
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 1 + ")")
	@CacheLookup
	private WebElement keyQ;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 2 + ")")
	@CacheLookup
	private WebElement keyW;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 3 + ")")
	@CacheLookup
	private WebElement keyE;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 4 + ")")
	@CacheLookup
	private WebElement keyR;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 5 + ")")
	@CacheLookup
	private WebElement keyT;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 6 + ")")
	@CacheLookup
	private WebElement keyY;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 7 + ")")
	@CacheLookup
	private WebElement keyU;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 8 + ")")
	@CacheLookup
	private WebElement keyI;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 9 + ")")
	@CacheLookup
	private WebElement keyO;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 10 + ")")
	@CacheLookup
	private WebElement keyP;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 11 + ")")
	@CacheLookup
	private WebElement keyDelete;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 12 + ")")
	@CacheLookup
	private WebElement keyA;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 13 + ")")
	@CacheLookup
	private WebElement keyS;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 14 + ")")
	@CacheLookup
	private WebElement keyD;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 15 + ")")
	@CacheLookup
	private WebElement keyF;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 16 + ")")
	@CacheLookup
	private WebElement keyG;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 17 + ")")
	@CacheLookup
	private WebElement keyH;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 18 + ")")
	@CacheLookup
	private WebElement keyJ;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 19 + ")")
	@CacheLookup
	private WebElement keyK;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 20 + ")")
	@CacheLookup
	private WebElement keyL;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 21 + ")")
	@CacheLookup
	private WebElement keyÑ;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 22 + ")")
	@CacheLookup
	private WebElement keyZ;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 23 + ")")
	@CacheLookup
	private WebElement keyX;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 24 + ")")
	@CacheLookup
	private WebElement keyC;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 25 + ")")
	@CacheLookup
	private WebElement keyV;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 26 + ")")
	@CacheLookup
	private WebElement keyB;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 27 + ")")
	@CacheLookup
	private WebElement keyN;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 28 + ")")
	@CacheLookup
	private WebElement keyM;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 29 + ")")
	@CacheLookup
	private WebElement keyBloqMayus;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 30 + ")")
	@CacheLookup
	private WebElement keyEnter;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 31 + ")")
	@CacheLookup
	private WebElement keySlash;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 32 + ")")
	@CacheLookup
	private WebElement keyDash;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 33 + ")")
	@CacheLookup
	private WebElement keyAccent;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 34 + ")")
	@CacheLookup
	private WebElement keyUpoints;
	@FindBy(css = "#alphaKeyboard > area:nth-child(" + 35 + ")")
	@CacheLookup
	private WebElement keyLowDash;

	/**
	 * Numeric keyboard
	 */
	@FindBy(xpath = "//div[text()='1']")
	@CacheLookup
	private WebElement keyOne;
	@FindBy(xpath = "//div[text()='2']")
	@CacheLookup
	private WebElement keyTwo;
	@FindBy(xpath = "//div[text()='3']")
	@CacheLookup
	private WebElement keyThree;
	@FindBy(xpath = "//div[text()='4']")
	@CacheLookup
	private WebElement keyFour;
	@FindBy(xpath = "//div[text()='5']")
	@CacheLookup
	private WebElement keyFive;
	@FindBy(xpath = "//div[text()='6']")
	@CacheLookup
	private WebElement keySix;
	@FindBy(xpath = "//div[text()='7']")
	@CacheLookup
	private WebElement keySeven;
	@FindBy(xpath = "//div[text()='8']")
	@CacheLookup
	private WebElement keyEight;
	@FindBy(xpath = "//div[text()='9']")
	@CacheLookup
	private WebElement keyNine;

	private static final String SCRIPT = "arguments[0].click();";

	public enum KeyboardQWERTY {
		Q, W, E, R, T, Y, U, I, O, P, DELETE, A, S, D, F, G, H, J, K, L, Ñ, Z, X, C, V, B, N, M, BLOQ_MAYUS, ENTER, SLASH, DASH, ACCENT, U_POINTS, LOW_DASH,
	}

	private Map<String, WebElement> mapKeysNormal = new HashMap<String, WebElement>() {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		{
			put(KeyboardQWERTY.Q.toString().toLowerCase(), keyQ);
			put(KeyboardQWERTY.Q.toString().toUpperCase(), keyQ);
			put(KeyboardQWERTY.W.toString().toLowerCase(), keyW);
			put(KeyboardQWERTY.W.toString().toUpperCase(), keyW);
			put(KeyboardQWERTY.E.toString().toLowerCase(), keyE);
			put(KeyboardQWERTY.E.toString().toUpperCase(), keyE);
			put(KeyboardQWERTY.R.toString().toLowerCase(), keyR);
			put(KeyboardQWERTY.R.toString().toUpperCase(), keyR);
			put(KeyboardQWERTY.T.toString().toLowerCase(), keyT);
			put(KeyboardQWERTY.T.toString().toUpperCase(), keyT);
			put(KeyboardQWERTY.Y.toString().toLowerCase(), keyY);
			put(KeyboardQWERTY.Y.toString().toUpperCase(), keyY);
			put(KeyboardQWERTY.U.toString().toLowerCase(), keyU);
			put(KeyboardQWERTY.U.toString().toUpperCase(), keyU);
			put(KeyboardQWERTY.I.toString().toLowerCase(), keyI);
			put(KeyboardQWERTY.I.toString().toUpperCase(), keyI);
			put(KeyboardQWERTY.O.toString().toLowerCase(), keyO);
			put(KeyboardQWERTY.O.toString().toUpperCase(), keyO);
			put(KeyboardQWERTY.P.toString().toLowerCase(), keyP);
			put(KeyboardQWERTY.P.toString().toUpperCase(), keyP);
			put(KeyboardQWERTY.A.toString().toLowerCase(), keyA);
			put(KeyboardQWERTY.A.toString().toUpperCase(), keyA);
			put(KeyboardQWERTY.S.toString().toLowerCase(), keyS);
			put(KeyboardQWERTY.S.toString().toUpperCase(), keyS);
			put(KeyboardQWERTY.D.toString().toLowerCase(), keyD);
			put(KeyboardQWERTY.D.toString().toUpperCase(), keyD);
			put(KeyboardQWERTY.F.toString().toLowerCase(), keyF);
			put(KeyboardQWERTY.F.toString().toUpperCase(), keyF);
			put(KeyboardQWERTY.G.toString().toLowerCase(), keyG);
			put(KeyboardQWERTY.G.toString().toUpperCase(), keyG);
			put(KeyboardQWERTY.H.toString().toLowerCase(), keyH);
			put(KeyboardQWERTY.H.toString().toUpperCase(), keyH);
			put(KeyboardQWERTY.J.toString().toLowerCase(), keyJ);
			put(KeyboardQWERTY.J.toString().toUpperCase(), keyJ);
			put(KeyboardQWERTY.K.toString().toLowerCase(), keyK);
			put(KeyboardQWERTY.K.toString().toUpperCase(), keyK);
			put(KeyboardQWERTY.L.toString().toLowerCase(), keyL);
			put(KeyboardQWERTY.L.toString().toUpperCase(), keyL);
			put(KeyboardQWERTY.Ñ.toString().toLowerCase(), keyÑ);
			put(KeyboardQWERTY.Ñ.toString().toUpperCase(), keyÑ);
			put(KeyboardQWERTY.Z.toString().toLowerCase(), keyZ);
			put(KeyboardQWERTY.Z.toString().toUpperCase(), keyZ);
			put(KeyboardQWERTY.X.toString().toLowerCase(), keyX);
			put(KeyboardQWERTY.X.toString().toUpperCase(), keyX);
			put(KeyboardQWERTY.C.toString().toLowerCase(), keyC);
			put(KeyboardQWERTY.C.toString().toUpperCase(), keyC);
			put(KeyboardQWERTY.V.toString().toLowerCase(), keyV);
			put(KeyboardQWERTY.V.toString().toUpperCase(), keyV);
			put(KeyboardQWERTY.B.toString().toLowerCase(), keyB);
			put(KeyboardQWERTY.B.toString().toUpperCase(), keyB);
			put(KeyboardQWERTY.N.toString().toLowerCase(), keyN);
			put(KeyboardQWERTY.N.toString().toUpperCase(), keyN);
			put(KeyboardQWERTY.M.toString().toLowerCase(), keyM);
			put(KeyboardQWERTY.M.toString().toUpperCase(), keyM);
			put("Á", keyA);
			put("É", keyE);
			put("Í", keyI);
			put("Ó", keyO);
			put("Ú", keyU);
			put("á", keyA);
			put("é", keyE);
			put("í", keyI);
			put("ó", keyO);
			put("ú", keyU);
			put("/", keySlash);
			put("-", keyDash);
			put("_", keyLowDash);
			put("ü", keyUpoints);
		}
	};

	private Map<String, WebElement> numericMap = new HashMap<String, WebElement>() {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		{
			put("1", keyOne);
			put("2", keyTwo);
			put("3", keyThree);
			put("4", keyFour);
			put("5", keyFive);
			put("6", keySix);
			put("7", keySeven);
			put("8", keyEight);
			put("9", keyNine);
		}
	};

	private LoginPageSVE loginPage;

	/**
	 * 
	 * @param driver
	 */
	public Keyboard(WebDriver driver) {
		super(driver);
	}

	public WebElement getKeyBloqMayus() {
		return keyBloqMayus;
	}

	public void setKeyBloqMayus(WebElement keyBloqMayus) {
		this.keyBloqMayus = keyBloqMayus;
	}

	public WebElement getKeyAccent() {
		return keyAccent;
	}

	public void setKeyAccent(WebElement keyAccent) {
		this.keyAccent = keyAccent;
	}

	public Map<String, WebElement> getMapKeysNormal() {
		return mapKeysNormal;
	}

	public Map<String, WebElement> getNumericMap() {
		return numericMap;
	}

	public void enterPassword(String password) {
		char[] arrayPass = password.toCharArray();

		for (int i = 0; i < password.length(); i++) {
			String letterString = String.valueOf(arrayPass[i]);
			try {
				Integer.parseInt(letterString);
				clickOnNumericKey(letterString);
			} catch (NumberFormatException e) {
				isUpperCase(arrayPass[i]);
				hasAccent(letterString);

				clickOnKey(mapKeysNormal.get(letterString));

				isUpperCase(arrayPass[i]);
				hasAccent(letterString);
			}
		}
	}

	private void clickOnKey(WebElement element) {
		RunEvents.executeScriptByElement(element, SCRIPT, loginPage.getWebDriver());
	}

	private void clickOnNumericKey(String number) {
		RunEvents.loadStandBy(2);
		WebElement element = numericMap.get(number);
		element.click();
		RunEvents.moveMouse(loginPage.getTitleSVE(), loginPage.getWebDriver());
	}

	private boolean validateAccent(String letter) {
		Pattern pattern = Pattern.compile("[áÁéÉíÍúÚóÓ]");
		Matcher matcher = pattern.matcher(letter);

		return matcher.matches();
	}

	private void isUpperCase(char character) {
		if (Character.isUpperCase(character))
			clickOnKey(keyBloqMayus);
	}

	private void hasAccent(String letter_string) {
		if (validateAccent(letter_string))
			clickOnKey(keyAccent);
	}

	public void setLoginPage(LoginPageSVE loginPage) {
		this.loginPage = loginPage;
	}

}
