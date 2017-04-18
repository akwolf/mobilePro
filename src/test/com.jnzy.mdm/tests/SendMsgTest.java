package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SendMsgTest extends HttpClientTest {
	/**
	 * 发送短信 后台调用
	 * 
	 * @throws Exception
	 */
	@Test
	public void insertSendMsg() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/sendMsg/insertSendMsg";//13256738589  18353182181  18866812719
		data.put("type","28");
		data.put("userId","4");
		data.put("pushId","8");
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
