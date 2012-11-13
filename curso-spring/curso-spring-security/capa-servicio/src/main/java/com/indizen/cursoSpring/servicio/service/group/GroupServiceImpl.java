package com.indizen.cursoSpring.servicio.service.group;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.group.GroupExample;
import com.indizen.cursoSpring.servicio.model.group.GroupExample.Criteria;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.persistence.group.GroupDAO;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.servicio.util.Constants;
/**
 * Servicio que gestiona todas las operaciones CRUD 
 * sobre objetos Group.
 * Esta clase a parte de gestionar dichas operaciones CRUD se puede extender
 * para realizar otro tipo de procesamientos sobre los objetos Group.
 *
 */
 
 			 
 
@Service("groupService")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
@SuppressWarnings("unchecked")
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupDAO groupDAO;
	private GroupExample example;
	private BeanProxy proxyEntity;
	private BeanProxy proxyCriteria;
	
	public GroupServiceImpl() {
		try
		{
			proxyCriteria = new BeanProxy(Criteria.class);
			proxyEntity = new BeanProxy(Group.class);
		}catch (Exception e) {
			LOGGER.error("Error los proxy de los servicios Group : "+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void add(Group aGroup) {
		groupDAO.insert(aGroup);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(Group aGroup) {
					groupDAO.updateByPrimaryKey(aGroup);
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(Group aGroup, GroupExample aGroupExample) {
					groupDAO.updateByExample(aGroup, aGroupExample);
			}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Group aGroup) {
					groupDAO.deleteByPrimaryKey(aGroup.getId());
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(GroupExample aGroupExample) {
					groupDAO.deleteByExample(aGroupExample);
			}

	public List<Group> getAll() {		
		return groupDAO.selectByExample(new GroupExample());
	}

	public List<Group> getAll(GroupExample aGroupExample) {		
		return groupDAO.selectByExample(aGroupExample);
	}
	
	public int getTotal(){
		return groupDAO.countByExample(new GroupExample());
	}
	
	public int getTotal(GroupExample aGroupExample){
		return groupDAO.countByExample(aGroupExample);
	}
	
											
		public Group findById(Integer groupId){
			return groupDAO.selectByPrimaryKey(groupId);
		}	

	public Group findById(BigDecimal groupId){
			return null;
		}
	
	//Borra todos los datos de la tabla
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllData(){
			groupDAO.deleteAllData();
		}
	
	public List<Group> findSearch(GroupExample example,
			PaginationContext paginationContext, int page) {
		if(page < 1)
		{
			page = 1;
		}
		this.example = example;
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		return groupDAO.selectByExamplePaginated(example,paginationContext);
	}

	public List<Group> getPaginated(String operation, PaginationContext paginationContext) { //previous,next,first,last,
		List<Group> groups = null;
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
		groups = groupDAO.selectByExamplePaginated(example,paginationContext);

		// Imprime los resultados
		String msg = String.format(
				"skipResults %d de un total de %d records, en paginas de %d ",
				paginationContext.getSkipResults(), paginationContext
						.getTotalCount(), paginationContext.getMaxResults());
		LOGGER.debug(msg);
		return groups;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrInsert(Group aGroup,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException{		
		GroupExample tableConditions = new GroupExample();
		if (conditions.length>0){
			tableConditions= createUpdateCriteria(aGroup,tableConditions,conditions);			
			int rows = groupDAO.updateByExample(aGroup, tableConditions);
			if (rows==0){
				groupDAO.insert(aGroup);
			}
		}
		else
		{
			groupDAO.insert(aGroup);
		}
	}
	
	private GroupExample createUpdateCriteria(Group aGroup,GroupExample tableConditions,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException {
		int numberOfConditions=conditions.length;
		Criteria criteria = tableConditions.createCriteria();
		proxyCriteria.setBean(criteria);
		proxyEntity.setBean(aGroup);
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
	