package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hardy on 2016/6/7.
 */
public interface IOtherService {

    /**
     *获取客户端升级地址
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selAppUploadUrl(HttpServletRequest request) throws Exception;
    /**
     *proxy     demo     contacts
     * 获取客户端升级地址
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selAppUploadUrlSystem(HttpServletRequest request) throws Exception;
    /**
     *获取用户在线状态
     * @return
     * @throws Exception
     */
    ServiceProxyResponse updateUserLastTime(HttpServletRequest request) throws Exception;
    /**
     *终端管控中的锁屏
     * @return
     * @throws Exception
     */
    ServiceProxyResponse deviceLockscreen(HttpServletRequest request) throws Exception;
    /**
     * 锁屏应用
     * @return
     * @throws Exception
     */
    ServiceProxyResponse lockscreenApp(HttpServletRequest request) throws Exception;
    /**
     * 应用基础配置
     * @return
     * @throws Exception
     */
    ServiceProxyResponse appConfig() throws Exception;
    /**
     * 应用黑白名单
     * @return
     * @throws Exception
     */
    ServiceProxyResponse appListBlackWhite(HttpServletRequest request) throws Exception;
    /**
     * 网址基础设置
     * @return
     * @throws Exception
     */
    ServiceProxyResponse urlConfig(HttpServletRequest request) throws Exception;
    /**
     * 网址黑白名单
     * @return
     * @throws Exception
     */
    ServiceProxyResponse urlListBlackWhite(HttpServletRequest request) throws Exception;
    /**
     * 网址导航数据
     * @return
     * @throws Exception
     */
    ServiceProxyResponse websiteNavigation(HttpServletRequest request) throws Exception;
    /**
     * 手机一键定位
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertMobileGps(HttpServletRequest request) throws Exception;
    /**
     * 客户端电子围栏报警记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertRailIllegal(HttpServletRequest request) throws Exception;
    /**
     * 客户端电子围栏报警记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertMobieUserInfo(HttpServletRequest request) throws Exception;
    /**
     * 网址黑白名单 分页
     * @return
     * @throws Exception
     */
    ServiceProxyResponse urlListBlackWhitePage(HttpServletRequest request) throws Exception;
    /**
     * (0:网址黑名单1:文件敏感词2:通讯录敏感词3:网址敏感词) DB文件Url
     * @return
     * @throws Exception
     */
    ServiceProxyResponse DbFileUrls(HttpServletRequest request) throws Exception;
    /**
     * 记录用户动态ip日志
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertUserIpLog(HttpServletRequest request) throws Exception;
    /**
     * 获取手机端文件目录跟文件路径
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertAppCatalog(HttpServletRequest request) throws Exception;
    /**
     * 根据路径上传手机端文件
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertCatalogFile(HttpServletRequest request) throws Exception;
    /**
     *  手机sd卡记录
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertSDcardLog(HttpServletRequest request) throws Exception;
    /**
     *  获取手机当前权限
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertUserPermissions(HttpServletRequest request) throws Exception;

    /**
     *  获取技术客户邮箱
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse selEmail(HttpServletRequest request) throws Exception;
    /**
     *  定期获取手机推送注册ID
     * @param request
     * @return
     * @throws Exception
     */
    ServiceProxyResponse insertPushRegistId(HttpServletRequest request) throws Exception;
}
