import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LogHandler {

	private String FILE_SEPERATOR = "_";
	private String DIR_SEPERATOR = "\\"; // For windows use "\\" and Linux use "/"
	private String LOG_EXTENSION = ".log";
	private static String logFileName;
	private Logger rootLogger;
	private PatternLayout layout;
	private static final Logger log = Logger.getLogger(LogHandler.class);
	private static LogHandler logHandlerInstance;
	
	protected LogHandler() {
		// TODO Auto-generated constructor stub
		rootLogger = Logger.getRootLogger();
		rootLogger.setLevel(Level.DEBUG);

		// Define log pattern layout
		layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");
		//PatternLayout layout = new PatternLayout("%r [%t] %p %c %x - %m%n");

		// Add console appender to root logger
		rootLogger.addAppender(new ConsoleAppender(layout));
	}
	
	//singleton pattern logger instance function
	public static LogHandler getLoggerInstance(){
		if(logHandlerInstance == null){
			logHandlerInstance = new LogHandler();
		}
		return logHandlerInstance;
	}
	
	/*
	 * @param directory - absolute path of the Log folder Eg: /raid/dwsupport/datafeed/ or c:\\raid\\dwsupport\\datafeed\\
	 * @param fileName - file name for the log
	 * @param flag - 0 - append the date Eg: 2014-11-12  
	 *	  			 1 - append the date with hour Eg: 2014-11-12_22
	 * 
	 */
	public void setFileName(String directory, String fileName,int flag) {
		try {
		File dir = new File(directory);
		//Creating directory if it does not exist
		if (!(dir.isDirectory() && dir.exists())) {
			dir.mkdirs();
		}

		Date date = new Date();
		String format = "yyyy-MM-dd";
		if(flag == 1) {
			format = "yyyy-MM-dd_hh";
		}
			
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		logFileName = directory + DIR_SEPERATOR + fileName + FILE_SEPERATOR + dateFormat.format(date)+ LOG_EXTENSION;

		RollingFileAppender fileAppender = new RollingFileAppender(layout,logFileName);
		rootLogger.addAppender(fileAppender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loggerInfo(String msg) {
		log.info(msg);
	}
	
	public void loggerError(String msg) {
		log.error(msg);
	}
	
	public void loggerDebug(String msg) {
		log.debug(msg);
	}
	
	public void loggerWarning(String msg) {
		log.warn(msg);
	}

}
