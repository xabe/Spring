package com.indizen.springCurso;

public interface UserService {

	
	void beforeAdice(String value);
	
	void afterAdice(String value);
	
	String afterRunningAdice(String value);
	
	void aroundAdice(String value);
	
	void throwAdice(String value) throws IllegalArgumentException;
}
