package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.push.PushMsgLogVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.persistence.PushMapper;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IPushService;
import com.jnzy.mdm.thead.notification.AsyncNotification;
import com.jnzy.mdm.thead.notification.AsyncNotificationSpare;
import com.jnzy.mdm.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hardy on 2016/5/22.
 */
@Service
public class PushService extends BaseService implements IPushService {
    @Autowired
    private PushMapper pushMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private AsyncNotification asyn;
    @Autowired
    private AsyncNotificationSpare asynSpare;
    /**
     * 给用户添加标签
     *
     * @return
     */
    @Override
    public ServiceProxyResponse insertUserTag(HttpServletRequest request) throws Exception {
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("registrationId")+"")){
            return ServiceProxyResponse.argsError();
        }
        //查询出数据库中的registrationId
        String registrationIdDao = pushMapper.selPushRegistrationId(map.get("deviceId")+"");
        //给用户打标签
        Set<String> tagSet=new HashSet<String>();
        tagSet.add(userVO.getOrganId()+"");
        tagSet.add(userVO.getModelTag());
        if(StringUtil.isNotBlank(registrationIdDao)&&registrationIdDao.equals(map.get("registrationId")+"")){
            /**
             * 判断是否打成功，不成功重新打标签
             */
            int tagResult = pushMapper.selUserTagResult(map);
            if(tagResult!=0){
                Integer addTagResult = asyn.addModelTagDevice(map.get("registrationId")+"",tagSet,map.get("biaoshi")+"");
                //成功
                if(addTagResult ==0){
                    pushMapper.updateUserTag(map);
                }
            }
        }else{
            /**
             * 1)不同则先以目前数据库的registration_id去极光把该设备对应标签(该极光id存在的所有标签下:如机型、组织ID)下的数据删掉(成功失败不管)
             2)再将新的registration_id更新到数据库
             3)将新registration_id打到其对应的业务标签下
             */
            if(StringUtil.isBlank(registrationIdDao)){
                pushMapper.insertPushRegistrationId(map);
            }else{
                //1
                asyn.delTagDevice(registrationIdDao,tagSet,map.get("biaoshi")+"");
                //2
                pushMapper.updatePushRegistrationId(map);
            }
            //3
            Integer addTagResult = asyn.addModelTagDevice(map.get("registrationId")+"",tagSet,map.get("biaoshi")+"");
            map.put("organId",userVO.getOrganId()+","+userVO.getModelTag());
            map.put("userId",userVO.getUserId());
            map.put("isSuccess",addTagResult);
            pushMapper.insertUserTag(map);
        }

        return ServiceProxyResponse.success();
    }



