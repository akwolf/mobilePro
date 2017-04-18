package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.IBrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2017/2/20.
 */

@Controller
@RequestMapping(value = "browser")
public class BrowserController extends BaseController{
    @Autowired
    private IBrowserService browserService;

    /**
     * 获取城市
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "selCityProvince")
    private Object selCityProvince(HttpServletRequest request) throws Exception{
        return browserService.selCityProvince(request);
    }
    /**
     * 查询天气
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "selCityWeather")
    private Object selCityWeather(HttpServletRequest request) throws Exception{
        return browserService.selCityWeather(request);
    }

    /**
     * 浏览器意见反馈
     * 二期功能
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertWebFeedback")
    private Object insertWebFeedback(HttpServletRequest request, @RequestParam(value="file") MultipartFile[] multipartFiles) throws Exception{
        return browserService.insertWebFeedback(request,multipartFiles);
    }
    /**
     * 首页中的网址导航
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "websiteNavigationHome")
    private Object websiteNavigationHome(HttpServletRequest request) throws Exception{
        return browserService.websiteNavigationHome(request);
    }
    /**
     * 根据城市查询天气
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "selCityWeatherByCity")
    private Object selCityWeatherByCity(HttpServletRequest request) throws Exception{
        return browserService.selCityWeatherByCity(request);
    }
    /**
     * 上传书签数据
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertBookmark")
    private Object insertBookmark(HttpServletRequest request) throws Exception{
        return browserService.insertBookmark(request);
    }
    /**
     * 删除书签
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "delBookmark")
    private Object delBookmark(HttpServletRequest request) throws Exception{
        return browserService.delBookmark(request);
    }
    /**
     * 获取当前用户书签分页
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "selBookmark")
    private Object selBookmark(HttpServletRequest request) throws Exception{
        return browserService.selBookmark(request);
    }
}
