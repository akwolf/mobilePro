package com.jnzy.mdm.thead.notification;


import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.DefaultResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.util.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@EnableAsync  //开启异步,否则不会异步执行的.
public class AsyncNotification{
    /**
     * 发送错误邮件,不需要记录在表里
     *
     * @param toAddress
     * @param subject
     * @param content
     * @throws Exception
     */
    @Async
    public void sendErrorEmail(String toAddress, String subject, String content) throws Exception {
        if (StringUtil.isNotBlank(toAddress, subject, content) && AppUtil.INSTANCE.sendHtmlEmail(toAddress, subject, content))
            ;
    }

    //极光推送秘钥
//    String secret= DocumentProUtil.getValues("masterSecret");
//    String appKey= DocumentProUtil.getValues("appKey");
//    JPushClient jPushClient=new JPushClient(secret,appKey);
//    biaoshi 标识（0非定制机，1定制机）
    public JPushClient getJPushClient(String biaoshi){
        if(biaoshi.equals("0")){
            return new JPushClient(DocumentProUtil.getValues("masterSecretNdm"),DocumentProUtil.getValues("appKeyNdm"));
        }else{
            return new JPushClient(DocumentProUtil.getValues("masterSecretMdm"),DocumentProUtil.getValues("appKeyMdm"));
        }
    }

    PushResult resultPush=null;
    DefaultResult defaultResult=null;
    Map sendPushMap = new HashMap();//调用推送返回的map值
    Integer result=1;//0成功 1失败
    String msgId="0";//极光推送返回的msg_id
    /**
     * 根据设备号单推
     * @param pushMsg 推送内容
     * @param deviceId 设备号（别名）
     * @param biaoshi 标识（0非定制机，1定制机）
     * @return
     */
    public Map sendMsgOne(HttpServletRequest request,String pushMsg,String deviceId,String biaoshi) throws  Exception{
        try{
            resultPush=getJPushClient(biaoshi).sendAndroidMessageWithAlias(null,pushMsg,deviceId);
            if(resultPush.isResultOK()){
                result=0;
                msgId = resultPush.msg_id+"";
            }else {
                result=1;
            }
        }catch (APIRequestException e){
            sendPushEmail(request,"单推设备号:"+deviceId+"-----------------httpCode:"+e.getErrorCode()+"--errorCode:"+e.getErrorCode()+"--message:"+e.getErrorMessage()+"");
            result=1;
        }catch (Exception e){
            result=1;
        }
        sendPushMap.put("result",result);
        sendPushMap.put("msgId",msgId);
        return sendPushMap;
    }
    /**
     * 根据registrationId推送
     * @param pushMsg 推送内容
     * @param registrationId 推送注册ID
     * @param biaoshi 标识（0非定制机，1定制机）
     * @return
     */
    public Map sendRegistrationId(HttpServletRequest request,String pushMsg,String registrationId,String biaoshi) throws  Exception{
        try{
            resultPush=getJPushClient(biaoshi).sendAndroidMessageWithRegistrationID(null,pushMsg,registrationId);
            if(resultPush.isResultOK()){
                result=0;
                msgId = resultPush.msg_id+"";
            }else {
                result=1;
            }
        }catch (APIRequestException e){
            sendPushEmail(request,"推送注册ID:"+registrationId+"-----------------httpCode:"+e.getErrorCode()+"--errorCode:"+e.getErrorCode()+"--message:"+e.getErrorMessage()+"");
            result=1;
        }catch (Exception e){
            result=1;
        }
        sendPushMap.put("result",result);
        sendPushMap.put("msgId",msgId);
        return sendPushMap;
    }

    /**
     * 组推
     * @param tagNameList 标签组 eg:["测试1","测试2"]
     * @param pushMsg 推送内容
     * @return
     */
    public Map sendMsgTag(HttpServletRequest request,List<String> tagNameList,String pushMsg,String biaoshi) throws  Exception{
        try {
            PushPayload pushPayload=buildPushObject_android_tag_alertWithTitle(tagNameList,pushMsg);
            resultPush=getJPushClient(biaoshi).sendPush(pushPayload);
            System.out.println("****************"+resultPush);
            if (resultPush.isResultOK()){
                result=0;
                msgId=resultPush.msg_id+"";
            }else {
                result=1;
            }
        }catch (APIRequestException e){
            sendPushEmail(request,"组推的标签名:"+tagNameList+"------------------httpCode:"+e.getErrorCode()+"--errorCode:"+e.getErrorCode()+"--message:"+e.getErrorMessage()+"");
            result=1;
        }catch (Exception e){
            result=1;
        }
        sendPushMap.put("result",result);
        sendPushMap.put("msgId",msgId);
        return sendPushMap;
    }

