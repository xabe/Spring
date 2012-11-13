package com.indizen.cursoSpring.web.gui.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;
import com.indizen.cursoSpring.web.gui.MessageManager;
import com.indizen.cursoSpring.web.security.Security;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

@ManagedBean(name="changePasswordController")
@ViewScoped
public class ChangePasswordController implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger("CapaServicio");
	private String newPassword;
	private String oldPassword;
	private String confirmationPassword;
	@ManagedProperty(value="#{securityService}") 
	private SecurityService securityService;
	@ManagedProperty(value="#{security}") 
	private Security security;
	private UserLogged userLogged;
	private static final String ERROR_LOG = "Error when trying to change password: By User ";
	private String codeSecureAction;
	private String auxCodeSecureAction;
	
	public ChangePasswordController() {
		generateRandom();
	}
	
	@PostConstruct
	public void init(){
		this.userLogged = securityService.getUserlogged();
	}

	public String changePassword(){
		if (!auxCodeSecureAction.equals(codeSecureAction))
		{
			generateRandom();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtils.getStringFromBundle("ErrorValidation"),JSFUtils.getStringFromBundle("ErrorValidation")));
		}
		generateRandom();
		if (newPassword.equals(confirmationPassword)) 
		{
			switch (security.changePassword(oldPassword, newPassword)) {
			case Constants.OPTION_ONE: LOGGER.debug(ERROR_LOG+userLogged.getUsername());
					 MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorOldPasswordDistint"));
				 	 break;
			case Constants.OPTION_TWO: LOGGER.debug(ERROR_LOG+userLogged.getUsername());
					 MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorLengthPassword")+" "+securityService.getMinLengthPassword());
		 	         break;
		 	 
			case Constants.OPTION_THREE: LOGGER.debug(ERROR_LOG+userLogged.getUsername());
					 MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorCharacterPassword"));
					 break;
			default: LOGGER.debug("Password successfully changed: By User "+userLogged.getUsername());
					 MessageManager.createInfoMessage(null,JSFUtils.getStringFromBundle("PasswordChangeSucces"));
					 break;
			}
		} 
		else 
		{
			LOGGER.debug("Error when trying to change password: By User "+userLogged.getUsername());
			MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorPasswordDistint"));
		}
		return null;
	}		

	/* Generater Random  CSFR */
	
	private void  generateRandom() {
		codeSecureAction = JSFUtils.getNewRandomString();
		auxCodeSecureAction = codeSecureAction;
	}
	
	public boolean getUpdateRandom(){
		return true;
	}
	
	/* Method GET and SET attributes */
	
	public void setSecurity(Security security) {
		this.security = security;
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getCodeSecureAction() {
		return codeSecureAction;
	}

	public void setCodeSecureAction(String codeSecureAction) {
		this.codeSecureAction = codeSecureAction;
	}
}
