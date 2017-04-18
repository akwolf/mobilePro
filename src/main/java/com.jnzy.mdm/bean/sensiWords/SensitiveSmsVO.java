package com.jnzy.mdm.bean.sensiWords;

/**通讯敏感词
 * Created by hardy on 2016/6/3.
 * `sms_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `keywords` varchar(128) NOT NULL COMMENT '敏感词',
 `add_time` datetime DEFAULT NULL COMMENT '时间',
 `organ_name` varchar(128) DEFAULT NULL COMMENT '所属组织',
 `status` tinyint(1) unsigned DEFAULT '1' COMMENT '1启用  2禁用',
 */
public class SensitiveSmsVO {
    private String keywords;
    private String addTime;
    private Integer isDel;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
