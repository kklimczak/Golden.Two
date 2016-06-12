package com.goldentwo.utils.Exceptions;

import com.goldentwo.utils.Logger.Logger;

public class SQLUpdateException extends Exception {

	Logger logger = new Logger(SQLUpdateException.class);
	
	public SQLUpdateException() {
		super();
	}

	public SQLUpdateException(String message) {
		super(message);
		logger.error(message);
	}
	
	
}
