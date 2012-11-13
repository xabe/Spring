package com.indizen.springCurso;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	private static final String MESSAGES = "\tMe llega el valor: ";
	
	public void beforeAdice(String value) {
		LOGGER.info(MESSAGES+value);
	}

	public void afterAdice(String value) {
		LOGGER.info(MESSAGES+value);
	}
	
	public String afterRunningAdice(String value) {
		LOGGER.info(value);
		return MESSAGES+value;
	}

	public void aroundAdice(String value) {
		LOGGER.info(MESSAGES+value);
	}

	public void throwAdice(String value) throws IllegalArgumentException {
		LOGGER.info(MESSAGES+value);
		throw new IllegalArgumentException();		
	}

}
