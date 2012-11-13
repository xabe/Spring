package com.indizen.cursoSpring.web.gui.user;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.indizen.cursoSpring.servicio.model.user.User;
import com.indizen.cursoSpring.servicio.model.user.UserExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.user.UserService;
import com.indizen.cursoSpring.web.gui.DataModelLazy;

public class LazyTableUser extends DataModelLazy<User, UserExample, UserService>{

private static final long serialVersionUID = 1L;
	
	public LazyTableUser(UserService service, UserExample example, PaginationContext paginationContext) {
		super(service, example, paginationContext);
	}
	
	@Override  
	@SuppressWarnings(value ="unchecked")
    public User getRowData(String rowKey) {  
        for(User entity : (List<User>)getWrappedData()) {  
            if(entity.equals(rowKey))  
                return entity;  
        }   
        return null;  
    } 
	
	@Override  
	public Object getRowKey(User entity) {  
	        return entity.getId();  
	    } 
	
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		
		if(sortField != null)
		{
			getExample().setOrderByClause(sortField + (sortOrder == SortOrder.ASCENDING ?" ASC":" DESC"));
		}
		
		List<User> list = getService().findSearch(getExample(), getPaginationContext(), getCurrentPage(first, getPaginationContext().getMaxResults()));
		
		this.setPageSize(getPaginationContext().getMaxResults());
	    this.setRowCount(getPaginationContext().getTotalCount());  
		return list;
	}
}
