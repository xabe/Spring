package com.indizen.cursoSpring.web.gui.login;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ManagedBean(name="loginController")
@ViewScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;
	private String user;
	private String password;


	public String login() throws Exception{
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/commons/j_spring_security_check");

		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();
		return "";
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

}
