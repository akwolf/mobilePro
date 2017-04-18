package com.jnzy.mdm.util;

import org.apache.log4j.Logger;

/**
 * 
 * @author wangkuan
 *
 */
public class JnzyLogger {
	public static boolean initLoggger = true;

	private Logger logger;

	private JnzyLogger(Class<?> clazz) {
		super();
		logger = Logger.getLogger(clazz);
	}

	private JnzyLogger(String loggerName) {
		super();
		logger = Logger.getLogger(loggerName);
	}

	/**
	 * 获取一个logger对象，采用静态方法，方便使用。
	 * 
	 * @param loggerName
	 *            日志记录名
	 * @return {@link JnzyLogger}
	 */
	public static JnzyLogger getLogger(String loggerName) {
		return new JnzyLogger(loggerName);
	}

	/**
	 * 获取一个logger对象，采用静态方法，方便使用
	 * 
	 * @param clazz
	 *            日志记录的类名
	 * @return {@link JnzyLogger}
	 */
	public static JnzyLogger getLogger(Class<?> clazz) {
		return new JnzyLogger(clazz);
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	public void debug(Throwable t) {
		logger.debug(t.getMessage(), t);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void warn(Throwable t) {
		logger.warn(t.getMessage(), t);
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	public void error(Throwable t) {
		logger.error(t.getMessage(), t);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	public void info(Throwable t) {
		logger.info(t.getMessage(), t);
	}

	public void trace(String msg) {
		logger.trace(msg);
	}

	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
	}

	public void trace(Throwable t) {
		logger.trace(t.getMessage(), t);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
}
