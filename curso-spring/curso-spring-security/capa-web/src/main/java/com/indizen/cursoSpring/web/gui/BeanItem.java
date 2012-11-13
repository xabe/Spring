package com.indizen.cursoSpring.web.gui;

public class BeanItem {
	private String name;
	private String id;
	private static final int PRIME = 17;
	private static final int PRIME_SECOND = 37;
	
	public BeanItem(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	

	public String toString() {
		return name+":"+id;
	}
	
	@Override
	public int hashCode() {
		int result = PRIME;
        result = PRIME_SECOND*result + name.hashCode();
        result = PRIME_SECOND*result + id.hashCode();
        return result;
	}
	
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(this.getClass() != obj.getClass()){
			return false;
		}
		final BeanItem other  = (BeanItem) obj;
		if(id == null)
		{
			if(other.getId() != null){
				return false;
			}
		}
		else if(!id.equals(other.getId())){
			return false;
		}
		if(name == null)
		{
			if(other.getName() != null){
				return false;
			}
		}
		else if(!name.equals(other.getName())){
			return false;
		}
		return true;
	}	
}
