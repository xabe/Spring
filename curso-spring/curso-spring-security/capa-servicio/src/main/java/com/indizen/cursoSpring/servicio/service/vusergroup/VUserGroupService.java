package com.indizen.cursoSpring.servicio.service.vusergroup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroup;
import com.indizen.cursoSpring.servicio.model.vusergroup.VUserGroupExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 

 public interface VUserGroupService extends ServiceBase<VUserGroup, VUserGroupExample> {
	
	public List<VUserGroup> findSearch(VUserGroupExample example, PaginationContext paginationContext, int page);

	public List<VUserGroup> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(VUserGroup aVUserGroup, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}