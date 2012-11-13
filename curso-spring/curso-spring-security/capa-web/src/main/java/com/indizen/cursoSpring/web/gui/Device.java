package com.indizen.cursoSpring.web.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

@ManagedBean(name = "device")
@ViewScoped
public class Device implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger("capaServicio");
	public static final String MOBILE = "mobile";
	public static final String WINDOWS = "windows";
	public static final String LINUX = "linux";
	public static final String IOS = "iphone";
	public static final String IOS_1 = "ipad";
	public static final String ANDROID = "android";
	public static final String WINDOWS_PHONE = "windows phone";
	public static final String BLACKBERRY = "blackBerry";
	public static final String MAC_OS = "macintosh";
	public static final String IE = "msie";
	public static final String FF = "firefox";
	public static final String SAFARI = "safari";
	public static final String CHROME = "chrome";
	
	public static final String ERROR_IMG = "error.png";
	public static final String MOBILE_IMG = "mobile.png";
	public static final String PC_IMG = "pc.png";
	public static final String WINDOWS_IMG = "windows.png";
	public static final String LINUX_IMG = "linux.png";
	public static final String MAC_OS_IMG = "mac.png";
	public static final String IE_IMG = "ie.png";
	public static final String FF_IMG = "ff.png";
	public static final String SAFARI_IMG = "safari.png";
	public static final String CHROME_IMG = "chrome.png";
	public static final String IOS_IMG = "ios.png";
	public static final String ANDROID_IMG = "android.png";
	public static final String WINDOWS_PHONE_IMG = "wphone.png";
	public static final String BLACKBERRY_IMG = "blackBerry.png";
	private String device;
	private String system;
	private String navegatior;
	
	private String getDevice(String userAgent){
		String result = PC_IMG;
		if(userAgent.indexOf(MOBILE) != -1)
		{
			return MOBILE_IMG;
		}
		return result;
	}
	
	private String getSystem(String userAgent){
		String result = ERROR_IMG;
		if(userAgent.indexOf(WINDOWS) != -1)
		{
			return WINDOWS_IMG;
		}
		else if(userAgent.indexOf(MAC_OS) != -1)
		{
			return MAC_OS_IMG;
		}
		else if(userAgent.indexOf(IOS) != -1)
		{
			return IOS_IMG;
		}
		else if(userAgent.indexOf(ANDROID) != -1)
		{
			return ANDROID_IMG;
		}
		else if(userAgent.indexOf(WINDOWS_PHONE) != -1)
		{
			return WINDOWS_PHONE_IMG;
		}
		else if(userAgent.indexOf(BLACKBERRY) != -1)
		{
			return BLACKBERRY_IMG;
		}
		else if (userAgent.indexOf(LINUX) != -1)
		{
			return LINUX_IMG;
		}
		return result;
	}
	
	private String getNavegatior(String userAgent){
		String result = ERROR_IMG;
		if(userAgent.indexOf(IE) != -1)
		{
			return IE_IMG;
		}
		else if (userAgent.indexOf(FF) != -1)
		{
			return FF_IMG;
		}
		else if(userAgent.indexOf(CHROME) != -1)
		{
			return CHROME_IMG;
		}
		else if(userAgent.indexOf(SAFARI) != -1)
		{
			return SAFARI_IMG;
		}
		return result;
	}
	
	public Device() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String userAgent = request.getHeader("user-agent").toLowerCase();
		setDevice(getDevice(userAgent));
		setSystem(getSystem(userAgent));
		setNavegatior(getNavegatior(userAgent));
		LOGGER.info(userAgent);
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getNavegatior() {
		return navegatior;
	}

	public void setNavegatior(String navegatior) {
		this.navegatior = navegatior;
	}
}
