package com.indizen.springCurso;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.indizen.springCurso.model.product.Product;
import com.indizen.springCurso.persistence.PersistenceBase;
import com.indizen.springCurso.persistence.entity.ProductDAO;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProductDAO dao = (ProductDAO) context.getBean("productDAO");
		LOGGER.info("#################-Empiza el uso DAO con JPA-#################");
		Product entity = new Product();
		entity.setNombre("Juan");
		entity.setNumero(12);
		entity.setFecha(new Date());
		LOGGER.info("Creamos: "+entity.toString());
		LOGGER.info("--------------Insert--------------");
		dao.insert(entity);
		verTabla(dao);
		LOGGER.info("--------------Insert--------------");
		
		LOGGER.info("--------------Update--------------");
		entity.setNombre("Juan Pablo");
		entity.setNumero(1);
		entity.setFecha(new Date());
		dao.update(entity);
		verTabla(dao);
		LOGGER.info("--------------Update--------------");
		
		LOGGER.info("--------------Delete--------------");
		dao.delete(entity);
		verTabla(dao);
		LOGGER.info("--------------Delete--------------");		
		LOGGER.info("#################-Fin del uso DAO con JPA-#################");
		context.close();
	}

	
	private static void verTabla(PersistenceBase<?> persistenceBase){
		List<?> list = persistenceBase.getAll();
		for(Object object : list){
			LOGGER.info(object.toString());
		}
	}
}
