package com.jnzy.mdm.constant;

/**
 * backup备份常量类.
 */
public final class BackUpConstants {
    private BackUpConstants() {
    }

    //全局表名
    public static final String TABLE_NAME = "tableName";
    //分表基数
    public static final Integer DIVIDEND = 4;
    //通讯录表名前缀
    public static final String CONTACTS_TABLE_NAME = "m_contacts_backup";
    //短信表名前缀
    public static final String SMS_TABLE_NAME = "m_sms_backup";
    //加密参数
    public static final String KEY = "0123456789123456";
    //通讯录备份文件夹
    public static final String CONTACT_PATH = "contacts";
    //短信备份文件夹
    public static final String SMS_PATH = "sms";
    //通话记录文件夹
    public static final String CALL_PATH = "call";
    //应用列表记录文件夹
    public static final String APPLIST_PATH = "appList";
}
