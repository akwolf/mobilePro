package com.jnzy.mdm.bean.record;

/**
 * 用户应用表
 * Created by hardy on 2016/8/30.
 *   `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `soft_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'APP名称',
 `version` varchar(100) NOT NULL DEFAULT '' COMMENT '版本名称',
 `add_time` datetime NOT NULL COMMENT '时间',
 `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
 `o_id` int(10) unsigned NOT NULL COMMENT '用户所属组织id',
 `package_name` varchar(200) NOT NULL COMMENT '应用包名',
 `app_md5_sign` varchar(255) DEFAULT NULL COMMENT '应用签名值，APP的md5值',
 `warning_level` int(1) DEFAULT '-1' COMMENT '0表示危险; 4表示未知',
 `virusDesc` varchar(200) DEFAULT NULL COMMENT '风险描述',
 `version_code` varchar(20) DEFAULT NULL COMMENT '版本号',
 */
public class UserApplistVO {

    private Integer appId;
    private String versionCode;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
