package com.indizen.cursoSpring.servicio.service.usergroup;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.usergroup.UserGroup;
import com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample;
import com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample.Criteria;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.persistence.usergroup.UserGroupDAO;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.servicio.util.Constants;
/**
 * Servicio que gestiona todas las operaciones CRUD 
 * sobre objetos UserGroup.
 * Esta clase a parte de gestionar dichas operaciones CRUD se puede extender
 * para realizar otro tipo de procesamientos sobre los objetos UserGroup.
 *
 */
 
 				 
 
@Service("userGroupService")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
@SuppressWarnings("unchecked")
public class UserGroupServiceImpl implements UserGroupService {
	@Autowired
	private UserGroupDAO userGroupDAO;
	private UserGroupExample example;
	private BeanProxy proxyEntity;
	private BeanProxy proxyCriteria;
	
	public UserGroupServiceImpl() {
		try
		{
			proxyCriteria = new BeanProxy(Criteria.class);
			proxyEntity = new BeanProxy(UserGroup.class);
		}catch (Exception e) {
			LOGGER.error("Error los proxy de los servicios UserGroup : "+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void add(UserGroup aUserGroup) {
		userGroupDAO.insert(aUserGroup);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(UserGroup aUserGroup) {
					userGroupDAO.updateByPrimaryKey(aUserGroup);
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(UserGroup aUserGroup, UserGroupExample aUserGroupExample) {
					userGroupDAO.updateByExample(aUserGroup, aUserGroupExample);
			}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(UserGroup aUserGroup) {
					userGroupDAO.deleteByPrimaryKey(aUserGroup.getId());
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(UserGroupExample aUserGroupExample) {
					userGroupDAO.deleteByExample(aUserGroupExample);
			}

	public List<UserGroup> getAll() {		
		return userGroupDAO.selectByExample(new UserGroupExample());
	}

	public List<UserGroup> getAll(UserGroupExample aUserGroupExample) {		
		return userGroupDAO.selectByExample(aUserGroupExample);
	}
	
	public int getTotal(){
		return userGroupDAO.countByExample(new UserGroupExample());
	}
	
	public int getTotal(UserGroupExample aUserGroupExample){
		return userGroupDAO.countByExample(aUserGroupExample);
	}
	
														
		public UserGroup findById(Integer userGroupId){
			return userGroupDAO.selectByPrimaryKey(userGroupId);
		}	

	public UserGroup findById(BigDecimal userGroupId){
			return null;
		}
	
	//Borra todos los datos de la tabla
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllData(){
			userGroupDAO.deleteAllData();
		}
	
	public List<UserGroup> findSearch(UserGroupExample example,
			PaginationContext paginationContext, int page) {
		if(page < 1)
		{
			page = 1;
		}
		this.example = example;
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		return userGroupDAO.selectByExamplePaginated(example,paginationContext);
	}

	public List<UserGroup> getPaginated(String operation, PaginationContext paginationContext) { //previous,next,first,last,
		List<UserGroup> userGroups = null;
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
		userGroups = userGroupDAO.selectByExamplePaginated(example,paginationContext);

		// Imprime los resultados
		String msg = String.format(
				"skipResults %d de un total de %d records, en paginas de %d ",
				paginationContext.getSkipResults(), paginationContext
						.getTotalCount(), paginationContext.getMaxResults());
		LOGGER.debug(msg);
		return userGroups;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrInsert(UserGroup aUserGroup,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException{		
		UserGroupExample tableConditions = new UserGroupExample();
		if (conditions.length>0){
			tableConditions= createUpdateCriteria(aUserGroup,tableConditions,conditions);			
			int rows = userGroupDAO.updateByExample(aUserGroup, tableConditions);
			if (rows==0){
				userGroupDAO.insert(aUserGroup);
			}
		}
		else
		{
			userGroupDAO.insert(aUserGroup);
		}
	}
	
	private UserGroupExample createUpdateCriteria(UserGroup aUserGroup,UserGroupExample tableConditions,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException {
		int numberOfConditions=conditions.length;
		Criteria criteria = tableConditions.createCriteria();
		proxyCriteria.setBean(criteria);
		proxyEntity.setBean(aUserGroup);
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
	