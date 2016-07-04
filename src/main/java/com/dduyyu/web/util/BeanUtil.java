package com.dduyyu.web.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;  
import java.util.Map; 

/**
 * 工具类
 * 
 */
public class BeanUtil{
	private static final Log logger=LogFactory.getLog(BeanUtil.class);
	
	public static Map<String, String> transBean2Map(Object obj,boolean isFilter) {
		if (obj == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					if(value == null){
						continue;
					}
					if(isFilter){
						if("sign".equals(key) || "sign_type".equals(key) || "sign_version".equals(key)){
							continue;
						}else{
							map.put(key, value.toString());
						}
					}else{
						map.put(key, value.toString());
					}
				}
			}
		} catch (Exception e) {
			logger.error("transBean2Map Error " + e);
		}
		return map;
	}
}