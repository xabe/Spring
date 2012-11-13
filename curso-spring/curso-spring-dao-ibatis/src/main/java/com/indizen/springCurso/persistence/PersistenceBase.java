package com.indizen.springCurso.persistence;

import java.util.List;

import com.indizen.springCurso.model.EntityBase;

public interface PersistenceBase <T extends EntityBase> {

	void insert(T t);
	
	void update(T t);
	
	void delete(T t);
	
	List<T> getAll();
}