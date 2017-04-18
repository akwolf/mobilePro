package com.jnzy.mdm.bean.list;

/**手机黑名单
 * Created by hardy on 2016/6/8.
 * CREATE TABLE `m_list_mobile` (
 `m_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `number` varchar(32) DEFAULT NULL COMMENT '号码',
 `remark` varchar(255) DEFAULT '' COMMENT '备注',
 `add_time` datetime DEFAULT NULL COMMENT '添加时间',
 `sys_u_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '操作用户id',
 `o_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '操作用户组织id',
 PRIMARY KEY (`m_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='手机黑名单';
 */
public class ListMobile {
    private String number;
    private String addTime;
    private Integer isDel;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
