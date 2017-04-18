package com.jnzy.mdm.service;

import com.jnzy.mdm.util.DocumentProUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * service 基类。extends AbstractEvent 被观察者
 */
public abstract class BaseService implements IService {
    /**yyyy-MM-dd HH:mm:ss*/
    public static final String y_M_dHms = "yyyy-MM-dd HH:mm:ss";
    /**域名*/
    public static final String BASE_URL = DocumentProUtil.getValues("Baseurl");
    //public static final String BASE_URL = "http://cheweilian.weizhan360.cn";
    /**磁盘根路径*/
    public static final String BASE_PATH = DocumentProUtil.getValues("BasePathurl");
    /**db文件目录*/
    public static final String DB_PATH = DocumentProUtil.getValues("dbFolder");
    /**达到记录数量使用db文件*/
    public static final Integer MAX_NUM =Integer.valueOf(DocumentProUtil.getValues("maxNum"));

    public BaseService() {
    }

    /** php时间戳 */
    public int php_time() {
        return Integer.valueOf((System.currentTimeMillis() + "").substring(0,
                10));
    }
    /** 时间 */
    public String timeUid() {
        Date now = new Date();
        SimpleDateFormat timeUidSdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return timeUidSdf.format(now);
    }

    private IAdapter adapter;
    @Override
    public void setAdapter(IAdapter adapter) {
        this.adapter=adapter;
    }

    @Override
    public IAdapter getAdapter() {
        if (adapter==null){
            adapter=createAdapter();
        }
        return adapter;
    }
    abstract protected IAdapter createAdapter();
}