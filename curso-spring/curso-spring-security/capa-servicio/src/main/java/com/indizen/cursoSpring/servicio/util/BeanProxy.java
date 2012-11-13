package com.indizen.cursoSpring.servicio.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

/**
 * BeanProxy is a class for accessing a JavaBean dynamically at runtime. It 
 * provides methods for getting properties, setting properties, and invoking 
 * methods on a target bean. BeanProxy can be used in situations where bean 
 * properties and methods are not known at compile time, or when compile time
 * access to a bean would result in brittle code. 
 */
public class BeanProxy  {
   private Object    bean;
   private Class<?>     beanClass;
   private Map<String, PropertyDescriptor> pdsByName;

   /**
    * Constructs a proxy for the given class.
    *
    * @param theBeanClass The target bean class.
    */
   public BeanProxy(Class<?> theBeanClass) throws IntrospectionException {
      BeanInfo             bi;
      PropertyDescriptor[] pds;
      String               name;
      pdsByName = new Hashtable<String, PropertyDescriptor>();
      
      beanClass = theBeanClass;

      bi = Introspector.getBeanInfo(beanClass);
      pds = bi.getPropertyDescriptors();

      for (int i = 0; i < pds.length; i ++) {
         name = pds[i].getName();
         pdsByName.put(name, pds[i]);
      }
   }

   /**
    * Constructs a proxy for the given bean.
    *
    * @param theBean The target bean.
    */
   public BeanProxy(Object theBean) throws IntrospectionException {
      this(theBean.getClass());
      bean = theBean;
   }
   
   public Object getBean() {
      return bean;
   }

   public void setBean(Object newBean) {
      bean = newBean;
   }
   
   /**
    * Get bean property.
    *
    * @param name Bean property name.
    *
    * @return Bean property value as Object.
    */
   public Object get(String name) throws NoSuchFieldException,InvocationTargetException,IllegalAccessException,NoSuchMethodException {
      PropertyDescriptor pd;
      Method             getter;

      pd = (PropertyDescriptor) pdsByName.get(name);

      if (pd == null) {
         throw new NoSuchFieldException("Unknown property: " + name);
      }

      getter = pd.getReadMethod();

      if (getter == null) {
         throw new NoSuchMethodException("No read method for: " + name);
      }

      return getter.invoke(bean, new Object[] {});
   }

   /**
    * Set bean property.
    *
    * @param name   Bean property name.
    * @param value  Bean property value.
    */
   public Object set(String name, Object value) throws NoSuchFieldException,InvocationTargetException,IllegalAccessException,NoSuchMethodException {
      PropertyDescriptor pd;
      Method             setter;

      pd = (PropertyDescriptor) pdsByName.get(name);

      if (pd == null) {
         throw new NoSuchFieldException("Unknown property: " + name);
      }

      setter = pd.getWriteMethod();

      if (setter == null) {
         throw new NoSuchMethodException("No write method for: " + name);
      }

      return setter.invoke(bean, new Object[] { value } );
   }

   /**
    * Invoke named method on target bean.
    *
    * @param name       Method name.
    * @param types      Parameter types.
    * @param parameters List of parameters passed to method.
    *
    * @return Return value from method (may be null).
    *
    * @throws Throwable When any exception occurs.
    */
   public Object invoke(String name, Class<?>[] types, Object[] parameters) throws NoSuchMethodException,InvocationTargetException,IllegalAccessException {
      return beanClass.getMethod(name, types).invoke(bean, parameters);
   }
}