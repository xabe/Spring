package com.indizen.cursoSpring.web.gui;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public final class MessageManager {
	
	private MessageManager(){}

    public static void createInfoMessage(String id,String message) {
        createMessage(id, message, FacesMessage.SEVERITY_INFO);
    }
 
    public static void createWarnMessage(String id,String message) {
        createMessage(id, message, FacesMessage.SEVERITY_WARN);
    }

    public static void createErrorMessage(String id,String message) {
        createMessage(id, message, FacesMessage.SEVERITY_ERROR);
    }
 
    public static void createFatalMessage(String id,String message) {
        createMessage(id, message, FacesMessage.SEVERITY_FATAL);
    }

    public static void createMessage(String id, String message, FacesMessage.Severity severity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(id, new FacesMessage(severity, message, message));
    }
}
