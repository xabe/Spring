package com.indizen.cursoSpring.web.gui.user;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.dao.DuplicateKeyException;

import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroup;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.mail.MailService;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;
import com.indizen.cursoSpring.servicio.service.user.UserService;
import com.indizen.cursoSpring.web.gui.BaseBean;
import com.indizen.cursoSpring.web.gui.BeanItem;
import com.indizen.cursoSpring.web.gui.MessageManager;
import com.indizen.cursoSpring.web.security.Security;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

@ManagedBean(name="userController")
@ViewScoped
public class UserController extends BaseBean<User, UserExample, UserService> {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{userService}") 
	protected UserService service;
	@ManagedProperty(value="#{securityService}") 
	private SecurityService securityService;
	@ManagedProperty(value="#{security}") 
	private Security security;
	@ManagedProperty(value="#{mailService}") 
	private MailService mailService;
	private UserExporter exporter;
	private UserImporter importer;
	private UserSearch search;
	
	private DualListModel<BeanItem> dualListModelGroup;
	private List<Group> listGroups;
	private List<BeanItem> sourceListGroup;
	private List<BeanItem> targetListGroup;

	private UserLogged userLogged;
	private String password;

	public UserController() {
		this.currentEntity = new User();
		importer = new UserImporter();
		exporter = new UserExporter();
		search = new UserSearch();
		search.clean();
		generateRandom();
		paginationContext = new PaginationContext();
		
		sourceListGroup = new ArrayList<BeanItem>();
		targetListGroup = new ArrayList<BeanItem>();
		dualListModelGroup = new DualListModel<BeanItem>(sourceListGroup, targetListGroup);
	}
	
	@PostConstruct
	public void init(){
		listGroups = this.securityService.getGroups();
		this.userLogged = this.securityService.getUserlogged();
		importer.setService(service);
	}

	/* Get All User */

