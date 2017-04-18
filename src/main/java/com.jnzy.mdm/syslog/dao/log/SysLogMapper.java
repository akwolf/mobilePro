package com.jnzy.mdm.syslog.dao.log;

import com.jnzy.mdm.bean.loginfo.SysInterfaceLogTest;
import com.jnzy.mdm.syslog.dao.LogSqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yxm on 2016/8/30.
 */
public interface SysLogMapper extends LogSqlMapper
{

    public List<Map> selLogsTest(Map params);

    public void saveLogTest(List<SysInterfaceLogTest> sysInterfaceLogs);

    public void logInfoChanges(Map params);//日志表转换

    public void updateLogInfo(Map params);//更新当天日志级别

    public void updateLogError(Map params);//更新当天日志级别

    public List<Map> selApplicationPaths(Map params);

    public void saveResult(Map params);//保存日志读取结果

    public Integer saveErrorLog(Map params);//保存当天错误日志

    public Integer delSyslog(Map params);//删除当天读入的日志
}
