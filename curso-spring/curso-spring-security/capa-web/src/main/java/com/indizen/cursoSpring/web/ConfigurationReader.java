package com.indizen.cursoSpring.web;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
   
public final class ConfigurationReader { 
	private static final Logger LOGGER = Logger.getLogger(ConfigurationReader.class);
	private static Properties properties;
	 
	private ConfigurationReader() {}
	 
	static {
			try 
			{
				properties = new Properties();
				properties.load(ConfigurationReader.class.getClassLoader().getResourceAsStream("showcase.properties"));
			} catch (IOException e) {
				LOGGER.error("Error al cargar el ficheros de propiedades "+e.getMessage());
			}

		}  
 
     public static String getProperty(String property){  
    	 return properties.getProperty(property);   
     }     
 }