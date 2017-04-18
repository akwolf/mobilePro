package com.jnzy.mdm.bean.push;

/**
 * Created by hardy on 2016/7/12.
 */
public class PushAppVO {
    private String number;
    private String addTime;
    private Integer isDel;
    private String pushMsg;//是否删除 0:否1:是
    private Integer isUse;//是否使用 0使用，锁屏  1禁止，解锁

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

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }
}
