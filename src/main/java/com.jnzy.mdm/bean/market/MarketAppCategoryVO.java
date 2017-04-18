package com.jnzy.mdm.bean.market;

/**
 * APP分类表
 * Created by yyj on 2016/5/20.
 * CREATE TABLE `m_market_app_category` (
 `category_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `category_name` varchar(128) NOT NULL COMMENT '软件分类名称',
 `parent_id` int(10) DEFAULT '0' COMMENT '父类ID',
 `category_img` varchar(255) DEFAULT NULL COMMENT '分类图标',
 PRIMARY KEY (`category_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP分类表';
 */
public class MarketAppCategoryVO {
    private Integer categoryId;
    private String categoryName;
    private Integer parentId;
    private String categoryImg;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
}
