package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.ServiceProxyResponse;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OtherTest extends HttpClientTest {
	/**
	 * 从数据库中获取客户端升级地址
	 * 
	 * @throws Exception
	 */
	@Test
	public void selAppUploadUrl() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/selAppUploadUrl";//13256738589  18353182181
//		data.put("deviceId","868911020471214");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户在线状态
	 *
	 * @throws Exception
	 */
	@Test
	public void updateUserLastTime() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/updateUserLastTime";
		data.put("deviceId","860230030430089");
		data.put("imsi","460003122971474");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 终端管控中的锁屏
	 *
	 * @throws Exception
	 */
	@Test
	public void deviceLockscreen() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/deviceLockscreen";
//		data.put("deviceId","868111020058430");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 锁屏应用
	 *
	 * @throws Exception
	 */
	@Test
	public void lockscreenApp() throws Exception {
		ClientRequest request = new ClientRequest();
		String  url = appIp + "/other/lockscreenApp";
//		data.put("type","2");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 应用基础配置
	 *
	 * @throws Exception
	 */
	@Test
	public void appConfig() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/appConfig";
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 应用黑白名单
	 *
	 * @throws Exception
	 */
	@Test
	public void appListBlackWhite() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/appListBlackWhite";
		data.put("type","2");
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
	 *网址基础设置
	 *
	 * @throws Exception
	 */
	@Test
	public void urlConfig() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/urlConfig";
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 网址黑白名单
	 *
	 * @throws Exception
	 */
	@Test
	public void urlListBlackWhite() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/urlListBlackWhite";
		data.put("addTime","2016-09-05");
		data.put("type","1");
		data.put("startIndex","0");
		data.put("pageSize","10");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("结果..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 网址导航数据
	 *
	 * @throws Exception
	 */
	@Test
	public void websiteNavigation() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/websiteNavigation";
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("结果..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch blockdfg
			e.printStackTrace();
		}
	}
	/**
	 * 手机一键定位
	 *
	 * @throws Exception
	 */
	@Test
	public void insertMobileGps() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertMobileGps";
		data.put("deviceId","860230030430088");
		data.put("lng","116.231");
		data.put("lat","36.0121");
		data.put("address","山东济南");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("结果..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch blockdfg
			e.printStackTrace();
		}
	}
	/**
	 * 手机电子围栏
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRailIllegal() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertRailIllegal";
		data.put("lng","36.01");
		data.put("lat","116.231");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("结果..: " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch blockdfg
			e.printStackTrace();
		}
	}
	/**
	 * 手机基本信息
	 *
	 * @throws Exception
	 */
	@Test
	public void insertMobieUserInfo() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertMobieUserInfo";
		data.put("bootTime","2016-05-27 11:36:24");
		data.put("isRoot",1);
//		data.put("manufacturer","小米科技");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("结果..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch blockdfg
			e.printStackTrace();
		}
	}

	/**
	 * 网址黑白名单
	 *
	 * @throws Exception
	 */
	@Test
	public void urlListBlackWhitePage() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/urlListBlackWhitePage";
		data.put("addTime","2016-09-05");
		data.put("type","1");
		data.put("startIndex","0");
		data.put("pageSize","10");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("结果..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * db文件Url
	 *
	 * @throws Exception
	 */
	@Test
	public void DbFileUrls() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/DbFileUrls";
		data.put("addTime","2016-08-01");
		data.put("deviceId","866542020731539");
		data.put("type","0");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("结果..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *记录用户动态ip日志
	 *
	 * @throws Exception
	 */
	@Test
	public void insertUserIpLog() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertUserIpLog";
		data.put("appLocalIp","127.0.0.1");
		data.put("beginTime","2016-07-14 20:30:36");

		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *上传目录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertAppCatalog() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertAppCatalog";


		data.put("catalogJson","[\n" +
				"  {\n" +
				"    \"catalogName\": \"f1\",\n" +
				"    \"catalogFile\": \"/storage/emulated/0/Music\",\n" +
				"    \"catalogType\": \"0\",\n" +
				"    \"catalogSize\": \"0\"\n" +
				"  },\n" +
				"  {\n" +
				"    \"catalogName\": \"f2\",\n" +
				"    \"catalogFile\": \"/storage/sdcard1/f2\",\n" +
				"    \"catalogType\": \"0\",\n" +
				"    \"catalogSize\": \"100\"\n" +
				"  }\n" +
				"]");

		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



////		String path="/storage/sdcard1/DCIM/手机.doc";
////		String path="/storage/sdcard0/DCIM/手机.doc";
//		String path="/storage/emulated/0/Music";
//		int level=0;
//		System.out.println("@@@@@@@@@@@@@@@@"+path.split("/").length);
//		String[] pathArray = path.split("/");
//		for(int i=0;i<pathArray.length;i++){
//			System.out.println("**************"+pathArray[i]);
//		}
//		if((path.split("/")[3]+"").equals("0")){
//			level = path.split("/").length-4;
//		}else{
//			level = path.split("/").length-3;
//		}
//		System.out.println("@@@@@@@@@@@@@@"+level);

	}
	/**
	 *手机sd卡记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertSDcardLog() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertSDcardLog";
		data.put("isSdcard","1");
		data.put("sdcardTime","2016-07-14 20:30:36");

		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取手机当前权限
	 *
	 * @throws Exception
	 */
	@Test
	public void insertUserPermissions() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertUserPermissions";
		data.put("permissions","111,112,11");

		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取城市
	 *
	 * @throws Exception
	 */
	@Test
	public void selEmail() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/selEmail";
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取城市
	 *
	 * @throws Exception
	 */
	@Test
	public void insertPushRegistId() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/other/insertPushRegistId";
		data.put("registrationId","1104a89792a64f4fd96");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("sendCode..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public  void dateToStamp() {
		try {
			Document doc = Jsoup.connect("http://wthrcdn.etouch.cn/WeatherApi?citykey=101030100").get();
			Element respEle = doc.select("resp").get(0);
			String city = respEle.getElementsByTag("city").get(0).text();
			String temperature = respEle.getElementsByTag("wendu").get(0).text()+"℃";
			String pm25=respEle.getElementsByTag("environment").get(0).getElementsByTag("pm25").get(0).text();
			String quality = respEle.getElementsByTag("environment").get(0).getElementsByTag("quality").get(0).text();
			Element forecastEle = respEle.getElementsByTag("forecast").get(0);
			String highTemperature=forecastEle.getElementsByTag("weather").get(0).getElementsByTag("high").get(0).text();
			String lowTemperature=forecastEle.getElementsByTag("weather").get(0).getElementsByTag("low").get(0).text();
			Element weatherEle=forecastEle.getElementsByTag("weather").get(0).getElementsByTag("day").get(0);
			String weather = weatherEle.getElementsByTag("type").get(0).text();
			String wind=weatherEle.getElementsByTag("fengxiang").get(0).text()+weatherEle.getElementsByTag("fengli").get(0).text();

			if (highTemperature.length() >= 7) {
				highTemperature = highTemperature.substring(3, 7);
			} else if (highTemperature.length() >= 6) {
				highTemperature = highTemperature.substring(3, 6);
			} else {
				highTemperature = highTemperature.substring(3, 5);
			}
			if (lowTemperature.length() >= 7) {
				lowTemperature = lowTemperature.substring(3, 7);
			} else if (lowTemperature.length() >= 6) {
				lowTemperature = lowTemperature.substring(3, 6);
			} else {
				lowTemperature = lowTemperature.substring(3, 5);
			}
			System.out.println("-----"+city+"----"+temperature+"------"+highTemperature+"-------"+lowTemperature+"---"
			+"----"+pm25+"-----"+quality+"----"+weather+"---"+wind);

		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
