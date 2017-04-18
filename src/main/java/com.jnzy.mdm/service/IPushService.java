package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yyj on 2016/5/22.
 */
public interface IPushService {
    /**
     * 获取jpush的用户信息
     * @return
     */
    ServiceProxyResponse insertUserTag(HttpServletRequest request) throws Exception;

//    /**
//     * 推送
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    ServiceProxyResponse pushMsg(HttpServletRequest request) throws Exception;
    /**
     * 推送后台使用
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse pushMsgPhp(HttpServletRequest request) throws Exception;
    /**
     * 推送客户端使用
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse pushMsgApp(HttpServletRequest request) throws Exception;
    /**
     * 手机推送成功、失败统计
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse pushSuccFailNum(HttpServletRequest request) throws Exception;
    /**
     * 推送后台使用 应用市场
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse pushMsgPhpSpare(HttpServletRequest request) throws Exception;
}
