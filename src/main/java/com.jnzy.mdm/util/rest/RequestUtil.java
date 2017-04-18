package com.jnzy.mdm.util.rest;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析request里的参数。
 * @author wangkuan
 *
 */
public class RequestUtil {
	/**
	 * 遍历header里的值。
	 * 
	 * @param req
	 * @return
	 */
	public static Map<String, String> headerMap(HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration headerNaames = req.getHeaderNames();
		while (headerNaames.hasMoreElements()) {
			String pName = (String) headerNaames.nextElement();
			String pValues = req.getHeader(pName);
			if (null != pValues && pValues.length() > 0) {
				map.put(pName, pValues);
			}
		}
		return map;
	}
	
	
	/**
	 * 获取request里的参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map paramsMap(HttpServletRequest request) {
		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
      
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}
}
