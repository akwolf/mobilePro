package com.jnzy.mdm.bean.user;

/**用户表
 * Created by hardy on 2016/6/3.
 * `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `user_code` varchar(64) NOT NULL DEFAULT '' COMMENT '编号',
 `realname` varchar(128) NOT NULL COMMENT '姓名',
 `mobile` char(11) DEFAULT '' COMMENT '手机号码',
 `id_number` varchar(24) NOT NULL DEFAULT '' COMMENT '身份证号码',
 `sex` enum('男','女','保密') DEFAULT NULL COMMENT '性别',
 `device_id` varchar(64) NOT NULL DEFAULT '' COMMENT '手机设备号',
 `o_id` int(10) unsigned NOT NULL COMMENT '用户所属组织',
 `user_type` int(10) unsigned DEFAULT '1' COMMENT '用户类型1士兵',
 `status` tinyint(1) unsigned DEFAULT '1' COMMENT '用户状态,1启用 2禁用 3在线 4离线',
 `add_time` datetime DEFAULT NULL COMMENT '添加时间',
 */
public class UserVO {
    private Integer userId;
    private String mobile;
    private Integer organId;
    private String deviceId;
    private String imsi;
    private Integer isOnline;//数据库中用户状态
    private Integer isOnlineNow;//当前用户状态
    private Integer status;//用户状态,1启用 2禁用',
    private Integer isDelete;//是否删除，0否，1是',
    private String customized;//是否是定制机，0否，1是'
    private Integer modelId;//部门id
    private String modelTag;//用户机型标签


    private String createTime;//用户最后一次登陆时间或者添加用户的时间

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getOrganId() {
        return organId;
    }

    public void setOrganId(Integer organId) {
        this.organId = organId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }


    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getIsOnlineNow() {
        return isOnlineNow;
    }

    public void setIsOnlineNow(Integer isOnlineNow) {
        this.isOnlineNow = isOnlineNow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomized() {
        return customized;
    }

    public void setCustomized(String customized) {
        this.customized = customized;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelTag() {
        return modelTag;
    }

    public void setModelTag(String modelTag) {
        this.modelTag = modelTag;
    }
}
