package com.jnzy.mdm.bean.sensiWords;

/**敏感词
 * Created by hardy on 2016/7/5.
 *  `file_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `keywords` varchar(128) NOT NULL COMMENT '敏感词',
 `add_time` datetime DEFAULT NULL COMMENT '时间',
 */
public class SensitiveVO {
    private Integer fileId;
    private String keywords;
    private String addTime;
    private Integer isDel;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
