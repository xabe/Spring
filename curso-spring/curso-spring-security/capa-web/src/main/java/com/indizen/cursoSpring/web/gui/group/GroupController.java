package com.indizen.cursoSpring.web.gui.group;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.validator.ValidatorException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.group.GroupExample;
import com.indizen.cursoSpring.servicio.model.permission.Permission;
import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermission;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroup;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.group.GroupService;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;
import com.indizen.cursoSpring.web.gui.BaseBean;
import com.indizen.cursoSpring.web.gui.BeanItem;
import com.indizen.cursoSpring.web.gui.MessageManager;
import com.indizen.cursoSpring.web.security.Security;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

@ManagedBean(name="groupController")
@ViewScoped
public class GroupController extends BaseBean<Group, GroupExample, GroupService> {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{groupService}") 
	protected GroupService service;
	@ManagedProperty(value="#{securityService}") 
	private SecurityService securityService;
	@ManagedProperty(value="#{security}") 
	private Security security;
	private GroupSearch search;
	private GroupImporter importer;
	private GroupExporter exporter;
	
	private DualListModel<BeanItem> dualListModelUser;
	private List<User> listUsers;
	private List<BeanItem> sourceListUser;
	private List<BeanItem> targetListUser;
	
	private DualListModel<BeanItem> dualListModelPermission;
	private List<Permission> listPermissions;
	private List<BeanItem> sourceListPermission;
	private List<BeanItem> targetListPermission;
	

	private UserLogged userLogged;

	public GroupController() {
		this.currentEntity = new Group();
		importer = new GroupImporter();
		exporter = new GroupExporter();
		search = new GroupSearch();
		search.clean();
		generateRandom();
		paginationContext = new PaginationContext();
		sourceListPermission = new ArrayList<BeanItem>();
		targetListPermission = new ArrayList<BeanItem>();
		dualListModelPermission = new DualListModel<BeanItem>(sourceListPermission, targetListPermission);
		
		sourceListUser = new ArrayList<BeanItem>();
		targetListUser = new ArrayList<BeanItem>();
		dualListModelUser = new DualListModel<BeanItem>(sourceListUser, targetListUser);
	}
	
	@PostConstruct
	public void init(){
		this.importer.setService(service);
		this.listPermissions = securityService.getPermissions();
		this.listUsers = securityService.getUsers();
		this.userLogged = securityService.getUserlogged();
	}

	/* Get All Group */

	public void getAllGroups() {
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTableGroup(service, new GroupExample(), paginationContext);
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
	
		
	/* Export Group */
	
	private List<Group> getData(){
		List<Group> results = null;
		int numeroRegistro = 0;
		paginationContext.setMaxResults(10000);
		List<Group> auxResults;
		results = new ArrayList<Group>();
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
			List<Group> results = new ArrayList<Group>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.csvExporter(results);
			LOGGER.info("Exportado todo el Group csv");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Group csv : "+e.getMessage());
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
			List<Group> results = new ArrayList<Group>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.pdfExporter(results);
			LOGGER.info("Exportado todo el Group pdf");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Group pdf: "+e.getMessage());
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
			List<Group> results = new ArrayList<Group>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xmlExporter(results);
			LOGGER.info("Exportado todo el Group xml");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Group xml : "+e.getMessage());
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
			List<Group> results = new ArrayList<Group>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xlsExporter(results);
			LOGGER.info("Exportado todo el Group xls");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el Group xls : "+e.getMessage());
		}
		finally{
			if(paginationContext != null)
			{
				paginationContext.setMaxResults(Constants.MAX_RESULTS_TABLE);
			}
		}
		return null;
	}
	

	/* Get List Group por criterios de busquedas */
	
	public String findSearch() {
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTableGroup(service, search.createExampleFilter(), paginationContext);
		return "";
	}

	/* New Group */

	public String createGroup() {
		this.currentEntity = new Group();
		generateRandom();
		return null;
	}

