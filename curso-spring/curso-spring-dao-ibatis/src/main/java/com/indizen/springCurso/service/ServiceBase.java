package com.indizen.springCurso.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.indizen.springCurso.model.EntityBase;

public interface ServiceBase<T extends EntityBase> {
	public static final Logger LOGGER = Logger.getLogger(ServiceBase.class);

	public void add(T t);

	public void update(T t);

	public void delete(T t);

	public List<T> getAll();

}