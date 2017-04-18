package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.IMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 应用市场
 * Created by hardy on 2016/5/20.
 */
@Controller
@RequestMapping("market")
public class MarketController extends BaseController{
    @Autowired
    private IMarketService marketService;

    /**
     * 获取应用市场分类
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "marketClass")
    private Object marketClass() throws Exception {
        return marketService.marketClass();
    }
    /**
     * 获取分类下的列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "marketClassList")
    private Object marketClassList(HttpServletRequest request) throws Exception {
        return marketService.marketClassList(request);
    }
    /**
     * app详情
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "marketAppDetail")
    private Object marketAppDetail(HttpServletRequest request) throws Exception {
        return marketService.marketAppDetail(request);
    }
    /**
     * 搜索app名
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "searchMarketApp")
    private Object searchMarketApp(HttpServletRequest request) throws Exception {
        return marketService.searchMarketApp(request);
    }
    /**
     * 统计下载次数
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateAppDownNum")
    private Object updateAppDownNum(HttpServletRequest request) throws Exception {
        return marketService.updateAppDownNum(request);
    }
    /**
     * 可升级的app列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateAppList")
    private Object updateAppList(HttpServletRequest request) throws Exception {
        return marketService.updateAppList(request);
    }

    /**
     * 获取所有应用市场的包名
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selMarketPackage")
    private Object selMarketPackage(HttpServletRequest request) throws Exception {
        return marketService.selMarketPackage(request);
    }

    /**
     * 获取所有应用市场的包名 分页
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selMarketPackagePage")
    private Object selMarketPackagePage(HttpServletRequest request) throws Exception {
        return marketService.selMarketPackagePage(request);
    }
    /**
     * 应用市场意见反馈
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertMarketFeedback")
    private Object insertMarketFeedback(HttpServletRequest request) throws Exception {
        return marketService.insertMarketFeedback(request);
    }
}
