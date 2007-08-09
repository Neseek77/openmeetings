package org.xmlcrm.utils.mappings;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class to cast any LinkedHashMap to its JavaBean repraesentant
 * the idiom is that the attribute name in the LinkedHashMap is the same as in the JavaBean/Pojo
 * 
 * if the attribute's of the Bean are private (meaning it IS a Bean) then it will use the getters and setters
 * if the attribute's are public it will assign directly
 * if the attribute is final it will show an error in log
 * 
 * if the HashMap contains an null for a primitive attribute it will not assign that value
 * 
 * if the HashMap contains subelments nested as LinkedHashMap's it will add these Sub-Elements to the Main-Object
 * for an exmaple see:
 * http://openmeetings.googlecode.com/svn/branches/dev/xmlcrm/java/src/test/org/xmlcrm/utils/TestReflectionApi.java
 * 
 * TODO:
 * If the Sub Item is not an Object but a Set (meaning a List of Object) this List must be 
 * cast to Objects of the Bean too
 * 
 * @author swagner
 * 
 *
 */

public class CastHashMapToObject {
	
	private static final Log log = LogFactory.getLog(CastHashMapToObject.class);
	
	private CastHashMapToObject() {}

	private static CastHashMapToObject instance = null;

	public static synchronized CastHashMapToObject getInstance() {
		if (instance == null) {
			instance = new CastHashMapToObject();
		}
		return instance;
	}	
	
