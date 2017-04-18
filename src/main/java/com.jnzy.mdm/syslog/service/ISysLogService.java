package com.jnzy.mdm.syslog.service;

import com.jnzy.mdm.bean.loginfo.SysInterfaceLogTest;

import java.util.List;
import java.util.Map;

/**
 * Created by yxm on 2016/8/30.
 */
public interface ISysLogService
{
    public void insertLogs(List<SysInterfaceLogTest> sysInterfaceLogs,Map params) throws Exception;

    public List<Map> selLogs(Map params) throws Exception;

    public void logInfoChanges(Map params) throws  Exception;//日志表转换

    public void updateLogInfo(Map params) throws Exception;//更新当天日志级别

}
