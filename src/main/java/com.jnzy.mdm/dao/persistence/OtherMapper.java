package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.other.*;
import com.jnzy.mdm.dao.SqlMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/7.
 */
public interface OtherMapper extends SqlMapper{

    /**
     * 更新用户的最后一次登陆时间
     * @param map
     * @return
     * @throws Exception
     */
    Integer updateUserLastTime(Map map) throws Exception;
    /**
     * 查看数据库中是否有存在该用户最后一次登录时间
     * @param map
     * @return
     * @throws Exception
     */
    String selUserLastTime(Map map) throws Exception;
    /**
     * 插入用户的最后一次登陆时间
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertUserLastTime(Map map) throws Exception;
    /**
     * 如果imsi不为null，isOnline不为null
     * @param map
     * @return
     * @throws Exception
     */
    Integer updateUserImsiOnline(Map map) throws Exception;
    /**
     * 更新时间倒叙查询出该设备最新一条锁屏状态
     * @param map
     * @return
     * @throws Exception
     */
    Integer selPushLogLockscreen(Map map) throws Exception;
    /**
     * 锁屏应用
     * @param map
     * @return
     * @throws Exception
     */
    List<String> selLockscreenApp(Map map) throws Exception;
    /**
     * 应用基础配置
     * @return
     * @throws Exception
     */
    Integer selAppConfig() throws Exception;
    /**
     * 应用黑白名单的名称
     * @return
     * @throws Exception
     */
    List<String> selappListBlackWhite(Map map) throws Exception;
    /**
     *根据appName获取包名
     * @return
     * @throws Exception
     */
    String selPackageNameByAppname(@Param("appname") String appname) throws Exception;
    /**
     * 网址基础设置
     * @return
     * @throws Exception
     */
    Integer selUrlConfig() throws Exception;
    /**
     * 获取网址黑白名单地址
     * @return
     * @throws Exception
     */
    List<ListWebsiteVO> selUrlListBlackWhite(Map map) throws Exception;
    /**
     * 网址导航数据
     * @return
     * @throws Exception
     */
    List<WebsiteNavigationVO> selWebsiteNavigation() throws Exception;
    /**
     * 插入到一键定位
     * @return
     * @throws Exception
     */
    Integer insertMobileGps(Map map) throws Exception;
    /**
     * 更改用户的手机IMSI
     * @return
     * @throws Exception
     */
    Integer updateUserImsi(Map map) throws Exception;
    /**
     * 更新用户手机信息
     * @return
     * @throws Exception
     */
    Integer updateUserMobileInfo(Map map) throws Exception;
    /**
     * 插入到手机开机跟是否root表
     * @return
     * @throws Exception
     */
    Integer insertRootTimeLog(Map map) throws Exception;
    /**
     * 判断数据库中是否有设备号跟imsi的违规记录
     * @return
     * @throws Exception
     */
    Integer selrecodrdIllegalInfo(Map map) throws Exception;

    /**
     * 获取网址黑白名单地址 分页
     * @return
     * @throws Exception
     */
    List<ListWebsiteVO> selUrlListBlackWhitePage(Map map) throws Exception;
    /**
     * 获取网址黑白名单地址 随机返回200条
     * @return
     * @throws Exception
     */
    List<ListWebsiteVO> selUrlListBlackWhiteRdom(Map map) throws Exception;

    /**
     * 查询本次需要更新的网址黑白名单数量
     */
    Map selWebSiteCount(Map map) throws Exception;

    /**
     * 查询应用黑白名单所有包名
     */
    List<Map> selListBlackWhitePage(Map map) throws Exception;

    /**
     *查询应用黑白名单数量
     */
    Integer selListBlackWhiteCount(Map map) throws Exception;


    /**
     * 应用黑白名单 返回所有可以使用的应用
     */
    List<Map> selListBlackWhite(Map map) throws Exception;

    /**
     *记录用户动态ip日志
     * @return
     * @throws Exception
     */
    Integer insertUserIpLog(Map map) throws Exception;

    /**
     *查出最后一次用户ip
     * @return
     * @throws Exception
     */
    UserIpLogVO selUserLastIp(Map map) throws Exception;
    /**
     *更改该条记录时间
     * @return
     * @throws Exception
     */
    Integer updateUserLastIp(Map map) throws Exception;

    /**
     * 从数据库表m_app_lockscreen中获取数据
     * @return
     * @throws Exception
     */
    List<String> selByLockscreenApp() throws Exception;

    /**
     * 删除用户的该级目录
     * @return
     * @throws Exception
     */
    Integer delAppCatalogByLevel(Map map) throws Exception;

    /**
     * 将数据插入到手机目录文件表
     * @return
     * @throws Exception
     */
    Integer insertAppCatalog(Map map) throws Exception;

    /**
     * 将数据插入到手机sd卡记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertSDcardLog(Map map) throws Exception;

    /**
     * 获取手机当前权限
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertUserPermissions(Map map) throws Exception;

    /**
     *获取笑话内容
     * @return
     * @throws Exception
     */
    String selPhilContent(int philId) throws Exception;

    /**
     *获取技术客户邮箱
     * @return
     * @throws Exception
     */
    String selEmail() throws Exception;
    /**
     *根据registrationId跟deviceId查询是否存在该条记录
     * @return
     * @throws Exception
     */
    Integer selPushRegistId(Map map) throws Exception;
    /**
     *插入到手机推送注册ID记录表
     * @return
     * @throws Exception
     */
    Integer insertPushRegistId(Map map) throws Exception;
    /**
     *更新手机推送注册ID记录表
     * @return
     * @throws Exception
     */
    Integer updatePushRegistId(Map map) throws Exception;
}
