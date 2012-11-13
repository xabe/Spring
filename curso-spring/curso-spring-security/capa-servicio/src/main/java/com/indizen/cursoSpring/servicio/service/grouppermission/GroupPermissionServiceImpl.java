package com.indizen.cursoSpring.servicio.service.grouppermission;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermission;
import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermissionExample.Criteria;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.persistence.grouppermission.GroupPermissionDAO;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.servicio.util.Constants;
/**
 * Servicio que gestiona todas las operaciones CRUD 
 * sobre objetos GroupPermission.
 * Esta clase a parte de gestionar dichas operaciones CRUD se puede extender
 * para realizar otro tipo de procesamientos sobre los objetos GroupPermission.
 *
 */
 
 				 
 
@Service("groupPermissionService")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
@SuppressWarnings("unchecked")
public class GroupPermissionServiceImpl implements GroupPermissionService {
	@Autowired
	private GroupPermissionDAO groupPermissionDAO;
	private GroupPermissionExample example;
	private BeanProxy proxyEntity;
	private BeanProxy proxyCriteria;
	
	public GroupPermissionServiceImpl() {
		try
		{
			proxyCriteria = new BeanProxy(Criteria.class);
			proxyEntity = new BeanProxy(GroupPermission.class);
		}catch (Exception e) {
			LOGGER.error("Error los proxy de los servicios GroupPermission : "+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void add(GroupPermission aGroupPermission) {
		groupPermissionDAO.insert(aGroupPermission);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(GroupPermission aGroupPermission) {
					groupPermissionDAO.updateByPrimaryKey(aGroupPermission);
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(GroupPermission aGroupPermission, GroupPermissionExample aGroupPermissionExample) {
					groupPermissionDAO.updateByExample(aGroupPermission, aGroupPermissionExample);
			}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(GroupPermission aGroupPermission) {
					groupPermissionDAO.deleteByPrimaryKey(aGroupPermission.getId());
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(GroupPermissionExample aGroupPermissionExample) {
					groupPermissionDAO.deleteByExample(aGroupPermissionExample);
			}

	public List<GroupPermission> getAll() {		
		return groupPermissionDAO.selectByExample(new GroupPermissionExample());
	}

	public List<GroupPermission> getAll(GroupPermissionExample aGroupPermissionExample) {		
		return groupPermissionDAO.selectByExample(aGroupPermissionExample);
	}
	
	public int getTotal(){
		return groupPermissionDAO.countByExample(new GroupPermissionExample());
	}
	
	public int getTotal(GroupPermissionExample aGroupPermissionExample){
		return groupPermissionDAO.countByExample(aGroupPermissionExample);
	}
	
														
		public GroupPermission findById(Integer groupPermissionId){
			return groupPermissionDAO.selectByPrimaryKey(groupPermissionId);
		}	

	public GroupPermission findById(BigDecimal groupPermissionId){
			return null;
		}
	
	//Borra todos los datos de la tabla
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllData(){
			groupPermissionDAO.deleteAllData();
		}
	
	public List<GroupPermission> findSearch(GroupPermissionExample example,
			PaginationContext paginationContext, int page) {
		if(page < 1)
		{
			page = 1;
		}
		this.example = example;
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		return groupPermissionDAO.selectByExamplePaginated(example,paginationContext);
	}

	public List<GroupPermission> getPaginated(String operation, PaginationContext paginationContext) { //previous,next,first,last,
		List<GroupPermission> groupPermissions = null;
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
		groupPermissions = groupPermissionDAO.selectByExamplePaginated(example,paginationContext);

		// Imprime los resultados
		String msg = String.format(
				"skipResults %d de un total de %d records, en paginas de %d ",
				paginationContext.getSkipResults(), paginationContext
						.getTotalCount(), paginationContext.getMaxResults());
		LOGGER.debug(msg);
		return groupPermissions;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrInsert(GroupPermission aGroupPermission,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException{		
		GroupPermissionExample tableConditions = new GroupPermissionExample();
		if (conditions.length>0){
			tableConditions= createUpdateCriteria(aGroupPermission,tableConditions,conditions);			
			int rows = groupPermissionDAO.updateByExample(aGroupPermission, tableConditions);
			if (rows==0){
				groupPermissionDAO.insert(aGroupPermission);
			}
		}
		else
		{
			groupPermissionDAO.insert(aGroupPermission);
		}
	}
	
	private GroupPermissionExample createUpdateCriteria(GroupPermission aGroupPermission,GroupPermissionExample tableConditions,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException {
		int numberOfConditions=conditions.length;
		Criteria criteria = tableConditions.createCriteria();
		proxyCriteria.setBean(criteria);
		proxyEntity.setBean(aGroupPermission);
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
	