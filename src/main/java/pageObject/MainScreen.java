package pageObject;

import common.BasePage;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MainScreen extends BasePage {

    public static final String MENU_BUTTON = "MainScreen_MenuButton";
	
	public MainScreen(AppiumDriver driver) {
		super(driver);
	}
}
