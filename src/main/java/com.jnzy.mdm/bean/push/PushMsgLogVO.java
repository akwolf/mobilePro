package com.jnzy.mdm.bean.push;

import java.util.List;

/**
 * 推送记录表
 * Created by yyj on 2016/5/26.
 * `push_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
 `device_id` varchar(64) DEFAULT NULL COMMENT '手机设备号（用于单推）',
 `tag_name` varchar(255) DEFAULT NULL COMMENT '标签名称（数组），如["测试1","测试2"]，用于组推',
 `push_type` int(1) NOT NULL DEFAULT '0' COMMENT '推送类型 0单推，1组推，2群推',
 `push_msg` text NOT NULL COMMENT '推送内容，格式如下\r\n{\r\n    "networkBean": {--移动网络\r\n        "status": "0",//0 启用，1禁用\r\n        "week": "0,1,2",（0代表周日，1代表周一，2代表周二...）\r\n        "startTime": "00:00",\r\n        "endTime": "18:00"\r\n    },\r\n    "smsMsgBean": {--短信\r\n        "status": "1"\r\n    },\r\n    "telBean": {--通话\r\n        "status": "0",\r\n        "week": "1,2",\r\n        "startTime": "00:00",\r\n        "endTime": "18:00"\r\n    },\r\n    "wifiBean": {--wifi\r\n        "status": "0",\r\n        "week": "1,2",\r\n        "startTime": "00:00",\r\n        "endTime": "18:00"\r\n    },\r\n    "bluetoothBean": {--蓝牙\r\n        "status": "0",\r\n        "week": "1,2",\r\n        "startTime": "00:00",\r\n        "endTime": "18:00"\r\n    },\r\n    "cameraBean": {--摄像头\r\n        "status": "0",\r\n        "week": "1,2",\r\n        "startTime": "00:00",\r\n        "endTime": "18:00"\r\n    },\r\n    "gpsBean": {--gps\r\n        "status": "0",\r\n        "week": "1,2",\r\n        "startTime": "00:00",\r\n        "endTime": "18:00"\r\n},\r\n"lockScreen":{--锁屏时间段  （sunday周日，monday周一，tuesday周二，wednesday周三，thursday周四，friday周',
 `create_time` datetime NOT NULL COMMENT '创建时间',
 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
 push_model` int(1) NOT NULL DEFAULT '1' COMMENT '1关于策略推送，2关于敏感词推送，3关于版本升级推送',
 `strategy_id` int(10) NOT NULL DEFAULT '0' COMMENT '策略id',
 `is_use` int(1) NOT NULL DEFAULT '1' COMMENT '是否使用该策略 0使用该策略  1禁止该策略',
 `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0:否1:是',
 */
public class PushMsgLogVO {

    private Integer pushId;
    private String deviceId;
    private String tagName;
    private Integer pushType;
    private String pushMsg;
    private String createTime;
    private String updateTime;
    private Integer pushModel;
    private Integer strategyId;
    private Integer isUse;
    private Integer isDel;

    public Integer getPushId() {
        return pushId;
    }

    public void setPushId(Integer pushId) {
        this.pushId = pushId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPushModel() {
        return pushModel;
    }

    public void setPushModel(Integer pushModel) {
        this.pushModel = pushModel;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}