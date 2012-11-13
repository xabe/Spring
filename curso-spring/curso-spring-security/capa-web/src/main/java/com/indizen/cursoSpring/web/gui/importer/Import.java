package com.indizen.cursoSpring.web.gui.importer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

import com.csvreader.CsvReader;
import com.indizen.cursoSpring.servicio.model.EntityBase;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.FileUtil;
import com.indizen.cursoSpring.web.util.JSFUtils;

public abstract class Import  <T extends EntityBase, S extends ServiceBase<T, ?>> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected static final Logger LOGGER = Logger.getLogger(Import.class);	
	protected String fileName;
	protected String fileRealPath;
	protected StringBuffer importLog;
	protected String importXML;
	protected String webappDir;
	protected S service;
	protected BeanProxy beanProxy;
	protected Importer importer;
	protected boolean ignoreErrors;
	protected Map<String,PropertyDescriptor> propertyDescriptors;
	protected Map<String,Field> mapXmlFields;
	
	public void init() {
		fileRealPath = webappDir+ File.separator+Constants.UPLOADS+File.separator;
		File export=new File(fileRealPath);
		LOGGER.debug("Carpeta de subida : "+fileRealPath);
		ignoreErrors = getIgnoreErrors(importer.getIgnoreErrors());
		if (!export.exists())
		{
			boolean result = export.mkdir();
			if(!result)
			{
				LOGGER.error("Error al crear carpeta de importacion.");
			}
		}
		List<Field> xmlFields = importer.getFields();
		this.mapXmlFields = new HashMap<String, Field>();
		for(Field field : xmlFields){
			this.mapXmlFields.put(field.getId(), field);
		}
	}
	
	public void downloadImporterLog() throws IOException,IllegalAccessException{
		Constants.downloadImporterLog(importLog == null ? new StringBuffer() : importLog);
	}	
	
	public String importFile(UploadedFile file) {
		String infoProcess="";
		
		fileName=file.getFileName();
		
		LOGGER.debug("File type: " + file.getContentType());
		LOGGER.debug("File name: " + fileName);
		
		if(!fileName.endsWith(".csv"))
		{
			infoProcess=JSFUtils.getStringFromBundle("ErrorLoadFormat");
			return infoProcess;
		}
		
		try 
		{
			File inputFile = null;
			String uploadedFileName = FileUtil.trimFilePath(file.getFileName());
			inputFile = createFile(uploadedFileName, file);		
			File uniqueFile = FileUtil.uniqueFile(new File(fileRealPath),uploadedFileName);
			createFile(uniqueFile);
			FileInputStream inputStream = new FileInputStream(inputFile);
			FileUtil.write(uniqueFile,inputStream);
			fileName = uniqueFile.getName();
			LOGGER.debug("File real path: " + fileRealPath);
			infoProcess=loadFile(this.fileRealPath + fileName);			
		    if(infoProcess.equals(JSFUtils.getStringFromBundle("Success")))
		    {
		    	infoProcess=JSFUtils.getStringFromBundle("LoadFile");
		    }
		}
		catch (FileNotFoundException e) {
			infoProcess=JSFUtils.getStringFromBundle("FileNotFound");
			LOGGER.error(e.getMessage());
		}
		catch (IOException e) {
			infoProcess=JSFUtils.getStringFromBundle("FileNotFound");
			LOGGER.error(e.getMessage());
		}
		catch (Exception e) {
			infoProcess=JSFUtils.getStringFromBundle("ErrorLoadBBDD");
			LOGGER.error(e.getMessage());
		}		
		return infoProcess;
	}
	
	public String loadFile(String fileName){
		String infoProcess=JSFUtils.getStringFromBundle("Success");
		CsvReader reader = null;
		Reader data = null;
		String[] headers;
		try
		{
			String encodingFile = CommonImportUtils.getEncodingFile(fileName);
			data = new InputStreamReader(new FileInputStream(fileName), encodingFile);
			
			reader = new CsvReader(data, Constants.SEPARATOR.charAt(0));

			reader.readHeaders();
			headers = reader.getHeaders();
			
			headers[0] = CommonImportUtils.removeUTF8BOM(headers[0]).replace(Constants.DELIMITER_CSV, Constants.EMPTY).trim();
			reader.setHeaders(headers);
			
			if (importer.getType().equalsIgnoreCase("total"))
			{
				service.deleteAllData();
			}
			importLog = new StringBuffer();
			readData(reader, headers, infoProcess);
			reader.close();
		}catch (Exception e) {
			infoProcess = "Error";
			LOGGER.error("Error al hacer la importacion "+e.getMessage());
		}
		finally{
			if(reader != null)
			{
				reader.close();
			}
			if(data != null)
			{
				try
				{
					data.close();
				}catch (Exception e) {}
			}
		}
		return infoProcess;
	}

	protected abstract void readData(CsvReader reader,String[] headers,String infoProcess) throws ParseException, IOException, IllegalAccessException, NoSuchMethodException, SAXException, IntrospectionException, NoSuchFieldException, InvocationTargetException;
	
	public String validateField(String value,String header){
		String infoProcess="";
		Field xmlField = mapXmlFields.get(header);
		if (xmlField!=null){
			//Si un campo ha de ser obligatorio y viene como null entonces mostramos un error
			if (value.equalsIgnoreCase("NULL") && xmlField.getCompulsory().equalsIgnoreCase("true")){
				String msg = JSFUtils.getStringFromBundle("FielNotNull");
				infoProcess=JSFUtils.getStringFromBundle("Error")+". "+MessageFormat.format(msg,header);
			}
			if((value.equalsIgnoreCase("NULL") || value.equalsIgnoreCase("")) && !xmlField.getCompulsory().equalsIgnoreCase("true")){
				return infoProcess;
			}		
			//Se comprueba que el campo cumpla con las reglas de validacion establecidas en el xml
			List<ValidationRule> validationRules=xmlField.getValidationRules();
			ValidationRule validationRule;
			if (validationRules.size()>0){
				for (int i=0;i<xmlField.getValidationRules().size();i++){
					validationRule = validationRules.get(i);
					if (!Pattern.matches(validationRule.getRegex(),value)){
						infoProcess=JSFUtils.getStringFromBundle("Error")+". "+JSFUtils.getStringFromBundle(validationRule.getErrormsg());
					}
				}
			}
		}
		return infoProcess;
	}	
	
	public Object getCell(String cell,String type) throws ParseException{
		Object result = null;
		if (cell == null || cell.equalsIgnoreCase("NULL") || cell.length() == 0){
			return null;
		}else if (type.contains("String")){
			result = cell;
		}else if (type.contains("Integer")){
			result = Integer.parseInt(cell);
		}else if (type.contains("Float")){
			result = Float.parseFloat(cell);
		}if (type.contains("Long")){
			result = Long.parseLong(cell);
		}else if (type.contains("Date")){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			result = df.parse(cell);
		}else if (type.contains("BigDecimal")){
			result = BigDecimal.valueOf(Float.valueOf(cell));
		}
		return result;
	}
	
	public PropertyDescriptor getPropertyElement(String columnId){
		String nameColumn = columnId.replace("_", "");
		return propertyDescriptors.get(nameColumn.toLowerCase());
	}	
	
	public boolean getIgnoreErrors(String ignore){
		return ignore.equalsIgnoreCase("true");
	}
	
	public boolean haveIdfilling(String[] keys,T entityBean) throws NoSuchFieldException,InvocationTargetException,IllegalAccessException,NoSuchMethodException{
		beanProxy.setBean(entityBean);
		PropertyDescriptor propertyDescriptor;
		for(int i=0; i < keys.length; i++){
			propertyDescriptor = propertyDescriptors.get(keys[i]);			
			if(propertyDescriptor != null)
			{
				Object object = beanProxy.get(propertyDescriptor.getName());
				if (object == null)
				{
					return false;
				}
			}
		}		
		return true;
	}
	
	public File createFile(String uploadedFileName, UploadedFile file) throws IOException{
		File inputFile = null;
		BufferedOutputStream bufferedOutputStream = null;
		try
		{
			inputFile = new File(new File(fileRealPath), uploadedFileName);
			boolean result = inputFile.createNewFile();
			if(result)
			{
				LOGGER.error("Fallo al crear el fichero : "+inputFile.getName());
				throw new IOException("Error al crear el fichero : "+inputFile.getName());
			}
			FileOutputStream fileOutputStream = new FileOutputStream(inputFile);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(file.getContents(),0,file.getContents().length);
			bufferedOutputStream.close();
			fileOutputStream.close();
		}catch (IOException e) {
			LOGGER.error("Fallo al crear el fichero : "+inputFile.getName());
			throw new IOException(e.getMessage());
		}
		finally{
			if(bufferedOutputStream != null)
			{
				bufferedOutputStream.close();
			}
		}
		return inputFile;
	}
	
	public void createFile(File file) throws IOException{
		if (!file.exists())
		{
			boolean result = file.createNewFile();
			if(!result)
			{
				LOGGER.error("Error al crear el fichero : "+file.getName());
				throw new IOException("Error al crear el fichero : "+file.getName());
			}
		}	
	}
	
	public String errorLog(int row, String header, String value,Throwable e) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(JSFUtils.getStringFromBundle("NumberRow"));
		buffer.append(" ");
		buffer.append(row);
		buffer.append(" ---> Campo : \"");
		buffer.append(header);
		buffer.append("\" con el valor : \"");
		buffer.append(value);
		buffer.append("\" motivo del error : ");
		buffer.append(e.getMessage());
		return buffer.toString();
	}
	
	public String errorLog(int row, String header, String value,String message) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(JSFUtils.getStringFromBundle("NumberRow"));
		buffer.append(" ");
		buffer.append(row);
		buffer.append(" ---> Campo : \"");
		buffer.append(header);
		buffer.append("\" con el valor : \"");
		buffer.append(value);
		buffer.append("\" motivo del error : ");
		buffer.append(message);
		return buffer.toString();
	}
	
	
	public  void setService(S service) {
		this.service = service;
	}	
	
	public String getFileRealPath() {
		return fileRealPath;
	}

	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}
}
