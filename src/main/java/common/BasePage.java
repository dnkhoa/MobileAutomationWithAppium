package common;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import utils.*;
import utils.XPathResource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import static common.BaseTest.*;

import utils.Constants;

public class BasePage {

	protected AppiumDriver driver;
	private XPathResource mXPathResource;
//	private final TouchAction touchAction;
//	private boolean mIsAndroid = true;

	public BasePage(AppiumDriver driver) {
		this.driver = driver;
	//	this.touchAction = new TouchAction(driver);
	//	mIsAndroid = BaseTest.mIsAndroid;
		if(mXPathResource == null){
			mXPathResource = new XPathResource();
			mXPathResource.loadResource(BaseTest.platformVersion);
		}
	}

	private MobileElement getMobileElement(String elementID) {
		// TODO Auto-generated method stub
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(By.xpath(mXPathResource.getXPathByKey(elementID)));
		} catch (NoSuchElementException e) {
			testcaselogInfo.error = "Can not find element: " + elementID;
			Log.info("***** ERROR *****");
			Log.info(testcaselogInfo.error);
			Log.logAssertion(false, "Error: " + e.getMessage());
		}
		return element;
	}

	private String getText(String elementID) {
		return this.getMobileElement(elementID).getText();
	}

	protected boolean isElementPresentByXpath(String elementID) {
		Log.info("-----");
		Log.info("Check if " + elementID + " exists on the screen");
		Log.info("-----");
		try {
			driver.findElement(By.xpath(mXPathResource.getXPathByKey(elementID)));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	protected boolean isElementPresentByText(String item) {
		Log.info("-----");
		Log.info("Check if " + item + " exists");
		Log.info("-----");
		try {
			driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + item + "')]"));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	protected MobileElement findElementWithTimeout(String elementID, int timeOutInSeconds) {
		return (MobileElement) (new WebDriverWait(driver, timeOutInSeconds))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(mXPathResource.getXPathByKey(elementID))));
	}

	protected void verifyText(String elementID, String data) {
		String expected = data;
			String actual = this.getMobileElement(elementID).getText();
			Log.logVerifyElementResult(actual.equals(expected),
					Constants.KEYWORD_FAIL + " -- The actual text is not same with expected text -- ", actual, expected,
					testcaselogInfo);
	}
	
	protected boolean isImageElementPresent(String targetImgPath){
		return OCR.elementExists(targetImgPath);
	}

	protected void clickByImage(String targetImgPath){
		OCR.clickByImage(targetImgPath);
	}

	protected void clickButton(String elementID) {
		Log.info("-----");
		Log.info("Click button " + elementID);
		Log.info("-----");
		this.getMobileElement(elementID).click();
		waitForLoad(1000);
	}

	protected void writeInInput(String elementID, String data) {
		Log.info("-----");
		Log.info("Input textbox " + elementID + "with value " + data);
		Log.info("-----");
		this.getMobileElement(elementID).sendKeys(data);
	}

	protected void checkItemsOfList(String... items){
		for(String item : items){
			if(isElementPresentByText(item)){
				Log.logVerifyElementResult(false, "Item " + item + " is not displayed on list", testcaselogInfo);
			}
		}
	}

	protected void scrollToExpectedElement(String item) {
		Log.info("Find " + item + " from list then select");
		do {
			try {
				driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + item + "')]"));
				break;
			} catch (Exception NoSuchElementException) {
				swipingVertical();
			}
		} while (true);
		waitForLoad(500);
	}

	protected void swipingVertical() {
		// Get the size of screen.
		Dimension size = driver.manage().window().getSize();
		Log.info(size.toString());

		// Find swipe start and end point from screen's with and height.
		// Find starty point which is at bottom side of screen.
		int starty = (int) (size.height * 0.50);
		// Find endy point which is at top side of screen.
		int endy = (int) (size.height * 0.20);
		// Find horizontal point where you wants to swipe. It is in middle of
		// screen width.
		int startx = size.width / 2;
		Log.info("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);

		// Swipe from Bottom to Top.
		driver.swipe(startx, starty, startx, endy, 3000);
		waitForLoad(2000);
	}

	protected void rotateToLanscape() {
		driver.rotate(ScreenOrientation.LANDSCAPE);
	}

	protected void rotateToPortrait() {
		driver.rotate(ScreenOrientation.PORTRAIT);
	}

	protected ScreenOrientation getOrientation() {
		return driver.getOrientation();
	}

	protected void setLocation(double lat, double lon, double alt) {
		Location loc = new Location(lat, lon, alt);
		driver.setLocation(loc);
	}

	protected void longTapOnScreen() {
		Dimension size = driver.manage().window().getSize();
		TouchAction touchAction = new TouchAction(driver);
		touchAction.longPress((int) (size.width * 0.70), (int) (size.height * 0.30), java.time.Duration.ofMillis(3000))
				.release().perform();
	}

	protected void waitForLoad(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void checkGUIOfElement(String testCaseNumber, String deviceName, String elementID) {
		if (isElementPresentByXpath(elementID)) {
			String path = System.getProperty("user.dir") + File.separator + "data" + File.separator + testCaseNumber
					+ File.separator + deviceName + File.separator + elementID;
			if (Constants.MODE == 0)
				MyUtil.saveElementInfoToFile(path, driver, getMobileElement(elementID));
			else
				checkGUIOfElement(path, elementID);
		} else{
			testcaselogInfo.error = "Can not find element" + elementID;
			Log.info("***** ERROR *****");
			Log.info(testcaselogInfo.error);
			Log.logAssertion(false, "Error: " + testcaselogInfo.error);
		}
	}

	private void checkGUIOfElement(String path, String elementID) {
		// check attribute of element
		Log.info("-----");
		Log.info("Check attribute of element" + elementID);
		Log.info("-----");
		MobileElement element = getMobileElement(elementID);
		Map<String, String> expectedResult = MyUtil.getAttributesOfElementFromFile(path + ".properties");
		Map<String, String> actualResult = MyUtil.getAttributesOfElement(element);
		boolean result = MyUtil.compareEntries(expectedResult, actualResult);
		Log.logVerifyElementResult(result, "-- The attributes of element are not same with expected result --",
				testcaselogInfo);

		// compare image
		Log.info("-----");
		Log.info("Check image of element" + elementID);
		Log.info("-----");
		BufferedImage expectedImage = MyUtil.loadImageFromFile(path + ".png");
		BufferedImage actualImage = MyUtil.captureElementScreenshot(driver, element);
		String differentPointImagePath = path + "_changed" + ".png";
		result = MyUtil.compareImage(expectedImage, actualImage, differentPointImagePath);
		Log.logVerifyElementResult(result, "-- The image of element are not same with expected result --",
				testcaselogInfo);
	}

}