	public String addCurrentGroup() {
		try {
			if (!auxCodeSecureAction.equals(codeSecureAction)) {
				generateRandom();
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			LOGGER.debug("Create a new Group by :" + userLogged.getUsername()
					+ " :" + currentEntity.toString());
			service.add(currentEntity);
			MessageManager.createInfoMessage("newForm:message",
					JSFUtils.getStringFromBundle("CreateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error add group: " + ex.getMessage(), ex);
			MessageManager.createErrorMessage("newForm:message",
					ex.getLocalizedMessage());
		}
		reload();
		return "";
	}


	/* Edit Group */

	public String updateCurrentGroup() {
		try {
			if (!auxCodeSecureAction.equals(codeSecureAction)) {
				generateRandom();
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			LOGGER.debug("Update Group by :" + userLogged.getUsername()
					+ " group :" + currentEntity.toString());
			service.update(currentEntity);
			MessageManager.createInfoMessage("editForm:message",
					JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error update permission: " + ex.getMessage(), ex);
			MessageManager.createErrorMessage("editForm:message",
					ex.getLocalizedMessage());
		}
		reload();
		return "";
	}

	/* Delete Group */

	public String deleteCurrentGroup() {
		try {
			if (!auxCodeSecureAction.equals(codeSecureAction)) {
				generateRandom();
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			LOGGER.debug("Delete Group by :" + userLogged.getUsername()
					+ " group :" + currentEntity.toString());
			service.delete(currentEntity);
			MessageManager.createInfoMessage(null,
					JSFUtils.getStringFromBundle("DeleteOK"));
			executeCloseModalDelete();
		} catch (Exception ex) {
			LOGGER.error("Error delete permission: " + ex.getMessage(), ex);
			MessageManager.createErrorMessage("deleteForm:message",
					ex.getLocalizedMessage());
		}
		reload();
		return "";
	}

	
	/* Management User with Group */
	
	private List<BeanItem> user(List<VUserGroup> list){
		List<BeanItem> result = new ArrayList<BeanItem>();
		HashMap<java.lang.Integer,java.lang.Integer> map = new HashMap< java.lang.Integer,java.lang.Integer>();
		for(int i=0; i < list.size(); i++){
			if(!map.containsKey(list.get(i).getIdUser()))
			{
				result.add(new BeanItem(list.get(i).getNameUser(), list.get(i).getIdUser().toString()));
				map.put(list.get(i).getIdUser(), list.get(i).getIdUser());
			}
		}
		return result;
	}	
	
	public String editGroupUser(){
	
		VUserGroupExample groupViewExample = new VUserGroupExample();
		com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample.Criteria criteria = groupViewExample.createCriteria();
		List< java.lang.Integer> list = new ArrayList< java.lang.Integer>();
		list.add(currentEntity.getId());
		criteria.andIdGroupIn(list);
		List<VUserGroup> listUserIn = securityService.getUserGroupExample(groupViewExample);
		targetListUser = user(listUserIn);
		
		HashMap< java.lang.Integer,java.lang.Integer> map = new HashMap< java.lang.Integer,java.lang.Integer>();
		for(int i=0; i < targetListUser.size(); i++){
			map.put(new java.lang.Integer(targetListUser.get(i).getId()), new java.lang.Integer(targetListUser.get(i).getId()));
		}
		
		sourceListUser = new ArrayList<BeanItem>();
		for(int i=0; i < listUsers.size(); i++){
			if(!map.containsKey(listUsers.get(i).getId()))
			{
				sourceListUser.add(new BeanItem(listUsers.get(i).getName(), listUsers.get(i).getId().toString()));
			}
		}
		
		dualListModelUser = new DualListModel<BeanItem>(sourceListUser, targetListUser);
		generateRandom();
		return null;
	}
	
	private boolean dataCheckRequiredGroup(){
		boolean required = true;
		if(dualListModelUser.getTarget() == null || dualListModelUser.getTarget().size() == 0)
		{
			MessageManager.createErrorMessage("editFormGroup:message",JSFUtils.getStringFromBundle("ErrorNoSelectUser"));
			required = false;
		}
		return required;
	}
	
	public String updateCurrentUserGroupView() {
		try
		{
			if(!dataCheckRequiredGroup())
			{
				return "";
			}
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			security.addManyUserGroupEdit(currentEntity.getId().toString(), dualListModelUser.getTarget());
			MessageManager.createInfoMessage("editFormGroup:message",JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error update group with users: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("editFormGroup:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}
	
	/* Management Group with Permission */
	
	private List<BeanItem> permission(List<VGroupPermission> list){
		List<BeanItem> result = new ArrayList<BeanItem>();
		HashMap< java.lang.Integer,java.lang.Integer> map = new HashMap< java.lang.Integer,java.lang.Integer>();
		for(int i=0; i < list.size(); i++){
			if(!map.containsKey(list.get(i).getIdPermission()))
			{
				result.add(new BeanItem(list.get(i).getNamePermission(), list.get(i).getIdPermission().toString()));
				map.put(list.get(i).getIdPermission(), list.get(i).getIdPermission());
			}
		}
		return result;
	}

	
	/**
	 * Metodo para carga los permisos asociados a ese grupo
	 * @return
	 */
	public String editGroupPermission(){
		
		VGroupPermissionExample groupPermissionViewExample = new VGroupPermissionExample();
		com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample.Criteria criteria = groupPermissionViewExample.createCriteria();
		List< java.lang.Integer> list = new ArrayList< java.lang.Integer>();
		list.add(currentEntity.getId());
		criteria.andIdGroupIn(list);
		List<VGroupPermission> listUserIn = securityService.getGroupPermissionExample(groupPermissionViewExample);
		targetListPermission = permission(listUserIn);	
		
		HashMap< java.lang.Integer,java.lang.Integer> map = new HashMap< java.lang.Integer,java.lang.Integer>();
		for(int i=0; i < targetListPermission.size(); i++){
			map.put(new java.lang.Integer(targetListPermission.get(i).getId()), new java.lang.Integer(targetListPermission.get(i).getId()));
		}
		
		sourceListPermission = new ArrayList<BeanItem>();
		for(int i=0; i < listPermissions.size(); i++){
			if(!map.containsKey(listPermissions.get(i).getId()))
			{
				sourceListPermission.add(new BeanItem(listPermissions.get(i).getName(), listPermissions.get(i).getId().toString()));
			}
		}
		
		dualListModelPermission = new DualListModel<BeanItem>(sourceListPermission, targetListPermission);
		generateRandom();
		return null;
	}
	
	private boolean dataCheckRequiredPermission(){
		boolean required = true;
		if(dualListModelPermission.getTarget() == null || dualListModelPermission.getTarget().size() == 0)
		{
			MessageManager.createErrorMessage("editFormPermission:message",JSFUtils.getStringFromBundle("ErrorNoSelectGroup"));
			required = false;
		}
		return required;
	}
	
	public String updateCurrentGroupPermissionView() {
		try {
			if(!dataCheckRequiredPermission())
			{
				return "";
			}
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			security.addManyGroupPermissionEdit(currentEntity.getId().toString(), dualListModelPermission.getTarget());
			MessageManager.createInfoMessage("editFormPermission:message",JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error update Gruop with permissions: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("editFormPermission:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}

	/* Method of get and set Attributes */

	public GroupSearch getSearch() {
		return search;
	}
	
	public void setService(GroupService service) {
		this.service = service;
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public void setSecurity(Security security) {
		this.security = security;
	}
	
	public DualListModel<BeanItem> getDualListModelUser() {
		return dualListModelUser;
	}
	
	public void setDualListModelUser(DualListModel<BeanItem> dualListModelUser) {
		this.dualListModelUser = dualListModelUser;
	}
	
	public DualListModel<BeanItem> getDualListModelPermission() {
		return dualListModelPermission;
	}
	
	public void setDualListModelPermission(
			DualListModel<BeanItem> dualListModelPermission) {
		this.dualListModelPermission = dualListModelPermission;
	}

}