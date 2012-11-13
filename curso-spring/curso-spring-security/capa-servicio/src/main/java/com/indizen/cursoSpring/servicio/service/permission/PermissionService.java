package com.indizen.cursoSpring.servicio.service.permission;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.permission.Permission;
import com.indizen.cursoSpring.servicio.model.permission.PermissionExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 
 public interface PermissionService extends ServiceBase<Permission, PermissionExample> {
	
	public List<Permission> findSearch(PermissionExample example, PaginationContext paginationContext, int page);

	public List<Permission> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(Permission aPermission, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}