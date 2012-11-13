package com.indizen.cursoSpring.servicio;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.indizen.cursoSpring.servicio.model.rest.Rest;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
import com.indizen.cursoSpring.servicio.service.rest.RestService;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-servicio.xml");
		RestService service = context.getBean(RestService.class);
		LOGGER.info("#################-Empiza el uso DAO con Ibatis-#################");
		Rest entity = new Rest();
		entity.setNombre("Juan");
		entity.setNumero(12);
		entity.setFecha(new Date());
		LOGGER.info("Creamos: "+entity.toString());
		LOGGER.info("--------------Insert--------------");
		service.add(entity);
		verTabla(service);
		LOGGER.info("--------------Insert--------------");
		
		LOGGER.info("--------------Update--------------");
		entity.setNombre("Juan Pablo");
		entity.setNumero(1);
		entity.setFecha(new Date());
		service.update(entity);
		verTabla(service);
		LOGGER.info("--------------Update--------------");
		
		LOGGER.info("--------------Delete--------------");
		service.delete(entity);
		verTabla(service);
		LOGGER.info("--------------Delete--------------");		
		LOGGER.info("#################-Fin del uso DAO con Ibatis-#################");
		context.close();
	}

	
	private static void verTabla(ServiceBase<?,?> serviceBase){
		List<?> list = serviceBase.getAll();
		for(Object object : list){
			LOGGER.info(object.toString());
		}
	}
}
