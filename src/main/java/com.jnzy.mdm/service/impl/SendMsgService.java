package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.push.PushMsgLogVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.dao.persistence.OtherMapper;
import com.jnzy.mdm.dao.persistence.SendMsgMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.ISendMsgService;
import com.jnzy.mdm.thead.notification.AsyncNotification;
import com.jnzy.mdm.util.*;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hardy on 2016/6/2.
 */
@Service
public class SendMsgService extends BaseService implements ISendMsgService {

    @Autowired
    private AsyncNotification asyn;
    @Autowired
    private SendMsgMapper sendMsgMapper;
    @Autowired
    private OtherMapper otherMapper;

    /**
     * 发送短信 后台调用 (针对用户，组织发短信)
     * <p/>
     * 2016.6.15 更改为只针对用户
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertSendMsg(HttpServletRequest request) throws Exception {


        /**
         * 2016.6.15 更改为只针对用户
         */
        /**所有状态都需要type类型
         * 1关于策略推送(组、单)，
         * 2关于敏感词推送(组)，
         * 3关于采集版本升级推送(群)，
         * 4关于手机黑名单推送(群)，
         * 6锁屏应用（群），
         * 7应用基础配置（群），
         * 8应用黑名单（群），
         * 9应用白名单（群）,
         * 13文件的敏感词(组)，
         * 16获取文件目录（指令）,
         * 19关于通话版本升级推送(群)，
         * 23手机app中demo软件更新，
         * 24用户删除，
         * 28用户联网指令
         *
         *2）需要新加status（客户端状态：1锁屏，开启 0解锁，关闭）
         * 5关于手机锁屏(单)，
         *26SIM1开关状态，
         * 27SIM2开关状态'
         *
         *
         *
         *3）需要新加pushId（push的log表中自增id）
         * 10.远程拍照(单，文件上传)，
         * 17一键定位,
         *
         * 4）需要新加pushId（push的log表中自增id），time(录音时长)
         * 11远程录音(单，包括录音时长)，
         * 18拍摄时长(单，文件上传),
         *
         * 5）需要新加pushId（push的log表中自增id），switchStatus(开关状态:0开启，1关闭)
         * 12文件敏感词扫描开关设置,
         * 15监控通话录音开关（通话录音文件的上传），
         *
         * 6)path(路径)
         * 14删除手机中指定目录下的指定文件，
         * 20上传指定文件，（returnId）
         * 28获取手机目录
         *
         * 7）package（卸载包名）
         * 21远程卸载app，
         *
         * 8）installUrl(下载安装app地址)
         * 22远程安装app，
         *
         * 9）userStatus(用户状态:启用，1禁用）
         * 25用户禁用',
         *
         *
         *
         */

