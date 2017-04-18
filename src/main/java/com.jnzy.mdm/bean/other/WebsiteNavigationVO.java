package com.jnzy.mdm.bean.other;

/**网址导航数据
 * Created by hardy on 2016/7/7.
 * `website_navigation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
 `website_name` varchar(100) NOT NULL COMMENT '网址名单',
 `website_logo` varchar(100) NOT NULL COMMENT '网址logo',
 `website_url` varchar(100) NOT NULL COMMENT '网址地址',
 `sort` int(3) NOT NULL DEFAULT '1' COMMENT '排序，越小越靠前',
 `create_time` datetime NOT NULL COMMENT '创建时间',
 */
public class WebsiteNavigationVO {
    private Integer websiteNavigationId;
    private String  websiteName;
    private String websiteLogo;
    private String websiteUrl;

    public Integer getWebsiteNavigationId() {
        return websiteNavigationId;
    }

    public void setWebsiteNavigationId(Integer websiteNavigationId) {
        this.websiteNavigationId = websiteNavigationId;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getWebsiteLogo() {
        return websiteLogo;
    }

    public void setWebsiteLogo(String websiteLogo) {
        this.websiteLogo = websiteLogo;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
