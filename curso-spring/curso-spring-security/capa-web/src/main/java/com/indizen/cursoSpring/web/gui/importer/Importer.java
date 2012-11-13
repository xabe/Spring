package com.indizen.cursoSpring.web.gui.importer;

import java.util.ArrayList;
import java.util.List;

public class Importer {
	private String type;
	private String ignoreErrors;
	private String keys;
	private List<Field> fields;
	
	public Importer() {
		fields = new ArrayList<Field>();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIgnoreErrors() {
		return ignoreErrors;
	}
	public void setIgnoreErrors(String ignoreErrors) {
		this.ignoreErrors = ignoreErrors;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(Field field) {
		this.fields.add(field);
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	} 
}