        /**
         *
         */

        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if ((StringUtil.isBlank(map.get("type") + "")) || StringUtil.isBlank(map.get("userId") + "") || StringUtil.isBlank(map.get("pushId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        String type = map.get("type") + "";
        String userIdStr = map.get("userId") + "";
        String pushId = map.get("pushId") + "";
        JSONObject jsContent = new JSONObject();
        jsContent.put("type", type);

        //根据用户id获取用户手机号
        UserVO userVO = sendMsgMapper.selMobileByUserId(Integer.parseInt(userIdStr));
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        map.put("pushModel", Integer.parseInt(type));
        map.put("pushIdInt", Integer.parseInt(pushId));
        //判断pushId,pushModel判断是否存在
        PushMsgLogVO pushMsgLogVO = sendMsgMapper.selTypeByPushId(map);
        if (pushMsgLogVO == null) {
            return ServiceProxyResponse.argsError();
        }

        if (userVO.getCustomized().equals("0")) {
            /**
             * 以下是非定制机短信发送
             */
//        短信格式  笑话+（设备号-功能type值-开关值（0使用，锁屏，开启  1禁止，解锁，关闭）-pushId值-time（录音时长））
            //获取笑话内容
            String phiContent = otherMapper.selPhilContent(AppUtil.getRandomNum());
            if (StringUtil.isBlank(phiContent)) {
                return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
            }
            String content = phiContent + "(" + userVO.getDeviceId() + "-" + type + "-";

        /*
         *开关值（0使用，锁屏，开启  1禁止，解锁，关闭）
         */
            if (type.equals("5") || type.equals("26") || type.equals("27") || type.equals("12") || type.equals("15") || type.equals("25")) {
                //2）需要新加status
                // 5关于手机锁屏(单)，
                // 26SIM1开关状态，
                // 27SIM2开关状态'

//            5）需要新加pushId（push的log表中自增id），switchStatus(开关状态:0开启，1关闭)
//            * 12文件敏感词扫描开关设置,
//            * 15监控通话录音开关（通话录音文件的上传）

//            9）userStatus(用户状态:0启动，1禁用）
//            * 25用户禁用',

                //是否使用 （数据库中 '0使用，锁屏，开启  1禁止，解锁，关闭',）
                content = content + pushMsgLogVO.getIsUse() + "-";
            } else {
                content = content + "0-";
            }

            /**
             * pushId值
             */
            if (type.equals("10") || type.equals("17") || type.equals("11") || type.equals("18") || type.equals("12") || type.equals("15")
                    || type.equals("14") || type.equals("16") || type.equals("20") || type.equals("21") || type.equals("22")) {
//            3）需要新加pushId（push的log表中自增id）
//            * 10.远程拍照(单，文件上传)
//            * 17一键定位

//            4）需要新加pushId（push的log表中自增id），time(录音时长)
//           * 11远程录音(单，包括录音时长)，
//            * 18拍摄时长(单，文件上传),

//            5）需要新加pushId（push的log表中自增id），switchStatus(开关状态:0开启，1关闭)
//            * 12文件敏感词扫描开关设置,
//            * 15监控通话录音开关（通话录音文件的上传），

//            6)path(路径)
//            * 14删除手机中指定目录下的指定文件，
//            *16获取文件目录（指令）
//            * 20上传指定文件，
//            7）package（卸载包名）
//            * 21远程卸载app，
//            8）installUrl(下载安装app地址)
//            * 22远程安装app，

                content = content + pushId + "-";
            } else {
                content = content + "0-";
            }


            /**
             * time（录音时长）
             */
            if (type.equals("11") || type.equals("18")) {
//            4）需要新加pushId（push的log表中自增id），time(录音时长)
//            * 11远程录音(单，包括录音时长)，
//            * 18拍摄时长(单，文件上传),
                /**
                 * push_msg
                 * {"user_id":1,"time":10}
                 */
                if (pushMsgLogVO.getPushMsg() != null) {
                    String pushMsg = pushMsgLogVO.getPushMsg();
                    JSONObject pushObj = JSONObject.fromObject(pushMsg);
                    if (pushObj.containsKey("time")) {
                        content = content + pushObj.get("time");
                    } else {
                        return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                    }
                } else {
                    return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                }

            } else {
                content = content + "0";
            }

            content = content + ")";
            System.out.println("----------发送短信内容-----------------" + content);
            map.put("content", content);


            String result = asyn.sendMsg(userVO.getMobile(), content);
            System.out.println("----------------------------------只有一个用户" + userVO.getMobile() + "发送内容" + content + "，调用发送短信接口----------" + result);
//            //给固定手机发短信
//            asyn.sendMsg("15589916599","---用户手机号:"+userVO.getMobile()+"发送内容:"+content+"，调用发送短信接口结果(0成功，1失败)----------"+result);

            //是否发送成功。0成功，1失败'
            Integer isSuccess = 1;
            if (result.equals("0")) {
                isSuccess = 0;
                System.out.println("&&&&&&&&&&&&&发送成功@@@@@@@@@@@@@@@@@@");
            }
            map.put("userMobile", userVO.getMobile());
            map.put("isSuccess", isSuccess);
            map.put("table", "m_send_msg_log" + AppUtil.getYearMonth());
            //插入到m_send_msg_log
            sendMsgMapper.insertSendMsgLog(map);

        } else {
            /**
             * 以下是定制机短信发送
             */
            if (type.equals("5") || type.equals("26") || type.equals("27")|| type.equals("29")|| type.equals("30")) {
                //2）需要新加status（客户端状态：1锁屏,开启，允许 0解锁，关闭，禁止）
                // 5关于手机锁屏(单)，
                // 26SIM1开关状态，
                // 27SIM2开关状态'
                Map mapLogLockscreen = new HashMap();
                mapLogLockscreen.put("deviceId", userVO.getDeviceId());
                mapLogLockscreen.put("pushModel", Integer.parseInt(type));
                //是否使用 （数据库中 '0使用，锁屏，开启  1禁止，解锁，关闭',）
                Integer isUse = otherMapper.selPushLogLockscreen(mapLogLockscreen);
                if (isUse == null || isUse == 1) {
                    //状态  1锁屏,开启，允许 0解锁，关闭，禁止
                    jsContent.put("status", "0");
                } else {
                    //状态  1锁屏,开启，允许 0解锁，关闭，禁止
                    jsContent.put("status", "1");
                }

            } else if (type.equals("10") || type.equals("17")) {
//            3）需要新加pushId（push的log表中自增id）
//            * 10.远程拍照(单，文件上传)
                jsContent.put("pushId", pushId);
            } else if (type.equals("11") || type.equals("18")) {
//            4）需要新加pushId（push的log表中自增id），time(录音时长)
//           * 11远程录音(单，包括录音时长)，
//            * 18拍摄时长(单，文件上传),
                jsContent.put("pushId", pushId);
                /**
                 * push_msg
                 * {"user_id":1,"time":10}
                 */
                if (pushMsgLogVO.getPushMsg() != null) {
                    String pushMsg = pushMsgLogVO.getPushMsg();
                    JSONObject pushObj = JSONObject.fromObject(pushMsg);
                    if (pushObj.containsKey("time")) {
                        jsContent.put("time", pushObj.get("time") + "");
                    } else {
                        return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                    }
                } else {
                    return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                }

            } else if (type.equals("12") || type.equals("15")) {
//            5）需要新加pushId（push的log表中自增id），switchStatus(开关状态:0开启，1关闭)
//            * 12文件敏感词扫描开关设置,
//            * 15监控通话录音开关（通话录音文件的上传），
                jsContent.put("pushId", pushId);
                jsContent.put("switchStatus", pushMsgLogVO.getIsUse() + "");

            } else if (type.equals("14") || type.equals("20") || type.equals("16")) {
//            6)path(路径)
//            * 14删除手机中指定目录下的指定文件，
//            * 20上传指定文件，
                /**
                 * push_msg
                 * {"path":"kkkkk"}
                 */
                if (pushMsgLogVO.getPushMsg() != null) {
                    String pushMsg = pushMsgLogVO.getPushMsg();
                    JSONObject pushObj = JSONObject.fromObject(pushMsg);
                    if (type.equals("14")) {
                        if (pushObj.containsKey("file_app_path")) {
                            jsContent.put("path", pushObj.get("file_app_path") + "");
                        } else {
                            return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                        }
                    } else if (type.equals("20")) {
                        if (pushObj.containsKey("path")) {
                            jsContent.put("path", pushObj.get("path") + "");
                        } else {
                            return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                        }
                        if (pushObj.containsKey("return_id")) {
                            jsContent.put("return_id", pushObj.get("return_id") + "");
                        } else {
                            return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                        }
                    } else {
                        if (pushObj.containsKey("path")) {
                            jsContent.put("path", pushObj.get("path") + "");
                        } else {
                            return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                        }
                    }
                } else {
                    return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                }
            } else if (type.equals("21")) {
//            7）package（卸载包名）
//            * 21远程卸载app，
                /**
                 * push_msg
                 * {"push_msg":"com.zhangyou.cxql.activity"}
                 */
                if (pushMsgLogVO.getPushMsg() != null) {
                    String pushMsg = pushMsgLogVO.getPushMsg();
                    JSONObject pushObj = JSONObject.fromObject(pushMsg);
                    if (pushObj.containsKey("push_msg")) {
                        jsContent.put("package", pushObj.get("push_msg") + "");
                    } else {
                        return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                    }
                } else {
                    return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                }

            } else if (type.equals("22")) {
//            8）installUrl(下载安装app地址)
//            * 22远程安装app，
                /**
                 * push_msg
                 * {'push_msg':'http://guankong.weizhan360.cn/data/upload/sysupload/201608/1471943516/me.uniauto.mdm.contacts.apk'}
                 */
                if (pushMsgLogVO.getPushMsg() != null) {
                    String pushMsg = pushMsgLogVO.getPushMsg();
                    JSONObject pushObj = JSONObject.fromObject(pushMsg);
                    if (pushObj.containsKey("push_msg")) {
                        jsContent.put("installUrl", pushObj.get("push_msg") + "");
                    } else {
                        return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                    }
                } else {
                    return ServiceProxyResponse.error(BusinessDataCode.ERROR_DATA);
                }

            } else if (type.equals("25")) {
//            9）userStatus(用户状态:0启动，1禁用）
//            * 25用户禁用',
                jsContent.put("userStatus", pushMsgLogVO.getIsUse() + "");
            }


            System.out.println("----------加密前短信内容-----------------" + jsContent.toString());
            String content = Des3Util.encode(jsContent.toString(), DocumentProUtil.getValues("defaultKey") + HttpConstants._key_end);
            System.out.println("----------加密后短信内容-----------------" + content);
            map.put("content", content);

            //当前时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
            String nowTime = dateFormat.format(new Date());

            String result = asyn.sendMsg(userVO.getMobile(), content + "(" + nowTime + ")");
            System.out.println("----------------------------------只有一个用户" + userVO.getMobile() + "发送内容" + content + "(" + nowTime + ")" + "，调用发送短信接口----------" + result);
//            //给固定手机发短信
//            asyn.sendMsg("15589916599","---用户手机号:"+userVO.getMobile()+"发送内容:"+content+"("+nowTime+")"+"，调用发送短信接口结果(0成功，1失败)----------"+result);

            //是否发送成功。0成功，1失败'
            Integer isSuccess = 1;
            if (result.equals("0")) {
                isSuccess = 0;
                System.out.println("&&&&&&&&&&&&&发送成功@@@@@@@@@@@@@@@@@@");
            }
            map.put("userMobile", userVO.getMobile());
            map.put("isSuccess", isSuccess);

            map.put("table", "m_send_msg_log" + AppUtil.getYearMonth());
            //插入到m_send_msg_log
            sendMsgMapper.insertSendMsgLog(map);

        }
        return ServiceProxyResponse.success();
    }

    @Override
    protected IAdapter createAdapter() {
        return null;
    }
}
