package com.indizen.cursoSpring.servicio.service.user;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.model.user.UserExample.Criteria;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.persistence.user.UserDAO;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.servicio.util.Constants;
/**
 * Servicio que gestiona todas las operaciones CRUD 
 * sobre objetos User.
 * Esta clase a parte de gestionar dichas operaciones CRUD se puede extender
 * para realizar otro tipo de procesamientos sobre los objetos User.
 *
 */
 
 														 
 
@Service("userService")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	private UserExample example;
	private BeanProxy proxyEntity;
	private BeanProxy proxyCriteria;
	
	public UserServiceImpl() {
		try
		{
			proxyCriteria = new BeanProxy(Criteria.class);
			proxyEntity = new BeanProxy(User.class);
		}catch (Exception e) {
			LOGGER.error("Error los proxy de los servicios User : "+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void add(User aUser) {
		userDAO.insert(aUser);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(User aUser) {
					userDAO.updateByPrimaryKey(aUser);
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void update(User aUser, UserExample aUserExample) {
					userDAO.updateByExample(aUser, aUserExample);
			}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(User aUser) {
					userDAO.deleteByPrimaryKey(aUser.getId());
			}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void delete(UserExample aUserExample) {
					userDAO.deleteByExample(aUserExample);
			}

	public List<User> getAll() {		
		return userDAO.selectByExample(new UserExample());
	}

	public List<User> getAll(UserExample aUserExample) {		
		return userDAO.selectByExample(aUserExample);
	}
	
	public int getTotal(){
		return userDAO.countByExample(new UserExample());
	}
	
	public int getTotal(UserExample aUserExample){
		return userDAO.countByExample(aUserExample);
	}
	
																																												
		public User findById(Integer userId){
			return userDAO.selectByPrimaryKey(userId);
		}	

	public User findById(BigDecimal userId){
			return null;
		}
	
	//Borra todos los datos de la tabla
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAllData(){
			userDAO.deleteAllData();
		}
	
	public List<User> findSearch(UserExample example,
			PaginationContext paginationContext, int page) {
		if(page < 1)
		{
			page = 1;
		}
		this.example = example;
		paginationContext.setSkipResults(paginationContext.getMaxResults() * (page - 1));
		return userDAO.selectByExamplePaginated(example,paginationContext);
	}

	public List<User> getPaginated(String operation, PaginationContext paginationContext) { //previous,next,first,last,
		List<User> users = null;
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
		users = userDAO.selectByExamplePaginated(example,paginationContext);

		// Imprime los resultados
		String msg = String.format(
				"skipResults %d de un total de %d records, en paginas de %d ",
				paginationContext.getSkipResults(), paginationContext
						.getTotalCount(), paginationContext.getMaxResults());
		LOGGER.debug(msg);
		return users;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrInsert(User aUser,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException{		
		UserExample tableConditions = new UserExample();
		if (conditions.length>0){
			tableConditions= createUpdateCriteria(aUser,tableConditions,conditions);			
			int rows = userDAO.updateByExample(aUser, tableConditions);
			if (rows==0){
				userDAO.insert(aUser);
			}
		}
		else
		{
			userDAO.insert(aUser);
		}
	}
	
	private UserExample createUpdateCriteria(User aUser,UserExample tableConditions,String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException {
		int numberOfConditions=conditions.length;
		Criteria criteria = tableConditions.createCriteria();
		proxyCriteria.setBean(criteria);
		proxyEntity.setBean(aUser);
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
	