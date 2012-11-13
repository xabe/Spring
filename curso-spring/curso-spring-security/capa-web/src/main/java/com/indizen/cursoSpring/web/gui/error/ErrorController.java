package com.indizen.cursoSpring.web.gui.error;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.indizen.cursoSpring.web.util.Constants;

@ManagedBean(name="errorController")
@ViewScoped
public class ErrorController implements Serializable {
	private static final long serialVersionUID = 1L;
    private String fecha;
    private String accion;
    private String exception;
    private String mensaje;
    private String code;
    
    public String getFecha() {
    	return fecha;
	}
    
    public String getAccion(){
    	return accion;
    }
    
    public String getException(){
    	return exception;
    }
    
    public String getMensaje(){
    	return mensaje;
    }
    
    public String getCode(){
    	return code;
    }

    public ErrorController() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
        fecha = Constants.getDateString(new Date());
        
                accion = requestMap.get("javax.servlet.forward.request_uri")!=null?requestMap.get("javax.servlet.forward.request_uri").toString():"";
        exception = requestMap.get("javax.servlet.error.exception")!=null?requestMap.get("javax.servlet.error.exception").toString():"";
        mensaje = requestMap.get("javax.servlet.error.message")!=null?requestMap.get("javax.servlet.error.message").toString():"";
        code = requestMap.get("javax.servlet.error.status_code")!=null?requestMap.get("javax.servlet.error.status_code").toString():"";        
                
    }
	
}
