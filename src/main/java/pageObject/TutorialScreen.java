package pageObject;

import common.BasePage;
import io.appium.java_client.AppiumDriver;

public class TutorialScreen extends BasePage {

    public static final String NEXT_BUTTON = "TutorialScreen_NextButton";
    public static final String PREV_BUTTON = "TutorialScreen_PrevButton";
    public static final String START_BUTTON = "TutorialScreen_StartButton";

	public TutorialScreen(AppiumDriver driver) {
		super(driver);
        this.driver = driver;
		// TODO Auto-generated constructor stub
	}

    public MainScreen byPassTutorial(){

        clickButton(TutorialScreen.NEXT_BUTTON);
        clickButton(TutorialScreen.NEXT_BUTTON);
        clickButton(TutorialScreen.NEXT_BUTTON);
        clickButton(TutorialScreen.NEXT_BUTTON);
        clickButton(TutorialScreen.NEXT_BUTTON);
        clickButton(TutorialScreen.START_BUTTON);

        return new MainScreen(driver);
    }
}
