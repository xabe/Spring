package com.indizen.cursoSpring.servicio.service.user;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 
 public interface UserService extends ServiceBase<User, UserExample> {
	
	public List<User> findSearch(UserExample example, PaginationContext paginationContext, int page);

	public List<User> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(User aUser, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}