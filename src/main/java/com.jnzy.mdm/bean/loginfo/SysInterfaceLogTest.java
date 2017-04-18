package com.jnzy.mdm.bean.loginfo;

/**
 * Created by yxm on 2016/8/27.
 * 接口访问日志记录类
 */
public class SysInterfaceLogTest
{
    private String logId;

    private String logLevel;

    private String serverIp;

    private String groupId;

    private String deviceId;

    private String uri;

    private String logTime;

    private String createTime;

    private String containerPath;

    private String type;

    private String infos;

    public String getInfos()
    {
        return infos;
    }

    public void setInfos(String infos)
    {
        this.infos = infos;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getLogId()
    {
        return logId;
    }

    public void setLogId(String logId)
    {
        this.logId = logId;
    }

    public String getLogLevel()
    {
        return logLevel;
    }

    public void setLogLevel(String logLevel)
    {
        this.logLevel = logLevel;
    }

    public String getServerIp()
    {
        return serverIp;
    }

    public void setServerIp(String serverIp)
    {
        this.serverIp = serverIp;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getLogTime()
    {
        return logTime;
    }

    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getContainerPath()
    {
        return containerPath;
    }

    public void setContainerPath(String containerPath)
    {
        this.containerPath = containerPath;
    }

    @Override
    public String toString(){
        //2016-08-30 09:10:43--[INFO]--[192.168.20.6 [yinxingming] ]--[8efeb1bf-7935-4bb9-9c13-568909945a2b]--[868111020060626]--[requesturi:/m
        String s="--[";
        String e="]";
        StringBuffer sb=new StringBuffer();
        sb.append(this.getLogTime());
        sb.append(s);
        sb.append(this.getLogLevel());
        sb.append(e);
        sb.append(s);
        sb.append(this.getServerIp());
        sb.append(e);
        sb.append(s);
        sb.append(this.getLogId());
        sb.append(e);
        sb.append(s);
        sb.append(this.getDeviceId());
        sb.append(e);
        sb.append(s);
        System.out.println(sb.toString()+this.getUri());
        System.out.println(this.getContainerPath());
        return "";
    }
}
