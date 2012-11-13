package com.indizen.cursoSpring.web.gui.user;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.model.SelectItem;

import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.model.user.UserExample.Criteria;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.gui.search.Search;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;


public class UserSearch extends Search<UserExample> {
	private static final long serialVersionUID = 1L;
	private static final int MAX = 13;
	//Campos para la busqueda
	public static String ATTEMPTSLOGIN = "andAttemptsLoginLike";
	public static String BLOCKED = "andBlockedLike";
	public static String DATELASTLOGIN = "andDateLastLoginLike";
	public static String DATELASTPASSWORD = "andDateLastPasswordLike";
	public static String EMAIL = "andEmailLike";
	public static String ENABLE = "andEnableLike";
	public static String ID = "andIdLike";
	public static String NAME = "andNameLike";
	public static String PASSWORD = "andPasswordLike";
	public static String SURNAME1 = "andSurname1Like";
	public static String SURNAME2 = "andSurname2Like";
	public static String TELEPHONE = "andTelephoneLike";
	public static String USERNAME = "andUsernameLike";

	//Campos Para El filtro
	private String attemptsLoginFilter;
	private String blockedFilter;
	private String dateLastLoginFilter;
	private String dateLastPasswordFilter;
	private String emailFilter;
	private String enableFilter;
	private String idFilter;
	private String nameFilter;
	private String passwordFilter;
	private String surname1Filter;
	private String surname2Filter;
	private String telephoneFilter;
	private String usernameFilter;

	public UserSearch(){
		mapFields = new HashMap<String, String>();
		try
    	{
			UserExample example = new UserExample();
    		beanProxy = new BeanProxy(example.createCriteria());
    	}catch (IntrospectionException e) {
    		LOGGER.error("Error al crear el beanIntrospector en search PrecmbdGlosarioBaseExample: "+e.getMessage());
    	}
	}
	
	
	@Override
	protected void createFiels() {
		fields = new SelectItem[MAX+1];
		fields[0] =  new SelectItem(Constants.ALL_CRITERIA, JSFUtils.getStringFromBundle("AllFields"),"AllFields");   
		  	fields[1] =  new SelectItem(ATTEMPTSLOGIN,"attemptsLogin","attemptsLogin"); 
		  	fields[2] =  new SelectItem(BLOCKED,"blocked","blocked"); 
		  	fields[3] =  new SelectItem(DATELASTLOGIN,"dateLastLogin","dateLastLogin"); 
		  	fields[4] =  new SelectItem(DATELASTPASSWORD,"dateLastPassword","dateLastPassword"); 
		  	fields[5] =  new SelectItem(EMAIL,"email","email"); 
		  	fields[6] =  new SelectItem(ENABLE,"enable","enable"); 
		  	fields[7] =  new SelectItem(ID,"id","id"); 
		  	fields[8] =  new SelectItem(NAME,"name","name"); 
		  	fields[9] =  new SelectItem(PASSWORD,"password","password"); 
		  	fields[10] =  new SelectItem(SURNAME1,"surname1","surname1"); 
		  	fields[11] =  new SelectItem(SURNAME2,"surname2","surname2"); 
		  	fields[12] =  new SelectItem(TELEPHONE,"telephone","telephone"); 
		  	fields[13] =  new SelectItem(USERNAME,"username","username"); 
		}
	
	@Override
 	public void cleanFilter() {
			attemptsLoginFilter = "";
			blockedFilter = "";
			dateLastLoginFilter = "";
			dateLastPasswordFilter = "";
			emailFilter = "";
			enableFilter = "";
			idFilter = "";
			nameFilter = "";
			passwordFilter = "";
			surname1Filter = "";
			surname2Filter = "";
			telephoneFilter = "";
			usernameFilter = "";
	 	}
	
	
	@Override
	public UserExample createExampleSearch() {
		UserExample  example = new UserExample();
		Criteria criteria = example.createCriteria();
		String textToFind =  getTextSearch();
		if(textToFind.length() > 0 && getCriteriaSelection().equalsIgnoreCase(Constants.ALL_CRITERIA))
		{
						
					example.or(example.createCriteria().andAttemptsLoginLike(textToFind));
							
					example.or(example.createCriteria().andBlockedLike(textToFind));
							
					example.or(example.createCriteria().andDateLastLoginLike(textToFind));
							
					example.or(example.createCriteria().andDateLastPasswordLike(textToFind));
							
					example.or(example.createCriteria().andEmailLike(textToFind));
							
					example.or(example.createCriteria().andEnableLike(textToFind));
							
					example.or(example.createCriteria().andIdLike(textToFind));
							
					example.or(example.createCriteria().andNameLike(textToFind));
							
					example.or(example.createCriteria().andPasswordLike(textToFind));
							
					example.or(example.createCriteria().andSurname1Like(textToFind));
							
					example.or(example.createCriteria().andSurname2Like(textToFind));
							
					example.or(example.createCriteria().andTelephoneLike(textToFind));
							
					example.or(example.createCriteria().andUsernameLike(textToFind));
					}
		else if(textToFind.length() > 0)
		{
			try
			{
				beanProxy.setBean(criteria);
				beanProxy.invoke(getCriteriaSelection(), new Class<?>[] {String.class}, new Object[]{textToFind});
			}catch (IllegalAccessException e) {
				LOGGER.error("Error al search : "+e.getMessage());
			}catch (Exception e) {
				LOGGER.error("Error al search : "+e.getMessage());
			}
		}	
		return example;	
	}
	
