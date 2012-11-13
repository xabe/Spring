package com.indizen.cursoSpring.web.gui.language;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.indizen.cursoSpring.servicio.model.userLogged.UserLogged;
import com.indizen.cursoSpring.servicio.service.security.SecurityService;

@ManagedBean(name="languageController")
@SessionScoped
public class LanguageController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger("CapaServicio");
	private Locale locale;
	private Date date;
	@ManagedProperty(value="#{securityService}")
	private SecurityService securityService;
	
	
	public UserLogged getUserLogged() {
		UserLogged logged = null;
		if(this.securityService != null)
		{
			logged = this.securityService.getUserlogged();
		}
		return logged;
	}	
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public LanguageController(){
		date = new Date();
		locale = new Locale("es");
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public String getLanguage() {
        return getLocaleUse().getLanguage();
    }
		
	public Locale getLocaleUse(){
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getViewRoot().getLocale();
	}
	 
	public Date getDate() {
		return (Date) date.clone();
	}
		
	/**
	 * Cambia el lenguaje al especificado como parametro
	 * @param language la aplicacion esta preparada para multiples
	 */
	public String changeLanguage(){
		FacesContext context = FacesContext.getCurrentInstance();
		String language = context.getExternalContext().getRequestParameterMap().get("language");
		setLocale(new Locale(language));
		context.getViewRoot().setLocale(getLocale());
		return null;
	}
	
	/**
	*Eliminar la sesi�n al cerrar el navegador
	*/
	public String logOut() {
		closeSession();
		return "logout";
	}
	
	private boolean closeSession() {
		try {
			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpSession session = (HttpSession) ctx.getSession(false);
			session.invalidate();
			LOGGER.debug("Info close session");
			return true;
		} catch (IllegalStateException e) {
			LOGGER.error("Error close session");
			return false;
		}
	}
	
}
