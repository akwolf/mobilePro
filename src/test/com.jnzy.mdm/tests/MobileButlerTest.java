package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yxm on 2016/9/9.
 */
public class MobileButlerTest extends HttpClientTest
{
    @Test
    public void virusScanning() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/virusScanning";
        data.put("md5","05b009f8e6c30a1bc0a0f793049960bc");
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

    @Test
    public void insertcontactsbackup() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/contactsbackup";//13256738589  18353182181
        data.put("deviceId","868111020060626");
        data.put("md5","1113");
        String[] filePath={"D:\\111\\contactBase64.txt"};
        request.setParams(data.toString());
        request.setHeaderKey("12345678");
        try {
            String result = doPostFile(request, url,filePath);
            System.out.println("sendCode..: " + result);
            /**
            JSONObject jsObject = JSONObject.fromObject(result);
            String dataObj = jsObject.get("data").toString();
            System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void selcontacts() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/selcontacts";
        Map map=new HashMap();
        map.put("deviceId","868111020060626");
        data.put("baseParam",map);
        data.put("deviceId","868111020060626");
        request.setParams(data.toString());
        request.setHeaderKey("12345678");
        try {
            String result = doPost(request, url);
            System.out.println("结果..: " + result);
            /**
            JSONObject jsObject = JSONObject.fromObject(result);
            String dataObj = jsObject.get("data").toString();
            System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
            */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void insertsmsbackup() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/smsbackup";//13256738589  18353182181
        data.put("deviceId","869540025588422");
        data.put("md5","222");
        String[] filePath={"D:\\data\\upload\\file\\sms\\SmsBase64.xml"};
        request.setParams(data.toString());
        request.setHeaderKey("12345678");
        try {
            String result = doPostFile(request, url,filePath);
            System.out.println("sendCode..: " + result);
            /**
             JSONObject jsObject = JSONObject.fromObject(result);
             String dataObj = jsObject.get("data").toString();
             System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void selsms() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/selsms";
        Map map=new HashMap();
        map.put("deviceId","868111020060626");

        data.put("baseParam",map);
        //httpPost.setHeader("Accept-Encoding", "identity");
        data.put("Accept-Encoding","identity");
        data.put("deviceId","868111020060626");
        request.setParams(data.toString());
        request.setHeaderKey("12345678");
        try {
            String result = doPost(request, url);
            System.out.println("结果..: " + result);
            /**
             JSONObject jsObject = JSONObject.fromObject(result);
             String dataObj = jsObject.get("data").toString();
             System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 上传通话记录
     * @throws Exception
     */
    @Test
    public void insertCall() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/insertCall";//13256738589  18353182181
        data.put("deviceId","869540025588422");
        data.put("md5","1112");
        String[] filePath={"D:\\data\\upload\\file\\sms\\CallHistoryBase64.txt"};
        request.setParams(data.toString());
        request.setHeaderKey("12345678");
        try {
            String result = doPostFile(request, url,filePath);
            System.out.println("sendCode..: " + result);
            /**
             JSONObject jsObject = JSONObject.fromObject(result);
             String dataObj = jsObject.get("data").toString();
             System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 上传应用列表记录
     * @throws Exception
     */
    @Test
    public void insertAppList() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/insertAppList";//13256738589  18353182181
        data.put("deviceId","868911020471214");
        String[] filePath={"D:\\data\\upload\\file\\sms\\appBase64.txt"};
        request.setParams(data.toString());
        request.setHeaderKey("12345678");
        try {
            String result = doPostFile(request, url,filePath);
            System.out.println("sendCode..: " + result);
            /**
             JSONObject jsObject = JSONObject.fromObject(result);
             String dataObj = jsObject.get("data").toString();
             System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 通讯录文件上传
     * @throws Exception
     */
    @Test
    public void contactFile() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/mobileButler/contactFile";//13256738589  18353182181
        data.put("deviceId","868111020060626");
        data.put("md5","10120");
        String[] filePath={"D:\\111\\contactBase64.txt"};
        request.setParams(data.toString());
        request.setHeaderKey("12345678");
        try {
            String result = doPostFile(request, url,filePath);
            System.out.println("sendCode..: " + result);
            /**
             JSONObject jsObject = JSONObject.fromObject(result);
             String dataObj = jsObject.get("data").toString();
             System.out.println(Des3Util.decode(dataObj, "12345678" + HttpConstants._key_end));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
