package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface IMarketService {

    /**
     * 获取应用市场分类
     * @return
     * @throws Exception
     */
    ServiceProxyResponse marketClass()throws Exception;
    /**
     * 获取分类下的列表
     * @return
     * @throws Exception
     */
    ServiceProxyResponse marketClassList(HttpServletRequest request)throws Exception;
    /**
     * app详情
     * @return
     * @throws Exception
     */
    ServiceProxyResponse marketAppDetail(HttpServletRequest request)throws Exception;
    /**
     * 搜索app名
     * @return
     * @throws Exception
     */
    ServiceProxyResponse searchMarketApp(HttpServletRequest request)throws Exception;
    /**
     * 统计下载次数
     * @return
     * @throws Exception
     */
    ServiceProxyResponse updateAppDownNum(HttpServletRequest request)throws Exception;
    /**
     * 可升级的app列表
     * @return
     * @throws Exception
     */
    ServiceProxyResponse updateAppList(HttpServletRequest request)throws Exception;
    /**
     * 获取所有应用市场的包名
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selMarketPackage(HttpServletRequest request)throws Exception;
    /**
     * 获取所有应用市场的包名 分页
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selMarketPackagePage(HttpServletRequest request)throws Exception;
    /**
     * 应用市场意见反馈
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertMarketFeedback(HttpServletRequest request)throws Exception;
}