//    /**
//     * 推送
//     *
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public ServiceProxyResponse pushMsg(HttpServletRequest request) throws Exception {
//        Map map = ParameterUtil.getDecodeParamsMapByRequest(request);
//        //推送类型1.单推（deviceId不为空）  2.组推 3.群推
//        String type = (String) map.get("type");
//        //推送内容
//        String pushmsg = map.get("pushmsg") + "";
//        if (StringUtil.isBlank(type) || StringUtil.isBlank(pushmsg)) {
//            return ServiceProxyResponse.argsError();
//        }
//        JSONObject jsData = new JSONObject();
//        jsData.put("pushMsg", pushmsg.toString());
//        jsData.put("type", type);
//        JSONObject pushObj = new JSONObject();
////        pushObj.put("data_info", jsData.toString());
//        pushObj.put("data", pushmsg.toString());
//        System.out.println("******************" + pushObj.toString());
//
//        if (type.equals("0")) {
//            //设备号
//            String deviceId = (String) map.get("deviceId");
//            if (StringUtil.isBlank(deviceId)) {
//                return ServiceProxyResponse.argsError();
//            }
//            //0成功 1失败
//            Integer resultPush = asyn.sendMsgOne(request,pushObj.toString(), deviceId);
//            if (resultPush == 0) {
//                return ServiceProxyResponse.success();
//            } else {
//                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
//            }
//        } else if (type.equals("1")) {
//            List<String> tagNameList = (List<String>) map.get("tagName");
//            if (tagNameList.size() == 0) {
//                return ServiceProxyResponse.argsError();
//            }
//            //0成功 1失败
//            Integer resultPush = asyn.sendMsgTag(request,tagNameList, pushObj.toString());
//            if (resultPush == 0) {
//                return ServiceProxyResponse.success();
//            } else {
//                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
//            }
//        } else if (type.equals("2")) {
//            //0成功 1失败
//            Integer resultPush = asyn.sendMsgAll(request,pushObj.toString());
//            if (resultPush == 0) {
//                return ServiceProxyResponse.success();
//            } else {
//                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
//            }
//        } else {
//            return ServiceProxyResponse.argsError();
//        }
//    }

    /**
     * 推送 后台使用
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse pushMsgPhp(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        //推送日志id
        if (StringUtil.isBlank(map.get("pushId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        Integer pushId = Integer.parseInt(map.get("pushId") + "");
        //根据推送id获取推送信息
        PushMsgLogVO pushMsgLogVO = pushMapper.selPushLog(pushId);
        if (pushMsgLogVO == null || StringUtil.isBlank(pushMsgLogVO.getPushType() + "") || StringUtil.isBlank(pushMsgLogVO.getPushMsg())) {
            return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
        }

        String biaoshi = map.get("biaoshi") + "";

        //推送内容
        JSONObject pushMsgObj = new JSONObject();
        //推送标识id
        pushMsgObj.put("pushId", pushId + "");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //推送时间
        pushMsgObj.put("pushMsgTime", dateFormat.format(dateFormat.parse(pushMsgLogVO.getUpdateTime())));
        //推送消息
        pushMsgObj.put("pushMsg", pushMsgLogVO.getPushMsg());

        //是否禁止 0使用  1禁止'
        Integer isUse = pushMsgLogVO.getIsUse();
        if (pushMsgLogVO.getIsDel() == 1) {
            isUse = 1;
        }
        pushMsgObj.put("isUse", isUse + "");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(pushMsgObj.toString());
        //推送总内容
        JSONObject pushInfoObj = new JSONObject();
        JSONObject dataObj = new JSONObject();
        //推送的策略、敏感词、版本升级
        dataObj.put("pushInfo", jsonArray.toString());
        //推送类型  '1关于策略推送，2关于敏感词推送，3关于版本升级推送，4关于手机黑名单推送，5关于手机锁屏,6终端监控锁屏（单），7锁屏应用（群）
        dataObj.put("pushModel", pushMsgLogVO.getPushModel() + "");
        //推送当前时间
        dataObj.put("nowTime", System.currentTimeMillis() + "");
        pushInfoObj.put("data", dataObj.toString());
        System.out.println("*************************推送内容*****" + pushInfoObj.toString());
        //判断推送类型
        Integer pushType = pushMsgLogVO.getPushType();
        Map mapPush = new HashMap();
        mapPush.put("pushId", pushId);


        System.out.println("*******************************推送成功，内容为:" + "温馨提示：收到推送，发送的日志id" + pushId + ",本短信不收取任何费用,请勿回复。");

        if (pushType == 0) {
            //单推
            if (StringUtil.isBlank(pushMsgLogVO.getDeviceId())) {
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
            }
            map.put("deviceId", pushMsgLogVO.getDeviceId());
            //判断该设备号是否存在
            UserVO userVO = pushMapper.selUserIdByDeviceId(map);
            if (userVO == null) {
                return ServiceProxyResponse.argsError();
            }
            //根据设备号查询出最新的registration_id
            String registrationId = pushMapper.selPushRegistrationId(pushMsgLogVO.getDeviceId());
            //0成功 1失败
            Map resultMap = new HashMap();
            if (StringUtil.isNotBlank(registrationId)) {
                //
                System.out.println("------------根据registration_id推送-----------------" + registrationId);
                resultMap = asyn.sendRegistrationId(request, pushInfoObj.toString(), registrationId, biaoshi);
            } else {
                System.out.println("------------根据别名推送-----------------" + pushMsgLogVO.getDeviceId());
                resultMap = asyn.sendMsgOne(request, pushInfoObj.toString(), pushMsgLogVO.getDeviceId(), biaoshi);
            }

            //0成功 1失败
            mapPush.put("msgId", resultMap.get("msgId") + "");
            Integer resultPush = Integer.parseInt(resultMap.get("result") + "");
            if (resultPush == 0) {
                //插入到实际推送结果表中
                mapPush.put("deviceId", pushMsgLogVO.getDeviceId());
                mapPush.put("isSuccess", 1);
                mapPush.put("table", "m_app_push_log_result" + AppUtil.getYearMonth());
                pushMapper.insertPushResult(mapPush);

                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess", 0);
                pushMapper.updatePushLogInfo(mapPush);

                return ServiceProxyResponse.success();
            } else {
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess", 1);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
            }
        } else if (pushType == 1) {
            String pushTag = pushMsgLogVO.getTagName().trim();
            System.out.println("****标签************" + pushTag);
            if (pushTag.endsWith(",")) {
                pushTag = pushTag.substring(0, pushTag.length() - 1);
            }
            System.out.println("****处理后的标签************" + pushTag);
            String[] arrTag = pushTag.split(",");
            List<String> pushList = Arrays.asList(arrTag);
            if (pushList.size() == 0) {
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
            }

            if(pushList.size()>20){
                Integer resultPush = 1;
                int pushNum=pushList.size()/20;
                if(pushList.size()%20!=0){
                    pushNum++;
                }
                for(int i=0;i<pushNum;i++){
                    List<String> tagList = new ArrayList<String>();
                    if(i==pushNum-1){
                        for(int j=i*20;j<pushList.size();j++){
                            tagList.add(pushList.get(j));
                        }
                    }else{
                        for(int j=0;j<20;j++){
                            tagList.add(pushList.get(j));
                        }
                    }
                    System.out.println("---------------"+tagList.toString());
                    //0成功 1失败
                    Map resultMap = asyn.sendMsgTag(request, tagList, pushInfoObj.toString(), biaoshi);
                    mapPush.put("msgId", resultMap.get("msgId") + "");
                    resultPush = Integer.parseInt(resultMap.get("result") + "");
                }
                if (resultPush == 0) {
                    //将所有标签下的用户插入到推送表
                    for (String tagStr : arrTag) {
                        System.out.println("------------标签-----" + tagStr);
                        map.put("tagName", tagStr);
                        List<String> deviceList = pushMapper.selUserDevice(map);
                        if (deviceList.size() != 0) {
                            mapPush.put("isSuccess", 1);
                            for (String deviceId : deviceList) {
                                mapPush.put("deviceId", deviceId);
                                mapPush.put("table", "m_app_push_log_result" + AppUtil.getYearMonth());
                                pushMapper.insertPushResult(mapPush);
                            }
                        }
                    }
                    //更改推送记录是否成功状态
                    mapPush.put("isPushSeccess", 0);
                    pushMapper.updatePushLogInfo(mapPush);
                    return ServiceProxyResponse.success();
                }else {
                    //更改推送记录是否成功状态
                    mapPush.put("isPushSeccess", 1);
                    pushMapper.updatePushLogInfo(mapPush);
                    return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
                }
            }

            //0成功 1失败
            Map resultMap = asyn.sendMsgTag(request, pushList, pushInfoObj.toString(), biaoshi);
            mapPush.put("msgId", resultMap.get("msgId") + "");
            Integer resultPush = Integer.parseInt(resultMap.get("result") + "");
            if (resultPush == 0) {
                //将所有标签下的用户插入到推送表
                for (String tagStr : arrTag) {
                    System.out.println("------------标签-----" + tagStr);
                    map.put("tagName", tagStr);
                    List<String> deviceList = pushMapper.selUserDevice(map);
                    if (deviceList.size() != 0) {
                        mapPush.put("isSuccess", 1);
                        for (String deviceId : deviceList) {
                            mapPush.put("deviceId", deviceId);
                            mapPush.put("table", "m_app_push_log_result" + AppUtil.getYearMonth());
                            pushMapper.insertPushResult(mapPush);
                        }
                    }
                }
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess", 0);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.success();
            } else {
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess", 1);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
            }
        } else if (pushType == 2) {
            //版本升级推送
//            if (pushMsgLogVO.getPushModel() == 3||pushMsgLogVO.getPushModel() == 19) {
//                //按照手机型号推送
//                String pushStr = pushMsgLogVO.getPushMsg();
//                JSONObject jsonObject = JSONObject.fromObject(pushStr);
//                String pushTag = jsonObject.getString("mobile_version") + jsonObject.getString("imei");
//                System.out.println("****标签************" + pushTag);
//                if (pushTag.endsWith(",")) {
//                    pushTag = pushTag.substring(0, pushTag.length() - 1);
//                }
//                System.out.println("****处理后的标签************" + pushTag);
//                String[] arrTag = pushTag.split(",");
//                List<String> pushList = Arrays.asList(arrTag);
//                if (pushList.size() == 0) {
//                    return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
//                }
//                //0成功 1失败
//                Map resultMap = asyn.sendMsgTag(request, pushList, pushInfoObj.toString(), biaoshi);
//                mapPush.put("msgId", resultMap.get("msgId") + "");
//                Integer resultPush = Integer.parseInt(resultMap.get("result") + "");
//                if (resultPush == 0) {
//                    //将所有标签下的用户插入到推送表
//                    for (String tagStr : arrTag) {
//                        System.out.println("------------标签-----" + tagStr);
//                        map.put("tagName", tagStr);
//                        List<String> deviceList = pushMapper.selUserDevice(map);
//                        if (deviceList.size() != 0) {
//                            mapPush.put("isSuccess", 1);
//                            for (String deviceId : deviceList) {
//                                mapPush.put("deviceId", deviceId);
//                                mapPush.put("table", "m_app_push_log_result" + AppUtil.getYearMonth());
//                                pushMapper.insertPushResult(mapPush);
//                            }
//                        }
//                    }
//                    //更改推送记录是否成功状态
//                    mapPush.put("isPushSeccess", 0);
//                    pushMapper.updatePushLogInfo(mapPush);
//                    return ServiceProxyResponse.success();
//                }else {
//                    //更改推送记录是否成功状态
//                    mapPush.put("isPushSeccess", 1);
//                    pushMapper.updatePushLogInfo(mapPush);
//                    return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
//                }
//            } else {

            //群推
            //0成功 1失败
            Map resultMap = asyn.sendMsgAll(request, pushInfoObj.toString(), biaoshi);
            mapPush.put("msgId", resultMap.get("msgId") + "");
            Integer resultPush = Integer.parseInt(resultMap.get("result") + "");
            if (resultPush == 0) {
                map.put("tagName", null);
                List<String> deviceList = pushMapper.selUserDevice(map);
                if (deviceList.size() != 0) {
                    mapPush.put("isSuccess", 1);
                    for (String deviceId : deviceList) {
                        mapPush.put("deviceId", deviceId);
                        mapPush.put("table", "m_app_push_log_result" + AppUtil.getYearMonth());
                        pushMapper.insertPushResult(mapPush);
                    }
                }
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess", 0);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.success();
            } else {
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess", 1);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
            }
//            }

        } else {
            return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
        }
    }

    /**
     *推送客户端使用
     * 将该设备号所有的策略返给客户端
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse pushMsgApp(HttpServletRequest request) throws Exception {
        Map map=ParameterUtil.getDecodeParamsMap(request,String.class,String.class);
        //设备号
        String deviceId=map.get("deviceId")+"";
        map.put("deviceId",deviceId);
        if(StringUtil.isBlank(deviceId)){
            return ServiceProxyResponse.argsError();
        }
        //推送时间
        String pushMsgTime=map.get("pushMsgTime")+"";
        if(StringUtil.isNull(pushMsgTime)){
            map.put("pushMsgTime",null);
        }

        //根据设备号查询出该设备号的标签
        String tagName=((UserVO) request.getAttribute("userVO")).getOrganId()+"";
        if(StringUtil.isBlank(tagName)){
            map.put("tagName",null);
        }else {
            map.put("tagName",tagName);
        }
        //查询出所有跟该设备号有关的策略单推  从组推中查询出跟该设备号有关的策略 所有群推的策略  时间倒叙
        List<PushMsgLogVO> pushMsgLogVOList=pushMapper.selPushLogByOneTagAll(map);
        if(pushMsgLogVOList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.PUSH_NO_NEW_STRATEGY);
        }
        String[] keys={"pushId","pushMsgTime","pushMsg","isUse"};
        List<String> valus=new ArrayList<String>();
        for(PushMsgLogVO pushMsgLogVO:pushMsgLogVOList){
            valus.add(pushMsgLogVO.getPushId()+"");
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //推送时间
            valus.add(StringUtil.isNull(pushMsgLogVO.getUpdateTime())?"":dateFormat.format(dateFormat.parse(pushMsgLogVO.getUpdateTime())));
            valus.add(StringUtil.isNull(pushMsgLogVO.getPushMsg())?"":JSONObject.fromObject(pushMsgLogVO.getPushMsg()).toString());
            //是否使用该策略 0使用该策略  1禁止该策略
            //是否禁止 0使用  1禁止'
            Integer isUse=pushMsgLogVO.getIsUse();
            if(pushMsgLogVO.getIsDel()==1){
                isUse=1;
            }
            valus.add(isUse+"");
        }
        System.out.println("!!!!!!!!!!!!!!!!"+Property2JsonUtil.property2JsonArray(keys,valus));
        JSONObject resultObj=new JSONObject();
        resultObj.put("nowTime",System.currentTimeMillis()+"");
        resultObj.put("pushInfo",Property2JsonUtil.property2JsonArray(keys,valus));
        //推送类型  1关于策略推送，2关于敏感词推送，3关于版本升级推送
        resultObj.put("pushModel","1");

        return ServiceProxyResponse.success(resultObj);
    }

    /**
     * 手机推送成功统计
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse pushSuccFailNum(HttpServletRequest request) throws Exception {
        Map map=ParameterUtil.getDecodeParamsMap(request,String.class,String.class);
        //设备号
        String deviceId=map.get("deviceId")+"";
        //推送标识
        Integer pushId=Integer.parseInt(map.get("pushId")+"");
        if(StringUtil.isBlank(deviceId+"")||StringUtil.isBlank(pushId+"")){
            return ServiceProxyResponse.argsError();
        }

        //根据推送标识获取推送详情
        PushMsgLogVO pushMsgLogVO=pushMapper.selPushLog(pushId);
        if(pushMsgLogVO==null){
            return ServiceProxyResponse.argsError();
        }
        //判断类型

        Integer pushType=pushMsgLogVO.getPushType();
        if(pushType==0){
            //单推
            //判断客户端传入的跟数据库中的比较
            if(!deviceId.equals(pushMsgLogVO.getDeviceId())){
                return ServiceProxyResponse.argsError();
            }
        }
        else if(pushType==1){
            //组推
            //判断该设备属于哪个标签
            String tagName=((UserVO) request.getAttribute("userVO")).getOrganId()+"";
            if(StringUtil.isBlank(tagName)){
                return ServiceProxyResponse.argsError();
            }
            if(!pushMsgLogVO.getTagName().contains(tagName)){
                return ServiceProxyResponse.argsError();
            }
        }
        map.put("isSuccess",0);
        map.put("table","m_app_push_log_result"+ AppUtil.getYearMonth());
        //更改推送记录
        Integer updateNum=pushMapper.updatePushLog(map);
        if(updateNum==0){
            return ServiceProxyResponse.argsError();
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 推送 后台使用 应用市场推送  22
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse pushMsgPhpSpare(HttpServletRequest request) throws Exception {
        Map map=ParameterUtil.getDecodeParamsMap(request,String.class,String.class);
        //推送日志id
        if(StringUtil.isBlank(map.get("pushId")+"")){
            return ServiceProxyResponse.argsError();
        }
        Integer pushId=Integer.parseInt(map.get("pushId")+"");
        //根据推送id获取推送信息
        PushMsgLogVO pushMsgLogVO=pushMapper.selPushLog(pushId);
        if(pushMsgLogVO==null||StringUtil.isBlank(pushMsgLogVO.getPushType()+"")||StringUtil.isBlank(pushMsgLogVO.getPushMsg())){
            return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
        }
        //推送内容
        JSONObject pushMsgObj=new JSONObject();
        //推送标识id
        pushMsgObj.put("pushId",pushId+"");
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //推送时间
        pushMsgObj.put("pushMsgTime",dateFormat.format(dateFormat.parse(pushMsgLogVO.getUpdateTime())));
        //推送消息
        pushMsgObj.put("pushMsg", pushMsgLogVO.getPushMsg());

        //是否禁止 0使用  1禁止'
        Integer isUse=pushMsgLogVO.getIsUse();
        if(pushMsgLogVO.getIsDel()==1){
            isUse=1;
        }
        pushMsgObj.put("isUse",isUse+"");
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(pushMsgObj.toString());
        //推送总内容
        JSONObject pushInfoObj=new JSONObject();
        JSONObject dataObj=new JSONObject();
        //推送的策略、敏感词、版本升级
        dataObj.put("pushInfo",jsonArray.toString());
        //推送类型  '1关于策略推送，2关于敏感词推送，3关于版本升级推送，4关于手机黑名单推送，5关于手机锁屏,6终端监控锁屏（单），7锁屏应用（群）
        dataObj.put("pushModel",pushMsgLogVO.getPushModel()+"");
        //推送当前时间
        dataObj.put("nowTime",System.currentTimeMillis()+"");
        pushInfoObj.put("data",dataObj.toString());
        System.out.println("*************************推送内容*****"+pushInfoObj.toString());
        //判断推送类型
        Integer pushType=pushMsgLogVO.getPushType();
        Map mapPush=new HashMap();
        mapPush.put("pushId",pushId);

        //发短信
////        史建
//        asyn.sendMsg("15628989235","温馨提示：收到推送，发送的日志id"+pushId+",本短信不收取任何费用,请勿回复。");
//        //冯强
//        asyn.sendMsg("15589916599","温馨提示：收到推送，发送的日志id"+pushId+",本短信不收取任何费用,请勿回复。");
//        System.out.println("*******************************推送成功，内容为:"+"温馨提示：收到推送，发送的日志id"+pushId+",本短信不收取任何费用,请勿回复。");

        if (pushType==0){
            //单推
            if(StringUtil.isBlank(pushMsgLogVO.getDeviceId())){
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
            }
            map.put("deviceId",pushMsgLogVO.getDeviceId());
            //判断该设备号是否存在
            UserVO userVO = pushMapper.selUserIdByDeviceId(map);
            if(userVO==null){
                return ServiceProxyResponse.argsError();
            }

            map.put("biaoshi",map.get("biaoshi")+"");

            //0成功 1失败
            Integer resultPush = asynSpare.sendMsgOne(request,pushInfoObj.toString(), pushMsgLogVO.getDeviceId());

            if (resultPush == 0) {
                //插入到实际推送结果表中
                mapPush.put("deviceId",pushMsgLogVO.getDeviceId());
                mapPush.put("isSuccess",1);
                mapPush.put("table","m_app_push_log_result"+ AppUtil.getYearMonth());
                pushMapper.insertPushResult(mapPush);

                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess",0);
                pushMapper.updatePushLogInfo(mapPush);

                return ServiceProxyResponse.success();
            } else {
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess",1);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
            }
        }else if (pushType==1){
            String pushTag=pushMsgLogVO.getTagName().trim();
            System.out.println("****标签************"+pushTag);
            if(pushTag.endsWith(",")){
                pushTag=pushTag.substring(0,pushTag.length()-1);
            }
            System.out.println("****处理后的标签************"+pushTag);
            String[] arrTag=pushTag.split(",");
            List<String> pushList= Arrays.asList(arrTag);
            if(pushList.size()==0){
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
            }
            //0成功 1失败
            Map resultMap = asyn.sendMsgTag(request,pushList, pushInfoObj.toString(),"1");
            Integer resultPush =Integer.parseInt(resultMap.get("result")+"");
            if (resultPush == 0) {
                //将所有标签下的用户插入到推送表
                for(String tagStr:arrTag){
                    System.out.println("------------标签-----"+tagStr);
                    map.put("tagName",tagStr);
                    List<String> deviceList=pushMapper.selUserDevice(map);
                    if(deviceList.size()!=0){
                        mapPush.put("isSuccess",1);
                        for(String deviceId:deviceList){
                            mapPush.put("deviceId",deviceId);
                            map.put("table","m_app_push_log_result"+ AppUtil.getYearMonth());
                            pushMapper.insertPushResult(mapPush);
                        }
                    }
                }
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess",0);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.success();
            } else {
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess",1);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
            }
        }else if(pushType==2){
            //群推
            //0成功 1失败
            Integer resultPush = asynSpare.sendMsgAll(request,pushInfoObj.toString());
            if (resultPush == 0) {
                map.put("tagName",null);
                List<String> deviceList=pushMapper.selUserDevice(map);
                if(deviceList.size()!=0){
                    mapPush.put("isSuccess",1);
                    for(String deviceId:deviceList){
                        mapPush.put("deviceId",deviceId);
                        map.put("table","m_app_push_log_result"+ AppUtil.getYearMonth());
                        pushMapper.insertPushResult(mapPush);
                    }
                }
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess",0);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.success();
            } else {
                //更改推送记录是否成功状态
                mapPush.put("isPushSeccess",1);
                pushMapper.updatePushLogInfo(mapPush);
                return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR);
            }
        }else{
            return ServiceProxyResponse.error(BusinessDataCode.PUSH_ERROR_ERROR);
        }

    }
    @Override
    protected IAdapter createAdapter() {
        return null;
    }
}
