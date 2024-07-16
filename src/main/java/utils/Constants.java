package utils;

import java.io.File;

public class Constants {
	public static String APPINUM_SERVER = "http://127.0.0.1:4723/wd/hub";

    public static int NEW_COMMAND_TIMEOUT=300;
    public static long IMPLICIT_WAIT = 15; // second
    public static long ENTRY_APP_WAIT = 10000; //millisecond
    public static int MODE = 1; //0: Save expected result to file, 1: Load expected result from file to check
//    public static double RATIO = 8*1.0 / 9;
    public static String KEYWORD_PASS="PASS";
    public static String KEYWORD_FAIL="FAIL";
    public static String RESULT="Result";
    public static String TEXT="text";
    public static String RESOURCE_ID="resourceId";
    public static String CONTENT_DESC="contentDescription";
    public static String CHECKABLE="checkable";
    public static String CHECKED="checked";
    public static String CLICKABLE="clickable";
    public static String ENABLED="enabled";
    public static String FOCUSABLE="focusable";
    public static String FOCUSED="focused";
    public static String SCROLLABLE="scrollable";
    public static String LONG_CLICKABLE="longClickable";
    public static String SELECTED="selected";
    public static String LAT="lat";
    public static String LON="lon";
    public static String WIDTH="width";
    public static String HEIGHT="height";
    public static String CLASS_PATH_ROOT = System.getProperty("user.dir");
    public static String RESOURCE_IMAGE_PATH_ROOT = CLASS_PATH_ROOT + File.separator + "data" + File.separator + "drawable-xxhdpi";
    public static String SCALED_IMAGE_PATH_ROOT = CLASS_PATH_ROOT + File.separator + "data" + File.separator + "ScaledImage";
	
	// for Android
	public static String APP_PACKAGE = "net.zmap.nxcapp.drvplz";
	public static String APP_ACTIVITY = "net.zmap.nxcapp.drvplz.NewDrivePlazaActivity";

	// for iOS
	public static final String XCODEORGID = "";
	public static final String XCODE_SIGNING_ID = "";
	public static final String APP_BUNDLE_ID = "";
}
