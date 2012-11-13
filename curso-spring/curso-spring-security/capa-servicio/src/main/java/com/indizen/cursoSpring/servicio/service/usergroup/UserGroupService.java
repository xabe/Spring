package com.indizen.cursoSpring.servicio.service.usergroup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.usergroup.UserGroup;
import com.indizen.cursoSpring.servicio.model.usergroup.UserGroupExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 
 public interface UserGroupService extends ServiceBase<UserGroup, UserGroupExample> {
	
	public List<UserGroup> findSearch(UserGroupExample example, PaginationContext paginationContext, int page);

	public List<UserGroup> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(UserGroup aUserGroup, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}