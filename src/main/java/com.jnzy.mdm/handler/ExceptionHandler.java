package com.jnzy.mdm.handler;

import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.thead.notification.AsyncNotification;
import com.jnzy.mdm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 异常捕获
 *
 * @author wangkuan
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {
    private final JnzyLogger logger = JnzyLogger.getLogger(getClass());
    private final JnzyLogger infologger = JnzyLogger.getLogger("infolog");

    @Autowired
    private AsyncNotification asyncNotification;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        //logger.error("异常捕获", ex);
        //其他的错误才发邮件
        String uuid=request.getAttribute("uuid").toString();
        String uri = request.getRequestURI();
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        String subject = "亲,方法[ " + uri + " ]报错啦,抓紧时间看看呗!";
        String cipherKey = request.getAttribute(HttpConstants._cipher_key) + "";
        Map paramMap;
        if (StringUtil.isNotBlank(cipherKey)) {
            paramMap = ParameterUtil.getDecodeParamsMap(request, Object.class, Object.class);
        } else {
            paramMap = ParameterUtil.getParamsMap(request);
        }
        Map<String, String> heardMap = RequestUtil.headerMap(request);
        String localIp = AppUtil.INSTANCE.getLocalIp();
        StringBuffer contentinfo=new StringBuffer();
        contentinfo.append("["+localIp+"]");
        contentinfo.append("----");
        contentinfo.append("["+uuid+"]");
        contentinfo.append("----");
        contentinfo.append("["+paramMap.get("deviceId")+"]");
        //infologger.error(contentinfo.toString()+"--["+uri+"]");
        //infologger.error(contentinfo.toString()+"--["+heardMap.toString()+"]");
        //infologger.error(contentinfo.toString()+"--["+paramMap.toString()+"]");
        infologger.error(contentinfo.toString()+"----[exservererr:"+stringWriter.getBuffer().toString());
        infologger.error(contentinfo.toString()+"----[errend]----");
        String content = "\n机器的ip : " + localIp +
                "<br/><br/>\n报错时间 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                "<br/><br/>\n方法的名称 : " + uri +
                "<br/><br/>\n请求的头部 : " + heardMap.toString() +
                "<br/><br/>\n请求的参数 : " + paramMap.toString() +
                "<br/><br/>\n===========================异常如下:===========================<br/><br/>" + stringWriter.getBuffer().toString();
        if (StringUtil.isNotBlank(content)) {
            try {
                logger.error(content);
//                    //杨玉静
                asyncNotification.sendErrorEmail("yangyujing@swimi.net", subject, content);
                if(uri.contains("syslog"))
                {
                    asyncNotification.sendErrorEmail("616868374@qq.com", subject, content);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("exception");//未知异常
    }
}
