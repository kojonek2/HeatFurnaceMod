package pl.com.kojonek2.heatfurnace.utils;

import org.apache.logging.log4j.Logger;


public class LogHelper {

	private static Logger logger;
	
	public static void attachLoger(Logger logger) {
		LogHelper.logger = logger;
	}
	
	public static void info(String msg) {
		logger.info(msg);
	}
	
	public static void error(String msg) {
		logger.error(msg);
	}
	
	public static void debug(String msg) {
		logger.debug(msg);
	}
	
	public static void fatal(String msg) {
		logger.fatal(msg);
	}
	
	public static void trace(String msg) {
		logger.trace(msg);
	}
	
	public static void warn(String msg) {
		logger.warn(msg);
	}

}
