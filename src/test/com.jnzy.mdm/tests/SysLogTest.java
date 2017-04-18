package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import org.junit.Test;

/**
 * Created by yxm on 2016/8/30.
 */
public class SysLogTest extends HttpClientTest
{
    @Test
    public void readLogs(){
        ClientRequest request = new ClientRequest();
        String url = appIp + "/syslog/readlogs";
        data.put("deviceId","868111020058430");
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
