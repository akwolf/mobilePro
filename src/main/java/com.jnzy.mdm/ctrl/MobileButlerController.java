package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.service.IMobileButlerService;
import com.jnzy.mdm.util.HttpRequest;
import com.jnzy.mdm.util.ParameterUtil;
import com.jnzy.mdm.util.ServiceProxyResponse;
import com.jnzy.mdm.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yxm on 2016/9/9.
 */
@Controller
@RequestMapping("mobileButler")
public class MobileButlerController
{

    @Autowired
    private IMobileButlerService mobileButlerService;

    @ResponseBody
    @RequestMapping("virusScanning")
    public Object virusScanning(HttpServletRequest request) throws Exception{
        Map map = ParameterUtil.getDecodeParamsMap(request,String.class,String.class);
        if(StringUtil.isBlank(map.get("md5")+"")){
            return ServiceProxyResponse.argsError();
        }
        String result= HttpRequest.sendPost("http://m.qq.com/security_lab/check_result_json.jsp", "data="+map.get("md5")+"&type=md5");
        System.out.println(result);
        Map remap=new HashMap();
        JSONObject jo=JSONObject.fromObject(result);
        if(!"0".equals(jo.get("result"))){//调用查询结果失败
            return ServiceProxyResponse.error(BusinessDataCode.SCAN_FAIL);
        }
        JSONArray arr=JSONArray.fromObject(jo.get("info"));
        for(Object js:arr){
            JSONObject j=JSONObject.fromObject(js);
            Iterator t=j.keySet().iterator();
            while(t.hasNext()){
                Object key=t.next();
                remap.put(key,j.get(key.toString()));
            }
        }
        return ServiceProxyResponse.success(remap);
    }

    @ResponseBody
    @RequestMapping("smsbackup")
    public ServiceProxyResponse smsBackUp(HttpServletRequest request) throws Exception{//短信备份
        return mobileButlerService.insertSms(request);
    }

    @ResponseBody
    @RequestMapping("contactsbackup")
    public ServiceProxyResponse contactsBackUp(HttpServletRequest request) throws Exception{//通讯录备份
        return mobileButlerService.insertContacts(request);
    }

    @ResponseBody
    @RequestMapping("selcontacts")
    public ServiceProxyResponse selContacts(HttpServletRequest request,HttpServletResponse response) throws Exception{//查询通讯录数据
        return mobileButlerService.selContacts(request,response);
    }

    @ResponseBody
    @RequestMapping("selsms")
    public ServiceProxyResponse selSms(HttpServletRequest request,HttpServletResponse response) throws Exception{//查询短信数据
        return mobileButlerService.selSms(request,response);
    }

    /**
     * 上传通话记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("insertCall")
    public ServiceProxyResponse insertCall(HttpServletRequest request,HttpServletResponse response) throws Exception{//查询短信数据
        return mobileButlerService.insertCall(request,response);
    }
    /**
     * 上传应用列表记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("insertAppList")
    public ServiceProxyResponse insertAppList(HttpServletRequest request,HttpServletResponse response) throws Exception{//查询短信数据
        return mobileButlerService.insertAppList(request,response);
    }

    /**
     * 通讯录文件上传
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("contactFile")
    public ServiceProxyResponse contactFile(HttpServletRequest request) throws Exception{//通讯录备份
        return mobileButlerService.contactFile(request);
    }
}
