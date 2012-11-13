package com.indizen.cursoSpring.web.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

@ManagedBean(name = "themeController")
@SessionScoped
public class ThemeController implements Serializable {
	protected static final Logger LOGGER = Logger.getLogger("CapaServicio");
	private static final long serialVersionUID = 1L;
	private String theme;
	private List<SelectItem> themeList;

	public ThemeController() {
		themeList = new ArrayList<SelectItem>();
		themeList.add(new SelectItem("aristo", "Aristo"));
		themeList.add(new SelectItem("blitzer", "Blitzer"));
		themeList.add(new SelectItem("bluesky", "Bluesky"));
		themeList.add(new SelectItem("casablanca", "Casablanca"));
		themeList.add(new SelectItem("cupertino", "Cupertino"));
		themeList.add(new SelectItem("redmond", "Redmond"));
		themeList.add(new SelectItem("ui-darkness", "Ui-darkness"));
		themeList.add(new SelectItem("vader", "Vader"));
		themeList.add(new SelectItem("humanity", "Humanity"));
		themeList.add(new SelectItem("black-tie", "Black-tie"));
		themeList.add(new SelectItem("ui-lightness", "Ui-lightness"));
		theme = "aristo";
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void saveTheme() {
		LOGGER.debug("Cambio de theme a : "+getTheme());
	}

	public List<SelectItem> getThemeList() {
		return themeList;
	}
}
