package com.indizen.cursoSpring.servicio.service.grouppermission;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermission;
import com.indizen.cursoSpring.servicio.model.grouppermission.GroupPermissionExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 
 public interface GroupPermissionService extends ServiceBase<GroupPermission, GroupPermissionExample> {
	
	public List<GroupPermission> findSearch(GroupPermissionExample example, PaginationContext paginationContext, int page);

	public List<GroupPermission> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(GroupPermission aGroupPermission, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}