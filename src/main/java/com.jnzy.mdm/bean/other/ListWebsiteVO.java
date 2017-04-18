package com.jnzy.mdm.bean.other;

/**
 * 网址名单表（黑白名单）
 * Created by hardy on 2016/7/16.
 * `website_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `url` varchar(255) DEFAULT '' COMMENT '链接地址',
 `type` tinyint(4) DEFAULT '1' COMMENT '1白名单 2 黑名单',
 `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
 `add_time` datetime DEFAULT NULL COMMENT '时间',
 `sys_u_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人用户id',
 `o_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人组织id',
 `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0:否1:是',
 */
public class ListWebsiteVO {
    private Integer websiteId;
    private String websiteUrl;//链接地址
    private Integer type;
    private String addTime;
    private Integer isDel;

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
