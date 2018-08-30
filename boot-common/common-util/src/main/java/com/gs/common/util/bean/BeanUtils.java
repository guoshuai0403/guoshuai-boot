package com.gs.common.util.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanUtils {
	private static Object[] objects = new Object[0];
	private static HashMap<Class<?>, Method[]> beanMethodCache = new HashMap<Class<?>, Method[]>();
	private static HashMap<Class<?>,HashMap<String, Method>> fromBeanMethodCache = new HashMap<Class<?>, HashMap<String,Method>>();
	private static HashMap<Class<?>, HashMap<String, PropertyDescriptor>> fromBeanPropertyDescriptorCache = new HashMap<Class<?>, HashMap<String,PropertyDescriptor>>();
	
	private final static Log log = LogFactory.getLog(BeanUtils.class);
	
	/**
	 * 获取对象的属性信息
	 * @param obj
	 * @return
	 */
	public static Map<String, Class<?>> describe(Object obj) {
		Map<String,Class<?>> pros = new HashMap<String, Class<?>>();
		try{
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();			
			for(Field field: fields){
				if("serialVersionUID".equals(field.getName()))
					continue;
				pros.put(field.getName(), field.getType());
			}
			if(clazz.getSuperclass() != null){
				describe(pros, clazz.getSuperclass());
			}
		}catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return pros;
	}
	
	/**
	 * 为对象的属性设置值
	 * @param obj
	 * @param field
	 * @param fieldValue
	 */
	public static void setProperty(Object obj, String field, Object fieldValue) {
		Class<?> paramClazz = null;
		if(obj == null)
			return ;
				
		Method method = null;
		try{
			Class<?> clazz = obj.getClass();
			Map<String, PropertyDescriptor> prosMap = propertyDescriptorsWithFiles(clazz);	
			PropertyDescriptor propertyDescriptor = prosMap.get(field);
			if(propertyDescriptor == null )
				return;
			
			if(fieldValue == null)
				return;

			method = propertyDescriptor.getWriteMethod();
			Class<?>[] clazzes =  method.getParameterTypes();
			if(clazzes != null && clazzes.length == 1 ){
				paramClazz = clazzes[0];
				fieldValue = changeClassType(fieldValue, paramClazz);//这个空串可能转成null
				if(fieldValue != null){
					method.invoke(obj, new Object[]{fieldValue});
				}
			}
		}catch (Exception e) {
			log.warn("field="+field+"; paramClazz="+paramClazz+" fieldValue="+fieldValue+"; "+
					" methodParameterTypes=" +method.getParameterTypes() +
					"; \r\n msg="+e.getMessage(), e);
		}
	}
	
	/**
	 * 取对象指定的属性值
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object getProperty(Object obj, String field)  {
		try{
			Map<String, PropertyDescriptor> prosMap = propertyDescriptorsWithFiles(obj.getClass());			
			PropertyDescriptor propertyDescriptor=prosMap.get(field);			
			try{
				if(propertyDescriptor != null){
					Method method = propertyDescriptor.getReadMethod();
					return method.invoke(obj, objects);
				}
			}catch (Exception e) {}			
		}catch (Exception e) {
			log.warn("field:"+field+"; msg:"+e.getMessage(), e);
		}		
		return null;
	}
	
	/**
	 * 从键值对应设置所对应的obj的属性值
	 * @param obj
	 * @param properties
	 * @throws Exception
	 */
	public static void populate(Object obj, Map<Object, Object> properties) {
		String methodName = null;
		String proName = null;
		Object mapValue = null;
		Class<?> paramClazz = null;
		try{
			Class<?> clazz = obj.getClass();
			Method[] methods = clazz.getMethods();			
			for(Method m: methods){
				methodName = m.getName();				
				if(methodName.startsWith("set") && m.getParameterTypes().length>0){	
					paramClazz = m.getParameterTypes()[0];
					if(methodName.length()==4){
						proName = methodName.substring(3).toLowerCase();
					}else{
						proName = methodName.substring(3,4).toLowerCase() 
									+ methodName.substring(4);
					}					
					mapValue = properties.get(proName);
					if(mapValue != null){
						mapValue = changeClassType(mapValue, paramClazz);
						if(mapValue != null){
							m.invoke(obj, new Object[]{mapValue});
						}
					}
				}
			}			
		}catch (Exception e) {
			log.warn(e.getMessage()+", "+"methodName-->"+methodName, e);
		}		
	}	
	
	private static Object changeClassType(Object value , Class<?> clazz){
		Class<?> valueClass = value.getClass();
		if(valueClass.equals(clazz)){
			return value;
		}else{		
//			if("String".equals(value.getClass().getSimpleName()) 
//					&& !String.class.equals(clazz)){
				//字符串处理
				if( StringUtils.isNotBlank(value.toString()) ){					
					if(isPrimitiveOrEntity(clazz) ){
						value = primitiveObj(value.toString(), clazz);
					}else if( isOutPrimitiveEntity(clazz) ){
						value = outPrimitiveObj(value, clazz);
					}
				}else{
					value = null;
				}
//			}else{
//				
//			}
		}		
		return value;
		
//		if("String".equals(value.getClass().getSimpleName()) 
//				&& !String.class.equals(clazz)){
//			if( StringUtils.isNotBlank(value.toString()) ){					
//				if(isPrimitiveOrEntity(clazz) ){
//					value = primitiveObj(value.toString(), clazz);
//				}else if( isOutPrimitiveEntity(clazz) ){
//					value = outPrimitiveObj(value.toString(), clazz);
//				}
//			}else{
//				value = null;
//			}
//		}
//		return value;
	}

	/**
	 * 复制bean
	 * @param <T>
	 * @param beanClass
	 * @param fromBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyProperties(Class<T> beanClass,Object fromBean){		
		try {
			T bean = beanClass.newInstance();
			Class formBeanClass = fromBean.getClass();
			
			Method[] beanMethods = beanMethodCache.get(beanClass);
			if(beanMethods == null){
				beanMethods = beanClass.getMethods();
				beanMethodCache.put(beanClass, beanMethods);
			}
			
			HashMap<String, Method> fromBeanMethods = fromBeanMethodCache.get(formBeanClass);
			if(fromBeanMethods == null){
				fromBeanMethods = new HashMap<String, Method>();
				Method[] methods = formBeanClass.getMethods();
				String getMethodName = null;
				for(Method method: methods){
					getMethodName = method.getName();
					if( getMethodName.startsWith("get") || getMethodName.startsWith("is") )
						fromBeanMethods.put(getMethodName, method);
				}
				fromBeanMethodCache.put(formBeanClass, fromBeanMethods);
			}
			
			String methodName  = null;
			String getMethodName = null;
			Class[] paramsType = null;
			Class paramType = null;
			Object value = null;
			for(Method method: beanMethods){
				methodName = method.getName();				
				if(methodName.startsWith("set")){
					paramsType = method.getParameterTypes();
					if(paramsType.length != 1)
						continue;
					
					paramType = paramsType[0];
					if("boolean".equals(paramType.toString())){
						getMethodName = "is"+methodName.substring(3);
					}else{
						getMethodName = "get"+methodName.substring(3);
					}
					Method formBeanGetMethod = fromBeanMethods.get(getMethodName);
					if(formBeanGetMethod !=null ){
						if( paramType.equals( formBeanGetMethod.getReturnType() )){
							value = formBeanGetMethod.invoke(fromBean,objects);
							if (value != null){
								method.invoke(bean,new Object[]{value});
							}
						}
					}
				}				
			}		
			return bean;
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}	
	
	private static void describe(Map<String, Class<?>> pros, Class<?> superClazz) {
		Field[] fields = superClazz.getDeclaredFields();			
		for(Field field: fields){
			if("serialVersionUID".equals(field.getName()))
				continue;
			pros.put(field.getName(), field.getType());
		}	
		if(superClazz.getSuperclass()!=null){
			describe(pros, superClazz.getSuperclass());
		}
	}
	
	/*
	 * 把基本类型转成对应的对象
	 */
	public static Object primitiveObj(String value, Class<?> clazz) {
		if(clazz.isInstance(int.class) || "int".equals(clazz.getName()) || Integer.class.equals(clazz)){
			if( "null".equals(value) ){
				return null;
			}
			
			if(value.indexOf(".")>0){
				return Float.valueOf(value).intValue();
			}else{
				return Integer.valueOf(value);
			}
		}else if(clazz.isInstance(boolean.class) || "boolean".equals(clazz.getName()) || Boolean.class.equals(clazz)){
			if("on".equalsIgnoreCase(value)|| "1".equals(value)){
				return Boolean.TRUE;
			}else if("off".equalsIgnoreCase(value)|| "0".equals(value)){
				return Boolean.FALSE;
			}else{
				return Boolean.valueOf(value);
			}
		}else if(clazz.isInstance(long.class) || "long".equals(clazz.getName()) || Long.class.equals(clazz)){
			if( "null".equals(value) ){
				return null;
			}
			
			return Long.valueOf(value);
		}else if(clazz.isInstance(float.class) || "float".equals(clazz.getName()) || Float.class.equals(clazz)){
			if( "null".equals(value) ){
				return null;
			}
			return Float.valueOf(value);
		}else if(clazz.isInstance(double.class) || "double".equals(clazz.getName()) || Double.class.equals(clazz)){
			if( "null".equals(value) ){
				return null;
			}
			return Double.valueOf(value);
		}else if(clazz.isInstance(byte.class) || "byte".equals(clazz.getName()) || Byte.class.equals(clazz)){
			return Byte.valueOf(value);
		}else if(clazz.isInstance(char.class) || "char".equals(clazz.getName()) || Character.class.equals(clazz)){
			return Character.valueOf(value.charAt(0));
		}else if(clazz.isInstance(short.class) || "short".equals(clazz.getName()) || Short.class.equals(clazz)){
			return Short.valueOf(value);
		}
		return null;
	}
	
	public static Object outPrimitiveObj(Object value, Class<?> clazz) {
		try{
			Class valueClass = value.getClass();
			if(java.util.Date.class.equals(valueClass)){
				java.util.Date d = (java.util.Date)value;
				long l = d.getTime();
				if( Date.class.equals(clazz)){		
					return new Date( l );
				}else if( java.util.Date.class.equals(clazz)){
					return value;
				}else if( Timestamp.class.equals(clazz)){
					return new Timestamp(l);
				}else if( Time.class.equals(clazz)){
					return new Time(l);
				}else if(BigDecimal.class.equals(clazz)){
					return BigDecimal.valueOf(l);
				}
			}else{
				String v = value.toString();
				if( Date.class.equals(clazz)){		
					return Date.valueOf( v );
				}else if( java.util.Date.class.equals(clazz)){
					SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
					return fmt.parse( v );
				}else if( Timestamp.class.equals(clazz)){
					return Timestamp.valueOf( v );
				}else if( Time.class.equals(clazz)){
					return Time.valueOf( v );
				}else if(BigDecimal.class.equals(clazz)){
					return BigDecimal.valueOf(Double.valueOf( v ));
				}
			}
		}catch (Exception e) {
			log.warn(value+" cast to "+clazz +" error. ");
		}
		return null;
	}
	
	/**
	 * 是否基本类型或者基本类型的对象
	 * @param clazz
	 * @return
	 */
	public static boolean isPrimitiveOrEntity(Class<?> clazz){
		if(clazz.isPrimitive())
			return true;
		
		return  Integer.class.equals(clazz)
				|| Boolean.class.equals(clazz)
				|| Long.class.equals(clazz)
				|| Float.class.equals(clazz)
				|| Double.class.equals(clazz)
				|| Character.class.equals(clazz)
				|| Short.class.equals(clazz)
				|| Byte.class.equals(clazz);
	}
	
	public static boolean isOutPrimitiveEntity(Class<?> clazz){
		return Date.class.equals(clazz)
			|| java.util.Date.class.equals(clazz)
			|| Timestamp.class.equals(clazz)
			|| Time.class.equals(clazz)
			|| BigDecimal.class.equals(clazz);
	}
	
	// 获取Bean的描述信息
	public static PropertyDescriptor[] propertyDescriptors(Class<?> c)
	    throws Exception {
	    
	    BeanInfo beanInfo = null;
	    try {
	    	// Introspector 缓存BeanInfo来获取更高的性能
	        beanInfo = Introspector.getBeanInfo(c);
	    } catch (IntrospectionException e) {
	        throw new Exception(
	            "Bean introspection failed: " + e.getMessage());
	    }	
	    return beanInfo.getPropertyDescriptors();
	}
	
	public static Map<String, PropertyDescriptor> propertyDescriptorsWithFiles(Class<?> c) throws Exception {
	    
		HashMap<String, PropertyDescriptor> map = null;
	    try {
	    	map = fromBeanPropertyDescriptorCache.get(c);
	    	if(map==null){
	    		String propName = null;
	    		PropertyDescriptor[] pros = propertyDescriptors(c);
	    		map = new HashMap<String, PropertyDescriptor>(pros.length);
	    		for(PropertyDescriptor p : pros){
	    			propName = p.getName();
	    			if("class".equalsIgnoreCase(propName))
						continue;
	    			map.put(propName, p);
	    		}
	    		fromBeanPropertyDescriptorCache.put(c, map);
	    	}
	    } catch (IntrospectionException e) {
	        throw new Exception(
	            "Bean introspection failed: " + e.getMessage());
	    }	
	    return map;
	}
	
	public static void main(String[] args) {
		Float a = new Float(123.f);		
//		System.err.println(Float.class.equals(a.getClass()));
		float b = 12f;
		Object d = b;
		System.out.println(b);
	}
	
	
}

