package com.indizen.cursoSpring.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ValidatorInput implements Validator {
	private static String EMPTY_STRING = "";
	
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		String strValue;
		if (value instanceof Number) 
		{
			strValue = value.toString().trim();
		} else {
			strValue = (String) value;
		}
		if (!EMPTY_STRING.equalsIgnoreCase(strValue)) {
			isValid(strValue);
		}
	}
	
	public boolean isValid(String t){

		String text = null;
		
		if (t != null) {
			text = t.trim();
		} else {
			return false;
		}
		if (text.indexOf(';') >= 0) {
			createError();
		} else if (text.indexOf('\'') >= 0) {
			createError();
		} else if (text.indexOf('"') >= 0) {
			createError();
		} else if (text.indexOf('|') >= 0) {
			createError();
		} else if (text.indexOf('<') >= 0) {
			createError();
		} else if (text.indexOf('>') >= 0) {
			createError();
		} else if (text.indexOf('=') >= 0) {
			createError();
		} else if (text.indexOf('(') >= 0) {
			createError();
		} else if (text.indexOf(')') >= 0) {
			createError();
		} else if (text.indexOf('*') >= 0) {
			createError();
		} else if (text.indexOf('&') >= 0) {
			createError();
		} else if (text.indexOf('%') >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf("SELECT") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf("UPDATE") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf("INSERT") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf("DELETE") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf("DROP") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf("CREATE") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf("SCRIPT") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf(" OR ") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf(" AND ") >= 0) {
			createError();
		} else if (text.toUpperCase().indexOf(" LIKE ") >= 0) {
			createError();
		} else if (text.toUpperCase().trim().length() == 0) {
			createError();
		} 
		return true;
	}

	private void createError(){
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		message.setSummary(JSFUtils.getStringFromBundle("ErrorTextInput"));
		message.setDetail(JSFUtils.getStringFromBundle("ErrorTextInput"));
		throw new ValidatorException(message);
	}
}
