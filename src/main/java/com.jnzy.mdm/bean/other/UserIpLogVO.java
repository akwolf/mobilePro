package com.jnzy.mdm.bean.other;

/**
 * Created by hardy on 2016/9/30.
 * CREATE TABLE `m_user_ip_log` (
 `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
 `user_id` int(11) NOT NULL COMMENT '当前用户id',
 `app_local_ip` varchar(255) NOT NULL COMMENT '客户端ip',
 `begin_time` datetime NOT NULL COMMENT '开始时间',
 `end_time` datetime NOT NULL COMMENT '结束时间',
 `create_time` datetime NOT NULL COMMENT '该记录插入时间',
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户动态ip日志';
 */
public class UserIpLogVO {
    private Integer id;
    private Integer userId;
    private String beginTime;
    private String appLocalIp;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getAppLocalIp() {
        return appLocalIp;
    }

    public void setAppLocalIp(String appLocalIp) {
        this.appLocalIp = appLocalIp;
    }
}
