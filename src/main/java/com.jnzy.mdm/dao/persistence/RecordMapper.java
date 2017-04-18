package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.record.UserApplistVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/7.
 */
public interface RecordMapper extends SqlMapper{
    /**
     * 根据设备号查询出该用户id
     * @param map
     * @return
     * @throws Exception
     */
    UserVO selUserIdByDeviceId(Map map) throws Exception;
    /**
     * 根据callTime通话时间跟userId判断数据库中是否有重复
     * @param map
     * @return
     * @throws Exception
     */
    Integer selRecordCallInfo(Map map) throws Exception;
    /**
     * 插入到通话记录表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordCall(Map map) throws Exception;
    /**
     * 插入到通话记录表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordCallList(Map map) throws Exception;
    /**
     * 插入到通话拦截记录表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordCallIntercept(Map map) throws Exception;

    /**
     * 判断电子围栏是否重复
     * @param map
     * @return
     * @throws Exception
     */
    Integer selRecordIllegal(Map map) throws Exception;
    /**
     * 插入到违规记录表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordIllegal(Map map) throws Exception;

    /**
     * 客户端电子围栏报警记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordIllegalRail(Map map) throws Exception;

    /**
     * 插入到通讯录记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordIm(Map map) throws Exception;
    /**
     * 插入到通讯录记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordImList(Map map) throws Exception;
    /**
     * 插入到短信记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordsms(Map map) throws Exception;
    /**
     * 插入到短信记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordsmsList(Map map) throws Exception;
    /**
     * 判断短信是否重复
     * @param map
     * @return
     * @throws Exception
     */
    Integer selRecordSms(Map map) throws Exception;
    /**
     * 判断短信敏感词拦截记录是否重复
     * @param map
     * @return
     * @throws Exception
     */
    Integer selSmsSensitive(Map map) throws Exception;
    /**
     * 插入到短信敏感词拦截记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordsmsSensitive(Map map) throws Exception;
    /**
     * 判断是否在应用列表中存在
     * @param map
     * @return
     * @throws Exception
     */
    UserApplistVO selRecordAppList(Map map) throws Exception;
    /**
     * 插入到应用列表记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordAppList(Map map) throws Exception;
    /**
     * 插入到应用列表记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordCallListByMap(Map map) throws Exception;
    /**
     * 更新应用列表记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer updateRecordAppList(Map map) throws Exception;
    /**
     * 插入到应用使用记录表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordAppUse(Map map) throws Exception;
    /**
     * 判断上网记录表中该用户插入的最后时间
     * @param map
     * @return
     * @throws Exception
     */
    String selRecordWebsiteAddTime(Map map) throws Exception;
    /**
     * 插入到上网记录表中
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordWebsite(Map map) throws Exception;
    /**
     * 判断上网敏感词记录表中该用户插入的最后时间
     * @param map
     * @return
     * @throws Exception
     */
    String selRecordWebsiteSensitiveAddTime(Map map) throws Exception;
    /**
     * 插入到上网敏感词记录表中
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordWebsiteSensitive(Map map) throws Exception;
    /**
     * 用户在应用记录的应用名称
     * @param map
     * @return
     * @throws Exception
     */
    String selAppSoftName(Map map) throws Exception;

    /**
     * 获取到40s差的用户状态
     * @return
     * @throws Exception
     */
    List<UserVO> selUserOnline() throws Exception;

    /**
     * 更改用户状态
     * @param map
     * @return
     * @throws Exception
     */
    Integer updateUserOnline(Map map) throws Exception;
    /**
     * list记录
     * 插入到上网记录表中
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordWebsiteList(Map map) throws Exception;
    /**
     * list记录
     * 插入到上网敏感词记录表中
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertRecordWebsiteSensitiveList(Map map) throws Exception;

    /**
     * 查询出3天内离线的用户
     * @return
     * @throws Exception
     */
    List<UserVO> selUserOffline() throws Exception;

}
