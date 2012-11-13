package com.indizen.cursoSpring.web.gui.importer;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private String id;
	private String compulsory;
	private List<ValidationRule> validationRules;
	
	public Field() {
		validationRules=new ArrayList<ValidationRule>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCompulsory() {
		return compulsory;
	}
	
	public void setCompulsory(String compulsory) {
		this.compulsory = compulsory;
	}
	
	public List<ValidationRule> getValidationRules() {
		return validationRules;
	}
	
	public void setValidationRules(ValidationRule validationRule) {
		this.validationRules.add(validationRule);
	}	
}
