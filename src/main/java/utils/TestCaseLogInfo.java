package utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestCaseLogInfo {
	public String testSuite = "";
	public String testCaseID = "";
	public String testCaseDescription = "";
	public String deviceModel = "";
	public String platform = "";
	public String startTime = "";
	public String endTime = "";
	public String testResult = "";
	public String error = "";
	public String fileName = "";
	
	public TestCaseLogInfo() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		this.startTime = timeStamp;
		this.testResult = "Failed";
	}
	
	public void writeError (boolean condition, String message, String actual, String expected) {
		if (condition == true)
			return;
		this.error += "\n" + message + "\n";
		this.error += "Actual: " + actual + "\n";
		this.error += "Expected: " + expected + "\n";
	}

	public void errorAppend(String message, String actual, String expected) {
		this.error += "\n" + message + "\n";
		this.error += "Actual: " + actual + "\n";
		this.error += "Expected: " + expected + "\n";
	}

	public void writeError (boolean condition, String message) {
		if (condition != true)
			this.error += "\n" + message + "\n";
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		//determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		//print field names paired with their values
		for ( Field field : fields  ) {
			try {
				result.append( field.getName() );
				result.append(": ");
				//requires access to private field:
				result.append( field.get(this) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		return result.toString();
	}
}
