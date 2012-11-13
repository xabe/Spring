package com.indizen.springCurso;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.springCurso.model.product.Product;
import com.indizen.springCurso.persistence.entity.ProductDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class EntityServiceTest{
	private static final Logger log = Logger.getLogger(EntityServiceTest.class);
	@Autowired
	private ProductDAO entityDAO;
	private List<Product> entities;

	
	@BeforeClass
	public static void inicializacionClass(){
		log.info("Class initialization EntityServiceTest...");
	}

	@Before
	public void insertEntity() {
		entities = new ArrayList<Product>();
		Product entityTest = new Product();
		entities.add(entityTest);
		for (Product i : entities)
			entityDAO.insert(i);
		log.info("Insert entity");
	}

	@BeforeTransaction
	public void verifyInitialDatabaseState() {
		List<Product> entities = this.entityDAO.getAll();
		assertNotNull(entities);
		assertTrue("Verify data base", entities.size() >= 0);
	}
	
	@Test
	@Rollback(true)
	public void prueba(){
		List<Product> actual = this.entityDAO.getAll();
		assertNotNull(actual);
		assertTrue("Verify data base", actual.size() >= entities.size());
		log.info("Verify info");
	}

	@AfterTransaction
	public void verifyFinalDatabaseState() {
		List<Product> entities = this.entityDAO.getAll();
		assertNotNull(entities);
		assertTrue("Verify data base", entities.size() >= 0);
		log.info("Verify info after transaction");
	}
	

	@AfterClass
	public static void finalizacionClass(){
		log.info("End EntityServiceTest.");
	}

}
