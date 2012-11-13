package com.indizen.cursoSpring.web.gui;

import org.primefaces.model.LazyDataModel;

import com.indizen.cursoSpring.servicio.model.EntityBase;
import com.indizen.cursoSpring.servicio.model.ExampleBase;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;

public abstract class DataModelLazy <T extends EntityBase, D extends ExampleBase, S extends ServiceBase<T, D>> extends LazyDataModel<T>{

	private static final long serialVersionUID = 1L;
	private D example;
	private S service;
	private PaginationContext paginationContext;

	public DataModelLazy(S service, D example, PaginationContext paginationContext) {
		this.service = service;
		this.example = example;
		this.paginationContext = paginationContext;
	}
  
	protected int getCurrentPage(int skipResults,int maxResults) {
		return 1+(skipResults/maxResults);
	}
	
	public PaginationContext getPaginationContext() {
		return paginationContext;
	}
	
	public void setPaginationContext(PaginationContext paginationContext) {
		this.paginationContext = paginationContext;
	}
	
	public D getExample() {
		return example;
	}
	
	public void setExample(D example) {
		this.example = example;
	}
	
	public S getService() {
		return service;
	}
	
	public void setService(S service) {
		this.service = service;
	}
}
