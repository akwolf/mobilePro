package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2017/2/20.
 */
public interface IBrowserService {
    /**
     *  获取城市
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selCityProvince(HttpServletRequest request) throws Exception;
    /**
     *  查询天气
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selCityWeather(HttpServletRequest request) throws Exception;
    /**
     *   浏览器意见反馈
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertWebFeedback(HttpServletRequest request, MultipartFile[] multipartFiles) throws Exception;
    /**
     *   首页中的网址导航
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse websiteNavigationHome(HttpServletRequest request) throws Exception;
    /**
     *   首页中的网址导航
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selCityWeatherByCity(HttpServletRequest request) throws Exception;
    /**
     *  上传书签数据
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertBookmark(HttpServletRequest request) throws Exception;
    /**
     *   删除书签
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse delBookmark(HttpServletRequest request) throws Exception;
    /**
     * 获取当前用户书签分页
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selBookmark(HttpServletRequest request) throws Exception;
}
