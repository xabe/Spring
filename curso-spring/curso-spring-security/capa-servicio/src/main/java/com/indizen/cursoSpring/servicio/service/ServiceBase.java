package com.indizen.cursoSpring.servicio.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.indizen.cursoSpring.servicio.model.EntityBase;
import com.indizen.cursoSpring.servicio.model.ExampleBase;

public interface ServiceBase<T extends EntityBase, D extends ExampleBase> {
	public static final Logger LOGGER = Logger.getLogger(ServiceBase.class);

	public void add(T t);

	public void update(T t);
	
	public void update(T t,D d);

	public void delete(T t);
	
	public void delete(D d);

	public List<T> getAll();
	
	public List<T> getAll(D d);
	
	public int getTotal();
	
	public int getTotal(D d);

	public T findById(Integer id);
	
	public T findById(BigDecimal id);
	
	public void deleteAllData();

}