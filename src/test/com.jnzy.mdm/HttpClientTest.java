package com.jnzy.mdm;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import com.jnzy.mdm.ClientRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.JnzyLogger;
import com.jnzy.mdm.util.cipher.Des3Util;
import com.jnzy.mdm.util.cipher.MD5;

public class HttpClientTest {

    protected static JSONObject data = new JSONObject();

    /**
     * 该静态块,放的是公共参数.只初始化一次.其他参数在方法里接着添加,data.put("password","123456")
     */
    static {
        data.put(HttpConstants._paramsCode, "17");
        data.put(HttpConstants._paramsName, "sdjnzymdm");
        data.put(HttpConstants._paramsPsw, "mdm2016Interface");
        data.put(HttpConstants._paramsTime, System.currentTimeMillis());
        data.put(HttpConstants._paramsPackage, "me.uniauto.mdm.market");
        data.put(HttpConstants._biaoshi, "1");
        data.put("deviceId", "868911020471214");
    }

    /**
     * 设置ip
     *
     * @param appIp_
     */
    protected void setAppIp(String appIp_) {
        appIp = appIp_;
    }
//
    private final JnzyLogger logger = JnzyLogger.getLogger(getClass());

//    protected static String appIp = "Http://localhost:8080/mdmInterfaceCommon";
    protected static String appIp = "http://192.168.80.39:8081/mdmInterfaceCommon";
//      protected static String appIp = "http://124.128.231.155:8081/mdmInterfaceCommon";
//    protected static String appIp = "Http://10.1.0.17:8083/mdmInterfaceCommon";
//protected static String appIp = "http://120.192.28.84:202/mdmInterfaceCommon";

    protected static CookieStore cookie;

    protected final String _session = "JSESESSION";

    private static DefaultHttpClient httpClient = new DefaultHttpClient(createHttpParams());

    private static HttpParams createHttpParams() {
        final HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
        HttpConnectionParams.setSoTimeout(params, 60 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192 * 5);
        return params;
    }

    protected String doPost(ClientRequest request, String url, boolean encode) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("headerk", request.getHeaderKey());
        if (null != cookie) {
            httpClient.setCookieStore(cookie);
        }
        String params = request.getParams();
         System.out.println("加密前params:——————————————————————  " + params);
        if (encode && StringUtils.isNoneBlank(params)) {
            params = Des3Util.encode(params, request.getHeaderKey().substring(0, 8) + HttpConstants._key_end);
            MD5 md5 = new MD5();
            httpPost.addHeader("headers", md5.GetMD5Code(params));
            System.out.println("headersign: " + md5.GetMD5Code(params));
        }
        System.out.println("加密后params:——————————————————————  " + params);
        List<NameValuePair> p = new ArrayList<NameValuePair>();
        p.add(new BasicNameValuePair("params", params));
        httpPost.setEntity(new UrlEncodedFormEntity(p, HTTP.UTF_8));

        HttpResponse response;
        String result = null;
        try {
            response = httpClient.execute(httpPost);
            System.out.println("response:" + response);
            if (null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            } else {
                result = null;
            }
            logger.debug("httpResult:" + result);
        } catch (ClientProtocolException e) {
            logger.error(e);
            Assert.fail();
        } catch (IOException e) {
            logger.error(e);
            Assert.fail();
        } finally {
        }
        return result;
    }

    protected String doPost(com.jnzy.mdm.ClientRequest request, String url) throws Exception {
        return doPost(request, url, true);
    }

    /**
     * 上传文件
     * <p/>
     * url请求路径
     * filePath文件绝对路径。
     *
     * @return
     * @throws Exception
     */
    protected String doPostFile( ClientRequest request,String url, String[] filePath) throws Exception {
        ArrayList<File> files;//所有的文件
        files = new ArrayList<File>();

        HttpPost httpPost = new HttpPost(url);
        if (null != cookie) {
            httpClient.setCookieStore(cookie);
        }
        if (filePath.length > 0 && filePath != null) {
            for (int i = 0; i < filePath.length; i++) {

                File file = new File(filePath[i]);
                if (null == file || file.length() == 0 || !file.exists()) {
                    Assert.fail("文件不存在");
                    System.out.println("文件不存在");
                } else {
                    files.add(file);
                }
            }
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        int count = 0;
        for (File file : files) {
            builder.addBinaryBody("file" + count, file);
            count++;
        }
//
        httpPost.addHeader("headerk", request.getHeaderKey());
        if (null != cookie) {
            httpClient.setCookieStore(cookie);
        }
        String params = request.getParams();
        if (StringUtils.isNoneBlank(params)) {
            params = Des3Util.encode(params, request.getHeaderKey().substring(0, 8) + HttpConstants._key_end);
            MD5 md5 = new MD5();
            httpPost.addHeader("headers", md5.GetMD5Code(params));
            System.out.println("sign: " + md5.GetMD5Code(params));
        }


        System.out.println("##########params:" + params);
        builder.addTextBody("params", params);

        HttpEntity entity = builder.build();//生成 HTTP POST 实体
        System.out.println("entity:" + entity);
        httpPost.setEntity(entity);

        HttpResponse response;
        String result = null;
        try {

            response = httpClient.execute(httpPost);
            System.out.println("response:" + response);
            if (null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            } else {
                result = null;
            }
            logger.debug("httpResult:" + result);
        } catch (ClientProtocolException e) {
            logger.error(e);
            Assert.fail();
        } catch (IOException e) {
            logger.error(e);
            Assert.fail();
        } finally {
        }
        return result;
    }


    protected String versionName = "1.0";

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
