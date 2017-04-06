package cn.keepfight.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * properties utility class
 * @author Tom
 *
 */
public class PropertieUtil {

	public static final String PROPERTIES_RESOURCE_PATH = "/cn/keepfight/resources/properties/";

	/**
	 * load a properties file by specifying a file name under {@link #PROPERTIES_RESOURCE_PATH}
	 * @param file the file name which will be loaded
	 * @return Properties object
	 * @throws IOException IOException occurred during loading which this function unable to handle
	 */
	public static Properties loadProperties(String file) throws IOException{
		Properties resProperties = new Properties();

		// construct URL
		String filePath = PROPERTIES_RESOURCE_PATH+file;

		//load properties file
		resProperties.loadFromXML(PropertieUtil.class.getResourceAsStream(filePath));

		return resProperties;
	}



	/**
	 * load a properties file by specifying a file name under {@link #PROPERTIES_RESOURCE_PATH}
	 * <br/>
	 * optionally, it could be passed another parameter defaultFile to try to read another file
	 * when exception occur.
	 * @param file the file name which will be loaded
	 * @param defaultFile default properties file which may be could contain configuration
	 * @return Properties object
	 * @throws IOException IOException occurred during loading which this function unable to handle
	 */
	public static Properties loadProperties(String file, String defaultFile) throws IOException{
		try {
			return loadProperties(file);
		} catch (IOException e) {
			return loadProperties(defaultFile);
		}
	}
}
