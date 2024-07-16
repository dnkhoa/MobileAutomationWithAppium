package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import common.BaseTest;

public class XPathResource {
	private String mIOSVersion = "9.3";
	private Properties mXPathProperties;
	private String miOSFileNameFormat = "XPath_iOS_%s.properties";
	private String mAndroidSFileName = "XPath_Android.properties";
	private boolean mIsAndroid = true;

	public XPathResource(){
		mIsAndroid = BaseTest.mIsAndroid;
	}
	
	public void loadResource(String platformVersion) {
		URL resource;
		if(mIsAndroid){
			ClassLoader classLoader = XPathResource.class.getClassLoader();
			resource = classLoader.getResource(mAndroidSFileName);
		}
		else{
			mIOSVersion = platformVersion;
			ClassLoader classLoader = XPathResource.class.getClassLoader();
			String fileName = String.format(miOSFileNameFormat, mIOSVersion);
			resource = classLoader.getResource(fileName);
		}
		FileInputStream inputStream = null;
		File file;
		try {
			file = new File(resource.toURI());
			inputStream = new FileInputStream(file);
			mXPathProperties = new Properties();
			mXPathProperties.load(inputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getXPathByKey(String inKey) {
		return mXPathProperties.getProperty(inKey);
	}
}
