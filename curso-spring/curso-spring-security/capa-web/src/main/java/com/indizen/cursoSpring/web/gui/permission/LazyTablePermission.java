package com.indizen.cursoSpring.web.gui.permission;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.indizen.cursoSpring.servicio.model.permission.Permission;
import com.indizen.cursoSpring.servicio.model.permission.PermissionExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.permission.PermissionService;
import com.indizen.cursoSpring.web.gui.DataModelLazy;

public class LazyTablePermission extends DataModelLazy<Permission, PermissionExample, PermissionService>{

	private static final long serialVersionUID = 1L;
	
	public LazyTablePermission(PermissionService service, PermissionExample example, PaginationContext paginationContext) {
		super(service, example, paginationContext);
	}
	
	@Override  
	@SuppressWarnings(value ="unchecked")
    public Permission getRowData(String rowKey) {  
        for(Permission entity : (List<Permission>)getWrappedData()) {  
            if(entity.equals(rowKey))  
                return entity;  
        }   
        return null;  
    } 
	
	@Override  
	public Object getRowKey(Permission entity) {  
	        return entity.getId();  
	    } 
	
	@Override
	public List<Permission> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		
		if(sortField != null)
		{
			getExample().setOrderByClause(sortField + (sortOrder == SortOrder.ASCENDING ?" ASC":" DESC"));
		}
		
		List<Permission> list = getService().findSearch(getExample(), getPaginationContext(), getCurrentPage(first, getPaginationContext().getMaxResults()));
		
		this.setPageSize(getPaginationContext().getMaxResults());
	    this.setRowCount(getPaginationContext().getTotalCount());  
		return list;
	}

}
