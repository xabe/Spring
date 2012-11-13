package com.indizen.cursoSpring.web.util;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

public final class JSFUtils {
	private static final int MAX = 255;
	private static final int MAX_LENGTH = 8;
	
	private JSFUtils() {
	}
	
	public static String getStringFromBundle(String key) {
		ResourceBundle bundle = getBundle();
		return getStringSafely(bundle, key, key);
	}
	
	public static FacesMessage getMessageFromBundle(String key,
			FacesMessage.Severity severity) {
		ResourceBundle bundle = getBundle();
		String summary = getStringSafely(bundle, key, null);
		String detail = getStringSafely(bundle, key + "_detail", summary);
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(severity);
		return message;
	}
	
	private static ResourceBundle getBundle() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String bundle = ctx.getApplication().getMessageBundle();

		UIViewRoot uiRoot = ctx.getViewRoot();
		Locale locale = uiRoot.getLocale();
		ClassLoader ldr = Thread.currentThread().getContextClassLoader();
		return ResourceBundle.getBundle(bundle, locale, ldr);
	}
	
	private static String getStringSafely(ResourceBundle bundle, String key,
			String defaultValue) {
		String resource;
		try
		{
			resource = bundle.getString(key);
			if(resource.length() == 0 && defaultValue != null){
				resource = defaultValue;
			}
			else if(resource.length() == 0 && defaultValue == null)
			{
				resource = key;
			}
		}catch (Exception e) {
			resource = defaultValue;
		}
		return resource;
	}
	
	public static String getRealPath(String dir) {
		FacesContext context = FacesContext.getCurrentInstance();
		return ((ServletContext)context.getExternalContext().getContext()).getRealPath(dir);
	}
	
	public static String getProject(){
		return "com.prueba".replace('.', '/');
	}
	
	public static Object getBeanApplicationContext(Class<?> bean){
		return WebApplicationContextUtils.getWebApplicationContext((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getBean(bean);
	}
	
	public static Object getBeanApplicationContext(String bean){
		return WebApplicationContextUtils.getWebApplicationContext((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getBean(bean);
	}
	
	public static String getNewRandomString(){
		StringBuffer result = new StringBuffer();
		
	    long milis = new java.util.GregorianCalendar().getTimeInMillis();
	    Random randomInt = new Random(milis);
	    
	    int count=0;
	    while( count < MAX_LENGTH){
	        char character = (char)randomInt.nextInt(MAX);
	        if(
	        	(character>='0' && character<='9') || 
	        	(character>='a' && character<='z') || 
	        	(character>='A' && character<='Z')  ){
	            result.append(character);
	            count++;
	        }
	    }
	    return result.toString();
	}
}