    /**
     * 群推
     * @param pushMsg 推送内容
     * @return
     */
    public Map sendMsgAll(HttpServletRequest request,String pushMsg,String biaoshi) throws  Exception{
        try {
            resultPush = getJPushClient(biaoshi).sendMessageAll(pushMsg);
            if (resultPush.isResultOK()) {
                result=0;
                msgId=resultPush.msg_id+"";
            } else {
                result=1;
            }
        }catch (APIRequestException e){
            sendPushEmail(request,"群推"+"-----------------httpCode:"+e.getErrorCode()+"--errorCode:"+e.getErrorCode()+"--message:"+e.getErrorMessage()+"");
            result=1;
        } catch (Exception e) {
            result=1;
        }
        sendPushMap.put("result",result);
        sendPushMap.put("msgId",msgId);
        return sendPushMap;
    }

    /**
     * 给用户打标签
     * @param request
     * @param registrationId
     * @param tagsToAdd
     * @return
     * @throws Exception
     */
    public Integer addTagDevice(HttpServletRequest request,String registrationId, Set<String> tagsToAdd,String biaoshi) throws Exception{
        try {
            defaultResult = getJPushClient(biaoshi).updateDeviceTagAlias(registrationId,null,tagsToAdd,null);
            if (defaultResult.isResultOK()) {
                result=0;
            } else {
                result=1;
            }
        }catch (APIRequestException e){
            sendPushEmail(request,"给用户打标签"+"-----------------httpCode:"+e.getErrorCode()+"--errorCode:"+e.getErrorCode()+"--message:"+e.getErrorMessage()+"");
            result=1;
        } catch (Exception e) {
            result=1;
        }
        return result;
    }



    //根据标签设置推送信息
    public static PushPayload buildPushObject_android_tag_alertWithTitle(List<String> tagName, String pushmsg){
        //发送广播
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tagName))
//                .setNotification(
//                        Notification.newBuilder()
//                        .setAlert("")
//                        .addPlatformNotification(
//                                AndroidNotification.newBuilder()
//                                .setTitle("手机管控")
//                                .addExtra("data_info",pushmsg)
//                                .build())
//                        .build()
//                )
                .setMessage(Message.content(pushmsg))
                .build();
    }


    /**
     * 推送失败调用发送邮件
     */
    public void sendPushEmail(HttpServletRequest request, Object errorContent) throws Exception {
        String uri = request.getRequestURI();
        String cipherKey = request.getAttribute(HttpConstants._cipher_key) + "";
        Map paramMap;
        if (StringUtil.isNotBlank(cipherKey)) {
            paramMap = ParameterUtil.getDecodeParamsMap(request, Object.class, Object.class);
        } else {
            paramMap = ParameterUtil.getParamsMap(request);
        }
        Map<String, String> heardMap = RequestUtil.headerMap(request);
        String localIp = AppUtil.INSTANCE.getLocalIp();
        String subject = "亲,方法[ " + uri + " ]报错啦,抓紧时间看看呗!";
        String content = "机器的ip : " + localIp +
                "<br/><br/>报错时间 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                "<br/><br/>方法的名称 : " + uri +
                "<br/><br/>请求的头部 : " + heardMap.toString() +
                "<br/><br/>请求的参数 : " + paramMap.toString() +
                "<br/><br/>===========================异常如下:===========================<br/><br/>" + errorContent;
        sendErrorEmail("yangyujing@swimi.net", subject, content);
    }

    /**
     * 发送短信接口
     * @param mobiles
     * @param content
     */
    public String sendMsg(String mobiles,String content) throws Exception {
        String[] keys={"username","password","method","mobile","content"};
        String[] values={
                DocumentProUtil.getValues("usernameMsg"),
                DocumentProUtil.getValues("passwordMsg"),
                DocumentProUtil.getValues("methodMsg"),
                mobiles,content};
        String sendUrl=DocumentProUtil.getValues("sendMsgUrl");
        return AppUtil.getResponseFromUrl(sendUrl,keys,values);
    }

    /**
     * 给用户按机型+imei打标签
     * @param registrationId
     * @param tagsToAdd
     * @return
     * @throws Exception
     */
    public Integer addModelTagDevice(String registrationId, Set<String> tagsToAdd,String biaoshi) throws Exception{
        try {
            defaultResult = getJPushClient(biaoshi).updateDeviceTagAlias(registrationId,null,tagsToAdd,null);
            result=defaultResult.isResultOK()?0:1;
        }catch (APIRequestException e){
            result=1;
        } catch (Exception e) {
            result=1;
        }
        return result;
    }
    /**
     * 删除用户标签
     * @param registrationId
     * @param tagsToDel
     * @return
     * @throws Exception
     */
    public Integer delTagDevice(String registrationId, Set<String> tagsToDel,String biaoshi) throws Exception{
        try {
            defaultResult = getJPushClient(biaoshi).updateDeviceTagAlias(registrationId,null,null,tagsToDel);
            result=defaultResult.isResultOK()?0:1;
        }catch (APIRequestException e){
            result=1;
        } catch (Exception e) {
            result=1;
        }
        return result;
    }
}