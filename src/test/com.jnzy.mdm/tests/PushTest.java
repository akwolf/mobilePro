package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.junit.Test;

public class PushTest extends HttpClientTest {

	/**
	 * 推送  废弃
	 *
	 * @throws Exception
	 */
	@Test
	public void pushMsg() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/push/pushMsg";
		data.put("deviceId","13212345678");
		data.put("type","3");
//		data.put("tagName","[\"测试1\",\"测试2\"]");
		data.put("pushmsg","{\n" +
				"    \"networkBean\": {\n" +
				"        \"status\": \"0\",\n" +
				"        \"week\": \"1,2\",\n" +
				"        \"startTime\": \"00:00\",\n" +
				"        \"endTime\": \"18:00\"\n" +
				"    },\n" +
				"    \"smsMsgBean\": {\n" +
				"        \"status\": \"1\"\n" +
				"    },\n" +
				"    \"telBean\": {\n" +
				"        \"status\": \"0\",\n" +
				"        \"week\": \"1,2\",\n" +
				"        \"startTime\": \"00:00\",\n" +
				"        \"endTime\": \"18:00\"\n" +
				"    },\n" +
				"    \"wifiBean\": {\n" +
				"        \"status\": \"0\",\n" +
				"        \"week\": \"1,2\",\n" +
				"        \"startTime\": \"00:00\",\n" +
				"        \"endTime\": \"18:00\"\n" +
				"    },\n" +
				"    \"bluetoothBean\": {\n" +
				"        \"status\": \"0\",\n" +
				"        \"week\": \"1,2\",\n" +
				"        \"startTime\": \"00:00\",\n" +
				"        \"endTime\": \"18:00\"\n" +
				"    },\n" +
				"    \"cameraBean\": {\n" +
				"        \"status\": \"0\",\n" +
				"        \"week\": \"1,2\",\n" +
				"        \"startTime\": \"00:00\",\n" +
				"        \"endTime\": \"18:00\"\n" +
				"    },\n" +
				"    \"gpsBean\": {\n" +
				"        \"status\": \"0\",\n" +
				"        \"week\": \"1,2\",\n" +
				"        \"startTime\": \"00:00\",\n" +
				"        \"endTime\": \"18:00\"\n" +
				"    },\n" +
				"    \"lockScreen\": {\n" +
				"        \"sunday\": {\n" +
				"            \"startTime\": \"00:00\",\n" +
				"            \"endTime\": \"18:00\"\n" +
				"        }\n" +
				"    }\n" +
				"}");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("insertDeviceInfo..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 推送 后台使用
	 *1,7,13,12,11,15,9,
	 * @throws Exception
	 */
	@Test
	public void pushMsgPhp() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/push/pushMsgPhp";
		data.put("pushId","8");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("pushMsgPhp..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 推送 客户端使用
	 *
	 * @throws Exception
	 */
	@Test
	public void pushMsgApp() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/push/pushMsgApp";
		data.put("deviceId","860230030430089");
		data.put("pushMsgTime","2016-01-29 11:36:24");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("pushMsgApp..: " + result);
			JSONObject jsObject = JSONObject.fromObject(result);
			String dataObj = jsObject.get("6data").toString();
			System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *手机推送成功、失败统计
	 *
	 * @throws Exception
	 */
	@Test
	public void pushSuccFailNum() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/push/pushSuccFailNum";
		data.put("deviceId","860806021850473");
		data.put("pushId","27");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("pushSuccFailNum..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 给用户添加标签
	 *
	 * @throws Exception
	 */
	@Test
	public void insertUserTag() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/push/insertUserTag";
		data.put("deviceId","868911020471214");
		data.put("registrationId","1104a89792a64f4fd96");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("insertDeviceInfo..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 推送 应用市场推送
	 *1,7,13,12,11,15,9,
	 * @throws Exception
	 */
	@Test
	public void pushMsgPhpSpare() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/push/pushMsgPhpSpare";
		data.put("pushId","20");
		data.put("biaoshi","1");
		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPost(request, url);
			System.out.println("insertDeviceInfo..: " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
