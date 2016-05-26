package com.goldentwo.app;

import org.apache.log4j.Logger;

import groovy.util.logging.Slf4j;

@Slf4j
public class Application {
	
	final static Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {
		System.out.println("work!");
		logger.info("Application start!");
	}

}
