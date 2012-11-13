package com.indizen.cursoSpring.web.gui.user;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.xml.sax.SAXException;

import com.csvreader.CsvReader;
import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.service.user.UserService;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.gui.importer.Import;
import com.indizen.cursoSpring.web.gui.importer.ParseImporterXML;
import com.indizen.cursoSpring.web.util.BeanIntrospector;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;


public class UserImporter extends Import<User, UserService>{
	private static final long serialVersionUID = 1L;	
	
	public UserImporter(){
		webappDir=JSFUtils.getRealPath("User");
    	importXML=JSFUtils.getRealPath("WEB-INF")+File.separator+"classes"+File.separator+"com/indizen/cursoSpring/web/gui"+File.separator+"user"+File.separator+"UserImporter.xml";
		try
    	{
    		importer=new ParseImporterXML().parseXML(importXML,"User");
    		propertyDescriptors= BeanIntrospector.getProperties(User.class);
    		beanProxy = new BeanProxy(new User());
    	}catch (IntrospectionException e) {
    		LOGGER.error("Error al crear el beanProxy User: "+e.getMessage());
		}catch (SAXException e) {
    		LOGGER.error("Error al crear el parseador User: "+e.getMessage());
		}catch (IOException e) {
    		LOGGER.error("Error al encontrar el xml para parsear User: "+e.getMessage());
		}
		init();
	}
	
	protected void readData(CsvReader reader,String[] headers,String infoProcess) throws IOException, IllegalAccessException, NoSuchMethodException, SAXException, IntrospectionException, NoSuchFieldException, InvocationTargetException{
		String header="";
		PropertyDescriptor propertyDescriptor;
		
		String type = "";
		String value = "";
		String info = "";
		Object field = null;
		
		int i=0;
		int row=2;
		boolean rowError=false;
		boolean tableError=false;
		String error = "";
		while (reader.readRecord() && !tableError) {	
			i=0;
			rowError=false;
			beanProxy.setBean(new User());
			while ((i<headers.length)&& !rowError){
				header = headers[i];
				try 
				{
					propertyDescriptor=getPropertyElement(header);
					if(propertyDescriptor != null)
					{
						type = propertyDescriptor.getPropertyType().toString();
						value = reader.get(header);
						info = validateField(value,header);
						if(info.contains(JSFUtils.getStringFromBundle(Constants.ERROR)))
						{
							rowError=true;
							error = errorLog(row, header, value, info);
						}	
						else
						{
							field = getCell(value,type);
							beanProxy.set(propertyDescriptor.getName(), field);
						}
					}
				} catch (Exception e) {
					error = errorLog(row, header, value, e);
					rowError=true;
				}
				if (!rowError){
					i++;
				}
			}	
			
			//Comprobamos que no tiene error
			if(!rowError)
			{
				if (importer.getType().equalsIgnoreCase("total"))
				{
					service.add((User) beanProxy.getBean());
				}
				else
				{
					//Si la importacion es incremental
					String[] keys= new String[0];
					if(haveIdfilling(importer.getKeys().split(","),(User) beanProxy.getBean())){
						keys = importer.getKeys().split(",");
					}
					service.updateOrInsert((User) beanProxy.getBean(),keys);
				}
			}
			else if(!ignoreErrors){
				tableError=true;
			}
			else
			{
				importLog.append(error);
				importLog.append(Constants.CRLF);
			}
			row++;		
		}
	}
}