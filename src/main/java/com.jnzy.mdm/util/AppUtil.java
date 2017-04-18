package com.jnzy.mdm.util;

import com.google.common.collect.Maps;
import com.jnzy.mdm.constant.AppConstants;
import com.jnzy.mdm.mail.MailSenderInfo;
import com.jnzy.mdm.mail.SimpleMailSender;
import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by yyj on 15/12/21.
 */
public enum AppUtil {
    INSTANCE;
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    private static final int DEF_DIV_SCALE_2 = 2;
    private JnzyLogger logger = JnzyLogger.getLogger(AppUtil.class.getSimpleName());

    /**
     * <td><code>"yyyy-MM-dd' T 'HH:mm:ss.SSSZ"</code>
     * <td><code>2001-07-04 T 12:08:56.235-0700</code>
     *
     * @return
     */
    public String currentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取count个随机数
     *
     * @param count 随机数个数
     * @return
     */
    public static String getRandom(int count) {
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        try {
//            String[] keys = {"你好"};
//            String[] values = {"hahah"};
//            String[] keys = {"method", "username", "password", "mobile", "content",};// 参数名
//            String[] values = {"pasdMT", "pasd", "b1346697bebb72f5", "15863298561", "测试"};// 参数值
//            System.out.print(AppUtil.INSTANCE.getResponseFromUrl("http://jnsms.cxql.net//ZYHttp/SendSMS", keys, values));
//            AppUtil.INSTANCE.sendHtmlEmail("yangyujing@swimi.net", "哈哈哈", "蓉蓉拿到发发牢骚就");
//            System.out.print(AppUtil.INSTANCE.encodeResponseData(false, "T10003", "对不起,您所用的版本需要更新才能继续使用!", "Sdw8ream0XQ=", "12345678sdjnzyxxkjxx2015"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取外部url
     *
     * @param baseUrl
     * @param keyAndValue
     * @return
     * @throws Exception
     */
    public String getResponseFromUrl(String baseUrl, Map keyAndValue) throws Exception {
        Connection connection = Jsoup.connect(baseUrl);
        connection.timeout(180 * 1000);//180s
        connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.ignoreContentType(true);
        connection.method(Connection.Method.POST);
        connection.postDataCharset(AppConstants.UTF_8);
        if (null != keyAndValue) {
            connection.data(keyAndValue);
        }
        Connection.Response response = connection.execute();
        if (response.statusCode() == HttpStatus.SC_OK) {
            String restultStr=response.body();
            System.out.println("-------------第三方接口返回值----------------"+restultStr);
            return restultStr;
        }
        return null;
    }

    /**
     * 根据外部url，参数和值获取返回值
     */
    public static String getResponseFromUrl(String baseUrl, String[] keys,
                                            String[] values) throws Exception {
        Connection connection = Jsoup.connect(baseUrl);
        connection.timeout(180 * 1000);//180s
        connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.ignoreContentType(true);
        connection.method(Connection.Method.POST);
        connection.postDataCharset(AppConstants.GB2312);

        Map<String, String> paramMap = Maps.newHashMap();
        if (null != keys && keys.length > 0) {
            for (int i = 0; i < keys.length; i++) {
                if(values[i]==null){
                    values[i]="";
                }
                paramMap.put(keys[i], values[i]);
            }
            connection.data(paramMap);
        }
        Connection.Response response = connection.execute();
        if (response.statusCode() == HttpStatus.SC_OK) {
            return response.body();
        }
        return null;
    }

    /**
     * 根据外部url，参数和值获取返回值
     */
    public String getResponseFromUrlGet(String baseUrl, String[] keys,
                                        String[] values) throws Exception {
        Connection connection = Jsoup.connect(baseUrl);
        connection.timeout(180 * 1000);//180s
        connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> paramMap = Maps.newHashMap();
        if (null != keys && keys.length > 0) {
            for (int i = 0; i < keys.length; i++) {
                paramMap.put(keys[i], values[i]);
            }
        }
        Connection.Response response = connection.ignoreContentType(true).method(Connection.Method.GET)
                .data(paramMap).execute();
        if (response.statusCode() == HttpStatus.SC_OK) {
            return response.body();
        }
        return null;
    }

    /**
     * 根据外部url跟相关值，get请求
     * http://121.42.146.224:20052/companyopencustomer/partner/shandongpingan/order/参数
     */
    public String getResponseUrlValue(String baseUrl, String value) throws Exception {
        Connection connection = Jsoup.connect(baseUrl + "/" + value);
        connection.timeout(180 * 1000);//180s
        connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        try {
            Connection.Response response = connection.ignoreContentType(true).method(Connection.Method.GET).execute();
            if (response.statusCode() == HttpStatus.SC_OK) {
                return response.body();
            }
            return null;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 根据外部url，参数和值获取返回值
     */
    public String getResponseFromUrl2(String baseUrl, String[] keys,
                                        String[] values) throws Exception {
        InputStream is = getResFromUrl(baseUrl, keys, values);
        BufferedReader br = new BufferedReader(new InputStreamReader(is,
                "UTF-8"));
        String line;
        StringBuffer result = new StringBuffer();
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        br.close();
        is.close();
        return result.toString();
    }

    /**
     * 获取流
     *
     * @param baseUrl
     * @param keys
     * @param values
     * @return
     * @throws Exception
     */
    public InputStream getResFromUrl(String baseUrl, String[] keys,
                                     String[] values) throws Exception {
        String url = baseUrl;
        if (null != keys && keys.length > 0) {
            for (int i = 0; i < keys.length; i++) {
                url += (i == 0 && baseUrl.indexOf("?") < 0 ? "?" : "&");
                url += keys[i] + "=" + URLEncoder.encode(values[i], "UTF-8");
                System.out.println(keys[i] + ":" + values[i]);
            }
        }
        URL uRL = new URL(url);
        InputStream is = uRL.openStream();
        return is;
    }

    /**
     * 下载远程文件并保存到本地
     *
     * @param remoteFileUri 远程文件路径
     * @param localFile     本地文件路径
     */
    public void downloadFile(String remoteFileUri, File localFile) {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            urlfile = new URL(remoteFileUri);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(localFile));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送HTML的邮件的方法
     *
     * @param toAddress
     * @param subject
     * @param content
     * @return
     */
    public boolean sendHtmlEmail(String toAddress, String subject, String content) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.exmail.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("jifenbao@swimi.net");// 设置发送邮箱的用户名
        mailInfo.setPassword("jfb20140");// 您的邮箱密码
        mailInfo.setFromAddress("jifenbao@swimi.net"); // 发送的邮箱地址
        mailInfo.setToAddress(toAddress); // 接收的邮箱地址
        mailInfo.setSubject(subject); // 邮件标题
        mailInfo.setContent(content); // 邮件内容
        // 这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        return sms.sendHtmlMail(mailInfo);// 发送html格式
//        return true;
    }

    /**
     * 发送HTML的奔溃日志,邮件的方法
     *
     * @param toAddress
     * @param subject
     * @param content
     * @return
     */
    public boolean sendCrashEmail(String toAddress, String subject, String content) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.exmail.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("jifenbao@swimi.net");// 设置发送邮箱的用户名
        mailInfo.setPassword("jfb20140");// 您的邮箱密码
        mailInfo.setFromAddress("jifenbao@swimi.net"); // 发送的邮箱地址
        mailInfo.setToAddress(toAddress); // 接收的邮箱地址
        mailInfo.setSubject(subject); // 邮件标题
        mailInfo.setContent(content); // 邮件内容
        // 这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        return sms.sendHtmlMail(mailInfo);// 发送html格式
    }

    /**
     * 获取本机的ip
     *
     * @return
     * @throws SocketException
     */
    public String getLocalIp() {
        String localIp = null;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            localIp = inetAddress.getHostAddress() + " [" + inetAddress.getHostName() + "] ";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return localIp;
    }
    /**
     * 获取当前年月份
     *格式201611
     * @return
     * @throws SocketException
     */
    public static String getYearMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        return df.format(new Date());
    }
    /**
     * 获取1-1000中的随机数
     * @return
     */
    public static int getRandomNum(){
        return (int)(Math.random()*1000);
    }
}
