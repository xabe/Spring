package com.indizen.cursoSpring.servicio.service.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.rest.Rest;
import com.indizen.cursoSpring.servicio.model.rest.RestExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 
 public interface RestService extends ServiceBase<Rest, RestExample> {
	
	public List<Rest> findSearch(RestExample example, PaginationContext paginationContext, int page);

	public List<Rest> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(Rest aRest, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}