	@SuppressWarnings(value="unchecked")
	private Criteria getLastCriteria(UserExample example){
		List<Criteria> criterias = example.getOredCriteria();
		if(criterias == null || criterias.size() == 0)
		{
			return example.createCriteria();
		}
		else
		{
			return criterias.get(criterias.size() - 1);
		}
	}
	
	@Override
 	public UserExample createExampleFilter() {
 		UserExample example = createExampleSearch();
 		try
		{
			String value = "";
			Criteria criteria = getLastCriteria(example);
			beanProxy.setBean(criteria);		
			for(Entry<String, String> entry: mapFields.entrySet()){
				value = entry.getValue().trim();
				if(value != null && value.length() > 0){
					if(entry.getValue().contains("*")){
						value = value.replace('*', '%');
					}
					beanProxy.invoke(entry.getKey(), new Class<?>[] {String.class}, new Object[]{value});
				}
			}
		}catch (Exception e) {
			LOGGER.error("Error al filtrar : "+e.getMessage());
		}	
		return example;	 		
 	}	
	
	//Metodos get y set del filtro

	
	public String getAttemptsLoginFilter() {
		return attemptsLoginFilter;
	}
	
	public void setAttemptsLoginFilter(String attemptsLoginFilter) {
		this.attemptsLoginFilter = attemptsLoginFilter;
		this.mapFields.put(ATTEMPTSLOGIN, attemptsLoginFilter);
	}
	
	
	public String getBlockedFilter() {
		return blockedFilter;
	}
	
	public void setBlockedFilter(String blockedFilter) {
		this.blockedFilter = blockedFilter;
		this.mapFields.put(BLOCKED, blockedFilter);
	}
	
	
	public String getDateLastLoginFilter() {
		return dateLastLoginFilter;
	}
	
	public void setDateLastLoginFilter(String dateLastLoginFilter) {
		this.dateLastLoginFilter = dateLastLoginFilter;
		this.mapFields.put(DATELASTLOGIN, dateLastLoginFilter);
	}
	
	
	public String getDateLastPasswordFilter() {
		return dateLastPasswordFilter;
	}
	
	public void setDateLastPasswordFilter(String dateLastPasswordFilter) {
		this.dateLastPasswordFilter = dateLastPasswordFilter;
		this.mapFields.put(DATELASTPASSWORD, dateLastPasswordFilter);
	}
	
	
	public String getEmailFilter() {
		return emailFilter;
	}
	
	public void setEmailFilter(String emailFilter) {
		this.emailFilter = emailFilter;
		this.mapFields.put(EMAIL, emailFilter);
	}
	
	
	public String getEnableFilter() {
		return enableFilter;
	}
	
	public void setEnableFilter(String enableFilter) {
		this.enableFilter = enableFilter;
		this.mapFields.put(ENABLE, enableFilter);
	}
	
	
	public String getIdFilter() {
		return idFilter;
	}
	
	public void setIdFilter(String idFilter) {
		this.idFilter = idFilter;
		this.mapFields.put(ID, idFilter);
	}
	
	
	public String getNameFilter() {
		return nameFilter;
	}
	
	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
		this.mapFields.put(NAME, nameFilter);
	}
	
	
	public String getPasswordFilter() {
		return passwordFilter;
	}
	
	public void setPasswordFilter(String passwordFilter) {
		this.passwordFilter = passwordFilter;
		this.mapFields.put(PASSWORD, passwordFilter);
	}
	
	
	public String getSurname1Filter() {
		return surname1Filter;
	}
	
	public void setSurname1Filter(String surname1Filter) {
		this.surname1Filter = surname1Filter;
		this.mapFields.put(SURNAME1, surname1Filter);
	}
	
	
	public String getSurname2Filter() {
		return surname2Filter;
	}
	
	public void setSurname2Filter(String surname2Filter) {
		this.surname2Filter = surname2Filter;
		this.mapFields.put(SURNAME2, surname2Filter);
	}
	
	
	public String getTelephoneFilter() {
		return telephoneFilter;
	}
	
	public void setTelephoneFilter(String telephoneFilter) {
		this.telephoneFilter = telephoneFilter;
		this.mapFields.put(TELEPHONE, telephoneFilter);
	}
	
	
	public String getUsernameFilter() {
		return usernameFilter;
	}
	
	public void setUsernameFilter(String usernameFilter) {
		this.usernameFilter = usernameFilter;
		this.mapFields.put(USERNAME, usernameFilter);
	}
	
}
