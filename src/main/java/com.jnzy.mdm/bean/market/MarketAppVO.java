package com.jnzy.mdm.bean.market;

/**应用市场APP表
 * Created by yyj on 2016/5/24.
 * `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `softname` varchar(128) DEFAULT '' COMMENT '应用名称',
 `icon` varchar(128) DEFAULT '' COMMENT '图标',
 `package` varchar(128) DEFAULT '' COMMENT '包名',
 `category_id` int(10) NOT NULL COMMENT '栏目id',
 `status` tinyint(1) DEFAULT '1' COMMENT '状态 1 上架 2下架',
 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
 `add_time` datetime DEFAULT NULL COMMENT '添加时间',
 `download_num` int(10) unsigned DEFAULT NULL COMMENT '下载次数',
 `sequence` int(10) DEFAULT '0' COMMENT '排序',
 `version` varchar(64) DEFAULT '' COMMENT '版本号',
 `sys_require` varchar(255) DEFAULT NULL COMMENT '系统要求',
 `desc` text COMMENT '软件介绍',
 `is_top` int(1) DEFAULT '1' COMMENT '是否置顶 0是 1否',
 `down_url` varchar(50) DEFAULT NULL COMMENT '下载地址',
 `app_size` varchar(30) DEFAULT NULL COMMENT '应用大小',
 */
public class MarketAppVO {
    private Integer appId;
    private String softname;
    private String icon;
    private String packageStr;
    private Integer categoryId;
    private Integer status;
    private Integer downloadNum;
    private String version;
    private String sysRequire;
    private String description;
    private String downUrl;
    private String appSize;
    private String categoryName;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getSoftname() {
        return softname;
    }

    public void setSoftname(String softname) {
        this.softname = softname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSysRequire() {
        return sysRequire;
    }

    public void setSysRequire(String sysRequire) {
        this.sysRequire = sysRequire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
