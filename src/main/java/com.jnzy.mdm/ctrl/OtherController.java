package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.IOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2016/6/7.
 */
@Controller
@RequestMapping("other")
public class OtherController {

    @Autowired
    private IOtherService otherService;

    /**
     * 浏览器，应用市场
     * 获取客户端升级地址
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selAppUploadUrl")
    private Object selAppUploadUrl(HttpServletRequest request) throws Exception {
        return otherService.selAppUploadUrl(request);
    }
    /**
     * proxy     demo     contacts
     * 获取客户端升级地址
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selAppUploadUrlSystem")
    private Object selAppUploadUrlSystem(HttpServletRequest request) throws Exception {
        return otherService.selAppUploadUrlSystem(request);
    }
    /**
     * 获取用户在线状态
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateUserLastTime")
    private Object updateUserLastTime(HttpServletRequest request) throws Exception {
        return otherService.updateUserLastTime(request);
    }
    /**
     * 终端管控中的锁屏
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deviceLockscreen")
    private Object devicelockscreen(HttpServletRequest request) throws Exception {
        return otherService.deviceLockscreen(request);
    }
    /**
     * 锁屏应用
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "lockscreenApp")
    private Object lockscreenApp(HttpServletRequest request) throws Exception {
        return otherService.lockscreenApp(request);
    }
    /**
     * 应用基础配置
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "appConfig")
    private Object appConfig() throws Exception {
        return otherService.appConfig();
    }
    /**
     * 应用黑白名单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "appListBlackWhite")
    private Object appListBlackWhite(HttpServletRequest request) throws Exception {
        return otherService.appListBlackWhite(request);
    }
    /**
     * 网址基础设置
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "urlConfig")
    private Object urlConfig(HttpServletRequest request) throws Exception {
        return otherService.urlConfig(request);
    }
    /**
     * 网址黑白名单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "urlListBlackWhite")
    private Object urlListBlackWhite(HttpServletRequest request) throws Exception {
        return otherService.urlListBlackWhite(request);
    }
    /**
     * 网址导航数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "websiteNavigation")
    private Object websiteNavigation(HttpServletRequest request) throws Exception {
        return otherService.websiteNavigation(request);
    }
    /**
     * 手机一键定位
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertMobileGps")
    private Object insertMobileGps(HttpServletRequest request) throws Exception {
        return otherService.insertMobileGps(request);
    }
    /**
     * 客户端电子围栏报警记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertRailIllegal")
    private Object insertRailIllegal(HttpServletRequest request) throws Exception {
        return otherService.insertRailIllegal(request);
    }
    /**
     * 手机基本信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertMobieUserInfo")
    private Object insertMobieUserInfo(HttpServletRequest request) throws Exception {
        return otherService.insertMobieUserInfo(request);
    }

    /**
     * 网址黑白名单分页
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "urlListBlackWhitePage")
    private Object urlListBlackWhitePage(HttpServletRequest request) throws Exception {
        return otherService.urlListBlackWhitePage(request);
    }

    /**
     * 网址黑白名单db文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "DbFileUrls")
    private Object DbFileUrls(HttpServletRequest request) throws Exception {
        return otherService.DbFileUrls(request);
    }

    /**
     * 记录用户动态ip日志
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertUserIpLog")
    private Object insertUserIpLog(HttpServletRequest request) throws Exception {
        return otherService.insertUserIpLog(request);
    }

    /**
     * 获取手机端文件目录跟文件路径
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertAppCatalog")
    private Object insertAppCatalog(HttpServletRequest request) throws Exception{
        return otherService.insertAppCatalog(request);
    }

    /**
     * 根据路径上传手机端文件
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertCatalogFile")
    private Object insertCatalogFile(HttpServletRequest request) throws Exception{
        return otherService.insertCatalogFile(request);
    }
    /**
     * 手机sd卡记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertSDcardLog")
    private Object insertSDcardLog(HttpServletRequest request) throws Exception{
        return otherService.insertSDcardLog(request);
    }
    /**
     * 获取手机当前权限
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertUserPermissions")
    private Object insertUserPermissions(HttpServletRequest request) throws Exception{
        return otherService.insertUserPermissions(request);
    }

    /**
     * 获取技术客户邮箱
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "selEmail")
    private Object selEmail(HttpServletRequest request) throws Exception{
        return otherService.selEmail(request);
    }
    /**
     * 定期获取手机推送注册ID
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertPushRegistId")
    private Object insertPushRegistId(HttpServletRequest request) throws Exception{
        return otherService.insertPushRegistId(request);
    }

}
