package com.indizen.cursoSpring.servicio.service.vgrouppermission;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermission;
import com.indizen.cursoSpring.servicio.model.vgrouppermission.VGroupPermissionExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 
 public interface VGroupPermissionService extends ServiceBase<VGroupPermission, VGroupPermissionExample> {
	
	public List<VGroupPermission> findSearch(VGroupPermissionExample example, PaginationContext paginationContext, int page);

	public List<VGroupPermission> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(VGroupPermission aVGroupPermission, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}