package com.indizen.cursoSpring.web.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class Constants {
	
	private Constants() {}
	public static final int MAX_RESULTS_TABLE = 10;
	private static final Integer TRUE = Integer.valueOf("1");
	public static final BigDecimal ACTIVO = BigDecimal.ONE;
	public static final String FICHEROS =  "ficheros";
	public static final String ERROR = "Error";
	public static final int BYTES = 1024;
	public static final int BYTES_FILES = 4096;
	public static final String UTF_8 = "UTF-8";
	public static final String UTF_8_BOM_BEGINING = "\uFEFF";
	public static final String DELIMITER_CSV = "\"";
	public static final String EMPTY = "";
	public static final String PATTERN_FILE_NAME_CSV = "([^\\\\]*)[\\.]csv";
	public static final String FIELD = "importer/fields/field";
	public static final String VALIDATION = "importer/fields/field/validation-rule";
	public static final String ERROR_VALIDATION = "ErrorValidation";
	public static final String ERROR_BLANK = "ErrorlBlank";
	public static final int OPTION_ZERO = 0;
	public static final int OPTION_ONE = -1;
	public static final int OPTION_TWO = -2;
	public static final int OPTION_THREE = -3;
	public static final String FORMAT_DATE = "dd/MM/yyyy";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(FORMAT_DATE);
	public static final String RETURN = "\n";
	public static final String CRLF = "\r\n";
	public static final double TIME = 1000.0;
	public static final String ALL_CRITERIA = "allCriteria";
	public static final String FRIST = "first";
	public static final String NEXT = "next";
	public static final String PREVIOUS = "previous";
	public static final String LAST = "last";
	
	public static final String CONTENT_TYPE_CSV = "application/csv";
	public static final String CONTENT_TYPE_PDF = "application/pdf";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
	
	public static final String SORT_FIELD = "sortField";
	
	public static final String SEPARATOR = ";";
	public static final String UPLOADS = "uploads";
	
	public static final int TYPE_NONE = -1;
	public static final int TYPE_ALL = 3;
	
	public static synchronized String getDateString(Date date){
		return FORMATTER.format(date);
	}
	
	public static boolean getBoolean(Number number){
		return number.intValue() == TRUE.intValue();
	}
	
	public static Integer getTrue(Integer id){
		return Integer.valueOf("1");
	}
	
	public static BigDecimal getTrue(BigDecimal id){
		return BigDecimal.ONE;
	}
	
	public static Integer getFalse(Integer id){
		return Integer.valueOf("0");
	}
	
	public static BigDecimal getFalse(BigDecimal id){
		return BigDecimal.ZERO;
	}
	
	public static Integer getCero(Integer id){
		return Integer.valueOf("0");
	}
	
	public static BigDecimal getCero(BigDecimal id){
		return BigDecimal.ZERO;
	}
	
	public static Integer getValor(Integer id,int valor){
		return Integer.valueOf(valor);
	}
	
	public static BigDecimal getValor(BigDecimal id, int valor){
		return new BigDecimal(valor);
	}
	
	public static void downloadImporterLog(StringBuffer importLog) throws IllegalAccessException, IOException{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		response.setContentType("text/plain");		
		response.setHeader("Expires", "0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
		response.setHeader("Content-Disposition", "attachment;filename=\"importer.txt\"");
			
		ServletOutputStream os = response.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, Constants.UTF_8);
		
		PrintWriter logFile = new PrintWriter(osw);	
		
		logFile.append(importLog);
			
		logFile.flush();
		logFile.close();
			

		context.responseComplete(); 
	}	
	
	public static FileInputStream readFile(String rutaFile) throws FileNotFoundException {
		return new FileInputStream(rutaFile);
	}
	
	public static HttpServletRequest getRequest(ServletRequest requests) {
		return (HttpServletRequest) requests;
	}
	
	public static HttpServletResponse getResponse(ServletResponse responses) {
		return (HttpServletResponse) responses;
	}
}