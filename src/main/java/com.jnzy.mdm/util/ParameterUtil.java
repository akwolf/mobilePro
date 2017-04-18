package com.jnzy.mdm.util;

import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用来处理Request参数的工具类
 *
 * @author willis
 */
public class ParameterUtil {
    private static JnzyLogger logger = JnzyLogger.getLogger(ParameterUtil.class);

    public static void setCharacterEncoding(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
    }

    public static String getString(HttpServletRequest request, String paramName) throws UnsupportedEncodingException {
        String value = request.getParameter(paramName);
        return value == null ? "" : value;
    }

    public static String[] getArray(HttpServletRequest request, String paramName) throws UnsupportedEncodingException {
        return request.getParameterValues(paramName);
    }


    public static byte getByte(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value == null || value.length() == 0)
            return 0;
        return Byte.parseByte(value);
    }


    public static int getInt(HttpServletRequest request, String paramName) {
        return getInt(request, paramName, 0);
    }

    /**
     * 分页处理时候用到（Action类），所以重构了上面的getInt方法
     *
     * @param request
     * @param paramName
     * @param defaultValue
     * @return
     */
    public static int getInt(HttpServletRequest request, String paramName, int defaultValue) {
        String value = request.getParameter(paramName);
        if (value == null || value.length() == 0)
            return defaultValue;
        return Integer.parseInt(value);
    }


    public static long getLong(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value == null || value.length() == 0)
            return 0;
        return Long.parseLong(value);
    }


    public static short getShort(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value == null || value.length() == 0)
            return 0;
        return Short.parseShort(value);
    }


    public static boolean getBoolean(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value == null || value.length() == 0)
            return false;
        return Boolean.valueOf(value).booleanValue();
    }

    /**
     * 从HttpServletRequest的头里获取did，拼接加密尾部，得到加密的key。
     *
     * @param request
     * @return
     */
    public static String getCipherKey(HttpServletRequest request) {
        return request.getHeader(HttpConstants._headerKey).substring(0, 8) + HttpConstants._key_end;
    }


    /**
     * did，拼接加密尾部，得到加密的key。
     *
     * @param did
     * @return
     */
    public static String getCipherKey(String did) {
        return did.substring(0, 8) + HttpConstants._key_end;
    }


    /**
     * 获取解密参数
     *
     * @param clazz
     * @return
     */
    public static <T> T getDecodeParamsObject(HttpServletRequest request, Class<T> clazz) {
        String cipherKey = (String) request.getAttribute(HttpConstants._cipher_key);
        String params = request.getParameter(HttpConstants._params);
        String decodeParams = null;
        try {
            decodeParams = Des3Util.decode(params, cipherKey);
        } catch (Exception e) {
            logger.error("Des3Util.decode error!!!");
            e.printStackTrace();
        }
        if (decodeParams == null || StringUtils.isBlank(decodeParams.toString())) {
            return null;
        }
        return ObjectJsonSerializer.deSerialize(clazz, decodeParams.toString());
    }

    /**
     * 这个只支持参数是params.
     *
     * @param request
     * @return
     */
    public static Map getParamsMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name;
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        if (returnMap.containsKey(HttpConstants._params)) {
            return ObjectJsonSerializer.deSerialize((returnMap.get(HttpConstants._params) + ""), String.class, String.class);
        } else {
            return returnMap;
        }
    }

    /**
     * 获取解密参数里的键值对。
     *
     * @return
     */
    public static <K, V> Map<K, V> getDecodeParamsMapByRequest(HttpServletRequest request) {
        String cipherKey = (String) request.getAttribute(HttpConstants._cipher_key);
        String params = request.getParameter(HttpConstants._params);
        String decodeParams = null;
        try {
            decodeParams = Des3Util.decode(params, cipherKey);
        } catch (Exception e) {
            logger.error("Des3Util.decode error!!!");
            e.printStackTrace();
        }
        if (decodeParams == null || StringUtils.isBlank(decodeParams.toString())) {
            return null;
        }
        JSONObject jsonObject=JSONObject.fromObject(decodeParams);
        Map<String, Object> map = new HashMap<String, Object>();
        for(Iterator keys=jsonObject.keys();keys.hasNext();){
            String key= (String) keys.next();
            map.put(key,jsonObject.get(key));
        }
        if (map.containsKey("startIndex") && map.containsKey("pageSize")) {
            int startIndex = Integer.parseInt(map.get("startIndex") + "");
            int pageSize = Integer.parseInt(map.get("pageSize") + "");
            map.put("startIndex", startIndex * pageSize);
            map.put("pageSize", pageSize);
        } else {
            map.put("startIndex", 0);
            map.put("pageSize", 10);
        }
        return (Map<K, V>) map;
    }

    /**
     * 获取解密参数里的键值对。
     *
     * @param request
     * @return
     */
    public static <K, V> Map<K, V> getDecodeParamsMapByJSON(HttpServletRequest request) {
        String cipherKey = (String) request.getAttribute(HttpConstants._cipher_key);
        String params = request.getParameter(HttpConstants._params);
        String decodeParams = null;
        try {
            decodeParams = Des3Util.decode(params, cipherKey);
        } catch (Exception e) {
            logger.error("Des3Util.decode error!!!");
            e.printStackTrace();
        }
        if (decodeParams == null || StringUtils.isBlank(decodeParams.toString())) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject jsonObject= JSONObject.fromObject(decodeParams);
        Iterator it=jsonObject.keys();
        while(it.hasNext()){
            String keyIEMI=String.valueOf(it.next());
            String valueIEMI=jsonObject.getString(keyIEMI);
            map.put(keyIEMI, valueIEMI);
        }
        if (map.containsKey("startIndex") && map.containsKey("pageSize")) {
            int startIndex = Integer.parseInt(map.get("startIndex") + "");
            int pageSize = Integer.parseInt(map.get("pageSize") + "");
            map.put("startIndex", startIndex * pageSize);
            map.put("pageSize", pageSize);
        } else {
            map.put("startIndex", 0);
            map.put("pageSize", 10);
        }
        return (Map<K, V>) map;
    }
    /**
     * 获取解密参数里的键值对。
     *
     * @param key
     * @param value
     * @return
     */
    public static <K, V> Map<K, V> getDecodeParamsMap(HttpServletRequest request, Class<K> key, Class<V> value) {
        String cipherKey = (String) request.getAttribute(HttpConstants._cipher_key);
        String params = request.getParameter(HttpConstants._params);
        String decodeParams = null;
        try {
            decodeParams = Des3Util.decode(params, cipherKey);
        } catch (Exception e) {
            logger.error("Des3Util.decode error!!!");
            e.printStackTrace();
        }
        if (decodeParams == null || StringUtils.isBlank(decodeParams.toString())) {
            return null;
        }
        System.out.println("---------解密参数后------"+decodeParams);
        Map<String, Object> map = (Map<String, Object>) ObjectJsonSerializer.deSerialize(decodeParams.toString(), key, value);
        if (map.containsKey("startIndex") && map.containsKey("pageSize")) {
            int startIndex = Integer.parseInt(map.get("startIndex") + "");
            int pageSize = Integer.parseInt(map.get("pageSize") + "");
            map.put("startIndex", startIndex * pageSize);
            map.put("pageSize", pageSize);
        } else {
            map.put("startIndex", 0);
            map.put("pageSize", 100);
        }
        Set<String> mapkey = map.keySet();
        for (Iterator it = mapkey.iterator(); it.hasNext();) {
            String s = (String) it.next();
            System.out.print("***********key:"+s+"**value:"+map.get(s));
        }
        return (Map<K, V>) map;
    }

    /**
     * php用的，不加密
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map getDecodeParamsMapPhp(HttpServletRequest request) throws Exception {

        Map paramMap = request.getParameterMap();

        String uri = request.getRequestURI();

        String ip = request.getRemoteAddr();
//	  	System.out.println("客户端传递的参数:"+paramMap);

//	  	获得的客户端传过来的参数
        HashMap stringMap = new HashMap();
        for (Iterator it = paramMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry elem = (Map.Entry) it.next();
            Object key = elem.getKey();
            String[] valueStr = (String[]) elem.getValue();
//	    	System.out.println("客户端传过来的参数@@"+key+"@@"+valueStr[0]);
            stringMap.put(key, valueStr[0]);
        }
        stringMap.put("ip", ip);
        System.out.println("没加密URI_IP :" + uri + "【 " + ip + " 】\nParams :" + stringMap);
        return stringMap;

    }

    /**
     * 从request中获取到解密的参数，并封装分页
     */
    public static<K,V> Map<K,V> getMapParamsByRequest(HttpServletRequest request){
        Map<String, Object> map = (Map<String, Object>) request.getAttribute(HttpConstants._map_params);
        if (map.containsKey("startIndex") && map.containsKey("pageSize")) {
            int startIndex = Integer.parseInt(map.get("startIndex") + "");
            int pageSize = Integer.parseInt(map.get("pageSize") + "");
            map.put("startIndex", startIndex * pageSize);
            map.put("pageSize", pageSize);
        } else {
            map.put("startIndex", 0);
            map.put("pageSize", 100);
        }
        Set<String> mapkey = map.keySet();
        for (Iterator it = mapkey.iterator(); it.hasNext();) {
            String s = (String) it.next();
            System.out.print("***********key:"+s+"**value:"+map.get(s));
        }
        return (Map<K, V>) map;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public static String uploadFile(HttpServletRequest request) throws Exception {
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断是否有
        //判断request是否有文件上传,即多部分请求.
        if (multipartResolver.isMultipart(request)) {
            String filePath = null;
            //转化成多部分request.
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //取得request中得所有文件名.
            Iterator<String> iter = multipartHttpServletRequest.getFileNames();
            while (iter.hasNext()) {
                //取得当前上传文件的文件名称.
                MultipartFile file = multipartHttpServletRequest.getFile(iter.next());
                if (file != null) {
                    //获取当前上传文件的文件名称.
                    String fileName = file.getOriginalFilename();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    String yearMonth = sdf.format(new Date());
                    String relativePath = DocumentProUtil.getValues("qr.code.path") + File.separator + yearMonth;
                    File fileRoot = new File(BaseService.BASE_PATH + relativePath);
                    if (!fileRoot.exists()) {
                        fileRoot.mkdirs();
                    }
                    // 新文件名.
                    filePath = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_" + fileName;
                    // 创建文件.
                    File newFile = new File(fileRoot.getAbsolutePath(), filePath);
                    // 保存文件.
                    file.transferTo(newFile);
                }
            }
            return filePath;
        } else {
            return null;
        }

    }
}