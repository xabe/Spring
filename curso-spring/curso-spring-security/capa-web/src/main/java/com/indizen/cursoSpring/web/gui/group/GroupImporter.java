package com.indizen.cursoSpring.web.gui.group;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.xml.sax.SAXException;

import com.csvreader.CsvReader;
import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.service.group.GroupService;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.gui.importer.Import;
import com.indizen.cursoSpring.web.gui.importer.ParseImporterXML;
import com.indizen.cursoSpring.web.util.BeanIntrospector;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;


public class GroupImporter extends Import<Group, GroupService>{
	private static final long serialVersionUID = 1L;	
	
	public GroupImporter(){
		webappDir=JSFUtils.getRealPath("Group");
    	importXML=JSFUtils.getRealPath("WEB-INF")+File.separator+"classes"+File.separator+"com/indizen/cursoSpring/web/gui"+File.separator+"group"+File.separator+"GroupImporter.xml";
		try
    	{
    		importer=new ParseImporterXML().parseXML(importXML,"Group");
    		propertyDescriptors= BeanIntrospector.getProperties(Group.class);
    		beanProxy = new BeanProxy(new Group());
    	}catch (IntrospectionException e) {
    		LOGGER.error("Error al crear el beanProxy Group: "+e.getMessage());
		}catch (SAXException e) {
    		LOGGER.error("Error al crear el parseador Group: "+e.getMessage());
		}catch (IOException e) {
    		LOGGER.error("Error al encontrar el xml para parsear Group: "+e.getMessage());
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
			beanProxy.setBean(new Group());
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
					service.add((Group) beanProxy.getBean());
				}
				else
				{
					//Si la importacion es incremental
					String[] keys= new String[0];
					if(haveIdfilling(importer.getKeys().split(","),(Group) beanProxy.getBean())){
						keys = importer.getKeys().split(",");
					}
					service.updateOrInsert((Group) beanProxy.getBean(),keys);
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