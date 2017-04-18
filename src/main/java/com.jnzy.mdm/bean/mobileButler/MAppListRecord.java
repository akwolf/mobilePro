package com.jnzy.mdm.bean.mobileButler;

import java.io.Serializable;

/**
 * Created by hardy on 2017/1/4.
 *
 * softName	String	APP名称
 appMd5Sign	String	APP的md5值（选填，即应用签名值）
 packageName	String	APP包名
 version	String	版本
 versionCode	String	版本号
 *
 */
public class MAppListRecord implements Serializable {
    private String softName;
    private String appMd5Sign;
    private String packageName;
    private String version;
    private String versionCode;
    private Integer userId;
    private Integer organId;

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getAppMd5Sign() {
        return appMd5Sign;
    }

    public void setAppMd5Sign(String appMd5Sign) {
        this.appMd5Sign = appMd5Sign;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrganId() {
        return organId;
    }

    public void setOrganId(Integer organId) {
        this.organId = organId;
    }
}
