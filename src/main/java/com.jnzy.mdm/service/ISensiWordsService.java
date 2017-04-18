package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2016/6/2.
 */
public interface ISensiWordsService{

    /**
     * 通讯敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse sensiWordsSms(HttpServletRequest request) throws Exception;
    /**
     * 网址敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse sensiWordsWebPage(HttpServletRequest request) throws Exception;
    /**
     * 文件敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse sensiWordsFilePage(HttpServletRequest request) throws Exception;
}
