package tests;

import com.jnzy.mdm.ClientRequest;
import com.jnzy.mdm.HttpClientTest;
import org.junit.Test;

/**
 * Created by hardy on 2017/2/20.
 */
public class BrowserTest extends HttpClientTest{
    /**
     * 获取城市
     *
     * @throws Exception
     */
    @Test
    public void selCityProvince() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/browser/selCityProvince";
//        data.put("citylevel","2");
//        data.put("adcode","110000");

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
     * 获取天气
     *
     * @throws Exception
     */
    @Test
    public void selCityWeather() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/browser/selCityWeather";
        data.put("tqMark","101010100");

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
     * 根据城市获取天气预报
     *
     * @throws Exception
     */
    @Test
    public void selCityWeatherByCity() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/browser/selCityWeatherByCity";
        data.put("cityName","济南");

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
     * 获取浏览器导航
     *
     * @throws Exception
     */
    @Test
    public void websiteNavigationHome() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/browser/websiteNavigationHome";
        data.put("more","0");

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
     * 以json形式上传书签记录
     *
     * @throws Exception
     */
    @Test
    public void insertBookmark() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/browser/insertBookmark";
        String bookMarkList="" +
                "[{" +
                "\"bookmarkName\": \"测试11\",\n" +
                "\"bookmarkUrl\": \"http://www.baidu.com\",\n" +
                "\"bookmarkIdent\": \"11\",\n" +
                "\"level\": \"0\"" +
                "}]" +
                "";
        data.put("bookMarkList",bookMarkList);

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
     * 删除书签记录
     *
     * @throws Exception
     */
    @Test
    public void delBookmark() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/browser/delBookmark";
        data.put("bookmarkIdent","1");

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
     * 查询书签记录（分页）
     *
     * @throws Exception
     */
    @Test
    public void selBookmark() throws Exception {
        ClientRequest request = new ClientRequest();
        String url = appIp + "/browser/selBookmark";
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
