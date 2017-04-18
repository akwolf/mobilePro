package com.jnzy.mdm.comm.exception;

/**
 * 运行时异常.
 * @author wangkuan
 *
 */
public class AppRuntimeException extends RuntimeException {

	public AppRuntimeException() {
	}

	public AppRuntimeException(String message) {
		super(message);
	}

	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
	}

	public AppRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
