package com.indizen.springCurso;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	
	public static void main(String[] agrs){
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		MovieListen movieListen = context.getBean(MovieListen.class);
		movieListen.seeAllMovie();		
		context.close();
	}
}
