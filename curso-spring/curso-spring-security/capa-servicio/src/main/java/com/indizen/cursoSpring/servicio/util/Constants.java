package com.indizen.cursoSpring.servicio.util;

import java.math.BigDecimal;


public final class Constants {
	
	private Constants() {}
	public static final int MAX_RESULTS_TABLE = 10;
	public static final BigDecimal ACTIVO = BigDecimal.ONE;
	public static final String FICHEROS =  "ficheros";
	public static final String ERROR = "Error";
	public static final int BYTES = 1024;
	public static final int BYTES_FILES = 4096;
	public static final String UTF_8 = "UTF-8";
	public static final String UTF_8_BOM_BEGINING = "\uFEFF";
	public static final String DELIMITER_CSV = "\"";
	public static final String EMPTY = "";
	public static final String RETURN = "\n";
	public static final String CRLF = "\r\n";
	public static final double TIME = 1000.0;
	public static final String ALL_CRITERIA = "allCriteria";
	public static final String FRIST = "first";
	public static final String NEXT = "next";
	public static final String PREVIOUS = "previous";
	public static final String LAST = "last";	
	public static final String SORT_FIELD = "sortField";	
	public static final String SEPARATOR = ";";	
	public static final  String ANONYMOUSLY = "anonymously";
	private static final Integer TRUE = Integer.valueOf("1");
	
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
}