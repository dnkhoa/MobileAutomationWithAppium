package utils;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;

import common.BaseTest;

public class Log {
	// Initialize Log4j logs

	private static Logger Log = Logger.getLogger(Log.class.getName());//

	// This is to print log for the beginning of the test case, as we usually
	// run so many test cases as a test suite

	public static void startTestCase(String sTestCaseName) {

		Log.info("****************************************************************************************");	

		Log.info(sTestCaseName);

		Log.info("****************************************************************************************");

	}
	
	public static void startTestSuite(){
		Log.info("****************************************************************************************");

		Log.info("Start Test Suite");

		Log.info("****************************************************************************************");
	}
	
	public static void endTestSuite(){
		Log.info("Test suite END");

		Log.info("-----------------------------------------------------------------------------------------");
	}
	
	

	// This is to print log for the ending of the test case

	public static void endTestCase(String sTestCaseName) {
		if(BaseTest.passedTest){
			Log.info("PASSED");
		}else{
			Log.info("FALSE");
		}

		Log.info("----------------------------------------------------------------------------------------");


	}

	// Need to create these methods, so that they can be called

	public static void info(String message) {

		Log.info(message);

	}

	public static void warn(String message) {

		Log.warn(message);

	}

	public static void error(String message) {

		Log.error(message);

	}

	public static void fatal(String message) {

		Log.fatal(message);

	}

	public static void debug(String message) {

		Log.debug(message);
	}
	
	public static void logAssertion(boolean condition,String message){
		if(!condition){
			Log.error(message);
			BaseTest.passedTest = false;
			AssertJUnit.fail();
		}
	}
	
	public static void logAssertion(boolean condition,String message,TestCaseLogInfo tcInfo){
		if(!condition){
			Log.error(message);
			tcInfo.writeError(false, message);
			BaseTest.passedTest = false;
			AssertJUnit.fail();
		}
	}
	
/*	public static void logAssertion(String message,String actual, String expected,boolean condition){
//		if(!condition){
//			Log.error(message);
//			Log.error("Actual result: " + actual);
//			Log.error("Actual expected: " + expected);
//			AssertJUnit.fail();
//		}
		if(!condition){
			try {
				Log.error(message);
				Log.error("Actual result: " + actual);
				Log.error("Expected result: " + expected);
				BaseTest.passedTest = false;
				Assert.fail();
			} catch (Exception e) {
				Log.error(e.getMessage());
			}
		}
	}*/
	
	public static void logAssertion(boolean condition, String message, String actual, String expected, TestCaseLogInfo tcInfo){
		if(!condition){
			try {
				Log.error(message);
				Log.error("Actual result: " + actual);
				Log.error("Expected result: " + expected);								
				tcInfo.writeError(false, message, actual, expected);
				BaseTest.passedTest = false;
				Assert.fail();
			} catch (Exception e) {
				Log.error(e.getMessage());
			}
		}
	}

/*	public static void logVerifyElementResult(String message, String actual, String expected, TestCaseLogInfo tcInfo){
		try {
			Log.error(message);
			Log.error("Actual result: " + actual);
			Log.error("Expected result: " + expected);								
			tcInfo.errorAppend(message, actual, expected);
			BaseTest.passedTest = false;
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
	}*/
	
	public static void logVerifyElementResult(boolean condition, String message, String actual, String expected, TestCaseLogInfo tcInfo){
		if(!condition){
			try {
				Log.error(message);
				Log.error("Actual result: " + actual);
				Log.error("Expected result: " + expected);								
				tcInfo.writeError(false, message, actual, expected);
				BaseTest.passedTest = false;
			} catch (Exception e) {
				Log.error(e.getMessage());
			}
		}
	}
	
	public static void logVerifyElementResult(boolean condition, String message, TestCaseLogInfo tcInfo){
		if(!condition){
			try {
				Log.error(message);								
				tcInfo.writeError(false, message);
				BaseTest.passedTest = false;
			} catch (Exception e) {
				Log.error(e.getMessage());
			}
		}
	}
}
