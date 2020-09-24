package com.github.nitoa_s.log;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogFile{
	public static final String ext="log";
	private Logger logger;
	private Handler handler;
	private Formatter formatter;
	public LogFile(String LogName, String path, Level level) {
		logger = Logger.getLogger(LogName);
		try {
			handler = new FileHandler(path);
			logger.addHandler(handler);
			formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);
			logger.setLevel(level);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LogName+"でエラーが発生しました");
		}
	}

	public LogFile(String LogName, String path) {
		this(LogName, path, Level.INFO);
	}
	public void setLog(Level level, String logContent) {
		logger.log(level, logContent);
	}
}
