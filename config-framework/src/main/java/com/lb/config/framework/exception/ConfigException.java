package com.lb.config.framework.exception;

/**
 * @author liuzhenhua
 * @Email：lbhuanggua@sina.com
 */
public class ConfigException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 6737426436279258981L;

	public ConfigException() {
		super();
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}
}
