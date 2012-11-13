package com.indizen.cursoSpring.web.gui.rest;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.model.SelectItem;

import com.indizen.cursoSpring.servicio.model.rest.RestExample;
import com.indizen.cursoSpring.servicio.model.rest.RestExample.Criteria;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.gui.search.Search;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.JSFUtils;


public class RestSearch extends Search<RestExample> {
	private static final long serialVersionUID = 1L;
	private static final int MAX = 4;
	//Campos para la busqueda
	public static String FECHA = "andFechaLike";
	public static String ID = "andIdLike";
	public static String NOMBRE = "andNombreLike";
	public static String NUMERO = "andNumeroLike";

	//Campos Para El filtro
	private String fechaFilter;
	private String idFilter;
	private String nombreFilter;
	private String numeroFilter;

	public RestSearch(){
		mapFields = new HashMap<String, String>();
		try
    	{
			RestExample example = new RestExample();
    		beanProxy = new BeanProxy(example.createCriteria());
    	}catch (IntrospectionException e) {
    		LOGGER.error("Error al crear el beanIntrospector en search PrecmbdGlosarioBaseExample: "+e.getMessage());
    	}
	}
	
	
	@Override
	protected void createFiels() {
		fields = new SelectItem[MAX+1];
		fields[0] =  new SelectItem(Constants.ALL_CRITERIA, JSFUtils.getStringFromBundle("AllFields"),"AllFields");   
		  	fields[1] =  new SelectItem(FECHA,"fecha","fecha"); 
		  	fields[2] =  new SelectItem(ID,"id","id"); 
		  	fields[3] =  new SelectItem(NOMBRE,"nombre","nombre"); 
		  	fields[4] =  new SelectItem(NUMERO,"numero","numero"); 
		}
	
	@Override
 	public void cleanFilter() {
			fechaFilter = "";
			idFilter = "";
			nombreFilter = "";
			numeroFilter = "";
	 	}
	
	
	@Override
	public RestExample createExampleSearch() {
		RestExample  example = new RestExample();
		Criteria criteria = example.createCriteria();
		String textToFind =  getTextSearch();
		if(textToFind.length() > 0 && getCriteriaSelection().equalsIgnoreCase(Constants.ALL_CRITERIA))
		{
						
					example.or(example.createCriteria().andFechaLike(textToFind));
							
					example.or(example.createCriteria().andIdLike(textToFind));
							
					example.or(example.createCriteria().andNombreLike(textToFind));
							
					example.or(example.createCriteria().andNumeroLike(textToFind));
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
	private Criteria getLastCriteria(RestExample example){
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
 	public RestExample createExampleFilter() {
 		RestExample example = createExampleSearch();
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

	
	public String getFechaFilter() {
		return fechaFilter;
	}
	
	public void setFechaFilter(String fechaFilter) {
		this.fechaFilter = fechaFilter;
		this.mapFields.put(FECHA, fechaFilter);
	}
	
	
	public String getIdFilter() {
		return idFilter;
	}
	
	public void setIdFilter(String idFilter) {
		this.idFilter = idFilter;
		this.mapFields.put(ID, idFilter);
	}
	
	
	public String getNombreFilter() {
		return nombreFilter;
	}
	
	public void setNombreFilter(String nombreFilter) {
		this.nombreFilter = nombreFilter;
		this.mapFields.put(NOMBRE, nombreFilter);
	}
	
	
	public String getNumeroFilter() {
		return numeroFilter;
	}
	
	public void setNumeroFilter(String numeroFilter) {
		this.numeroFilter = numeroFilter;
		this.mapFields.put(NUMERO, numeroFilter);
	}
	
}
