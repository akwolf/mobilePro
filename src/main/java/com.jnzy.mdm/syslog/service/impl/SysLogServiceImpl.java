package com.jnzy.mdm.syslog.service.impl;

import com.jnzy.mdm.bean.loginfo.SysInterfaceLogTest;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.syslog.dao.log.SysLogMapper;
import com.jnzy.mdm.syslog.service.ISysLogService;
import com.jnzy.mdm.thead.notification.AsyncNotification;
import com.jnzy.mdm.util.AppUtil;
import com.jnzy.mdm.util.EmojiFilter;
import com.jnzy.mdm.util.JnzyLogger;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yxm on 2016/8/30.
 */
@Service
public class SysLogServiceImpl extends BaseService implements ISysLogService
{
    private long lastTimeFileSize = 0; // 上次文件大小
    private String webpath;
    private String datetime;
    private int i=0;
    List<SysInterfaceLogTest> sysInterfaceLogs;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private AsyncNotification asyncNotification;


    private final JnzyLogger logger = JnzyLogger.getLogger(getClass());

    @Override
    protected IAdapter createAdapter()
        {
            return null;
        }

    @Override
    public List<Map> selLogs(Map params)
    {
        return sysLogMapper.selLogsTest(params);
    }


    @Override
    public void insertLogs(List<SysInterfaceLogTest> sysInterfaceLogs,Map params) throws Exception
    {
        //记录数太大,分批次插入表中,并保证事务
        //int i=3000;
        //int j=0;
        List<SysInterfaceLogTest> logs = new ArrayList<SysInterfaceLogTest>();
        for(int i=0;i<sysInterfaceLogs.size();i++){
            logs.add(sysInterfaceLogs.get(i));
            if(i!=0 && i%3000==0){
                sysLogMapper.saveLogTest(logs);
                logger.error("循环插入"+logs.size()+"条");
                logs.clear();
            }
        }
        if(logs.size()>0){
            sysLogMapper.saveLogTest(logs);
            logger.error("插入剩余:"+logs.size()+"条");
            logs.clear();
        }
        sysInterfaceLogs.clear();
        /**
        while(sysInterfaceLogs.size()/i>=1){//每满3000执行插入
            logs=sysInterfaceLogs.subList(j,i);
            sysLogMapper.saveLogTest(logs);
            logger.error("循环插入"+logs.size()+"条");
            j=i;
            i+=3000;
        }
        if(j<sysInterfaceLogs.size()){
            logs=sysInterfaceLogs.subList(j,sysInterfaceLogs.size());//不足1000的
            sysLogMapper.saveLogTest(logs);
            logger.error("插入剩余:"+logs.size()+"条");
        }
         */
        sysLogMapper.saveResult(params);//插入日志结果记录表
    }


    @Override
    public void logInfoChanges(Map params)
    {
        sysLogMapper.logInfoChanges(params);
    }


    @Override
    public void updateLogInfo(Map params)
    {
        sysLogMapper.updateLogInfo(params);
        sysLogMapper.updateLogError(params);
    }

    /**
     * 读取日志
     * 1)查询分布应用配置表项目路径及对应应用日志读取状态
     * 2)获取路径读取昨天的日志
     * @throws Exception
     */
    public void readLogs() throws Exception{
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        datetime=sf.format(c.getTime()).replaceAll("-","");
        Map params=new HashMap();
        params.put("tablename","sys_interface_log_"+datetime);
        try
        {
            params.put("logTime",sf.format(c.getTime()));
            List<Map> webPaths=sysLogMapper.selApplicationPaths(params);
            String log=File.separator+"mdminfo_"+sf.format(c.getTime())+".log";
            for(Map map:webPaths){
                webpath=map.get("application_path")==null?"":map.get("application_path").toString();
                String filepath=map.get("application_path")+log;
                logger.error("准备读取日志文件："+filepath);
                File file=new File(filepath);
                map.put("tablename",params.get("tablename"));
                if(map.get("status")==null || !"1".equals(map.get("status").toString()))//未读取成功继续读取
                {
                    this.readResource(file, map);//读取单个项目日志
                }
            }
            //所有路径下web项目日志读取完毕,换表,跟新日志级别
            System.out.println("表名："+params.get("tablename"));
            /***以下sql为同一个事务**/
            logInfoChanges(params);
            updateLogInfo(params);//加项目路径条件,数据量大了效率会高
            sysLogMapper.saveErrorLog(params);//错误log单独保存到error表
            sysLogMapper.delSyslog(params);//删除sys_log
            logger.error("日志读取完毕");
        } catch (Exception e)
        {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            logger.error("读取日志异常："+stringWriter.getBuffer().toString());
            String content = "\n日志模型入库后转换日志信息失败 : "+"\n机器的ip : " + AppUtil.INSTANCE.getLocalIp()+
                    "<br/><br/>\n报错时间 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                    "<br/><br/>\n===========================异常如下:===========================<br/><br/>" + stringWriter.getBuffer().toString();
            //发送邮件
            asyncNotification.sendErrorEmail("616868374@qq.com", "日志转换失败", content);
            stringWriter.close();
        }
    }

