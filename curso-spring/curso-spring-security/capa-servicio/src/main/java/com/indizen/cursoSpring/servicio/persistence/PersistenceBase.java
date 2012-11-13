package com.indizen.cursoSpring.servicio.persistence;

import java.util.List;

import com.indizen.cursoSpring.servicio.model.EntityBase;
import com.indizen.cursoSpring.servicio.model.ExampleBase;

public interface PersistenceBase <T extends EntityBase, D extends ExampleBase> {

	public List<T> selectByExamplePaginated(D example,PaginationContext paginationContext);

	public void deleteAllData();
}