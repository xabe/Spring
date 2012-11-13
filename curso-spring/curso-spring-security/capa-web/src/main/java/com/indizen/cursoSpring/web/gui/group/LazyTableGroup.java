package com.indizen.cursoSpring.web.gui.group;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.group.GroupExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.group.GroupService;
import com.indizen.cursoSpring.web.gui.DataModelLazy;

public class LazyTableGroup extends DataModelLazy<Group, GroupExample, GroupService>{

private static final long serialVersionUID = 1L;
	
	public LazyTableGroup(GroupService service, GroupExample example, PaginationContext paginationContext) {
		super(service, example, paginationContext);
	}
	
	@Override  
	@SuppressWarnings(value ="unchecked")
    public Group getRowData(String rowKey) {  
        for(Group entity : (List<Group>)getWrappedData()) {  
            if(entity.equals(rowKey))  
                return entity;  
        }   
        return null;  
    } 
	
	@Override  
	public Object getRowKey(Group entity) {  
	        return entity.getId();  
	    } 
	
	@Override
	public List<Group> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		
		if(sortField != null)
		{
			getExample().setOrderByClause(sortField + (sortOrder == SortOrder.ASCENDING ?" ASC":" DESC"));
		}
		
		List<Group> list = getService().findSearch(getExample(), getPaginationContext(), getCurrentPage(first, getPaginationContext().getMaxResults()));
		
		this.setPageSize(getPaginationContext().getMaxResults());
	    this.setRowCount(getPaginationContext().getTotalCount());  
		return list;
	}
}
