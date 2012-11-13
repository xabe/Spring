package com.indizen.cursoSpring.web.gui.permission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.validator.ValidatorException;

import org.primefaces.event.FileUploadEvent;

import com.indizen.cursoSpring.servicio.model.permission.Permission;
import com.indizen.cursoSpring.servicio.model.permission.PermissionExample;
import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.permission.PermissionService;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;
import com.indizen.cursoSpring.web.gui.BaseBean;
import com.indizen.cursoSpring.web.gui.MessageManager;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

@ManagedBean(name="permissionController")
@ViewScoped
public class PermissionController extends BaseBean<Permission, PermissionExample, PermissionService>{
	private static final long serialVersionUID = 1L;
	private PermissionSearch search;
	private PermissionImporter importer;
	private PermissionExporter exporter;
	@ManagedProperty(value="#{permissionService}") 
	protected PermissionService service;
	@ManagedProperty(value="#{securityService}") 
	private SecurityService securityService;
	
	private UserLogged userLogged;
	
	public PermissionController() {
		this.currentEntity = new Permission();
		importer = new PermissionImporter();
		exporter = new PermissionExporter();
		search = new PermissionSearch();
		search.clean();
		generateRandom();
		paginationContext = new PaginationContext();
	}
	
	@PostConstruct
	public void init(){
		importer.setService(service);
	}
	
	/* Get All Permission */
	
	public void getAllPermissions () {
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTablePermission(service, new PermissionExample(), paginationContext);
	}
	
	/* Method Import Permission */
	
	public void handleFileUpload(FileUploadEvent event) {  

		if (!auxCodeSecureAction.equals(codeSecureAction))
		{
			generateRandom();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
		}
		
		String importProcessResult= importer.importFile(event.getFile());

		if (importProcessResult.contains("Error"))
		{
			MessageManager.createErrorMessage("importForm:message",JSFUtils.getStringFromBundle("ImportKO"));
		}
		else
		{
			MessageManager.createInfoMessage("importForm:message",JSFUtils.getStringFromBundle("ImportOK"));
		}  
	 }  
	 
	public String downloadImportLog(){
		try
		{
			importer.downloadImporterLog(); 
		}catch (IOException e) {
			LOGGER.error("Error al importar log de subida de fichero de Permission "+e.getMessage());
		}
		catch (IllegalAccessException e) {
			LOGGER.error("Error al importar log de subida de fichero de Permission "+e.getMessage());
		}
		return null;
	}
	
	
	/* Export Permission */
	
	private List<Permission> getData(){
		List<Permission> results = null;
		int numeroRegistro = 0;
		paginationContext.setMaxResults(10000);
		List<Permission> auxResults;
		results = new ArrayList<Permission>();
		results.addAll(service.getPaginated(Constants.FRIST, paginationContext));
		numeroRegistro = results.size();
		while( numeroRegistro < paginationContext.getTotalCount()){
			auxResults = service.getPaginated(Constants.NEXT, paginationContext);
			numeroRegistro += auxResults.size();
			results.addAll(auxResults);
		}
		return results;
	}
	
	public String exportCsv() {
		try
		{
			List<Permission> results = new ArrayList<Permission>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.csvExporter(results);
			LOGGER.info("Exportado todo el Permission csv");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Permission csv : "+e.getMessage());
		}
		finally{
			if(paginationContext != null)
			{
				paginationContext.setMaxResults(Constants.MAX_RESULTS_TABLE);
			}
		}
		return null;
	}
	
	public String exportPdf() {
		try
		{
			List<Permission> results = new ArrayList<Permission>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.pdfExporter(results);
			LOGGER.info("Exportado todo el Permission pdf");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Permission pdf: "+e.getMessage());
		}
		finally{
			if(paginationContext != null)
			{
				paginationContext.setMaxResults(Constants.MAX_RESULTS_TABLE);
			}
		}
		return null;
	}
	
	public String exportXml() {
		try
		{
			List<Permission> results = new ArrayList<Permission>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xmlExporter(results);
			LOGGER.info("Exportado todo el Permission xml");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Permission xml : "+e.getMessage());
		}
		finally{
			if(paginationContext != null)
			{
				paginationContext.setMaxResults(Constants.MAX_RESULTS_TABLE);
			}
		}
		return null;
	}
	
	public String exportXls() {
		try
		{
			List<Permission> results = new ArrayList<Permission>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xlsExporter(results);
			LOGGER.info("Exportado todo el Permission xls");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Permission xls : "+e.getMessage());
		}
		finally{
			if(paginationContext != null)
			{
				paginationContext.setMaxResults(Constants.MAX_RESULTS_TABLE);
			}
		}
		return null;
	}
	
	/* Get List Permission por criterios de busquedas */
	
	public String findSearch() {
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTablePermission(service, search.createExampleFilter(), paginationContext);
		return null;
	}	
		
	/* New Permission */
	
	public String createPermission() {
		this.currentEntity = new Permission();
		generateRandom();
		return null;
	}
	
	public String addCurrentPermission() {		
		try
		{
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			service.add(currentEntity);
			LOGGER.info("Creado un nuevo Permission por :"+userLogged.getUsername());
			MessageManager.createInfoMessage("newForm:message",JSFUtils.getStringFromBundle("CreateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error crear un nuevo Permission: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("newForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}
	
	/* Edit Permission */
	
	public String updateCurrentPermission() {		 
		 try
		{
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			service.update(currentEntity);
			LOGGER.info("Actulizado Permission by :"+userLogged.getUsername());
			MessageManager.createInfoMessage("editForm:message",JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error al actulizar Permission : "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("editForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}
	
	/* Delete Permission */
	
	public String deleteCurrentPermission() {
		try
		{
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			service.delete(currentEntity);
			LOGGER.info("Borrado Permission por :"+userLogged.getUsername()+" :"+currentEntity.toString());
			MessageManager.createInfoMessage(null,JSFUtils.getStringFromBundle("DeleteOK"));
			executeCloseModalDelete();
		} catch (Exception ex) {
			LOGGER.error("Error al borrar Permission: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("deleteForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}	
    
    /* Method of get and set Attributes */
	
	public  void setService(PermissionService service) {
		this.service = service;
	}

	public PermissionSearch getSearch() {
		return search;
	}	
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
		this.userLogged = this.securityService.getUserlogged();
	}

}