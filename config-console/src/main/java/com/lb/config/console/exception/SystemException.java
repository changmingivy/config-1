package com.lb.config.console.exception;

/**
 * throw new SystemException("exception message")
 * @author liuzhenhua
 * @date 2016-4-19
 * @Email：lbhuanggua@sina.com
 */
public class SystemException extends RuntimeException {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -4130227848912844890L;
	
	private String code;

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String code, String message) {
		super(message);
		this.code = code;
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
