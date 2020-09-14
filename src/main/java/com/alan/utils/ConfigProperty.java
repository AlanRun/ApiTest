package com.alan.utils;

import java.io.*;
import java.util.Properties;

public class ConfigProperty {

	public String fileLocation;

	Properties p = new Properties();
	BufferedReader br;

	public ConfigProperty() {
	}

	public ConfigProperty(String configFileLocation) {

		fileLocation = configFileLocation;

	}

	public String getProperty(String key) {
		String value = null;
		try {
			br = new BufferedReader(new FileReader(fileLocation));

			p.load(br);

			value = p.getProperty(key);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public boolean setProperty(String key, String value) {
		FileOutputStream oFile;
		try {
			oFile = new FileOutputStream(fileLocation, true);
			p.setProperty(key, value);
			p.store(oFile, "");
			oFile.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
