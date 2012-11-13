package com.indizen.cursoSpring.web.gui;



import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import com.indizen.cursoSpring.servicio.model.EntityBase;
import com.indizen.cursoSpring.servicio.model.ExampleBase;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

public abstract class BaseBean <T extends EntityBase, D extends ExampleBase, S extends ServiceBase<T, D>> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected static final Logger LOGGER = Logger.getLogger("capaServicio");
	protected T currentEntity;
	protected String codeSecureAction;
	protected String auxCodeSecureAction;
	protected DataModelLazy<T, D, S> model;
	protected int maxRowsTable = Constants.MAX_RESULTS_TABLE;
	protected PaginationContext paginationContext;
	protected int typeSearch = Constants.TYPE_NONE;
	protected UploadedFile file;  
	
	
	
	public BaseBean() {
		generateRandom();
	}
	
	/* Generater Random  CSFR */
	
	public String  generateRandom() {
		codeSecureAction = JSFUtils.getNewRandomString();
		auxCodeSecureAction = codeSecureAction;
		return null;
	}
	
	public boolean getUpdateRandom(){
		return true;
	}
	
	public void setCodeSecureAction(String codeSecureAction) {
		this.codeSecureAction = codeSecureAction;
	}
	
	public String getCodeSecureAction() {
		return codeSecureAction;
	}
	
	public DataModelLazy<T, D, S> getDataModel() {
		return model;
	}
	
	public T getCurrent() {
		return currentEntity;
	}

	public void setCurrent(T t) {
		this.currentEntity = t;
	}
	
	public String getParam(String key){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}
	
	public void putSession(String key,Object value){
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) ctx.getSession(false);
		session.setAttribute(key, value);
	}
	
	public Object getSession(String key){
		Object result;
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) ctx.getSession(false);
		result = session.getAttribute(key);
		session.removeAttribute(key);
		return result;
	}
	
	public int getMaxRowsTable() {
		return maxRowsTable;
	}
	
	public void setMaxRowsTable(int maxRowsTable) {
		this.maxRowsTable = maxRowsTable;
	}
	
	public UploadedFile getFile() {
		return file;
	}
	
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public void reload(){
		if(typeSearch != Constants.TYPE_NONE)
		{
			findSearch();
		}
	}
	
    public void executeCloseModalDelete(){  
    	RequestContext.getCurrentInstance().execute("deleteModal.hide();");  
    }  
	
	public abstract String findSearch();
}
