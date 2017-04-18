package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import org.junit.Test;

public class RecordTest extends HttpClientTest {
	/**
	 * 通话记录
	 * 
	 * @throws Exception
	 */
	@Test
	public void insertRecordCall() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordCall";//13256738589  18353182181
		data.put("contact","123");
		data.put("callNumber","123");
		data.put("callTime","2016-08-03 10:55:08");
		data.put("timeLength","0");
		data.put("type","1");

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
	 * 通话拦截记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordCallIntercept() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordCallIntercept";//13256738589  18353182181
		data.put("contact","123");
		data.put("callNumber","123");
		data.put("type","1");
		data.put("addTime","2016-05-27 11:36:24");
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
	 * 通讯录记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordIm() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordIm";//13256738589  18353182181
		data.put("contact","牛\uE04A");
		data.put("callNumber","123");
		data.put("addTime","2016-05-27 11:36:24");
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
	 * 短信记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordsms() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordsms";//13256738589  18353182181
		data.put("contact","123");
		data.put("number","123");
		data.put("content","哼哼唧唧叫姐姐斤斤计较咔咔咔咔咔咔咔\ue04a");
		data.put("type","1");
		data.put("addTime","2016-05-27 11:36:24");

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
	 * 短信敏感词拦截记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordsmsSensitive() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordsmsSensitive";//13256738589  18353182181
//		data.put("contact","123");
		data.put("number","12345678");
		data.put("source","微信");
		data.put("content","短信内容咔咔咔\uE04A");
		data.put("addTime","2016-05-27 11:36:24");
		data.put("sensKeywords","敏感词");
		data.put("type","1");

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
	 * 应用列表记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordAppList() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordAppList";//13256738589  18353182181
		data.put("softName","Atci_service");
		data.put("packageName","com.mediatek.atci.service");
		data.put("version","1");
		data.put("appMd5Sign","8df060cc7b74aeed4ec38c094446a7fb");
		data.put("versionCode","1.0");
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
	 * 应用使用列表记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordAppUseList() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordAppUseList";//13256738589  18353182181
		data.put("packageName","me.uniauto.mdm.demo");
//		data.put("useNum","4");
		data.put("timeLength","200");
		data.put("visitedTime","2016-05-27 11:36:24");
		data.put("softName","11");
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
	 * 客户端上网记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordWebsiteList() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordWebsiteList";//13256738589  18353182181
////		data.put("websiteName","测试应用1");
//		data.put("websiteType","2");
//		data.put("websiteUrl","http://www.baidu.com");
////		data.put("addTime","2016-05-27 11:36:24");

		String websiteList="[\n" +
				"        {\n" +
				"            \"websiteName\": \"http://www.baidu.com\",\n" +
				"            \"websiteUrl\": \"1\",\n" +
				"            \"websiteType\": \"2\",\n" +
				"            \"addTime\": \"2016-05-27 11:36:24\"\n" +
				"        }" +
				"]";

		data.put("websiteList",websiteList);

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
	 * 客户端上网敏感词记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertRecordWebsiteSensitive() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertRecordWebsiteSensitiveList";//13256738589  18353182181
//		data.put("keywords","测试");
////		data.put("websiteUrl","http://www.baidu.com");
//		data.put("deviceId","868111020058430");
		String websiteList="[\n" +
				"        {\n" +
				"            \"keywords\": \"123\",\n" +
				"            \"websiteUrl\": \"\",\n" +
				"            \"addTime\": \"2016-05-27 11:36:24\"\n" +
				"        }" +
				"]";

		data.put("webSensList",websiteList);

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
	 * 客户端上网敏感词记录
	 *
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		String str="123";
		System.out.println(str.equals("null"));
	}
	/**
	 *  sd卡违规记录
	 *
	 * @throws Exception
	 */
	@Test
	public void insertIllegalSdcard() throws Exception {
		ClientRequest request = new ClientRequest();
		String url = appIp + "/record/insertIllegalSdcard";//13256738589  18353182181

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
