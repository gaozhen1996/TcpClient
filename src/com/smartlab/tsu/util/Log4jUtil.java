package com.smartlab.tsu.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author huang
 *
 */
public class Log4jUtil {
	
	public static Logger getLogger(Class<?> clazz){
		PropertyConfigurator.configure("log4j.properties");
		Logger logger=Logger.getLogger(clazz);
		return logger;
	}
}
