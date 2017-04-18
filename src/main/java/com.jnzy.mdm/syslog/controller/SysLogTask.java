package com.jnzy.mdm.syslog.controller;

import com.jnzy.mdm.syslog.service.impl.SysLogServiceImpl;
import com.jnzy.mdm.util.JnzyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yxm on 2016/8/27.
 */
@Controller
@RequestMapping("syslog")
public class SysLogTask
{
    private final JnzyLogger logger = JnzyLogger.getLogger(getClass());

    @Autowired
    private SysLogServiceImpl sysLogService;

    /**
     *读取日志计划任务
     * @throws Exception
     */
    @RequestMapping("readlogs")
    public void readLogs() throws Exception{
        logger.error("执行日志计划任务");
        sysLogService.readLogs();
        //调用业务层
    }

    /**
     * webservice接口实时读取日志文件
     */
    @ResponseBody
    @RequestMapping("realtimeread")
    public Object realTimeRead(){

        return "";
    }

}
