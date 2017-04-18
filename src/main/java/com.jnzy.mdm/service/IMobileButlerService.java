package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yxm on 2016/9/9.
 * 病毒扫描
 */
public interface IMobileButlerService
{
    ServiceProxyResponse insertContacts(HttpServletRequest request) throws Exception;

    ServiceProxyResponse selContacts(HttpServletRequest request,HttpServletResponse response) throws Exception;

    ServiceProxyResponse insertSms(HttpServletRequest request) throws Exception;

    ServiceProxyResponse selSms(HttpServletRequest request,HttpServletResponse response) throws Exception;

    /**
     *上传通话记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertCall(HttpServletRequest request,HttpServletResponse response) throws Exception;
    /**
     *上传应用列表记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertAppList(HttpServletRequest request,HttpServletResponse response) throws Exception;
    /**
     *通讯录文件上传
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    ServiceProxyResponse contactFile(HttpServletRequest request) throws Exception;

}
