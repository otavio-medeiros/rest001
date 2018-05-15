package br.edu.unifor.mia.rest001.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public static String getProperty(String propertyName) {

		String propertyValue = null;
		Properties properties = new Properties();
		InputStream inputStream = null;

		try {
			StringBuilder propertiesFile = new StringBuilder(System.getProperty("EXTERNAL_CONFIG_PATH"));
			propertiesFile.append(System.getProperty("PROPERTIES_FILE"));
			inputStream = new FileInputStream(propertiesFile.toString());
			properties.load(inputStream);
			propertyValue = properties.getProperty(propertyName);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}

		return propertyValue;

	}

}