package com.indizen.cursoSpring.web.gui.login;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.model.user.UserExample.Criteria;
import com.indizen.cursoSpring.servicio.service.mail.MailService;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;
import com.indizen.cursoSpring.web.gui.MessageManager;
import com.indizen.cursoSpring.web.security.Security;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

@ManagedBean(name="rememberPasswordController")
@ViewScoped
public class RememberPasswordController implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger("CapaServicio");
	private String email;
	@ManagedProperty(value="#{securityService}") 
	private SecurityService securityService;
	@ManagedProperty(value="#{mailService}") 
	private MailService mailService;
	@ManagedProperty(value="#{security}") 
	private Security security;

	public String rememberPassword(){
		if(email == null || email.trim().length() == 0)
		{
			MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorlBlank"));
			return null;
		}
		if(!securityService.checkEmail(email))
		{
			MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorlEmail"));
			return null;
		}
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> users = securityService.getSelectExample(userExample);
		if(users.size() > 0)
		{
			User user = users.get(0);
			String newPassword = securityService.generatePassword();
			String newEncode = security.encodePassword(newPassword);
			try
			{
				user.setPassword(newEncode);
				user.setAttemptsLogin(Constants.getCero(user.getAttemptsLogin()));
				user.setBlocked(Constants.getFalse(user.getBlocked()));
				user.setEnable(Constants.getTrue(user.getId()));
				securityService.updateUser(user);
				String text = JSFUtils.getStringFromBundle("textGeneraterNewPassword");
				mailService.send(user.getEmail(), JSFUtils.getStringFromBundle("SubjectGeneraterNewPassword"), MessageFormat.format(text,newPassword));
				MessageManager.createInfoMessage(null,JSFUtils.getStringFromBundle("UserUpdateEmailSucces"));
			}
			catch (Exception e) {
				LOGGER.error("Error retrieving user: "+email, e);
				MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorMail"));
				return null;
			}
			return "index";
		}
		else
		{
			MessageManager.createErrorMessage(null,JSFUtils.getStringFromBundle("ErrorlEmailNoExit"));
		}
		return null;
	}
	
	/* Method GET and SET attributes */

	public void setSecurity(Security security) {
		this.security = security;
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
