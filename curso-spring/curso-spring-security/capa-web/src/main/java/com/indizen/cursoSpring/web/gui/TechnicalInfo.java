package com.indizen.cursoSpring.web.gui;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import com.indizen.cursoSpring.web.ConfigurationReader;

@ManagedBean(name = "technicalInfo", eager = true)
@ApplicationScoped
public class TechnicalInfo implements Serializable {
	protected static final Logger LOGGER = Logger.getLogger("CapaServicio");
	private static final long serialVersionUID = 1L;

	private String primeFaces;
	private String primeFacesExt;
	private String jsfImpl;
	private String server;
	private String buildTime;

	@PostConstruct
	protected void initialize() {
		try 
		{
			String strAppProps = ConfigurationReader.getProperty("application.properties");
			int lastBrace = strAppProps.indexOf("}");
			strAppProps = strAppProps.substring(1, lastBrace);

			Map<String, String> appProperties = new HashMap<String, String>();
			String[] appProps = strAppProps.split("[\\s,]+");
			for (String appProp : appProps) {
				String[] keyValue = appProp.split("=");
				if (keyValue != null && keyValue.length > 1) {
					appProperties.put(keyValue[0], keyValue[1]);
				}
			}

			primeFaces = "PrimeFaces: "+ appProperties.get("primefaces.core.version");
			primeFacesExt = "PrimeFaces Extensions: "+ appProperties.get("primefaces.extensions.version");
			jsfImpl = "JSF-Impl.: " + appProperties.get("jsf.displayname")+ " " + appProperties.get("jsf.version");
			server = "Server: "+ appProperties.get("server");

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar calendar = Calendar.getInstance();
			String time = appProperties.get("timestamp") == null ? ""+new Date().getTime() : appProperties.get("timestamp");
			calendar.setTimeInMillis(Long.valueOf(time));
			buildTime = "Build time: " + formatter.format(calendar.getTime());

		} catch (MissingResourceException e) {
			LOGGER.warn("Recurso de 'showcase' no encontrado");
		}
	}

	public String getPrimeFaces() {
		return primeFaces;
	}

	public void setPrimeFaces(String primeFaces) {
		this.primeFaces = primeFaces;
	}

	public String getPrimeFacesExt() {
		return primeFacesExt;
	}

	public void setPrimeFacesExt(String primeFacesExt) {
		this.primeFacesExt = primeFacesExt;
	}

	public String getJsfImpl() {
		return jsfImpl;
	}

	public void setJsfImpl(String jsfImpl) {
		this.jsfImpl = jsfImpl;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	
	

}