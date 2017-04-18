package com.jnzy.mdm.comm.interceptor;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.service.IBeforeChainService;
import com.jnzy.mdm.service.IOtherService;
import com.jnzy.mdm.util.ParameterUtil;
import net.sf.json.JSONObject;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证版本号拦截器
 * 在filter后执行，在controller前执行。
 * 所以能到这里，说明参数是没问题的，因为参数在filter里已经验证过啦。
 *
 */
public class CheckVersionInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private IBeforeChainService beforeChainService;
    @Autowired
    private RecordMapper recordMapper;

//    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        System.out.println("##################################拦截器中uri地址#######################:"+uri);

        request.setCharacterEncoding(HTTP.UTF_8);
        //过滤掉不要验证deviceId的接口
        for(String eUrl:HttpConstants._not_version_excludeUrls){
            if (uri.contains(eUrl)){
                return true;
            }
        }
        Map map = ParameterUtil.getDecodeParamsMap(request,Object.class,Object.class);
        map.put("biaoshi",map.get("biaoshi")+"");
        if(map.containsKey(HttpConstants._deviceId)){
            //根据设备号查询出该用户
            UserVO userVO=recordMapper.selUserIdByDeviceId(map);
            if(userVO==null){
//                非定制机
                if((map.get("biaoshi")+"").equals("0")){
                    response.getWriter().print(HttpConstants._user_no_ndm);
                }else{
                    response.getWriter().print(HttpConstants._args_error_json);
                }
                return false;
            }else{
                //用户状态,1启用 2禁用,3退伍
                if(userVO.getStatus()==2){
                    response.getWriter().print(HttpConstants._user_disable);
                    return false;
                }
                if(userVO.getStatus()==3){
                    response.getWriter().print(HttpConstants._user_delete);
                    return false;
                }
                request.setAttribute("userVO",userVO);
                return true;
            }
        }else{
            response.getWriter().print(HttpConstants._args_error_json);
            return false;
        }

//        //验证版本号
//        boolean flag=beforeChainService.checkVersionCode(request);
//        if(!flag){
//            //获取最新的版本号地址
//            String downUrl=beforeChainService.selNewDownUrl(request);
//            JSONObject jsonObject=new JSONObject();
//            jsonObject.put("code", BusinessDataCode.VERSION_CANNOT_USE.getCode());
//            jsonObject.put("message",BusinessDataCode.VERSION_CANNOT_USE.getDesc());
//            jsonObject.put("data",downUrl);
//            response.getWriter().println(jsonObject.toString());
//            return false;
//        }
//        else{
//            return true;
//        }
//        return true;
    }
}

