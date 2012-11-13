package com.indizen.cursoSpring.web.gui.rest;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.indizen.cursoSpring.servicio.model.rest.Rest;
import com.indizen.cursoSpring.servicio.model.rest.RestExample;
import com.indizen.cursoSpring.servicio.persistence.PaginationContext;
import com.indizen.cursoSpring.servicio.service.rest.RestService;
import com.indizen.cursoSpring.web.gui.DataModelLazy;

public class LazyTableRest extends DataModelLazy<Rest, RestExample, RestService>{

	private static final long serialVersionUID = 1L;

	
	public LazyTableRest(RestService service, RestExample example, PaginationContext paginationContext) {
		super(service, example, paginationContext);
	}
	
	@Override  
	@SuppressWarnings(value ="unchecked")
	public Rest getRowData(String rowKey) {  
        for(Rest entity : (List<Rest>)getWrappedData()) {  
            if(entity.equals(rowKey))  
                return entity;  
        }   
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Rest entity) {  
        return entity.getId();  
    } 
	
    @Override
	public List<Rest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		
		if(sortField != null)
		{
			getExample().setOrderByClause(sortField + (sortOrder == SortOrder.ASCENDING ?" ASC":" DESC"));
		}
		
		List<Rest> list = getService().findSearch(getExample(), getPaginationContext(), getCurrentPage(first, getPaginationContext().getMaxResults()));
		
		this.setPageSize(getPaginationContext().getMaxResults());
	    this.setRowCount(getPaginationContext().getTotalCount());  
		return list;
	}
}
