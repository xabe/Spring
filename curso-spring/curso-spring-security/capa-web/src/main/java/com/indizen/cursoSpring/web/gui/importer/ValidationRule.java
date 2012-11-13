package com.indizen.cursoSpring.web.gui.importer;

public class ValidationRule {
	private String regex;
	private String errormsg;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
}