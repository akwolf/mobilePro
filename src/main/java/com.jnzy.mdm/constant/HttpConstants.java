package com.jnzy.mdm.constant;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.util.DocumentProUtil;

public class HttpConstants {
    /**
     * MD5签名
     */
    public static final String _headerSign = "headers";
    /**
     * 设备id
     */
    public static final String _headerKey = "headerk";
    /**
     * 加密的key
     */
    public static final String _cipher_key = "cipherKey";
    /**
     * 解密后的参数
     */
    public static final String _map_params = "mapParams";
    /**
     * 参数
     */
    public static final String _params = "params";
    /**
     * 版本号
     */
    public static final String _paramsCode = "paramsCode";
    /**
     * 应用账号sdjnzymdm
     */
    public static final String _paramsName = "paramsName";
    /**
     * 应用账号sdjnzymdm
     */
    public static final String _paramsNameValue = "sdjnzymdm";
    /**
     * 应用密码mdm2016Interface
     */
    public static final String _paramsPsw = "paramsPsw";
    /**
     * 应用密码mdm2016Interface
     */
    public static final String _paramsPswValue = "mdm2016Interface";

    /**
     * 请求接口时的时间戳
     */
    public static final String _paramsTime = "paramsTime";
    /**
     * 应用包名
     */
    public static final String _paramsPackage = "paramsPackage";
    /**
     * 标志  0:否1:是
     */
    public static final String _biaoshi = "biaoshi";

    /**
     * 解密之后的参数
     */
    public static final String _decodeParams = "decodeParams";

    /**
     * 加密尾部。
     */
    public static final String _key_end = "sdjnzyxxkmdm2016";

    public static final String _deviceId="deviceId";

    /**
     * 主要用于文件上传,不需要拦截的url
     */
    public static final String[] _excludeUrls = {
            "file/insertFileRemote",//文件上传
            "mobileButler/contactsbackup",//通讯录文件上传
            "mobileButler/selcontacts",//通讯录下载文件
            "mobileButler/smsbackup",//短信文件上传
            "mobileButler/selsms",//短信文件下载
            "mobileButler/insertCall",//上传通话记录
            "mobileButler/insertAppList",//上传应用列表记录
            "mobileButler/contactFile"//上传通讯录文件记录
    };

    /**
     * 过滤掉不要验证版本号的接口 (功能：如果版本号不同，需要更新。现功能已经去掉)
     */
    public static final String[] _excludeUserUrls = {
            "file/insertFileRemote",//文件上传
            "other/websiteNavigation",//网址导航
            "push/insertUserTag",//给用户添加标签
            "mobileButler/contactsbackup",//通讯录文件上传
            "mobileButler/selcontacts",//通讯录下载文件
            "mobileButler/smsbackup",//短信文件上传
            "mobileButler/selsms",//短信文件下载
            "mobileButler/insertCall",//上传通话记录
            "mobileButler/insertAppList",//上传应用列表记录
            "mobileButler/contactFile"//上传通讯录文件记录
    };

    /**
     * 不需要验证deviceId的url
     */
    public static final String[] _not_version_excludeUrls = {
            "WEB-INF/views/exception.jsp",
           "push/pushMsgPhp",//推送，后台使用
            "sendMsg/insertSendMsg",//短信，下发短信通道（后台使用）
            "file/insertFileRemote",//文件上传
            "other/updateUserLastTime",//获取用户在线状态
            "mobileButler/selcontacts",//下载文件
            "mobileButler/smsbackup",//短信文件上传
            "mobileButler/selsms",//短信文件下载
            "mobileButler/insertCall",//上传通话记录
            "mobileButler/insertAppList",//上传应用列表记录
            "mobileButler/contactFile"//上传通讯录文件记录
    };

    /**
     * 参数错误json
     */
    public static final String _args_error_json = "{ \"code\": \""
            + BusinessDataCode.ARGS_ERROR.getCode() + "\", \"message\": \""
            + BusinessDataCode.ARGS_ERROR.getDesc() + "\"}";

    /**
     * 非定制机没有用户数据提示联系管理员
     */
    public static final String _user_no_ndm = "{ \"code\": \""
            + BusinessDataCode.USER_NO_DATE.getCode() + "\", \"message\": \""
            + BusinessDataCode.USER_NO_DATE.getDesc() + "\"}";


    /**
     * 用户删除
     */
    public static final String _user_delete="{ \"code\":\""
            +BusinessDataCode.USER_DELETE.getCode()+"\",\"message\":\""
            +BusinessDataCode.USER_DELETE.getDesc()+"\"}";

    /**
     * 用户禁用
     */
    public static final String _user_disable="{ \"code\":\""
            +BusinessDataCode.USER_DISABLE.getCode()+"\",\"message\":\""
            +BusinessDataCode.USER_DISABLE.getDesc()+"\"}";


    /**
     * 服务器异常
     */
    public static final String _service_error_json = "{ \"code\": \""
            + BusinessDataCode.SERVICE_EXCEPTION.getCode() + "\", \"message\": \""
            + BusinessDataCode.SERVICE_EXCEPTION.getDesc() + "\"}";

    private static String appUploadUrl=DocumentProUtil.getValues("appUploadUrl");

//    /**
//     * 该版本正在维护。
//     */
//    public static final String _version_error_json = "{ \"code\": \""
//            + BusinessDataCode.VERSION_CANNOT_USE.getCode() + "\", \"message\": \""
//            + BusinessDataCode.VERSION_CANNOT_USE.getDesc() + "\",\"data\":{\"appUploadUrl\":\""
//            +appUploadUrl+"\"}"+"}";

    /**
     * 该版本正在维护。
     */
//    public static final String _version_error_json = "{ \"code\": \""
//            + BusinessDataCode.VERSION_CANNOT_USE.getCode() + "\", \"message\": \""
//            + BusinessDataCode.VERSION_CANNOT_USE.getDesc() + "\",\"data\":\""
//            +appUploadUrl+"\"}";

    /**
     * 请求成功
     */
    public static final String _success_json = "{ \"code\": \""
            + BusinessDataCode.SUCCESS.getCode() + "\", \"message\": \""
            + BusinessDataCode.SUCCESS.getDesc() + "\"}";

    /**
     * 请求失败
     */
    public static final String _fail_json = "{ \"code\": \""
            + BusinessDataCode.FAIL.getCode() + "\", \"message\": \""
            + BusinessDataCode.FAIL.getDesc() + "\"}";


}
