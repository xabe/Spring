package com.indizen.cursoSpring.web.util;

import org.apache.log4j.Logger;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BeanIntrospector {
	private static final Logger LOGGER = Logger.getLogger(BeanIntrospector.class);
	private static final int POS = 11;

	private BeanIntrospector(){}
	
	public static List<BeanProp> getBeanCriteria(Class<?> c) throws IntrospectionException{
		BeanInfo info = Introspector.getBeanInfo(c);
		List<BeanProp> v =new ArrayList<BeanProp>();
		BeanProp kv;
		PropertyDescriptor pdarray []=info.getPropertyDescriptors();
		for ( int i=0;i<pdarray.length;i++){
			PropertyDescriptor pd =pdarray[i];
			if(!pd.getName().equals("class")){
				kv=new BeanProp();
				kv.setName(pd.getName().substring(0, 1).toUpperCase()+pd.getName().substring(1, pd.getName().length()));
				kv.setKey(pd.getName().toUpperCase());
				kv.setValue(Integer.toString(i+POS));//Pone una constante por encima del 11
				kv.setType(pd.getPropertyType().toString());
				v.add(kv);
				LOGGER.debug("beanCriteria. Property: " + pd.getName() + " Type: "+ pd.getPropertyType());
			}
		}
		return v;
	}

	public static Map<String,PropertyDescriptor> getProperties(Class<?> c) throws IntrospectionException{
		BeanInfo info = Introspector.getBeanInfo(c);
		Map<String,PropertyDescriptor> v = new HashMap<String, PropertyDescriptor>(); 
		for (PropertyDescriptor pd : info.getPropertyDescriptors()){
			if(!pd.getName().equals("class")){
				LOGGER.debug("getProperties. Property: " + pd.getName().toLowerCase() + " Type: "+ pd.getPropertyType());
				v.put(pd.getName().toLowerCase(),pd);
			}
		}
		return v;
	}
}
