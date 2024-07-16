package testcases;

import common.BaseTest;
import org.testng.annotations.Test;

import pageObject.MainScreen;
import pageObject.TermsOfServiceScreen;
import pageObject.TutorialScreen;

public class DemoTest extends BaseTest {

	private TermsOfServiceScreen termsOfServiceScreen;
	private TutorialScreen tutorialScreen;
	private MainScreen mainScreen;

	@Test
	public void DemoTestCase(){
		termsOfServiceScreen = mApplication.termsOfServiceScreen();
		tutorialScreen = termsOfServiceScreen.acceptTermOfService();
		mainScreen = tutorialScreen.byPassTutorial();
	}
}
