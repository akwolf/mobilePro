package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.push.PushMsgLogVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/3.
 */
public interface SendMsgMapper extends SqlMapper{
    /**
     *插入到m_send_msg_log
     * @return
     */
    Integer insertSendMsgLog(Map map) throws Exception;
    /**
     *根据用户id获取用户手机号
     * @return
     */
    UserVO selMobileByUserId(Integer userId) throws Exception;

    /**
     *插入到m_send_msg_log_result
     * @return
     */
    Integer insertSendMsgLogResult(Map map) throws Exception;

    /**
     * 判断pushId,pushModel判断是否存在
     * @param map
     * @return
     * @throws Exception
     */
    PushMsgLogVO selTypeByPushId(Map map) throws Exception;
    /**
     * 根据pushId跟type获取当前日志记录
     * @param map
     * @return
     * @throws Exception
     */
    PushMsgLogVO selTypeByStatusId(Map map) throws Exception;
}
