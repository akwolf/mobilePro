package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.junit.Test;

public class FileTest extends HttpClientTest {
	/**
	 * 文件上传
	 * 
	 * @throws Exception
	 */
	@Test
	public void insertFileRemote() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/file/insertFileRemote";//13256738589  18353182181
		data.put("deviceId","866542020731539");
		data.put("pushId","12");
		data.put("type","2");
		data.put("fileAppPath","D:\\1\\1.jpg");
//		data.put("fileType","1");
//		String[] filePath={"D:\\1\\1.jpg"};
		String[] filePath={"D:\\1\\1.jpg","D:\\1\\2.jpg"};

		request.setParams(data.toString());
		request.setHeaderKey("12345678");
		try {
			String result = doPostFile(request, url,filePath);
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
