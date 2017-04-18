package com.jnzy.mdm.bean.comm;

/**
 * APP升级记录
 * Created by yyj on 2016/5/20.
 *`up_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
 `version_code` varchar(20) NOT NULL COMMENT '版本号',
 `add_time` datetime NOT NULL COMMENT '上传时间',
 `is_available` int(1) NOT NULL DEFAULT '0' COMMENT '是否可用 0可用，1不可用',
 `package_name` varchar(128) NOT NULL COMMENT '包名（采集包名:me.uniauto.mdm.proxy；应用市场包名:me.uniauto.mdm.market）',
 PRIMARY KEY (`up_id`)
 */
public class AppUpdateVO {
    private Integer upId;
    private String versionCode;
    private Integer isAvailable;
    private String downUrl;
    private String packageName;

    public Integer getUpId() {
        return upId;
    }

    public void setUpId(Integer upId) {
        this.upId = upId;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
