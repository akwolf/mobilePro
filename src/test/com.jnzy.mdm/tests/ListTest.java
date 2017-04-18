package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.junit.Test;

public class ListTest extends HttpClientTest {
	/**
	 * 手机黑名单获取
	 * 
	 * @throws Exception
	 */
	@Test
	public void selListMobile() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/list/selListMobile";//13256738589  18353182181
//		data.put("addTime","2016-12-22 13:54:18");
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
}
