package com.jnzy.mdm.bean.other;

/**
 * Created by hardy on 2017/1/18.
 */

public class MemberRedis {

    /**
     *
     */
    private static final long serialVersionUID = -1959528436584592183L;


    private String keyRedis;
    private String valueRedis;

    public MemberRedis(){}

    public MemberRedis(String keyRedis, String valueRedis) {
        this.keyRedis = keyRedis;
        this.valueRedis = valueRedis;
    }

    public String getKeyRedis() {
        return keyRedis;
    }

    public void setKeyRedis(String keyRedis) {
        this.keyRedis = keyRedis;
    }

    public String getValueRedis() {
        return valueRedis;
    }

    public void setValueRedis(String valueRedis) {
        this.valueRedis = valueRedis;
    }
}