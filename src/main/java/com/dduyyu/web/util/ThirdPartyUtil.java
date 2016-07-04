package com.dduyyu.web.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ThirdPartyUtil {
	
	private static final Log logger = LogFactory.getLog(ThirdPartyUtil.class);
	
	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map getParameterMap(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map returnMap = new HashMap();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
	}
	
	/**
	 * 设置返回rescode、resmsg_cn
	 * 
	 * */
	public static String setResultStringCn(Map<String, Object> map, 
			String rescode,String resmsgCn) {
		map.put("rescode", rescode);
		map.put("resmsg_cn", resmsgCn);
		// 处理返回结果
		return getJsonStringFromMap(map);
	}
	
	public static String setResultString(JSONObject map, 
			String rescode,String resmsgCn) {
		map.put("rescode", rescode);
		map.put("resmsg_cn", resmsgCn);
		// 处理返回结果
		return map.toString();
	}
	public static String setResultStringCn1(Map<String, Object> map, 
			String msgid,String result,String desc) {
		map.put("msgid", msgid);
		map.put("result", result);
		map.put("desc", desc);
		// 处理返回结果
		return getJsonStringFromMap(map);
	}
	
    /** 
     * 从Map对象得到Json字串 
     *  
     * @param map 
     * @return 
     */  
    public static String getJsonStringFromMap(Map map) {  
    	return JSONObject.fromObject(map).toString();  
    }
    
    /**
	 * json返回客户端(不加密)
	 * @throws UnsupportedEncodingException 
	 **/
	public static void responseJsonNotEncrypt(Object result, HttpServletResponse response){
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		}catch(IOException e){
			logger.error("/ThirdPartyUtil/responseJsonNotEncrypt 异常:" + e.getMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		
		String a = String.valueOf(map.get("tradeOrderId"));
		System.out.println(a);
	}

	
	/**
	 * json返回客户端(不加密)
	 * @throws UnsupportedEncodingException 
	 **/
	public static void responseJsonNotEncryptTwo(Object result, HttpServletResponse response,PrintWriter out){
		
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8");
		try{
			out.write(new String(result.toString().getBytes("utf-8"),"ISO8859-1"));
			out.print(result);
			out.flush();
			out.close();
		}catch(Exception e){
			logger.error("/ThirdPartyUtil/responseJsonNotEncrypt 异常:" + e.getMessage());
		}
	}

}
