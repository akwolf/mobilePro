package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.ISendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**发送短信
 * Created by hardy on 2016/6/2.
 */
@Controller
@RequestMapping("sendMsg")
public class SendMsgController {
    @Autowired
    private ISendMsgService sendMsgService;

    /**
     * 发送短信 后台调用
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertSendMsg")
    private Object insertSendMsg(HttpServletRequest request)throws Exception{
        return sendMsgService.insertSendMsg(request);
    }
}
