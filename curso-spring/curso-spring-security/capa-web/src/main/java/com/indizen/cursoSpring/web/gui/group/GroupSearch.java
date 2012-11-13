package com.indizen.cursoSpring.web.gui.group;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.model.SelectItem;

import com.indizen.cursoSpring.servicio.model.group.GroupExample;
import com.indizen.cursoSpring.servicio.model.group.GroupExample.Criteria;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.gui.search.Search;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;


public class GroupSearch extends Search<GroupExample> {
	private static final long serialVersionUID = 1L;
	private static final int MAX = 2;
	//Campos para la busqueda
	public static String ID = "andIdLike";
	public static String NAME = "andNameLike";

	//Campos Para El filtro
	private String idFilter;
	private String nameFilter;

	public GroupSearch(){
		mapFields = new HashMap<String, String>();
		try
    	{
			GroupExample example = new GroupExample();
    		beanProxy = new BeanProxy(example.createCriteria());
    	}catch (IntrospectionException e) {
    		LOGGER.error("Error al crear el beanIntrospector en search PrecmbdGlosarioBaseExample: "+e.getMessage());
    	}
	}
	
	
	@Override
	protected void createFiels() {
		fields = new SelectItem[MAX+1];
		fields[0] =  new SelectItem(Constants.ALL_CRITERIA, JSFUtils.getStringFromBundle("AllFields"),"AllFields");   
		  	fields[1] =  new SelectItem(ID,"id","id"); 
		  	fields[2] =  new SelectItem(NAME,"name","name"); 
		}
	
	@Override
 	public void cleanFilter() {
			idFilter = "";
			nameFilter = "";
	 	}
	
	
	@Override
	public GroupExample createExampleSearch() {
		GroupExample  example = new GroupExample();
		Criteria criteria = example.createCriteria();
		String textToFind =  getTextSearch();
		if(textToFind.length() > 0 && getCriteriaSelection().equalsIgnoreCase(Constants.ALL_CRITERIA))
		{
						
					example.or(example.createCriteria().andIdLike(textToFind));
							
					example.or(example.createCriteria().andNameLike(textToFind));
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
	private Criteria getLastCriteria(GroupExample example){
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
 	public GroupExample createExampleFilter() {
 		GroupExample example = createExampleSearch();
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
	
}
