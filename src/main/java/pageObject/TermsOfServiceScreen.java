package pageObject;

import common.BasePage;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TermsOfServiceScreen extends BasePage {

    public static final String AGREE_BUTTON = "TermsOfServiceScreen_AgreeButton";
    public static final String SUSPEND_BUTTON = "TermsOfServiceScreen_SuspendButton";

	public TermsOfServiceScreen(AppiumDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public TutorialScreen acceptTermOfService (){
		clickButton(TermsOfServiceScreen.AGREE_BUTTON);
		return new TutorialScreen(driver);
	}

	public void notAcceptTermOfService (){
		clickButton(TermsOfServiceScreen.SUSPEND_BUTTON);
	}
}
