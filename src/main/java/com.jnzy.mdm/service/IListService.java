package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2016/6/7.
 */
public interface IListService {
    /**
     * 获取手机黑名单
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selListMobile(HttpServletRequest request) throws Exception;

}
