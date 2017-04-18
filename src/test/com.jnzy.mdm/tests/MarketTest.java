package com.jnzy.mdm.tests;

import com.jnzy.mdm.ClientRequest;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import net.sf.json.JSONArray;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.Test;

import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.cipher.Des3Util;

import net.sf.json.JSONObject;
import org.springframework.expression.spel.ast.StringLiteral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketTest extends HttpClientTest {
	/**
	 * 获取应用市场分类
	 * 
	 * @throws Exception
	 */
	@Test
	public void marketClass() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/marketClass";
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取分类下的列表
	 *
	 * @throws Exception
	 */
	@Test
	public void marketClassList() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/marketClassList";
		data.put("type","全部");
		data.put("categoryId","10");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取app详情
	 *
	 * @throws Exception
	 */
	@Test
	public void marketAppDetail() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/marketAppDetail";
		data.put("appId","1");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 搜索app名
	 *
	 * @throws Exception
	 */
	@Test
	public void searchMarketApp() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/searchMarketApp";
		data.put("softname","音乐");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 统计下载次数
	 *
	 * @throws Exception
	 */
	@Test
	public void updateAppDownNum() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/updateAppDownNum";
		data.put("appId","1");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("updateAppDownNum..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 可升级的app列表
	 *
	 * @throws Exception
	 */
	@Test
	public void updateAppList() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/updateAppList";

		String packageNameList="[\n" +
				"        {\n" +
				"            \"packName\": \"com.music.youzhiqingyinyue\",\n" +
				"            \"version\": \"1.1\"\n" +
				"        },\n" +
				"        {\n" +
				"            \"packName\": \"com.youku.phone\",\n" +
				"            \"version\": \"5.7\"\n" +
				"        },\n" +
				"        {\n" +
				"            \"packName\": \"air.tv.douyu.android\",\n" +
				"            \"version\": \"2.2.6\"\n" +
				"        }]";
		data.put("packageNameList",packageNameList);
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("updateAppList..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test(){
//		List<String> stringList=new ArrayList<String>();
//		stringList.add("测试1");
//		stringList.add("测试2");
//		stringList.add("测试3");
//		System.out.println(JSONArray.fromObject(stringList).toString());
//
//		String jsObj=JSONArray.fromObject(stringList).toString();
//
//		JSONArray jsArray=JSONArray.fromObject(jsObj);
//		List<String> strList=jsArray.toList(jsArray);
//		System.out.println(strList.get(0));

//		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("alli",new JSONArray());
//		System.out.println(jsonObject.toString());
//
//		String pushTag="【测试1,测试2】";
//		System.out.println("****标签************"+pushTag);
//		System.out.println("!!!!!"+pushTag.substring(1,pushTag.length()-1));
//		List<String> pushList= Arrays.asList(pushTag.substring(1,pushTag.length()-1));
//		System.out.println("@@@"+pushList.get(0));
		String str="{\"error\":{\"code\":7000, \"message\":\"server internal error, please try again later\"}}\n" +
				"***********200";
		if(str.contains("error")){
			System.out.println("@@@if");
		}else{
			System.out.println("@@@else");
		}

	}
	/**
	 *获取所有应用市场的包名
	 *
	 * @throws Exception
	 */
	@Test
	public void selMarketPackage() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/selMarketPackage";
		data.put("deviceId","860230030429304");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("updateAppList..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *应用市场意见反馈
	 *
	 * @throws Exception
	 */
	@Test
	public void insertMarketFeedback() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/market/insertMarketFeedback";
		data.put("deviceId","860230030320040");
		data.put("feedbackContent","反馈");
		data.put("feedbackContact","123456");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("updateAppList..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
