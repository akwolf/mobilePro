package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.junit.Test;

public class SendsiWordsTest extends HttpClientTest {
	/**
	 * 敏感词，通讯敏感词的获取
	 * 
	 * @throws Exception
	 */
	@Test
	public void sensiWordsSms() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/sensiWords/sensiWordsSms";//18353182181
//		data.put("addTime","2016-05-30 11:00:34");
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
	 * 敏感词，上网敏感词的获取
	 *
	 * @throws Exception
	 */
	@Test
	public void sensiWordsWeb() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/sensiWords/sensiWordsWeb";//18353182181
		data.put("addTime","2016-05-30 11:00:34");
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
	 * 敏感词，上网敏感词的获取
	 *
	 * @throws Exception
	 */
	@Test
	public void sensiWordsWebPage() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/sensiWords/sensiWordsWebPage";//18353182181
//		data.put("startIndex","0");
//		data.put("pageSize","200");
//		data.put("addTime","2016-05-30 11:00:34");
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
}
