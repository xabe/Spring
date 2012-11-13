package com.indizen.cursoSpring.servicio.service.rest;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.rest.Rest;
import com.indizen.cursoSpring.servicio.model.rest.RestExample;
import com.indizen.cursoSpring.servicio.model.rest.RestExample.Criteria;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.persistence.rest.RestDAO;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.servicio.util.Constants;
/**
 * Servicio que gestiona todas las operaciones CRUD 
 * sobre objetos Rest.
 * Esta clase a parte de gestionar dichas operaciones CRUD se puede extender
 * para realizar otro tipo de procesamientos sobre los objetos Rest.
 *
 */
 
 					 
 
@Service("restService")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
@SuppressWarnings("unchecked")
public class RestServiceImpl implements RestService {
	@Autowired
	private RestDAO restDAO;
	private RestExample example;
	private BeanProxy proxyEntity;
	private BeanProxy proxyCriteria;
	
	public RestServiceImpl() {
		try
		{
			proxyCriteria = new BeanProxy(Criteria.class);
			proxyEntity = new BeanProxy(Rest.class);
		}catch (Exception e) {
			LOGGER.error("Error los proxy de los servicios Rest : "+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void add(Rest aRest) {
		restDAO.insert(aRest);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(Rest aRest) {
					restDAO.updateByPrimaryKey(aRest);
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(Rest aRest, RestExample aRestExample) {
					restDAO.updateByExample(aRest, aRestExample);
			}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Rest aRest) {
					restDAO.deleteByPrimaryKey(aRest.getId());
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(RestExample aRestExample) {
					restDAO.deleteByExample(aRestExample);
			}

	public List<Rest> getAll() {		
		return restDAO.selectByExample(new RestExample());
	}

	public List<Rest> getAll(RestExample aRestExample) {		
		return restDAO.selectByExample(aRestExample);
	}
	
	public int getTotal(){
		return restDAO.countByExample(new RestExample());
	}
	
	public int getTotal(RestExample aRestExample){
		return restDAO.countByExample(aRestExample);
	}
	
																	
		public Rest findById(Integer restId){
			return restDAO.selectByPrimaryKey(restId);
		}	

	public Rest findById(BigDecimal restId){
			return null;
		}
	
	//Borra todos los datos de la tabla
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllData(){
			restDAO.deleteAllData();
		}
	
	public List<Rest> findSearch(RestExample example,
			PaginationContext paginationContext, int page) {
		if(page < 1)
		{
			page = 1;
		}
		this.example = example;
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		return restDAO.selectByExamplePaginated(example,paginationContext);
	}

	public List<Rest> getPaginated(String operation, PaginationContext paginationContext) { //previous,next,first,last,
		List<Rest> rests = null;
		if (operation==null){
			paginationContext.firstPage();
		}
		else if (operation.equalsIgnoreCase(Constants.PREVIOUS)){
			paginationContext.previousPage();
		}
		else if (operation.equalsIgnoreCase(Constants.NEXT)){
			paginationContext.nextPage();
		}
		else if (operation.equalsIgnoreCase(Constants.LAST)){
			paginationContext.lastPage();
		}
		else
		{
			paginationContext.firstPage();
		}
		// Acceso a la paginacion
		rests = restDAO.selectByExamplePaginated(example,paginationContext);

		// Imprime los resultados
		String msg = String.format(
				"skipResults %d de un total de %d records, en paginas de %d ",
				paginationContext.getSkipResults(), paginationContext
						.getTotalCount(), paginationContext.getMaxResults());
		LOGGER.debug(msg);
		return rests;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrInsert(Rest aRest,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException{		
		RestExample tableConditions = new RestExample();
		if (conditions.length>0){
			tableConditions= createUpdateCriteria(aRest,tableConditions,conditions);			
			int rows = restDAO.updateByExample(aRest, tableConditions);
			if (rows==0){
				restDAO.insert(aRest);
			}
		}
		else
		{
			restDAO.insert(aRest);
		}
	}
	
	private RestExample createUpdateCriteria(Rest aRest,RestExample tableConditions,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException {
		int numberOfConditions=conditions.length;
		Criteria criteria = tableConditions.createCriteria();
		proxyCriteria.setBean(criteria);
		proxyEntity.setBean(aRest);
		for (int i=0;i<numberOfConditions;i++){
			String condition=conditions[i];
			condition= condition.replace("_", "");
			Object value = proxyEntity.get(condition);
			if(value != null){
				proxyCriteria.invoke(getNameMethod(condition), new Class<?>[]{value.getClass()}, new Object[] {value});
			}
		}
		return tableConditions;
	}
	
	private String getNameMethod(String key){
		StringBuffer buffer = new StringBuffer();
		buffer.append("and");
		buffer.append(key.substring(0, 1).toUpperCase());
		buffer.append(key.substring(1,key.length()));
		buffer.append("EqualTo");
		return buffer.toString();
	}
}
	