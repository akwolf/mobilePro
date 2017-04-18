package com.jnzy.mdm.bean.sensiWords;

/**上网敏感词
 * Created by hardy on 2016/7/5.
 * `sensitive_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `keywords` varchar(128) NOT NULL COMMENT '上网敏感词',
 `add_time` datetime DEFAULT NULL COMMENT '时间',
 `status` tinyint(1) unsigned DEFAULT '1' COMMENT '1启用  2禁用',
 is_del	int(1)	0	NO		是否删除 0:否1:是
 */
public class SensitiveWebVO {
    private Integer sensitiveId;
    private String keywords;
    private String addTime;
    private Integer status;
    private Integer isDel;

    public Integer getSensitiveId() {
        return sensitiveId;
    }

    public void setSensitiveId(Integer sensitiveId) {
        this.sensitiveId = sensitiveId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
