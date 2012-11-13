package com.indizen.cursoSpring.servicio.service.group;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.group.GroupExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.ServiceBase;
 
 public interface GroupService extends ServiceBase<Group, GroupExample> {
	
	public List<Group> findSearch(GroupExample example, PaginationContext paginationContext, int page);

	public List<Group> getPaginated(String operation,PaginationContext paginationContext);

	public void updateOrInsert(Group aGroup, String[] conditions) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException,IllegalAccessException;

}