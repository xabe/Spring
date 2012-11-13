package com.indizen.cursoSpring.servicio.service.permission;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.permission.Permission;
import com.indizen.cursoSpring.servicio.model.permission.PermissionExample;
import com.indizen.cursoSpring.servicio.model.permission.PermissionExample.Criteria;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.persistence.permission.PermissionDAO;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.servicio.util.Constants;
/**
 * Servicio que gestiona todas las operaciones CRUD 
 * sobre objetos Permission.
 * Esta clase a parte de gestionar dichas operaciones CRUD se puede extender
 * para realizar otro tipo de procesamientos sobre los objetos Permission.
 *
 */
 
 			 
 
@Service("permissionService")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
@SuppressWarnings("unchecked")
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionDAO permissionDAO;
	private PermissionExample example;
	private BeanProxy proxyEntity;
	private BeanProxy proxyCriteria;
	
	public PermissionServiceImpl() {
		try
		{
			proxyCriteria = new BeanProxy(Criteria.class);
			proxyEntity = new BeanProxy(Permission.class);
		}catch (Exception e) {
			LOGGER.error("Error los proxy de los servicios Permission : "+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void add(Permission aPermission) {
		permissionDAO.insert(aPermission);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(Permission aPermission) {
					permissionDAO.updateByPrimaryKey(aPermission);
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(Permission aPermission, PermissionExample aPermissionExample) {
					permissionDAO.updateByExample(aPermission, aPermissionExample);
			}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Permission aPermission) {
					permissionDAO.deleteByPrimaryKey(aPermission.getId());
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(PermissionExample aPermissionExample) {
					permissionDAO.deleteByExample(aPermissionExample);
			}

	public List<Permission> getAll() {		
		return permissionDAO.selectByExample(new PermissionExample());
	}

	public List<Permission> getAll(PermissionExample aPermissionExample) {		
		return permissionDAO.selectByExample(aPermissionExample);
	}
	
	public int getTotal(){
		return permissionDAO.countByExample(new PermissionExample());
	}
	
	public int getTotal(PermissionExample aPermissionExample){
		return permissionDAO.countByExample(aPermissionExample);
	}
	
											
		public Permission findById(Integer permissionId){
			return permissionDAO.selectByPrimaryKey(permissionId);
		}	

	public Permission findById(BigDecimal permissionId){
			return null;
		}
	
	//Borra todos los datos de la tabla
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllData(){
			permissionDAO.deleteAllData();
		}
	
	public List<Permission> findSearch(PermissionExample example,
			PaginationContext paginationContext, int page) {
		if(page < 1)
		{
			page = 1;
		}
		this.example = example;
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		return permissionDAO.selectByExamplePaginated(example,paginationContext);
	}

	public List<Permission> getPaginated(String operation, PaginationContext paginationContext) { //previous,next,first,last,
		List<Permission> permissions = null;
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
		permissions = permissionDAO.selectByExamplePaginated(example,paginationContext);

		// Imprime los resultados
		String msg = String.format(
				"skipResults %d de un total de %d records, en paginas de %d ",
				paginationContext.getSkipResults(), paginationContext
						.getTotalCount(), paginationContext.getMaxResults());
		LOGGER.debug(msg);
		return permissions;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrInsert(Permission aPermission,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException{		
		PermissionExample tableConditions = new PermissionExample();
		if (conditions.length>0){
			tableConditions= createUpdateCriteria(aPermission,tableConditions,conditions);			
			int rows = permissionDAO.updateByExample(aPermission, tableConditions);
			if (rows==0){
				permissionDAO.insert(aPermission);
			}
		}
		else
		{
			permissionDAO.insert(aPermission);
		}
	}
	
	private PermissionExample createUpdateCriteria(Permission aPermission,PermissionExample tableConditions,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException {
		int numberOfConditions=conditions.length;
		Criteria criteria = tableConditions.createCriteria();
		proxyCriteria.setBean(criteria);
		proxyEntity.setBean(aPermission);
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
	