package com.jnzy.mdm.task;

import cn.jpush.api.common.resp.APIRequestException;
import com.jnzy.mdm.bean.other.MAppPushUsermodelTag;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.persistence.MPushUserModelMapper;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.thead.notification.AsyncNotification;
import com.jnzy.mdm.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 计划任务
 */
@Controller
public class MdmTask {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private MPushUserModelMapper mPushUserModelMapper;

    @Autowired
    private AsyncNotification asyn;

    /**
     * 每隔10s执行一次
     *更改用户状态
     * @throws Exception
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    @Transactional
    public void upateUserOnline() throws Exception {
        System.out.println("#############用户在线状态##################定制机混合分表分区#####################upateUserOnline()");

        //获取到35s差的用户状态
        List<UserVO> userVOList = recordMapper.selUserOnline();
        Map userMap = new HashMap();
        Map recodeMap = new HashMap();
        for(UserVO userVO:userVOList){
            userMap.clear();
            userMap.put("userId",userVO.getUserId());
            //判断当前获取的状态值跟数据库中的比较  并且用户在线时
            if(userVO.getIsOnline()==2&&userVO.getIsOnlineNow()==1){
                userMap.put("isOnline",userVO.getIsOnlineNow());
                //更改用户状态
                recordMapper.updateUserOnline(userMap);
            }

        }


    }


    /**长期离线
     *fixedRate 单位为秒
     * 每3天执行一次  1000*60*60*24*3 =259200     0 0 0 1/3 * ?
     * 暂时改为每天请求一次
     */
//    @Scheduled(cron = "0 0 1 * * * ")
    public void userLongOffline() throws Exception {
        System.out.println("#############长期离线###########################定制机混合分表分区############userLongOffline()");
//        查询出3天内离线的用户
        List<UserVO> userVOList = recordMapper.selUserOffline();
        for(UserVO userVO:userVOList){
            Map recodeMap = new HashMap();
            recodeMap.clear();
            //插入到记录表中
            recodeMap.put("addTime",userVO.getCreateTime());
            recodeMap.put("illegalType",3);
            recodeMap.put("content","离线告警");
            recodeMap.put("status",0);
            recodeMap.put("userId",userVO.getUserId());
            recodeMap.put("oId",userVO.getOrganId());
            recodeMap.put("table","m_record_illegal"+ AppUtil.getYearMonth());
            recordMapper.insertRecordIllegal(recodeMap);
        }
    }

    /**
     * 定时为用户按机型打极光推送标签
     * @throws Exception
     */
    public void AddPushUserModelTags() throws Exception{
        List<Map<String,Object>> userInfos=mPushUserModelMapper.selUserModel();
        List<MAppPushUsermodelTag> mAppPushUsermodelTags=new ArrayList<MAppPushUsermodelTag>();
        for(Map<String,Object> map:userInfos){
            MAppPushUsermodelTag mAppPushUsermodelTag=new MAppPushUsermodelTag();
            Set<String> tagSet=new HashSet<String>();
            tagSet.add(String.valueOf(map.get("modelTag")));
            mAppPushUsermodelTag.setDeviceId(String.valueOf(map.get("deviceId")));
            mAppPushUsermodelTag.setRegistrationId(String.valueOf(map.get("registrationId")));
            mAppPushUsermodelTag.setModelId(String.valueOf(map.get("modelTag")));
            mAppPushUsermodelTag.setUserId(Integer.valueOf(map.get("userId").toString()));
            mAppPushUsermodelTag.setIsSuccess(asyn.addModelTagDevice(String.valueOf(map.get("registrationId")),tagSet,"1"));
            mAppPushUsermodelTags.add(mAppPushUsermodelTag);
        }
        if(mAppPushUsermodelTags.size()>0){
            int i=mPushUserModelMapper.saveUserModelResults(mAppPushUsermodelTags);
        }
    }

}
