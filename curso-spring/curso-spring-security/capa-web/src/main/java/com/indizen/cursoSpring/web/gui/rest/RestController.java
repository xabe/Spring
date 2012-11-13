package com.indizen.cursoSpring.web.gui.rest;

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

import com.indizen.cursoSpring.servicio.model.rest.Rest;
import com.indizen.cursoSpring.servicio.model.rest.RestExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.rest.RestService;
import com.indizen.cursoSpring.web.gui.BaseBean;
import com.indizen.cursoSpring.web.gui.MessageManager;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

@ManagedBean(name="restController")
@ViewScoped
public class RestController extends BaseBean<Rest, RestExample, RestService>{
	private static final long serialVersionUID = 1L;
	private RestSearch search;
	private RestImporter importer;
	private RestExporter exporter;
	@ManagedProperty(value="#{restService}") 
	protected RestService service;
	
	
	public RestController() {
		this.currentEntity = new Rest();
		importer = new RestImporter();
		exporter = new RestExporter();
		search = new RestSearch();
		search.clean();
		generateRandom();
		paginationContext = new PaginationContext();
	}
	
	@PostConstruct
	public void init(){
		importer.setService(service);
	}
	
	/* Get All Rest */
	
	public void getAllRests () { 
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTableRest(service, new RestExample(), paginationContext);
	}
	
	
	/* Method Import Rest */	
	
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
			LOGGER.error("Error al importar log de subida de fichero de rest "+e.getMessage());
		}
		catch (IllegalAccessException e) {
			LOGGER.error("Error al importar log de subida de fichero de rest "+e.getMessage());
		}
		return null;
	}
	
		
	/* Export Rest */
	
	private List<Rest> getData(){
		List<Rest> results = null;
		int numeroRegistro = 0;
		paginationContext.setMaxResults(10000);
		List<Rest> auxResults;
		results = new ArrayList<Rest>();
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
			List<Rest> results = new ArrayList<Rest>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.csvExporter(results);
			LOGGER.info("Exportado todo el Rest csv");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Rest csv : "+e.getMessage());
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
			List<Rest> results = new ArrayList<Rest>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.pdfExporter(results);
			LOGGER.info("Exportado todo el Rest pdf");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Rest pdf: "+e.getMessage());
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
			List<Rest> results = new ArrayList<Rest>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xmlExporter(results);
			LOGGER.info("Exportado todo el Rest xml");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Rest xml : "+e.getMessage());
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
			List<Rest> results = new ArrayList<Rest>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xlsExporter(results);
			LOGGER.info("Exportado todo el Rest xls");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Rest xls : "+e.getMessage());
		}
		finally{
			if(paginationContext != null)
			{
				paginationContext.setMaxResults(Constants.MAX_RESULTS_TABLE);
			}
		}
		return null;
	}
	
	
	/* Get List Rest por criterios de busquedas */
	
	@Override
	public String findSearch() {
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTableRest(service, search.createExampleFilter(), paginationContext);
		return null;
	}	
	
	/* New Rest */
	
	public String createRest() {
		this.currentEntity = new Rest();
		generateRandom();
		return null;
	}
	
	public String addCurrentRest() {		
		try
		{
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			service.add(currentEntity);
			LOGGER.info("Creado un nuevo Rest");
			MessageManager.createInfoMessage("newForm:message",JSFUtils.getStringFromBundle("CreateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error crear un nuevo Rest: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("newForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}
	
	/* Edit Rest */
	
	public String updateCurrentRest() {		 
		 try
		{
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			service.update(currentEntity);
			LOGGER.info("Actulizado Rest");
			MessageManager.createInfoMessage("editForm:message",JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error al actulizar Rest : "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("editForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}
	
	/* Delete Rest */
	
	public String deleteCurrentRest() {
		try
		{
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			service.delete(currentEntity);
			LOGGER.info("Borrado Rest");
			MessageManager.createInfoMessage(null,JSFUtils.getStringFromBundle("DeleteOK"));
			executeCloseModalDelete();
		} catch (Exception ex) {
			LOGGER.error("Error al borrar Rest: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("deleteForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}
	
    
    /* Method of get and set Attributes */
	public  void setService(RestService service) {
		this.service = service;
	}

	public RestSearch getSearch() {
		return search;
	}	
	

}