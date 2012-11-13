package com.indizen.springCurso.persistence;

import java.util.List;

public interface PersistenceBase <T> {

	void insert(T t);
	
	void update(T t);
	
	void delete(T t);
	
	List<T> getAll();
}