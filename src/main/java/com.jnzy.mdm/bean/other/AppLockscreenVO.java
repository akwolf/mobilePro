package com.jnzy.mdm.bean.other;

/**锁屏应用表'
 * Created by hardy on 2016/7/3.
 *   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `name` varchar(128) DEFAULT NULL COMMENT '应用名称',
 `status` tinyint(1) unsigned DEFAULT '1' COMMENT '1启用，2禁用',
 `is_show` tinyint(1) unsigned DEFAULT '1' COMMENT '锁屏后是否显示  1显示  2不显示',
 `create_time` datetime DEFAULT NULL COMMENT '提交时间',
 `package_name` varchar(50) DEFAULT NULL COMMENT '包名',
 `verson_no` varchar(50) DEFAULT NULL COMMENT '版本号',
 `ico_img` varchar(100) DEFAULT NULL COMMENT '图标',
 `down_url` varchar(100) DEFAULT NULL COMMENT '下载地址',
 */
public class AppLockscreenVO {
    private Integer id;
    private String name;
    private String packageName;
    private Integer isUse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }
}
