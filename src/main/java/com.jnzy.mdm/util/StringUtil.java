package com.jnzy.mdm.util;

import com.jnzy.mdm.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StringUtil {
    private static Logger log = Logger.getLogger(StringUtil.class);

    /**
     * isBlank
     *
     * @param param
     * @return
     */
    public static boolean isBlank(String... param) {
        for (String p : param) {
            if (null == p || StringUtils.isBlank(p) || "null".equals(p) || "".equals(p.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * isNotBlank
     *
     * @param param
     * @return
     */
    public static boolean isNotBlank(String... param) {
        return !isBlank(param);
    }

    public static boolean isNumber(String str) {
        if (str == null || str.trim().length() == 0)
            return false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }

        return true;
    }


    /**
     * trim方法的封装
     *
     * @param str
     * @return
     */
    public static String trimString(String str) {
        String strTmp;
        if (str == null || "".equals(str))
            strTmp = "";
        else
            strTmp = str.trim();

        return strTmp;
    }

    /**
     * 把null转换为空
     *
     * @param str
     * @param nvlStr
     * @return
     */
    public static String nvl(String str, String nvlStr) {
        if (str == null || "".equals(str) || "null".equals(str))
            return nvlStr;

        return str;
    }

    public static String nvl(Object obj, String value) {
        if (obj == null)
            return value;

        if (obj instanceof String)
            return obj.toString();
        else if (obj instanceof Integer)
            return obj + "";
        else
            return value;
    }

    public static boolean isNull(String str) {
        //有改动
        if (str == null || ("".equals(str)) || "undefined".equals(str) || "null".equals(str))
            return true;
        return false;
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * true的时候需要转化，false的时候不需要转化
     */
    public static boolean isNotNullWithHttpAndHttps(String str) {
        return !(str == null || ("".equals(str)) || "undefined".equals(str) || "null".equals(str) || (str.contains("http://")) || (str.contains("https://")));
        //return !isNull(str);
    }

    /**
     * 处理图片路径
     *
     * @param img
     * @return
     */
    public static String addHttp2UriStart(String img) {
        if (null == img||"null"==img || img.contains("http://") || img.contains("https://") || isBlank(img) || "undefined".equals(img)) {
            return "";
        } else {
            return BaseService.BASE_URL + File.separator + img;
        }
    }

    public static boolean objIsNull(Object obj) {
        if (obj == null)
            return true;

        //log.debug("#########["+obj.toString()+"]");
        if (isNull(obj.toString().trim()))
            return true;

        return false;
    }

    public static boolean objIsNotNull(Object obj) {
        return !objIsNull(obj);
    }


    /**
     * 数字转换为字符串
     *
     * @param num 要转换的数字
     * @param len 转换位数
     * @return
     */
    public static String num2Str(int num, int len) {
        String zeroStr = "";
        for (int i = 0; i < (len - String.valueOf(num).length()); i++)
            zeroStr += "0";

        return zeroStr + num;
    }

    /**
     * 把数组变为字符串，中间用给定的符号分开
     *
     * @param arr 数组
     * @param a   分开符
     * @return
     */
    public static String array2Str(String[] arr, String a) {
        if (arr == null)
            return "";

        String rs = "";
        for (int i = 0; i < arr.length; i++) {
            //log.debug("array2Str====="+arr[i]);
            if (i != 0)
                rs += a;

            rs = rs + arr[i];
        }

        //处理页面checkbox的值，最后一个值可能是个空值，所以多出一个分隔符 会出现 D,E,F,
        if (rs.length() > 0)
            if (a.equals(rs.substring(rs.length() - 1, rs.length())))
                rs = rs.substring(0, rs.length() - 1);


        return rs;
    }


    public static void main(String args[]) {
        //System.out.println("abcdefg".substring(3));
//		String a[]={"a","b","c","d"};
//		System.out.println(array2Str(a,","));
//		
//		String b = "3.0";
//		if(b.indexOf(".") != -1)
//			b = b.substring(0,b.indexOf("."));
//		System.out.println(decodeIP("001.034.3.40"));

//		String s = "804.8";
//		
//		System.out.println(subZeroAndDot(s));

    }

    /**
     * 判断某个字符串是否在数组中
     * @param arrayStr
     * @param str
     * @return
     */
    public static boolean contains(List<String> arrayStr, String str){
        for (int i=0;i<arrayStr.size();i++){
            if(arrayStr.get(i).equals(str)){
                return true;
            }
        }
        return false;
    }


}
