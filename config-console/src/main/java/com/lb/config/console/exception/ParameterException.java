package com.lb.config.console.exception;

/**
 * @author liuzhenhua
 * @date 2016-4-19
 * @Email：lbhuanggua@sina.com
 */
public class ParameterException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 6737426436279258981L;

	public ParameterException() {
		super();
	}

	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}

	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
