package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2016/6/2.
 */
public interface ISendMsgService {
    /**
     *发送短信
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertSendMsg(HttpServletRequest request) throws Exception;
}
