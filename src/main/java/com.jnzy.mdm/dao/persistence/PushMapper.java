package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.push.PushMsgLogVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.SqlMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/5/22.
 */
public interface PushMapper extends SqlMapper{
    /**
     * 根据推送id获取推送信息
     * @param pushId
     * @return
     * @throws Exception
     */
    PushMsgLogVO selPushLog(Integer pushId) throws Exception;
    /**
     * 查询出所有跟该设备号有关的策略单推  从组推中查询出跟该设备号有关的策略 所有群推的策略  时间倒叙
     * @param map
     * @return
     * @throws Exception
     */
    List<PushMsgLogVO> selPushLogByOneTagAll(Map map) throws Exception;

    /**
     * 插入到推送成功记录表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertSuccessLog(Map map) throws Exception;

    /**
     * 插入到推送失败记录表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertErrorLog(Map map) throws Exception;

    /**
     * 推送记录结果表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertPushResult(Map map) throws Exception;
    /**
     * 更改推送记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer updatePushLog(Map map) throws Exception;
    /**
     * 根据设别号查询用户id
     * @param map
     * @return
     * @throws Exception
     */
    UserVO selUserIdByDeviceId(Map map) throws Exception;
    /**
     * 根据设备号查询出最新的registration_id
     * @param deviceId
     * @return
     * @throws Exception
     */
    String selPushRegistrationId(String deviceId) throws Exception;
    /**
     * 更改用户激活字段
     * @param userId
     * @return
     * @throws Exception
     */
    Integer updateUserActiv(Integer userId) throws Exception;
    /**
     * 判断用户标签表中是否存在该记录
     * @param map
     * @return
     * @throws Exception
     */
    Integer selTagIdByUser(Map map) throws Exception;
    /**
     * 插入到极光推送的用户标签表
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertUserTag(Map map) throws Exception;
    /**
     * 更新用户标签表
     * @param map
     * @return
     * @throws Exception
     */
    Integer updateUserTag(Map map) throws Exception;

    /**
     *根据标签查询该标签下的用户
     * @param map
     * @return
     * @throws Exception
     */
    List<String> selUserDevice(Map map) throws Exception;
    /**
     *判断pushid是否存在
     * @param map
     * @return
     * @throws Exception
     */
    Integer selPushLogByIdModel(Map map) throws Exception;
    /**
     *更改推送记录是否成功状态
     * @param map
     * @return
     * @throws Exception
     */
    Integer updatePushLogInfo(Map map) throws Exception;
    /**
     * 更改用户中的RegistrationId
     */
    Integer updatePushRegistrationId(Map map) throws Exception;
    /**
     * 插入用户中的RegistrationId
     */
    Integer insertPushRegistrationId(Map map) throws Exception;

    /**
     * 根据RegistrationId查看该用户是否已经打标签成功
     */
    Integer selUserTagResult(Map map) throws Exception;
}
