package com.jnzy.mdm.comm.filter;

import com.jnzy.mdm.constant.AppConstants;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.*;
import com.jnzy.mdm.util.cipher.Des3Util;
import com.jnzy.mdm.util.cipher.MD5;
import com.jnzy.mdm.util.resp.ResponseWrapper;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

/**
 * 该类为参数验证过滤器。 1、取出header和parameter值。
 * 2、header里需要有JSESESSION、did、sign：JSESESSION是shiro生产的session
 * ，did是设备id，sign是参数MD5签名。
 * 3、把header里的did取出，和本地的_key_end拼起来作为加密的key值，放在request的attribute属性里
 * ，key为_cipher_key。 4、取出parameter里的params，进行MD5加密，和header里的sign作比对，一致，说明参数没问题。
 * 5、、、、、、、
 *
 * @author wangkuan
 */
public class CipherArgsCheckFilter implements Filter {
    private final JnzyLogger logger = JnzyLogger.getLogger(getClass());
    private final JnzyLogger infologger = JnzyLogger.getLogger("infolog");
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        req.setCharacterEncoding(AppConstants.UTF_8);
        String uri = req.getRequestURI();
        ResponseWrapper resp = new ResponseWrapper((HttpServletResponse) response);
        try{
            UUID uuid=UUID.randomUUID();
            req.setAttribute("uuid",uuid);
            //首先[文件上传类的url]： excludeUrls是不需要过滤的。不需要加密和解密的url。主要用于文件上传。因为没有did等参数，所以返回是明文的。
            for (String eUrl : HttpConstants._excludeUrls) {
                if (uri.endsWith(eUrl)) {
                    chain.doFilter(req, resp);
                    byte[] resultByte = resp.getResponseData();
                    resp.getResponse().getWriter().print(new String(resultByte, AppConstants.UTF_8));
                    System.out.println("filter...excludeUrl..{}   " + uri);
                    return;
                }
            }
            /**
             * 获取params
             * 跟sign比较
             * 判断公共参数
             * 将秘钥放入req中
             */
            Map<String,String> hMap= RequestUtil.headerMap(req);
            Map pMap=RequestUtil.paramsMap(req);
            String params= (String) pMap.get(HttpConstants._params);
            String headerSign=hMap.get(HttpConstants._headerSign);
            String headerKey=hMap.get(HttpConstants._headerKey);
            System.out.println("************************params:"+params+"&&&&&&&&&&&&&&headerSign:"+headerSign+"***************headerKey:"+headerKey);
            if(StringUtil.isBlank(params)||StringUtil.isBlank(headerSign)||StringUtil.isBlank(headerKey)){
                System.out.println("params跟签名不为null");
                resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                return;
            }
            //params跟sign比较
            MD5 md5=new MD5();
            String md5Params=md5.GetMD5Code(params);
            if(!headerSign.equalsIgnoreCase(md5Params)){
                resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                return;
            }
            //判断参数
            String cipherKey=headerKey.substring(0,8)+HttpConstants._key_end;
            String decodeParams = Des3Util.decode(params,cipherKey);
            //解密
            Map mapParams = (Map<String, Object>) ObjectJsonSerializer.deSerialize(decodeParams.toString(), String.class, Object.class);

            //看是否验证版本号、包名（后台过滤）
            boolean isCheckVersionCode=false;
            for(String eUrl:HttpConstants._not_version_excludeUrls){
                if (uri.contains(eUrl)){
                    isCheckVersionCode=true;
                }
            }
            if(!isCheckVersionCode){
                //检查是否含有版本号
                if (!mapParams.containsKey(HttpConstants._paramsCode)) {
                    System.out.println("paramsCode is null !!! ");
                    resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                    return;
                }
                //版本号
                String versionCode=mapParams.get(HttpConstants._paramsCode)+"";
                if(StringUtil.isBlank(versionCode)){
                    resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                    return;
                }
                //检查是否含有应用包名
                if (!mapParams.containsKey(HttpConstants._paramsPackage)) {
                    System.out.println("paramsPackage is null !!! ");
                    resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                    return;
                }
                //应用包名
                String paramsPackage=mapParams.get(HttpConstants._paramsPackage)+"";
                if(StringUtil.isBlank(paramsPackage)){
                    resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                    return;
                }
                //标识
                if(!mapParams.containsKey(HttpConstants._biaoshi)){
                    System.out.println("biaoshi is null!!!!!!!!!");
                    resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                    return;
                }
                String biaoshi=mapParams.get(HttpConstants._biaoshi)+"";
                if(StringUtil.isBlank(biaoshi)){
                    resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                    return;
                }
            }

            //检查是否含有用户名
            if (!mapParams.containsKey(HttpConstants._paramsName)) {
                System.out.println("appUsername is null !!! ");
                resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                return;
            }
            //用户名
            String appUsername=mapParams.get(HttpConstants._paramsName)+"";
            //检查是否含有密码
            if (!mapParams.containsKey(HttpConstants._paramsPsw)) {
                System.out.println("appPassword is null !!! ");
                resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                return;
            }
            //密码
            String appPassword=mapParams.get(HttpConstants._paramsPsw)+"";
            //检查是否含有时间戳
            if (!mapParams.containsKey(HttpConstants._paramsTime)) {
                System.out.println("timeStamp is null !!! ");
                resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                return;
            }
            //时间戳
            String timeStamp=mapParams.get(HttpConstants._paramsTime)+"";

            //判断参数
            if(StringUtil.isBlank(appUsername)||StringUtil.isBlank(appPassword)
                    ||StringUtil.isBlank(timeStamp)
                    ||!appUsername.equals(HttpConstants._paramsNameValue)
                    ||!appPassword.equals(HttpConstants._paramsPswValue)
                    ){
                resp.getResponse().getWriter().print(HttpConstants._args_error_json);
                return;
            }
//            //将参数放入
            req.setAttribute(HttpConstants._cipher_key,cipherKey);
            //将解密后的参数放入
            req.setAttribute(HttpConstants._map_params,mapParams);

            //到这所有参数判断完毕
            /**
             * 打印log日志
             */
            StringWriter stringWriter = new StringWriter();
            Map paramMap;
            if (StringUtil.isNotBlank(cipherKey)) {
                paramMap = ParameterUtil.getDecodeParamsMap(req, Object.class, Object.class);
            } else {
                paramMap = ParameterUtil.getParamsMap(req);
            }
            Map<String, String> heardMap = RequestUtil.headerMap(req);
            String localIp = AppUtil.INSTANCE.getLocalIp();
            StringBuffer contentinfo=new StringBuffer();
            contentinfo.append("["+localIp+"]");
            contentinfo.append("----");
            contentinfo.append("["+uuid+"]");
            contentinfo.append("----");
            contentinfo.append("["+paramMap.get("deviceId")+"]");
            infologger.info(contentinfo.toString()+"----[requesturi:"+uri+"]----");
            infologger.info(contentinfo.toString()+"----[headparams:"+heardMap.toString()+"]----");
            infologger.info(contentinfo.toString()+"----[contentparams:"+paramMap.toString().replaceAll("\\r|\\n","")+"]----");//去掉参数中的换行符
            chain.doFilter(req,resp);
            // 完成，封装加密参数放在response里。
            byte[] resultByte = resp.getResponseData();
            String result = new String(resultByte, AppConstants.UTF_8);
            System.out.println("Filter result origin..{}  " + uri + ":{" + result + "}");
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultObject = JSONObject.fromObject(result);
                if (resultObject.containsKey("data")) {
                    String data = resultObject.getString("data");
                    if (StringUtils.isNotEmpty(data) && !data.equalsIgnoreCase("null")) {
                        data = Des3Util.encode(data, cipherKey);
                        resultObject.put("data", data);
                    }
                }
                resp.setContentLength(-1);
                // 然后
                infologger.info(contentinfo.toString()+"----[@return]----");
                resp.getResponse().getWriter().print(resultObject.toString());
                return;
            } else {
                infologger.info(contentinfo.toString()+"----[@return]----");
                resp.getResponse().getWriter().print(HttpConstants._service_error_json);
                return;
            }

            /**
             * 记录响应时间
             */
            //infologger.info(contentinfo.toString()+"--[return]");
        }catch (Exception e){
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            logger.error("验证过滤器访问异常:"+stringWriter.getBuffer().toString());
            System.out.println("#############################catch");
            System.out.println(e);
            resp.getResponse().getWriter().print(HttpConstants._service_error_json);
        }
    }
}