    /**
     * 处理日志记录单个对象的格式
     * @param sourceStr
     * @param rexString
     * @return
     */
    private String rexString(String sourceStr,String rexString){
        return sourceStr.substring(1,sourceStr.length()-1).replace(rexString,"");
    }

    /**
     * 日志读取方法
     * @param file
     * @throws IOException
     */

    public void readResource(File file,Map params) throws Exception
    {
        sysInterfaceLogs=new ArrayList<SysInterfaceLogTest>();
        long fileLength = 0;
        StringBuffer sb=new StringBuffer("");//异常字符串
        StringBuffer row=new StringBuffer("");//被3M读取拆分的行
        boolean flg=false;//是否检测到异常
        final int BUFFER_SIZE = 0x300000;// 3M的缓冲
        fileLength = file.length();
        logger.error("新方式读取日志");
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        MappedByteBuffer inputBuffer=null;
        RandomAccessFile ra=null;
        try {
             ra=new RandomAccessFile(file, "r");
             inputBuffer = ra.getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
                            fileLength);// 读取大文件
            byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容
            for (int offset = 0; offset < fileLength; offset += BUFFER_SIZE) {
                if (fileLength - offset >= BUFFER_SIZE) {//够3M则直接读够3M
                    for (int i = 0; i < BUFFER_SIZE; i++)
                        dst[i] = inputBuffer.get(offset + i);
                } else {//如果不足3M,则直接读取剩余的
                    int m=(int)fileLength-offset;
                    dst = new byte[m];//剩余长度的字节数组
                    for (int i = 0; i < fileLength - offset; i++)
                        dst[i] = inputBuffer.get(offset + i);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(dst),"utf-8"));
                String s = null;
                while((s = br.readLine())!= null)
                {
                    /**
                     //if(s.split("----").length!=6 || !s.endsWith("----") || !s.startsWith("2016") || s.contains("errend")){//固定年份
                     t++;
                     //System.out.println(s);
                     sb.append(s);
                     if(sb.toString().contains("exservererr")){
                     sb.append("\r\n");
                     }
                     if(t%2==0){
                     if(sb.toString().contains("exservererr")){
                     //异常单独处理,向下读取
                     if(sb.toString().contains("errend")){
                     //System.out.println(sb.toString());
                     //填充对象
                     String[] re=sb.toString().split("\r\n");
                     //System.out.println("异常返回时间："+re[re.length-1]);
                     String ss=sb.toString().replace(re[re.length-1],"");
                     fillObject(ss);//填充异常
                     sb=new StringBuffer();
                     }
                     }else{
                     //System.out.println(sb.toString());
                     //填充对象
                     fillObject(sb.toString());
                     sb=new StringBuffer();
                     }
                     }
                     }
                     //处理3M读取行被截断的情况

                     //正常行
                     if(s.split("----").length==6 && s.endsWith("----") && s.startsWith("2016")){
                     fillObject(s);//填充对象
                     }
                     */
                    s=EmojiFilter.removeNonBmpUnicode(s);
                    if (s.contains("exservererr"))
                    {
                        sb.append(s);//拼接异常开始
                        flg = true;
                    }
                    if (flg && !s.contains("exservererr"))
                    {
                        if (!s.contains(year) && !s.contains("----"))
                        {//不含头不含尾(被拆分的行必含一种)
                            sb.append("\r\n");
                            sb.append(s);//拼接异常
                        } else
                        {//异常之后检测到常规行则异常对象结束，进行拼装
                            fillObject(sb.toString());//填充异常对象
                            sb = new StringBuffer();//初始异常字符串
                            flg = false;
                        }
                    }
                    if (!flg)
                    {//常规行
                        if (s.split("----").length == 6 && s.endsWith("----") && s.startsWith(year))
                        {
                            fillObject(s);//填充对象
                        } else
                        {//非常规行
                            row.append(s);
                            if (row.toString().split("----").length == 6 && row.toString().endsWith("----")
                                    && row.toString().startsWith(year))
                            {//拼接之后符合常规
                                fillObject(row.toString());
                                row = new StringBuffer();//初始
                            }
                        }
                    }
                }
                br.close();
            }
            logger.error("共计:"+sysInterfaceLogs.size());
            insertLogs(sysInterfaceLogs,params);//插入日志表
        } catch (Exception e) {
            // TODO Auto-generated catch block
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            logger.error(file.getName()+"日志读取异常："+stringWriter.getBuffer().toString());
            String content = "\n日志模型入库失败 : "+file.getPath()+
                    "<br/><br/>\n报错时间 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                    "<br/><br/>\n===========================异常如下:===========================<br/><br/>" + stringWriter.getBuffer().toString();
            //发送邮件
            asyncNotification.sendErrorEmail("616868374@qq.com", "日志模型入库失败", content);
            stringWriter.close();
        }finally
        {
            if(inputBuffer!=null)
            {
                if(ra!=null){
                    ra.close();
                }
                    //释放系统内存文件资源占用
                Method getCleanerMethod = null;
                try
                {
                    getCleanerMethod = inputBuffer.getClass().getMethod("cleaner", new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner = null;
                    cleaner = (sun.misc.Cleaner)
                            getCleanerMethod.invoke(inputBuffer, new Object[0]);
                    cleaner.clean();
                } catch (NoSuchMethodException e)
                {
                    e.printStackTrace();
                }catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                } catch (InvocationTargetException e)
                {
                   e.printStackTrace();
                }
            }
        }

    }

    private void fillObject(String linestr){
        SysInterfaceLogTest syslog=new SysInterfaceLogTest();
        /**验证读取后文件大小
        try
        {

            File wrfile = new File("E:\\apache-tomcat-7.0.69\\logs\\mdminfo_log\\test\\1.txt");
            FileOutputStream fos = new FileOutputStream(wrfile, true);//追加模式
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    fos, "utf8"));
            writer.write("\r\n");
            writer.write(linestr);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
         */
        String[] strs=linestr.split("----");
        if(linestr.split("----").length!=6){
            System.out.println(linestr);
        }
            syslog.setLogTime(strs[0]);
            syslog.setLogLevel(strs[1]);//默认info
            syslog.setServerIp(rexString(strs[2], ""));
            syslog.setLogId(UUID.randomUUID().toString());
            syslog.setDeviceId(rexString(strs[4], ""));
            syslog.setContainerPath(webpath);
            syslog.setGroupId(rexString(strs[3], ""));
            if (strs[5].contains("requesturi"))
            {
                syslog.setType("1");
                syslog.setInfos(rexString(strs[5],"requesturi:"));
            } else if (strs[5].contains("headparams"))
            {
                syslog.setType("2");
                syslog.setInfos(rexString(strs[5],"headparams:"));
            } else if (strs[5].contains("contentparams"))
            {
                syslog.setType("3");
                syslog.setInfos(rexString(strs[5],"contentparams:"));
            } else if (strs[5].contains("@return"))
            {
                syslog.setType("4");
                syslog.setInfos(rexString(strs[5],"@return:"));
            } else if (strs[5].contains("exservererr"))
            {
                syslog.setType("5");
                syslog.setInfos(rexString(strs[5],"exservererr:"));
            }else if(strs[5].contains("errend")){
                syslog.setType("4");
                syslog.setInfos(rexString(strs[5],"errend"));
            }else{
                logger.error("不合规则的行："+linestr);
            }
        if(strs.length==6)
        {
            sysInterfaceLogs.add(syslog);
        }else{
            logger.error("不符合条件的行："+linestr);
        }
        if(sysInterfaceLogs.size()==3000){//3000条处理一次
            sysLogMapper.saveLogTest(sysInterfaceLogs);
            logger.error("分次循环插入"+sysInterfaceLogs.size()+"条");
            sysInterfaceLogs.clear();
        }
    }


    /**
     * 获取本次应读取的日志列表
    private List<String> getWebPath() throws Exception{
         *根据数据库的日志情况读取日志
         * 1)根据不同web容器查询日志记录最后日期+1天确定读取的log日志

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        Map params=new HashMap();
        params.put("containerPath",realPath());
        List<Map> logdays=sysLogService.selLogs(params);
        List<String> logs=new ArrayList<String>();
        if(logdays.size()==0 || logdays.get(0)==null){//只查昨天的
            logs.add(realPath()+"logs/mdminfo_log/mdminfo_"+sf.format(c.getTime())+".log");
            return logs;
        }
        Date maxdate=sf.parse(logdays.get(0).get("log_time").toString());
        Calendar ca=Calendar.getInstance();
        ca.setTime(maxdate);
        while(c.compareTo(ca)>0){
            ca.add(Calendar.DATE,1);
            logs.add(realPath()+"logs/mdminfo_log/mdminfo_"+sf.format(ca.getTime())+".log");
        }
        if(logs.size()>0)
        {
            logs.remove(logs.size() - 1);
        }
        for(String str:logs){
            logger.error(str);
        }
        return logs;
    }
    */
}
