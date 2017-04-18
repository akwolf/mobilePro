package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.record.UserApplistVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IRecordService;
import com.jnzy.mdm.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hardy on 2016/6/7.
 */
@Service
public class RecordService extends BaseService implements IRecordService {
    @Autowired
    private RecordMapper recordMapper;

    /**
     * 通话记录
     *
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordCall(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("callNumber") + "") || StringUtil.isBlank(map.get("callTime") + "") || StringUtil.isBlank(map.get("timeLength") + "")
                || StringUtil.isBlank(map.get("type") + "") || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());
            map.put("timeLength", Integer.parseInt(map.get("timeLength") + ""));
            map.put("type", Integer.parseInt(map.get("type") + ""));
            map.put("table", "m_record_call"+AppUtil.getYearMonth());
            //根据callTime通话时间跟userId判断当月数据库中是否有重复
            Integer callId = recordMapper.selRecordCallInfo(map);
            if(StringUtil.isNull(callId+"")){
                //插入到通话记录表
                recordMapper.insertRecordCall(map);
            }
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 通话拦截记录表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordCallIntercept(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("callNumber") + "") || StringUtil.isBlank(map.get("addTime") + "")
                || StringUtil.isBlank(map.get("type") + "") || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        //通话类型 1 拨出 2 未接 3呼入
        Integer type = Integer.parseInt(map.get("type") + "");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());
            map.put("type", type);
            map.put("table", "m_record_call_intercept"+AppUtil.getYearMonth());
            //插入到通话拦截记录表
            recordMapper.insertRecordCallIntercept(map);
            map.put("illegalType", 2);
            String content = ((String) map.get("callNumber")).trim() + ":";
            //0其他，1呼出，2接听，3发出，4接收
            Integer status = 0;
            if (type == 1) {
                status = 1;
                content += "拨出";
            } else if (type == 2) {
                status = 2;
                content += "呼入";
            }
            map.put("content", content);
            //电话
            map.put("number", map.get("callNumber") + "");
            //状态    0其他，1呼出，2接听，3发出，4接收
            map.put("status", status);
            map.put("table","m_record_illegal"+AppUtil.getYearMonth());
            //插入到违规记录表
            recordMapper.insertRecordIllegal(map);
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 通讯录记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordIm(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("callNumber") + "") || StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("addTime") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            if(StringUtil.isNotBlank(map.get("contact")+"")){
                map.put("contact", EmojiFilter.removeNonBmpUnicode(map.get("contact")+""));
            }
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());
            map.put("table", "m_record_im"+AppUtil.getYearMonth());
            //插入到通讯录记录
            recordMapper.insertRecordIm(map);
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 短信记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordsms(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("number") + "") || StringUtil.isBlank(map.get("content") + "")
                || StringUtil.isBlank(map.get("addTime") + "") || StringUtil.isBlank(map.get("type") + "")
                || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
//            判断重复
            map.put("userId", userVO.getUserId());
            map.put("table", "m_record_sms"+AppUtil.getYearMonth());
            if(StringUtil.isBlank(recordMapper.selRecordSms(map)+"")){
                //去掉emoji表情
                map.put("content", EmojiFilter.removeNonBmpUnicode(map.get("content")+""));
                map.put("oId", userVO.getOrganId());
                //插入到短信记录
                recordMapper.insertRecordsms(map);
            }
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 短信敏感词拦截记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordsmsSensitive(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("number") + "") || StringUtil.isBlank(map.get("sensKeywords") + "")
                || StringUtil.isBlank(map.get("type") + "") || StringUtil.isBlank(map.get("type") + "")
                || StringUtil.isBlank(map.get("addTime") + "") || StringUtil.isBlank(map.get("type") + "")
                || StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("source") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            //去掉emoji表情
            map.put("content", EmojiFilter.removeNonBmpUnicode(map.get("content")+""));

            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());
            map.put("table", "m_record_sms_sensitive"+AppUtil.getYearMonth());

            //去掉重复
            if(StringUtil.isBlank(recordMapper.selSmsSensitive(map)+"")){
                //插入到短信敏感词拦截记录d
                recordMapper.insertRecordsmsSensitive(map);

                //0其他，1呼出，2接听，3发出，4接收
                Integer status = 0;
                //1接收 2发送
                Integer type = Integer.parseInt(map.get("type") + "");
                if (type == 1) {
                    status = 4;
                } else if (type == 2) {
                    status = 3;
                }
                //状态    0其他，1呼出，2接听，3发出，4接收
                map.put("status", status);
                if((map.get("source") + "").contains("短信")){
                    map.put("illegalType", 1);
                }else if((map.get("source") + "").contains("微信")){
                    map.put("illegalType", 10);
                }else{
                    map.put("illegalType", 11);
                }

                map.put("table","m_record_illegal"+AppUtil.getYearMonth());
                //插入到违规记录表
                recordMapper.insertRecordIllegal(map);
            }

        }
        return ServiceProxyResponse.success();
    }

    /**
     * 应用列表记录
     * 1.判断是否在用户应用表中存在该记录，如果存在,并且版本号不同则更新此记录，不存在则插入记录
     * 1.判断是否在应用使用表中存在该记录，如果存在则更新此记录，不存在则插入记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordAppList(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("softName") + "") || StringUtil.isBlank(map.get("version") + "") || StringUtil.isBlank(map.get("packageName") + "")|| StringUtil.isBlank(map.get("versionCode") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id 
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());
            //判断是否在应用列表中存在
            UserApplistVO userApplistVO= recordMapper.selRecordAppList(map);
            if (userApplistVO==null) {
                //插入应用列表记录
                recordMapper.insertRecordAppList(map);
            } else {
                //判断版本号是否相同
                if (!(map.get("versionCode") + "").equals(userApplistVO.getVersionCode())) {
                    //更新应用列表记录
                    recordMapper.updateRecordAppList(map);
                }
            }
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 应用使用列表记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordAppUseList(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("packageName") + "") || StringUtil.isBlank(map.get("visitedTime") + "")||StringUtil.isBlank(map.get("softName") + "")
                || StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("timeLength") + "")) {
            return ServiceProxyResponse.argsError();
        }
        if (StringUtil.isBlank(map.get("useNum") + "")) {
            map.put("useNum", 0);
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());
            map.put("appName",map.get("softName")+"");

            map.put("table", "m_record_app"+AppUtil.getYearMonth());
            recordMapper.insertRecordAppUse(map);
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 客户端上网记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordWebsite(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("websiteType") + "") || StringUtil.isBlank(map.get("websiteUrl") + "")
                || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("addTime", df.format(new Date()));
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());

            map.put("table", "m_record_website"+ AppUtil.getYearMonth());

            //插入到上网记录表中
            recordMapper.insertRecordWebsite(map);
            if ((map.get("websiteType") + "").equals("2")) {
                map.put("illegalType", 4);
                map.put("content", map.get("websiteUrl") + "");
                map.put("status", 0);
                map.put("table","m_record_illegal"+AppUtil.getYearMonth());
                //插入到违规记录表
                recordMapper.insertRecordIllegal(map);
            }
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 客户端上网敏感词记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordWebsiteSensitive(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("keywords") + "")
                || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("**********************"+df.format(new Date()));
            map.put("addTime", df.format(new Date()));
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());

            map.put("table","m_record_website_sensitive"+AppUtil.getYearMonth());
            //插入到上网敏感词记录表中
            recordMapper.insertRecordWebsiteSensitive(map);
            //消息类型，1敏感词（短信）2通话拦截，3离线告警，4网址黑名单，5敏感网址
            map.put("illegalType", 5);
            map.put("content", StringUtil.isNull(map.get("websiteUrl") + "")?map.get("keywords")+"":map.get("keywords")+":"+map.get("websiteUrl")+"");
            //0其他，1呼出，2接听，3发出，4接收
            map.put("status", 0);
            map.put("table","m_record_illegal"+AppUtil.getYearMonth());
            //插入到违规记录表
            recordMapper.insertRecordIllegal(map);
        }
        return ServiceProxyResponse.success();

    }

    /**
     * 客户端上网记录
     * 客户端以list传递
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordWebsiteList(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getDecodeParamsMapByJSON(request);
        if (StringUtil.isBlank(map.get("websiteList") + "") || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());
            String websiteList=map.get("websiteList")+"";
            JSONArray jsonArray = JSONArray.fromObject(websiteList);
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if(jsonObject.containsKey("websiteUrl")){

                    if(jsonObject.containsKey("appLocalIp")){
                        if(StringUtil.isNotBlank(jsonObject.getString("appLocalIp"))){
                            map.put("appLocalIp",jsonObject.getString("appLocalIp"));
                        }else{
                            map.put("appLocalIp",null);
                        }
                    }else{
                        map.put("appLocalIp",null);
                    }

                    map.put("websiteName",jsonObject.getString("websiteName"));
                    map.put("websiteUrl",jsonObject.getString("websiteUrl"));
                    map.put("websiteType",Integer.parseInt(jsonObject.get("websiteType")+""));
                    map.put("addTime",jsonObject.getString("addTime"));
                    map.put("table", "m_record_website"+ AppUtil.getYearMonth());
                    //插入到上网记录表中
                    recordMapper.insertRecordWebsiteList(map);
                    if ((jsonObject.get("websiteType") + "").equals("2")) {
                        map.put("illegalType", 4);
                        map.put("content", jsonObject.getString("websiteUrl") + "");
                        map.put("status", 0);
                        map.put("table","m_record_illegal"+AppUtil.getYearMonth());
                        //插入到违规记录表
                        recordMapper.insertRecordIllegal(map);
                    }
                }
            }

        }
        return ServiceProxyResponse.success();
    }

    /**
     * 客户端上网敏感词记录
     * 客户端以json传递
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRecordWebsiteSensitiveList(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getDecodeParamsMapByJSON(request);
        if (StringUtil.isBlank(map.get("webSensList") + "")
                || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查询出该用户id
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO != null && StringUtil.isNotBlank(userVO.getOrganId() + "")) {
            map.put("userId", userVO.getUserId());
            map.put("oId", userVO.getOrganId());

            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+map.get("webSensList")+"");
            JSONArray jsonArray = JSONArray.fromObject(map.get("webSensList")+"");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                if(jsonObject.containsKey("appLocalIp")){
                    if(StringUtil.isNotBlank(jsonObject.getString("appLocalIp"))){
                        map.put("appLocalIp",jsonObject.getString("appLocalIp"));
                    }else{
                        map.put("appLocalIp",null);
                    }
                }else{
                    map.put("appLocalIp",null);
                }
                if(jsonObject.containsKey("websiteUrl")){
                    if(StringUtil.isNotBlank(jsonObject.getString("websiteUrl"))){
                        map.put("websiteUrl",jsonObject.getString("websiteUrl"));
                    }else{
                        map.put("websiteUrl",null);
                    }
                }else{
                    map.put("websiteUrl",null);
                }

                map.put("keywords",jsonObject.getString("keywords"));
                map.put("addTime",jsonObject.getString("addTime"));
                map.put("table","m_record_website_sensitive"+AppUtil.getYearMonth());
                //插入到上网敏感词记录表中
                recordMapper.insertRecordWebsiteSensitiveList(map);
                //消息类型，1敏感词（短信）2通话拦截，3离线告警，4网址黑名单，5敏感网址
                map.put("illegalType", 5);
                map.put("content", StringUtil.isNull(jsonObject.getString("websiteUrl") + "")?jsonObject.getString("keywords")+"":jsonObject.getString("keywords")+":"+jsonObject.getString("websiteUrl")+"");
                //0其他，1呼出，2接听，3发出，4接收
                map.put("status", 0);
                map.put("table","m_record_illegal"+AppUtil.getYearMonth());
                //插入到违规记录表
                recordMapper.insertRecordIllegal(map);
            }
        }
        return ServiceProxyResponse.success();
    }

    /**
     * sd卡违规记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertIllegalSdcard(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        map.put("userId", userVO.getUserId());
        map.put("oId", userVO.getOrganId());
        //状态    0其他，1呼出，2接听，3发出，4接收
        map.put("status", 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("addTime", dateFormat.format(new Date()));
        //插入到违规记录表
        map.put("illegalType", 9);
        map.put("content", "sd卡变更");
        map.put("table", "m_record_illegal" + AppUtil.getYearMonth());
        recordMapper.insertRecordIllegal(map);

        return ServiceProxyResponse.success();
    }

    @Override
    protected IAdapter createAdapter() {
        return null;
    }
}
