package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2016/6/7.
 */
public interface IRecordService {
    /**
     * 通话记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordCall(HttpServletRequest request) throws Exception;
    /**
     * 通话拦截记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordCallIntercept(HttpServletRequest request) throws Exception;
    /**
     * 通讯录记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordIm(HttpServletRequest request) throws Exception;
    /**
     * 短信记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordsms(HttpServletRequest request) throws Exception;
    /**
     * 短信敏感词拦截记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordsmsSensitive(HttpServletRequest request) throws Exception;
    /**
     * 应用列表记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordAppList(HttpServletRequest request) throws Exception;
    /**
     * 应用使用列表记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordAppUseList(HttpServletRequest request) throws Exception;
    /**
     *客户端上网记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordWebsite(HttpServletRequest request) throws Exception;
    /**
     * 客户端上网敏感词记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordWebsiteSensitive(HttpServletRequest request) throws Exception;
    /**
     *客户端上网记录
     * 客户端以list传递
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordWebsiteList(HttpServletRequest request) throws Exception;
    /**
     * 客户端上网敏感词记录
     * 客户端以list传递
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRecordWebsiteSensitiveList(HttpServletRequest request) throws Exception;
    /**
     * sd卡违规记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertIllegalSdcard(HttpServletRequest request) throws Exception;
}
