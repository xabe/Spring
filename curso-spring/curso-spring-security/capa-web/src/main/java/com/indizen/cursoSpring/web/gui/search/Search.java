package com.indizen.cursoSpring.web.gui.search;

import java.io.Serializable;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.indizen.cursoSpring.servicio.model.ExampleBase;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;

public abstract class Search <T extends ExampleBase> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected static final Logger LOGGER = Logger.getLogger(Search.class);
	protected SelectItem[] fields;
	private String text;
	private String criteriaSelection;
	protected BeanProxy beanProxy;	
	protected Map<String, String> mapFields;
	
	
	public SelectItem[] getFields() {
		if(fields == null)
		{
			createFiels();
		}
		return translate();
	}
	
	protected abstract void createFiels();
	
	private SelectItem[] translate() {
		for (int i = 0; i < fields.length; i++) {
			fields[i].setLabel(JSFUtils.getStringFromBundle(fields[i]
					.getDescription()));
		}
		return (SelectItem[])this.fields.clone();
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getCriteriaSelection() {
		return criteriaSelection;
	}
	
	public void setCriteriaSelection(String criteriaSelection) {
		this.criteriaSelection = criteriaSelection;
	}
	
	public String getTextSearch(){
		String result = getText();
		if (result ==null || result.length() == 0){
			return "";
		}
		if(result.contains("*")){
			result = getText().replace('*', '%');
		}
		return result;
	}
	
	public void clean(){
		setText("");
		setCriteriaSelection(Constants.ALL_CRITERIA);
		cleanFilter();
	}	
	
	
	public abstract void cleanFilter();
	
	public abstract T createExampleSearch();
	
	public abstract T createExampleFilter();
	
}
