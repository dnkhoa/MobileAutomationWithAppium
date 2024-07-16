package app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import common.BasePage;
import pageObject.MainScreen;
import pageObject.TermsOfServiceScreen;
import pageObject.TutorialScreen;
import common.BaseTest;
import utils.Constants;

public class Application {
	private AppiumDriver mDriver;
	
	public void start(Capabilities capabilities) throws MalformedURLException, InterruptedException {
		boolean isAndroid = BaseTest.mIsAndroid;
		if (isAndroid) {
			mDriver = new AndroidDriver(new URL(Constants.APPINUM_SERVER), capabilities);
		} else {
			mDriver = new IOSDriver(new URL(Constants.APPINUM_SERVER), capabilities);
		}
		mDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(Constants.ENTRY_APP_WAIT);
	}

    public void stop() {
    	mDriver.quit();
    }
    
	public BasePage basePage() {
		return new BasePage(mDriver);
	}
    
	public MainScreen mainScreen() {
		return new MainScreen(mDriver);
	}
	
	public TermsOfServiceScreen termsOfServiceScreen(){
		return new TermsOfServiceScreen(mDriver);
	}
	
	public TutorialScreen tutorialScreen(){
		return new TutorialScreen(mDriver);
	}

}
