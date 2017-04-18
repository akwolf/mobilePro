package com.jnzy.mdm.bean.other;

/**
 * 城市表
 * Created by hardy on 2016/12/7.
 *
 *  `id` int(10) NOT NULL AUTO_INCREMENT,
 `name` varchar(100) DEFAULT NULL COMMENT '城市名称',
 `adcode` varchar(10) DEFAULT NULL COMMENT '行政区划',
 `citycode` varchar(10) DEFAULT NULL,
 `lat` varchar(20) DEFAULT NULL COMMENT '维度',
 `lng` varchar(20) DEFAULT NULL COMMENT '经度',
 `citylevel` int(1) DEFAULT NULL COMMENT '城市级别',
 `tq_mark` varchar(50) DEFAULT NULL COMMENT '天气城市ID',
 */
public class AppCityVO {

    private String name;
    private String adcode;
    private String tqMark;
    private Integer citylevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getTqMark() {
        return tqMark;
    }

    public void setTqMark(String tqMark) {
        this.tqMark = tqMark;
    }

    public Integer getCitylevel() {
        return citylevel;
    }

    public void setCitylevel(Integer citylevel) {
        this.citylevel = citylevel;
    }
}