	public void getAllUsers() {
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTableUser(service, new UserExample(), paginationContext);
	}
	
	
	/* Method Import User */
	
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
			LOGGER.error("Error al importar log de subida de fichero de User "+e.getMessage());
		}
		catch (IllegalAccessException e) {
			LOGGER.error("Error al importar log de subida de fichero de User "+e.getMessage());
		}
		return null;
	}
	
	
	/* Export User */
	
	private List<User> getData(){
		List<User> results = null;
		int numeroRegistro = 0;
		paginationContext.setMaxResults(10000);
		List<User> auxResults;
		results = new ArrayList<User>();
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
			List<User> results = new ArrayList<User>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.csvExporter(results);
			LOGGER.info("Exportado todo el User csv");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el User csv : "+e.getMessage());
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
			List<User> results = new ArrayList<User>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.pdfExporter(results);
			LOGGER.info("Exportado todo el User pdf");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el User pdf: "+e.getMessage());
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
			List<User> results = new ArrayList<User>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xmlExporter(results);
			LOGGER.info("Exportado todo el User xml");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el User xml : "+e.getMessage());
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
			List<User> results = new ArrayList<User>();
			if(typeSearch != Constants.TYPE_NONE && paginationContext != null)
			{
				results = getData();
			}
			exporter.xlsExporter(results);
			LOGGER.info("Exportado todo el User xls");
		}catch (Exception e) {
			LOGGER.error("Error al exportar el User xls : "+e.getMessage());
		}
		finally{
			if(paginationContext != null)
			{
				paginationContext.setMaxResults(Constants.MAX_RESULTS_TABLE);
			}
		}
		return null;
	}
	
	
	/* Get List User por criterios de busquedas */
	
	public String findSearch() {
		typeSearch = Constants.TYPE_ALL;
		model = new LazyTableUser(service, search.createExampleFilter(), paginationContext);
		return null;
	}
	

	/* New User */

	public void createUser() {
		currentEntity = new User();
		currentEntity.setAttemptsLogin(Constants.getCero(currentEntity.getAttemptsLogin()));
		currentEntity.setBlocked(Constants.getFalse(currentEntity.getBlocked()));
		currentEntity.setEnable(Constants.getTrue(currentEntity.getId()));
		currentEntity.setDateLastPassword(new Timestamp(new Date().getTime()));
		generateRandom();
	}
	
	private boolean dataCheckRequired(){
		boolean required = true;
		if(currentEntity.getPassword() == null || currentEntity.getPassword().trim().length() == 0)
		{
			MessageManager.createErrorMessage("newForm:password",JSFUtils.getStringFromBundle("ErrorlBlank"));
			required = false;
		}				
		if(!securityService.checkPasswordLenght(currentEntity.getPassword()))
		{
			MessageManager.createErrorMessage("newForm:password",JSFUtils.getStringFromBundle("ErrorLengthPassword")+" "+securityService.getMinLengthPassword());
			required = false;
		}
		if(!securityService.checkPasswordCharacter(currentEntity.getPassword()))
		{
			MessageManager.createErrorMessage("newForm:password",JSFUtils.getStringFromBundle("ErrorCharacterPassword"));
			required = false;
		}
		if(currentEntity.getEmail() == null || currentEntity.getEmail().trim().length() == 0)
		{
			MessageManager.createErrorMessage("newForm:email",JSFUtils.getStringFromBundle("ErrorlBlank"));
			required = false;
		}
		if(!securityService.checkEmail(currentEntity.getEmail()))
		{
			MessageManager.createErrorMessage("newForm:email",JSFUtils.getStringFromBundle("ErrorlEmail"));
			required = false;
		}
		if(currentEntity.getTelephone() != null && securityService.checkTelephone(currentEntity.getTelephone().toString()))
		{
			MessageManager.createErrorMessage("newForm:telephone",JSFUtils.getStringFromBundle("ErrorTelephone"));
			required = false;
		}
		return required;
	}
	
	public String addCurrentUser() {
		try {
			if(!dataCheckRequired())
			{
				generateRandom();
				return null;
			}	
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			LOGGER.debug("Create a new user by :"+userLogged.getUsername()+" user :"+currentEntity.getName());
			String password = currentEntity.getPassword();
			currentEntity.setPassword(security.encodePassword(password));
			securityService.addUser(currentEntity);
			String text = JSFUtils.getStringFromBundle("textNewUser");
			mailService.send(currentEntity.getEmail(), JSFUtils.getStringFromBundle("SubjectNewUser"), MessageFormat.format(text,currentEntity.getUsername(),password));
			MessageManager.createInfoMessage("newForm:message",JSFUtils.getStringFromBundle("CreateOK"));
		} catch (DuplicateKeyException e) {
			LOGGER.error("Error duplicate user: "+e.getMessage(), e);
			MessageManager.createErrorMessage("newForm:message",JSFUtils
					.getStringFromBundle("ErrorDuplicateUsername"));
		} catch (Exception ex) {
			LOGGER.error("Error add user: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("newForm:message",ex.getLocalizedMessage());
		} finally {

		}
		reload();
		return null;
	}

	/* Edit User */

	private boolean dataCheckRequiredUpdate(){
		boolean required = true;
		if(currentEntity.getEmail() == null || currentEntity.getEmail().trim().length() == 0)
		{
			MessageManager.createErrorMessage("editForm:email",JSFUtils.getStringFromBundle("ErrorlBlank"));
			required = false;
		}
		if(!securityService.checkEmail(currentEntity.getEmail()))
		{
			MessageManager.createErrorMessage("editForm:email",JSFUtils.getStringFromBundle("ErrorlEmail"));
			required = false;
		}
		if(currentEntity.getTelephone() != null && securityService.checkTelephone(currentEntity.getTelephone().toString()))
		{
			MessageManager.createErrorMessage("editForm:telephone",JSFUtils.getStringFromBundle("ErrorTelephone"));
			required = false;
		}
		return required;
	}
	
	public String updateCurrentUser() {
		try
		{
			if(!dataCheckRequiredUpdate())
			{
				generateRandom();
				return null;
			}
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			LOGGER.debug("Update user by :"+userLogged.getUsername()+" user :"+currentEntity.getName());
			service.update(currentEntity);
			MessageManager.createInfoMessage("editForm:message",JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error update user: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("editForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}

	/* Delete User */

	public String deleteCurrentUser() {
		try
		{
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			LOGGER.debug("Delete user by :"+userLogged.getUsername()+" user :"+currentEntity.getName());
			securityService.deleteUser(currentEntity);
			mailService.send(currentEntity.getEmail(), JSFUtils.getStringFromBundle("SubjectDeleteAccount"),JSFUtils.getStringFromBundle("textDeleteAccount"));
			MessageManager.createInfoMessage(null,JSFUtils.getStringFromBundle("DeleteOK"));
			executeCloseModalDelete();
		} catch (Exception ex) {
			LOGGER.error("Error delete user: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("deleteForm:message",ex.getLocalizedMessage());
		}
		reload();
		return null;
	}


	/* Method generate password */

	public String generaterPassword() {
		try {
			if (!auxCodeSecureAction.equals(codeSecureAction)) {
				generateRandom();
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),
						JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			currentEntity.setPassword(security.encodePassword(password));
			service.update(currentEntity);
			String text = JSFUtils
					.getStringFromBundle("textGeneraterNewPassword");
			mailService.send(
					currentEntity.getEmail(),
					JSFUtils.getStringFromBundle("SubjectGeneraterNewPassword"),
					MessageFormat.format(text, password));
			MessageManager.createInfoMessage("generaterPasswordForm:message",
					JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error generate password: " + ex.getMessage(), ex);
			MessageManager.createErrorMessage("generaterPasswordForm:message",
					ex.getLocalizedMessage());
		}
		reload();
		return null;
	}
	
	/* Method User Group */
	
	private List<BeanItem> group(List<VUserGroup> list){
		List<BeanItem> result = new ArrayList<BeanItem>();
		HashMap< java.lang.Integer,  java.lang.Integer> map = new HashMap< java.lang.Integer,  java.lang.Integer>();
		for(int i=0; i < list.size(); i++){
			if(!map.containsKey(list.get(i).getIdGroup()))
			{
				result.add(new BeanItem(list.get(i).getNameGroup(), list.get(i).getIdGroup().toString()));
				map.put(list.get(i).getIdGroup(), list.get(i).getIdGroup());
			}
		}
		return result;
	}

	public String editGroupUser(){
		
		VUserGroupExample groupViewExample = new VUserGroupExample();
		com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample.Criteria criteria = groupViewExample.createCriteria();
		List< java.lang.Integer> list = new ArrayList< java.lang.Integer>();
		list.add(currentEntity.getId());
		criteria.andIdUserIn(list);
		List<VUserGroup> listUserIn = securityService.getUserGroupExample(groupViewExample);
		targetListGroup = group(listUserIn);
		
		
		HashMap< java.lang.Integer,  java.lang.Integer> map = new HashMap< java.lang.Integer,  java.lang.Integer>();
		for(int i=0; i < targetListGroup.size(); i++){
			map.put(new  java.lang.Integer(targetListGroup.get(i).getId()), new  java.lang.Integer(targetListGroup.get(i).getId()));
		}
		
		sourceListGroup = new ArrayList<BeanItem>();
		for(int i=0; i < listGroups.size(); i++){
			if(!map.containsKey(listGroups.get(i).getId()))
			{
				sourceListGroup.add(new BeanItem(listGroups.get(i).getName(), listGroups.get(i).getId().toString()));
			}
		}	
		
		dualListModelGroup = new DualListModel<BeanItem>(sourceListGroup, targetListGroup);
		generateRandom();
		return null;
	}
	
	private boolean dataCheckRequiredGroup(){
		boolean required = true;
		if(dualListModelGroup.getTarget() == null || dualListModelGroup.getTarget().size() == 0)
		{
			MessageManager.createErrorMessage("editFormGroup:message",JSFUtils.getStringFromBundle("ErrorNoSelectGroup"));
			required = false;
		}
		return required;
	}
	
	public String updateCurrentUserGroupView() {
		try
		{
			if(!dataCheckRequiredGroup())
			{
				return null;
			}
			if (!auxCodeSecureAction.equals(codeSecureAction))
			{
				generateRandom();
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION),JSFUtils.getStringFromBundle(Constants.ERROR_VALIDATION)));
			}
			generateRandom();
			security.addManyGroupUserEdit(currentEntity.getId().toString(), dualListModelGroup.getTarget());
			MessageManager.createInfoMessage("editFormGroup:message",JSFUtils.getStringFromBundle("UpdateOK"));
		} catch (Exception ex) {
			LOGGER.error("Error update user with groups: "+ex.getMessage(), ex);
			MessageManager.createErrorMessage("editFormGroup:message",ex.getLocalizedMessage());
		}
		return null;
	}

	/* Method of get and set Attributes */

	public void setService(UserService service) {
		this.service = service;
	}
	
	public void setSecurity(Security security) {
		this.security = security;
	}

	public UserSearch getSearch() {
		return search;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public DualListModel<BeanItem> getDualListModelGroup() {
		return dualListModelGroup;
	}
	
	public void setDualListModelGroup(DualListModel<BeanItem> dualListModelGroup) {
		this.dualListModelGroup = dualListModelGroup;
	}

}