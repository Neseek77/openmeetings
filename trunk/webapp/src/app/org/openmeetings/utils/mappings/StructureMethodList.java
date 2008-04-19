package org.openmeetings.utils.mappings;

import java.util.LinkedHashMap;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;

public class StructureMethodList {
	
	private static final Log log = LogFactory.getLog(StructureMethodList.class);
	
	private StructureMethodList() {}

	private static StructureMethodList instance = null;

	public static synchronized StructureMethodList getInstance() {
		if (instance == null) {
			instance = new StructureMethodList();
		}
		return instance;
	}	
	
	/*
	 * 
	 */

	public LinkedHashMap<String,LinkedHashMap<String,Object>> parseClassToMethodList(Class targetClass){
		try {
			LinkedHashMap<String,LinkedHashMap<String,Object>> returnMap = new LinkedHashMap<String,LinkedHashMap<String,Object>>();
			
			for (Field field : targetClass.getDeclaredFields()) {
				String fieldName = field.getName();
				Class fieldTypeClass = field.getType();
				//log.error("fieldTypeClass Name " + fieldTypeClass.getName() );
				String capitalizedFieldName = StringUtils.capitalize(fieldName);
				String setterPre = "set";
				Method method = targetClass.getMethod(setterPre + capitalizedFieldName, fieldTypeClass);
				
				String methodName = method.getName();
				
				Class[] paramTypes = method.getParameterTypes();
				//log.error("parseClassToMethodList methodName: "+methodName);
				if (methodName.startsWith("set")) {
					//Found setter get Attribute name
					if (returnMap.get(fieldName)!=null) {
						LinkedHashMap<String,Object> methodListMap = returnMap.get(fieldName);
						methodListMap.put("setter", methodName);
						methodListMap.put("setterParamTypes", paramTypes);
					} else {
						LinkedHashMap<String,Object> methodListMap = new LinkedHashMap<String,Object>();
						methodListMap.put("setter", methodName);
						returnMap.put(fieldName, methodListMap);
						methodListMap.put("setterParamTypes", paramTypes);
					}
				} else if (methodName.startsWith("is")) {
					//Found setter(boolean) get Attribute name
					if (returnMap.get(fieldName)!=null) {
						LinkedHashMap<String,Object> methodListMap = returnMap.get(fieldName);
						methodListMap.put("getter", methodName);
					} else {
						LinkedHashMap<String,Object> methodListMap = new LinkedHashMap<String,Object>();
						methodListMap.put("getter", methodName);
						returnMap.put(fieldName, methodListMap);
					}
				} else if (methodName.startsWith("get")) {
					//Found setter(boolean) get Attribute name
					if (returnMap.get(fieldName)!=null) {
						LinkedHashMap<String,Object> methodListMap = returnMap.get(fieldName);
						methodListMap.put("getter", methodName);
					} else {
						LinkedHashMap<String,Object> methodListMap = new LinkedHashMap<String,Object>();
						methodListMap.put("getter", methodName);
						returnMap.put(fieldName, methodListMap);
					}
				}
				
			}			
			
			return returnMap;
		} catch (Exception ex) {
			log.error("[parseClassToMethodList]",ex);
			return new LinkedHashMap<String,LinkedHashMap<String,Object>>();
		}
		
	}
	
}
