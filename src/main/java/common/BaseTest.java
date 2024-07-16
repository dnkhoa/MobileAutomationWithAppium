package common;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import app.Application;
import utils.Constants;
import utils.TestCaseLogInfo;
import utils.*;

public class BaseTest {

	protected static Application mApplication;
	private DesiredCapabilities capabilities;
	public static boolean mIsAndroid = true;
	private boolean mIsIOS10OrAbove = false;
	public static String platformVersion = "";
	
	public final static String REPORT_FOLDER = "TestCaseLogInfo";
	public static TestCaseLogInfo testcaselogInfo;
	public static boolean passedTest = true;
	
	public static OCR OCR;
	
	@BeforeSuite
	public void beforeSuite() {
		createLogFile();
		DOMConfigurator.configure("log4j.xml");
		Log.startTestSuite();
	}
	
	@AfterSuite
	public void afterSuite() {
		Log.endTestSuite();
	}

	@BeforeMethod
	@Parameters({ "deviceName_", "UDID_", "platformName_", "platformVersion_", "URL_" })
	public void setUp(String deviceName_, String UDID_, String platformName_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
		mIsAndroid = platformName_.equals("Android");
		platformVersion = platformVersion_;
		mApplication = new Application();
		
		startApplication(deviceName_, UDID_, platformVersion_, URL_);
	}

	@AfterMethod
	public void tearDown() {
		Log.endTestCase("end testcase");
		saveTestcaseInfo();
		passedTest = true;
		mApplication.stop();
	}

	private void startApplication(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
		if (!mIsAndroid)
		{
			String[] platfomrmversion = platformVersion_.split("\\.");
			int firstnumberPlatForm = Integer.parseInt(platfomrmversion[0]);
			if (firstnumberPlatForm >= 10)
			{
				System.out.println("IOS version is 10 or above");
				mIsIOS10OrAbove = true;
			}
		}

		capabilities = new DesiredCapabilities();
		// common
		capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.2.0");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName_);
		capabilities.setCapability("udid", UDID_);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, mIsAndroid ? "Android" : "ios");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		capabilities.setCapability("noReset", "true");
		capabilities.setCapability("newCommandTimeout", Constants.NEW_COMMAND_TIMEOUT);

		if (mIsAndroid) {// for Android
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Constants.APP_PACKAGE);
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Constants.APP_ACTIVITY);
			//Use to Platform 7.0 and higher 
			String[] platfomrmversion = platformVersion.split("\\.");
			int firstnumberPlatForm = Integer.parseInt(platfomrmversion[0]);
			if(firstnumberPlatForm >= 7){
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
			}			
		} else {// for iOS
			capabilities.setCapability(MobileCapabilityType.UDID, UDID_);
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
			capabilities.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, Constants.XCODEORGID);
			capabilities.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, Constants.XCODE_SIGNING_ID);
			capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, Constants.APP_BUNDLE_ID);
		}
		Log.info("Device name: " + capabilities.getCapability("deviceName").toString());
		Log.startTestCase("start testcase");
		testcaselogInfo = new TestCaseLogInfo();
		Log.info("Application: Starting application ... ");
		mApplication.start(capabilities);
	}
	
	//Create log4j.xml to config log4j log file.
	private void createLogFile() {
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString();
		System.out.println(path);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Calendar.getInstance().getTime());
		String input = path + "//src//main//resources//log4j.xml";
		String output = path + "//log4j.xml";
		File inputFile = new File(input);
		File outputFile = new File(output);
		MyUtil.replaceInFile(inputFile, outputFile, "logfile.log", timeStamp + ".log");
	}

	// Save test case log info.
	private void saveTestcaseInfo() {
		Log.info("Start saving test case info ...");
		testcaselogInfo.endTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		if (passedTest) {
			testcaselogInfo.testResult = "PASSED";
		} else {
			testcaselogInfo.testResult = "FAILED";
		}

		String reportFolder = System.getProperty("user.dir") + File.separator + REPORT_FOLDER + File.separator
				+ testcaselogInfo.deviceModel;
		File directory = new File(reportFolder);

		// Write to file Here
		BufferedWriter writer = null;
		try {
			boolean success = false;
			if (!directory.exists()) {
				success = directory.mkdir();
				if (success) {
					Log.info("Report folder is created successfully " + reportFolder);
				} else {
					Log.info("Report folder creating fails " + reportFolder);
				}
			}

			// create a temporary file
			System.out.println(testcaselogInfo.testSuite);
			testcaselogInfo.fileName = testcaselogInfo.testSuite + "_"
					+ testcaselogInfo.testCaseID + "_" + testcaselogInfo.endTime + "_" + testcaselogInfo.testResult
					+ ".txt";
			String logName = reportFolder + File.separator + testcaselogInfo.fileName;
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(logName), Charset.forName("UTF-8").newEncoder()));
			writer.write(testcaselogInfo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	/*
	public void saveTestcaseDescription(String id, String des) {
		testcaselogInfo.testSuite = this.getClass().getSimpleName();
		testcaselogInfo.testCaseID = id;
		testcaselogInfo.testCaseDescription = des;
		testcaselogInfo.deviceModel = capabilities.getCapability("deviceName").toString();
		testcaselogInfo.platform = capabilities.getCapability("platformName").toString();

		Log.info("Testcase ID: " + id);
		Log.info("Testcase Description: " + des);
	}
	*/

	/*
	private String getDensityOfDevice(){
		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec("adb -s" + capabilities.getCapability("uid").toString() + 
					"shell getprop ro.sf.lcd_density");
			p.waitFor();
			BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();
	}*/
}