	public Object castByGivenObject(LinkedHashMap values, Class targetClass){
		try {
			Object returnObject = targetClass.newInstance();
//			log.error("returnObject");
//			log.error(returnObject);
//			log.error( "class " + targetClass.getName() ); 
//			log.error (" number of declared fields: " + targetClass.getDeclaredFields().length );
			LinkedHashMap<String,LinkedHashMap<String,Object>> structuredMethodMap = StructureMethodList.getInstance().parseClassToMethodList(targetClass);
			
			for ( Field anyField : targetClass.getDeclaredFields() )  { 
				String fieldName = anyField.getName(); 
				Class fieldType = anyField.getType();
				String fieldTypeName = anyField.getType().getName(); 

				if (this.compareTypeNameToBasicTypes(fieldTypeName)) {
					//log.info("Found Type: " + fieldName);
					//Get value from  set 
					Object t = values.get(fieldName);
					//log.info("fieldName Value: "+t);
					//log.info("fieldName Value: "+anyField.getModifiers());
					int mod = anyField.getModifiers();
					
					if (Modifier.isPrivate(mod) && !Modifier.isFinal(mod)){
						
						//log.info("is private so get setter method "+fieldName);
						LinkedHashMap<String,Object> methodSummery = structuredMethodMap.get(fieldName);
						
						if (methodSummery!=null) {
							if (methodSummery.get("setter")!=null) {
	
								String methodSetterName = methodSummery.get("setter").toString();
								Class[] paramTypes = (Class[]) methodSummery.get("setterParamTypes");
								Method m = targetClass.getMethod(methodSetterName, paramTypes);
								
								Class paramType = paramTypes[0];
								
								//try to cast the Given Object to the necessary Object
								if (t!=null && !paramType.getName().equals(t.getClass().getName())){
									for (Constructor crt : paramType.getConstructors()) {
										if (crt.getParameterTypes()[0].getName().equals("java.lang.String")){
											t = crt.newInstance(t.toString());	
										}
									}
								}
								if (paramType.isPrimitive() && t==null){
									//cannot cast null to primitve
								} else {
									Object[] arguments = new Object[]{ t }; 
									m.invoke(returnObject,arguments);
								}
							
							} else {
								log.error("could not find a setter-method from Structured table. Is there a setter-method for " + fieldName + " in Class " + targetClass.getName());
							}
						} else {
							log.error("could not find a method from Structured table. Is there a method for " + fieldName + " in Class " + targetClass.getName());
						}
						
					} else if (Modifier.isPublic(mod) && !Modifier.isFinal(mod)){
						if (t!=null && !anyField.getType().getName().equals(t.getClass().getName())){
							for (Constructor crt : anyField.getType().getConstructors()) {
								if (crt.getParameterTypes()[0].getName().equals("java.lang.String")){
									t = crt.newInstance(t.toString());
								}
							}

							//Is public attribute so set it directly
							anyField.set(returnObject, t);
						}
						
					} else if (Modifier.isFinal(mod)) {
						log.error("Final attributes cannot be changed ");
					} else {
						log.error("Unhandled Modifier Type: " + mod);
					}
					
				} else {
					
					//This will cast nested Object to the current Object
					//it does not matter how deep it is nested
					Object valueOfHashMap = values.get(fieldName);
					if (valueOfHashMap!=null){
						String valueTypeOfHashMap = valueOfHashMap.getClass().getName();
						if (this.compareTypeNameToAllowedListTypes(valueTypeOfHashMap)) {
							
							//Get value from  set 
							Object t = this.castByGivenObject((LinkedHashMap)valueOfHashMap, fieldType);
							int mod = anyField.getModifiers();
							
							if (Modifier.isPrivate(mod) && !Modifier.isFinal(mod)){
								
								//log.info("is private so get setter method "+fieldName);
								LinkedHashMap<String,Object> methodSummery = structuredMethodMap.get(fieldName);
								
								if (methodSummery!=null) {
									if (methodSummery.get("setter")!=null) {
			
										String methodSetterName = methodSummery.get("setter").toString();
										Class[] paramTypes = (Class[]) methodSummery.get("setterParamTypes");
										Method m = targetClass.getMethod(methodSetterName, paramTypes);
										
										Class paramType = paramTypes[0];
										//log.error("paramType: "+paramType.getName());
										if (paramType.isPrimitive() && t==null){
											//cannot cast null to primitve
										} else {
											Object[] arguments = new Object[]{ t }; 
											m.invoke(returnObject,arguments);
										}
									
									} else {
										log.error("could not find a setter-method from Structured table. Is there a setter-method for " + fieldName + " in Class " + targetClass.getName());
									}
								} else {
									log.error("could not find a method from Structured table. Is there a method for " + fieldName + " in Class " + targetClass.getName());
								}
							} else if (Modifier.isPublic(mod) && !Modifier.isFinal(mod)){
								
								//Is public attribute so set it directly
								anyField.set(returnObject, t);
								
							} else if (Modifier.isFinal(mod)) {
								log.error("Final attributes cannot be changed ");
							} else {
								log.error("Unhandled Modifier Type: " + mod);
							}
							
						}
					} else {
						//There is no nested Object for that given
						log.error("There is no nested Object for that given: Attribute: " + fieldName + " Class " + targetClass.getName());
					}
				}
			} 

			return returnObject;
		} catch (Exception ex) {
			log.error("[castByGivenObject]: " ,ex);
		}
		return null;
	}
	
	private boolean compareTypeNameToBasicTypes(String fieldTypeName) {
		try {
			
			for (Iterator it = CastBasicTypes.getCompareTypesSimple().iterator();it.hasNext();) {
				if (fieldTypeName.equals(it.next())) return true;
			}
			
			return false;
		} catch (Exception ex) {
			log.error("[compareTypeNameToBasicTypes]",ex);
			return false;
		}
	}
	
	private boolean compareTypeNameToAllowedListTypes(String fieldTypeName) {
		try {
			//log.error("compareTypeNameToAllowedListTypes"+ fieldTypeName);
			for (Iterator it = CastBasicTypes.getAllowedListTypes().iterator();it.hasNext();) {
				if (fieldTypeName.equals(it.next())) return true;
			}
			
			return false;
		} catch (Exception ex) {
			log.error("[compareTypeNameToBasicTypes]",ex);
			return false;
		}
	}
	
}
