package com.jnzy.mdm.bean.other;

/**
 *手机目录文件表
 * Created by yyj on 2016/10/17.
 * catalog_id` int(11) NOT NULL COMMENT '自增id',
 `user_id` int(11) NOT NULL COMMENT '用户id',
 `current_catalog_name` varchar(255) NOT NULL COMMENT '目录名称',
 `current_catalog_file` varchar(255) NOT NULL COMMENT '目录完整路径',
 `catalog_type` int(1) DEFAULT '0' COMMENT '类型，0目录，1文件',
 `catalog_level` int(11) NOT NULL DEFAULT '0' COMMENT '级别',
 */
public class AppCatalogVO {

    private Integer userId;
    private String currentCatalogName;
    private String currentCatalogFile;
    private Integer catalogType;
    private Integer catalogLevel;
    private Long catalogSize;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCurrentCatalogName() {
        return currentCatalogName;
    }

    public void setCurrentCatalogName(String currentCatalogName) {
        this.currentCatalogName = currentCatalogName;
    }

    public String getCurrentCatalogFile() {
        return currentCatalogFile;
    }

    public void setCurrentCatalogFile(String currentCatalogFile) {
        this.currentCatalogFile = currentCatalogFile;
    }

    public Integer getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(Integer catalogType) {
        this.catalogType = catalogType;
    }

    public Integer getCatalogLevel() {
        return catalogLevel;
    }

    public void setCatalogLevel(Integer catalogLevel) {
        this.catalogLevel = catalogLevel;
    }

    public Long getCatalogSize() {
        return catalogSize;
    }

    public void setCatalogSize(Long catalogSize) {
        this.catalogSize = catalogSize;
    }
}
