package utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MyUtil {

	public static void replaceInFile(File fileInput, File fileOutput, String content, String replace) {
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			FileReader fileRead = new FileReader(fileInput);
			FileWriter fw = new FileWriter(fileOutput);
			br = new BufferedReader(fileRead);
			if (!fileOutput.exists()) {
				fileOutput.createNewFile();
			}
			bw = new BufferedWriter(fw);
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.replaceAll(content, replace);
				bw.write(line);
				bw.newLine();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (br != null)
					br.close();
			} catch (Exception ex) {
				System.out.println("Error in closing the Buffere" + ex);
			}
		}
	}

	public static void saveElementInfoToFile(String path,
			AppiumDriver driver, MobileElement element) {
		// save attribute of element
		saveAttributeOfElementToFile(path, element);
		// save image of element
//		BufferedImage dest = captureElementScreenshot(driver, element);
//		saveImage(path, dest);
	}

	public static Map<String, String> getAttributesOfElement(MobileElement element) {
		Map<String, String> hashAttributeElement = new HashMap<String, String>();
		hashAttributeElement.put(Constants.TEXT, element.getAttribute(Constants.TEXT));
//		hashAttributeElement.put(Constants.RESOURCE_ID, element.getAttribute(Constants.RESOURCE_ID));
//		hashAttributeElement.put(Constants.CONTENT_DESC, element.getAttribute(Constants.CONTENT_DESC));
//		hashAttributeElement.put(Constants.CHECKABLE, element.getAttribute(Constants.CHECKABLE));
//		hashAttributeElement.put(Constants.CHECKED, element.getAttribute(Constants.CHECKED));
		hashAttributeElement.put(Constants.CLICKABLE, element.getAttribute(Constants.CLICKABLE));
		hashAttributeElement.put(Constants.ENABLED, element.getAttribute(Constants.ENABLED));
//		hashAttributeElement.put(Constants.FOCUSABLE, element.getAttribute(Constants.FOCUSABLE));
//		hashAttributeElement.put(Constants.FOCUSED, element.getAttribute(Constants.FOCUSED));
//		hashAttributeElement.put(Constants.SCROLLABLE, element.getAttribute(Constants.SCROLLABLE));
//		hashAttributeElement.put(Constants.LONG_CLICKABLE, element.getAttribute(Constants.LONG_CLICKABLE));
//		hashAttributeElement.put(Constants.SELECTED, element.getAttribute(Constants.SELECTED));
		hashAttributeElement.put(Constants.LAT, String.valueOf(element.getLocation().x));
		hashAttributeElement.put(Constants.LON, String.valueOf(element.getLocation().y));
		hashAttributeElement.put(Constants.WIDTH, String.valueOf(element.getSize().width));
		hashAttributeElement.put(Constants.HEIGHT, String.valueOf(element.getSize().height));
		return hashAttributeElement;
	}

	public static void saveAttributeOfElementToFile(String path,
			MobileElement element) {

		Map<String, String> hashAttributeElement = getAttributesOfElement(element);

		Properties properties = new Properties();
		for (Map.Entry<String, String> entry : hashAttributeElement.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
		try {
			// Save attribute of element to file
			File file = new File(path + ".properties");
			file.getParentFile().mkdirs();
			properties.store(new FileOutputStream(file), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, String> getAttributesOfElementFromFile(String path) {

		Map<String, String> expectedResult = new HashMap<String, String>();
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String key : properties.stringPropertyNames()) {
			expectedResult.put(key, properties.get(key).toString());
		}

		return expectedResult;
	}

	public static BufferedImage captureElementScreenshot(AppiumDriver driver, MobileElement element) {
		// Capture entire page screenshot as buffer.
		// Used TakesScreenshot, OutputType Interface of selenium and File class
		// of java to capture screenshot of entire page.
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Used selenium getSize() method to get height and width of element.
		// Retrieve width of element.
		int ImageWidth = element.getSize().getWidth();
		// Retrieve height of element.
		int ImageHeight = element.getSize().getHeight();

		// Used selenium Point class to get x y coordinates of Image element.
		// get location(x y coordinates) of the element.
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();

		// Reading full image screenshot.
		BufferedImage img = null;
		try {
			img = ImageIO.read(screen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// cut Image using height, width and x y coordinates parameters.
		BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
		return dest;
	}

	public static void saveImage(String path, BufferedImage dest) {
		// Used FileUtils class of apache.commons.io.
		// save Image screenshot
		try {
			ImageIO.write(dest, "png", new File(path + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BufferedImage loadImageFromFile(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
/*	public static String resizeImage(String imageName){
		String sourceImagePath = Constants.RESOURCE_IMAGE_PATH_ROOT + File.separator + imageName + ".png";
		BufferedImage sourceImage = loadImageFromFile(sourceImagePath);
		Double newWidth = sourceImage.getWidth()*Constants.RATIO;
		Double newHeight = sourceImage.getHeight()*Constants.RATIO;
		BufferedImage scaleBufferImage  = Scalr.resize(sourceImage, Scalr.Method.BALANCED,newWidth.intValue(),newHeight.intValue());
		String scaledImageFilePath = Constants.SCALED_IMAGE_PATH_ROOT + File.separator + imageName + ".png";
		File scaledImageFile = new File(scaledImageFilePath);
		scaledImageFile.getParentFile().mkdirs();
		try {
			ImageIO.write(scaleBufferImage, "png", scaledImageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scaledImageFilePath;
	}*/
	
	public static boolean compareImage(BufferedImage expectedImage, BufferedImage actualImage, String path) {
		// Create a compare object specifying the 2 images for comparison.
		ImageCompare ic = new ImageCompare(expectedImage, actualImage);
		// Set the comparison parameters. 
		//   (num vertical regions, num horizontal regions, sensitivity, stabilizer)
		ic.setParameters(5, 4, 5, 10);
		// Display some indication of the differences in the image.
		ic.setDebugMode(2);
		// Compare.
		ic.compare();
		// Display if these images are considered a match according to our parameters.
		Log.info("Match: " + ic.match());
		// If its not a match then write a file to show changed regions.
		if (!ic.match()) {
			ImageCompare.saveJPG(ic.getChangeIndicator(), path);
			Log.info("Refer the different point of image at: " + path);
			return false;
		}
		return true;
	}

	public static <K extends Comparable<? super K>, V> boolean compareEntries(final Map<K, V> map1,
			final Map<K, V> map2) {
		final Collection<K> allKeys = new HashSet<K>();
		allKeys.addAll(map1.keySet());
		allKeys.addAll(map2.keySet());
		final Map<K, Boolean> comparisonResult = new TreeMap<K, Boolean>();
		boolean result = true;
		for (final K key : allKeys) {
			boolean tempResult = (map1.containsKey(key) == map2.containsKey(key))
					&& (Boolean.valueOf(equal(map1.get(key), map2.get(key))));
			if (!tempResult)
				result = false;
			comparisonResult.put(key, tempResult);
		}
		for (final Entry<K, Boolean> entry : comparisonResult.entrySet()) {
			Log.info("Entry:" + entry.getKey() + ", value: " + entry.getValue());
		}
		return result;
	}

	private static boolean equal(final Object obj1, final Object obj2) {
		return obj1 == obj2 || (obj1 != null && obj1.equals(obj2));
	}
}
