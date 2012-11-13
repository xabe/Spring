package com.indizen.springCurso;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService service = (UserService) context.getBean("userService");
		LOGGER.info("");
		LOGGER.info("#####################-COMIENZO AOP XML-#####################");
		LOGGER.info("");
		LOGGER.info("--------- Comienzo Before");
		LOGGER.info("");
		service.beforeAdice("\tMetodo before");
		LOGGER.info("");
		LOGGER.info("--------- Fin Before");
		LOGGER.info("");
		LOGGER.info("");
		LOGGER.info("--------- Comienzo after");
		LOGGER.info("");
		service.afterAdice("\tMetodo after");
		LOGGER.info("");
		LOGGER.info("--------- Fin after");
		LOGGER.info("");
		LOGGER.info("");
		LOGGER.info("--------- Comienzo afterRunning");
		LOGGER.info("");
		service.afterRunningAdice("\tMetodo afterRunning");
		LOGGER.info("");
		LOGGER.info("--------- Fin afterRunning");
		LOGGER.info("");
		LOGGER.info("");
		LOGGER.info("--------- Comienzo around");
		LOGGER.info("");
		service.aroundAdice("\tMetodo around");
		LOGGER.info("");
		LOGGER.info("--------- Fin around");
		LOGGER.info("");
		LOGGER.info("");
		LOGGER.info("--------- Comienzo throw");
		LOGGER.info("");
		try
		{
			service.throwAdice("Metodo throw");
		}catch (Exception e) {
		}
		LOGGER.info("");
		LOGGER.info("--------- Fin throw");
		LOGGER.info("");
		LOGGER.info("");
		LOGGER.info("#####################-FIN AOP XML-#####################");
		context.close(); 
	}

}
