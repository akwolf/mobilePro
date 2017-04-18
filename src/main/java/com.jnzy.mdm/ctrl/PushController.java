package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.IPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2016/5/22.
 */
@Controller
@RequestMapping("push")
public class PushController extends BaseController{

    @Autowired
    private IPushService pushService;
    /**
     * 给用户添加标签
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertUserTag")
    private Object insertUserTag(HttpServletRequest request) throws Exception{
        return pushService.insertUserTag(request);
    }

//    /**
//     * 推送测试
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @ResponseBody
//    @RequestMapping(value = "pushMsg")
//    private Object pushMsg(HttpServletRequest request) throws Exception{
//        return pushService.pushMsg(request);
//    }

    /**
     * 推送后台使用
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "pushMsgPhp")
    private Object pushMsgPhp(HttpServletRequest request) throws Exception{
        return pushService.pushMsgPhp(request);
    }

    /**
     * 推送客户端使用
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "pushMsgApp")
    private Object pushMsgApp(HttpServletRequest request) throws Exception{
        return pushService.pushMsgApp(request);
    }
    /**
     * 手机推送成功、失败统计
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "pushSuccFailNum")
    private Object pushSuccFailNum(HttpServletRequest request) throws Exception{
        return pushService.pushSuccFailNum(request);
    }

    /**
     * 推送后台使用 应用市场
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "pushMsgPhpSpare")
    private Object pushMsgPhpSpare(HttpServletRequest request) throws Exception{
        return pushService.pushMsgPhpSpare(request);
    }
}
