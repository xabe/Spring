package com.indizen.cursoSpring.servicio.service.vgrouppermission;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermission;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample.Criteria;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.persistence.vgrouppermission.VGroupPermissionDAO;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.servicio.util.Constants;
/**
 * Servicio que gestiona todas las operaciones CRUD 
 * sobre objetos VGroupPermission.
 * Esta clase a parte de gestionar dichas operaciones CRUD se puede extender
 * para realizar otro tipo de procesamientos sobre los objetos VGroupPermission.
 *
 */
 
 						 
 
@Service("vGroupPermissionService")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
@SuppressWarnings("unchecked")
public class VGroupPermissionServiceImpl implements VGroupPermissionService {
	@Autowired
	private VGroupPermissionDAO vGroupPermissionDAO;
	private VGroupPermissionExample example;
	private BeanProxy proxyEntity;
	private BeanProxy proxyCriteria;
	
	public VGroupPermissionServiceImpl() {
		try
		{
			proxyCriteria = new BeanProxy(Criteria.class);
			proxyEntity = new BeanProxy(VGroupPermission.class);
		}catch (Exception e) {
			LOGGER.error("Error los proxy de los servicios VGroupPermission : "+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void add(VGroupPermission aVGroupPermission) {
		vGroupPermissionDAO.insert(aVGroupPermission);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(VGroupPermission aVGroupPermission) {
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(VGroupPermission aVGroupPermission, VGroupPermissionExample aVGroupPermissionExample) {
			}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(VGroupPermission aVGroupPermission) {
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(VGroupPermissionExample aVGroupPermissionExample) {
			}

	public List<VGroupPermission> getAll() {		
		return vGroupPermissionDAO.selectByExample(new VGroupPermissionExample());
	}

	public List<VGroupPermission> getAll(VGroupPermissionExample aVGroupPermissionExample) {		
		return vGroupPermissionDAO.selectByExample(aVGroupPermissionExample);
	}
	
	public int getTotal(){
		return vGroupPermissionDAO.countByExample(new VGroupPermissionExample());
	}
	
	public int getTotal(VGroupPermissionExample aVGroupPermissionExample){
		return vGroupPermissionDAO.countByExample(aVGroupPermissionExample);
	}
	
																				
		public VGroupPermission findById(Integer vGroupPermissionId){
			return null;
		}	

	public VGroupPermission findById(BigDecimal vGroupPermissionId){
			return null;
		}
	
	//Borra todos los datos de la tabla
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllData(){
		}
	
	public List<VGroupPermission> findSearch(VGroupPermissionExample example,
			PaginationContext paginationContext, int page) {
		if(page < 1)
		{
			page = 1;
		}
		this.example = example;
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		return vGroupPermissionDAO.selectByExamplePaginated(example,paginationContext);
	}

	public List<VGroupPermission> getPaginated(String operation, PaginationContext paginationContext) { //previous,next,first,last,
		List<VGroupPermission> vGroupPermissions = null;
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
		vGroupPermissions = vGroupPermissionDAO.selectByExamplePaginated(example,paginationContext);

		// Imprime los resultados
		String msg = String.format(
				"skipResults %d de un total de %d records, en paginas de %d ",
				paginationContext.getSkipResults(), paginationContext
						.getTotalCount(), paginationContext.getMaxResults());
		LOGGER.debug(msg);
		return vGroupPermissions;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrInsert(VGroupPermission aVGroupPermission,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException{		
		VGroupPermissionExample tableConditions = new VGroupPermissionExample();
		if (conditions.length>0){
			tableConditions= createUpdateCriteria(aVGroupPermission,tableConditions,conditions);			
			int rows = vGroupPermissionDAO.updateByExample(aVGroupPermission, tableConditions);
			if (rows==0){
				vGroupPermissionDAO.insert(aVGroupPermission);
			}
		}
		else
		{
			vGroupPermissionDAO.insert(aVGroupPermission);
		}
	}
	
	private VGroupPermissionExample createUpdateCriteria(VGroupPermission aVGroupPermission,VGroupPermissionExample tableConditions,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException {
		int numberOfConditions=conditions.length;
		Criteria criteria = tableConditions.createCriteria();
		proxyCriteria.setBean(criteria);
		proxyEntity.setBean(aVGroupPermission);
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
	