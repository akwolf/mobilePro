package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.mobileButler.*;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.constant.BackUpConstants;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.dao.persistence.MobileButlerMapper;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IMobileButlerService;
import com.jnzy.mdm.util.*;
import com.jnzy.mdm.util.cipher.Base64Util;
import com.jnzy.mdm.util.cipher.CipherUtil;
import com.jnzy.mdm.util.cipher.Des3Util;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.InflaterInputStream;

/**
 * Created by yxm on 2016/9/9.
 */
@Service
public class MobileButlerService extends BaseService implements IMobileButlerService {

    @Autowired
    private MobileButlerMapper mobileButlerMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    protected IAdapter createAdapter() {
        return null;
    }

    @Override
    public ServiceProxyResponse insertContacts(HttpServletRequest request) throws Exception {
        String headerk = request.getHeader(HttpConstants._headerKey);
        if (StringUtil.isNull(headerk)) {
            return ServiceProxyResponse.argsError();
        }
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("md5") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = mobileButlerMapper.selUserInfo(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        map.put("mobile",userVO.getMobile());
        map.put("type",1);
        MBackupRecord mBackupRecord=mobileButlerMapper.selBackupRecord(map);
        if(mBackupRecord!=null &&mBackupRecord.getFileMd5().equals(map.get("md5"))){
            return ServiceProxyResponse.success();//没有改动则直接返回成功
        }
        String fileName = "";
        String filePath = "";
        String destPath="";//解密后目标文件
        String destPath2="";//解密后目标文件
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                if (iter.hasNext()) {//只取一个文件
                    MultipartFile contactsfile = multiRequest.getFile(iter.next());
                    fileName = contactsfile.getOriginalFilename();
                    System.out.println("wenjianming:" + fileName);
                    File dir = new File(DocumentProUtil.getValues("BasePathurl") +
                            DocumentProUtil.getValues("filePath") + File.separator +
                            BackUpConstants.CONTACT_PATH);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    fileName = map.get("deviceId") + "-" + userVO.getMobile() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    byte[] bytes = contactsfile.getBytes();
                    filePath = DocumentProUtil.getValues("BasePathurl") +
                            DocumentProUtil.getValues("filePath") + File.separator +
                            BackUpConstants.CONTACT_PATH + File.separator + fileName;
                    destPath =DocumentProUtil.getValues("BasePathurl") +
                            DocumentProUtil.getValues("filePath") + File.separator +
                            BackUpConstants.CONTACT_PATH + File.separator +"aes" +fileName;
                    destPath2 =DocumentProUtil.getValues("BasePathurl") +
                            DocumentProUtil.getValues("filePath") + File.separator +
                            BackUpConstants.CONTACT_PATH + File.separator +"jiemi" +fileName;
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(bytes);
                    stream.close();
                }
            }
            //以下过程最好改成异步的加快备份时间
            CipherUtil.base64TOaes(filePath,destPath);
            CipherUtil.decrypt(destPath,destPath2,BackUpConstants.KEY);
            File file = new File(destPath2);
            String str = "";
            String str1 = "";
            FileInputStream fis = new FileInputStream(file);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");// InputStreamReader 是字节流通向字符流的桥梁,
            BufferedReader br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            List<MContactsBackup> contacts = new ArrayList<MContactsBackup>();
            while ((str = br.readLine()) != null) {
                str1 += str + "\n";
                if(StringUtil.isNotBlank(str)) {
                    String[] contact = str.split(",");
                    MContactsBackup mContactsBackup = new MContactsBackup();
                    mContactsBackup.setContact(contact[0]);
                    mContactsBackup.setCallNumber(contact[1]);
                    mContactsBackup.setDeviceId(map.get("deviceId").toString());
                    mContactsBackup.setMobile(userVO.getMobile());
                    contacts.add(mContactsBackup);
                }
            }
            // 当读取的一行不为空时,把读到的str的值赋给str1
            System.out.println(str1);// 打印出str1
            br.close();
            isr.close();
            fis.close();
            file.delete();
            String lastName = "";
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 0) {
                lastName = "00";
            }
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 1) {
                lastName = "01";
            }
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 2) {
                lastName = "02";
            }
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 3) {
                lastName = "03";
            }
            Map params = new HashMap();
            params.put(BackUpConstants.TABLE_NAME, BackUpConstants.CONTACTS_TABLE_NAME + lastName);
            map.put(BackUpConstants.TABLE_NAME,params.get(BackUpConstants.TABLE_NAME));
            if(mBackupRecord==null){
                mBackupRecord=new MBackupRecord();
                mBackupRecord.setMobile(userVO.getMobile());
                mBackupRecord.setDeviceId(map.get("deviceId").toString());
                mBackupRecord.setFileMd5(map.get("md5").toString());
                mBackupRecord.setType("1");
                mobileButlerMapper.saveBackupRecord(mBackupRecord);
            }else{
                mBackupRecord.setFileMd5(map.get("md5").toString());
                mobileButlerMapper.updateBackupRecord(mBackupRecord);
                mobileButlerMapper.delContacts(map);//删除原有记录
            }
            params.put("contacts", contacts);
            params.put("key", BackUpConstants.KEY);
            mobileButlerMapper.saveContacts(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceProxyResponse.serviceException();
        }
        return ServiceProxyResponse.success();
    }

    @Override
    public ServiceProxyResponse selContacts(HttpServletRequest request, HttpServletResponse response) throws Exception {//查询通讯录数据
        String headerk = request.getHeader(HttpConstants._headerKey);
        if (StringUtil.isNull(headerk)) {
            return ServiceProxyResponse.argsError();
        }
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        String cipherKey = (String) request.getAttribute(HttpConstants._cipher_key);
        String params = request.getParameter(HttpConstants._params);
        String decodeParams = null;
        try {
            decodeParams = Des3Util.decode(params, cipherKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (decodeParams == null || StringUtil.isBlank(decodeParams.toString())) {
            return ServiceProxyResponse.argsError();
        }
        System.out.println("---------解密参数后------" + decodeParams);
        JSONObject jsonObject = JSONObject.fromObject(decodeParams);
        JSONObject json = jsonObject.getJSONObject("baseParam");
        Map map = new HashMap();
        map.put("deviceId", json.get("deviceId"));
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = mobileButlerMapper.selUserInfo(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        // 下载本地文件
        String fileName = map.get("deviceId") + "-" + userVO.getMobile() + ".txt"; // 文件的默认保存名
        String filePath = DocumentProUtil.getValues("BasePathurl") +
                DocumentProUtil.getValues("filePath") + File.separator +
                BackUpConstants.CONTACT_PATH + File.separator + fileName;
        // 读到流中
        File file2 = new File(filePath);
        FileInputStream inStream = new FileInputStream(file2);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        int count = 0;
        while (count == 0) {
            count = Integer.parseInt("" + file2.length());//in.available();
        }
        byte[] bytes = new byte[count];
        int readCount = 0; // 已经成功读取的字节的个数
        while (readCount <= count) {
            if (readCount == count) break;
            readCount += inStream.read(bytes, readCount, count - readCount);
        }
        inStream.close();
        response.addHeader("Content-Length", bytes.length + "");
        response.getOutputStream().write(bytes);
        return null;
    }

    @Override
    public ServiceProxyResponse insertSms(HttpServletRequest request) throws Exception {
        String headerk = request.getHeader(HttpConstants._headerKey);
        if (StringUtil.isNull(headerk)) {
            return ServiceProxyResponse.argsError();
        }
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("md5") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = mobileButlerMapper.selUserInfo(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        map.put("mobile", userVO.getMobile());
        map.put("type", 2);
        MBackupRecord mBackupRecord = mobileButlerMapper.selBackupRecord(map);
        if (mBackupRecord != null && mBackupRecord.getFileMd5().equals(map.get("md5"))) {
            return ServiceProxyResponse.success();//没有改动则直接返回成功
        }
        String fileName = "";
        String filePath = "";
        String destPath = "";//解密后目标文件
        String destPath2 = "";//解密后目标文件
//        try {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            if (iter.hasNext()) {//只取一个文件
                MultipartFile contactsfile = multiRequest.getFile(iter.next());
                if(contactsfile==null){
                    return ServiceProxyResponse.argsError();
                }

                fileName = contactsfile.getOriginalFilename();
                System.out.println("-------------------ss:" + fileName);
                File dir = new File(DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.SMS_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                fileName = map.get("deviceId") + "-" + userVO.getMobile() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
                byte[] bytes = contactsfile.getBytes();
                filePath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.SMS_PATH + File.separator + fileName;
                destPath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.SMS_PATH + File.separator + "aes" + fileName;
                destPath2 = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + "jiemi" + fileName;
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(bytes);
                stream.close();
            }
            //以下过程最好改成异步的加快备份时间
            CipherUtil.base64TOaes(filePath, destPath);
            CipherUtil.decrypt(destPath, destPath2, BackUpConstants.KEY);
            File file = new File(destPath2);
            String str = "";
            String str1 = "";
            FileInputStream fis = new FileInputStream(file);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");// InputStreamReader 是字节流通向字符流的桥梁,
            BufferedReader br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            List<MSmsBackup> smss = new ArrayList<MSmsBackup>();
            while ((str = br.readLine()) != null) {
                str1 += str + "\n";
            }
            str1=str1.replaceAll("<body>","<body><![CDATA[").replaceAll("</body>","]]></body>");
            Document document = DocumentHelper.parseText(str1);
            Element rootElement = document.getRootElement();
            List data = document.selectNodes("/smss/sms");
            if(data.size()==0){
                return ServiceProxyResponse.argsError();
            }
            for (Object node1 : data) {
                Node node = (Node) node1;
                if ("Element".equals(node.getNodeTypeName())) {
                    MSmsBackup mSmsBackup = new MSmsBackup();
                    Element element = (Element) node;
                    // 读取属性值
                    mSmsBackup.setType(StringUtil.isBlank(element.elementText("type"))?"1":element.elementText("type"));
                    mSmsBackup.setReceiveNumber(StringUtil.isBlank(element.elementText("address"))?"":element.elementText("address"));
                    mSmsBackup.setContent(StringUtil.isBlank(element.elementText("body"))?"":EmojiFilter.removeNonBmpUnicode(element.elementText("body")));
                    mSmsBackup.setAddTime(StringUtil.isBlank(new Date(Long.valueOf(element.elementText("date")))+"")?new Date():new Date(Long.valueOf(element.elementText("date"))));
                    mSmsBackup.setDeviceId(map.get("deviceId").toString());
                    mSmsBackup.setMobile(userVO.getMobile());
                    mSmsBackup.setUserId(userVO.getUserId());
                    mSmsBackup.setOrganId(userVO.getOrganId());
                    if (StringUtil.isBlank(element.elementText("contact"))) {
                        mSmsBackup.setContact("");
                    } else {
                        mSmsBackup.setContact(element.elementText("contact"));
                    }
                    smss.add(mSmsBackup);
                }
            }
            System.out.println(smss.size());
            if(smss.size()<1||smss.toString().equals("[]")){
                return ServiceProxyResponse.argsError();
            }

            br.close();
            isr.close();
            fis.close();
            file.delete();
            String lastName = "";
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 0) {
                lastName = "00";
            }
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 1) {
                lastName = "01";
            }
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 2) {
                lastName = "02";
            }
            if (userVO.getUserId() % BackUpConstants.DIVIDEND == 3) {
                lastName = "03";
            }
            Map params = new HashMap();
            params.put(BackUpConstants.TABLE_NAME, BackUpConstants.SMS_TABLE_NAME + lastName);
            map.put(BackUpConstants.TABLE_NAME, params.get(BackUpConstants.TABLE_NAME));
            if (mBackupRecord == null) {
                mBackupRecord = new MBackupRecord();
                mBackupRecord.setMobile(userVO.getMobile());
                mBackupRecord.setDeviceId(map.get("deviceId").toString());
                mBackupRecord.setFileMd5(map.get("md5").toString());
                mBackupRecord.setType("2");
                mobileButlerMapper.saveBackupRecord(mBackupRecord);
            } else {
                mBackupRecord.setFileMd5(map.get("md5").toString());
                mobileButlerMapper.updateBackupRecord(mBackupRecord);
                mobileButlerMapper.delSmss(map);//删除原有记录
            }
            params.put("smss", smss);
            params.put("key", BackUpConstants.KEY);
            mobileButlerMapper.saveSms(params);
            Map mapParam = new HashMap();
            //插入到短信记录表中
//            for(int i=0;i<smss.size();i++){
//                mapParam.clear();
//                MSmsBackup mSmsBackup=smss.get(i);
//                mapParam.put("number", mSmsBackup.getReceiveNumber());
//                mapParam.put("content", mSmsBackup.getContent());
//                mapParam.put("addTime", mSmsBackup.getAddTime());
//                mapParam.put("type",mSmsBackup.getType());
//                mapParam.put("contact",StringUtil.isBlank(mSmsBackup.getContact())?null:mSmsBackup.getContact());
//                mapParam.put("userId", userVO.getUserId());
//                mapParam.put("oId", userVO.getOrganId());
//                mapParam.put("table", "m_record_sms"+ AppUtil.getYearMonth());
//                recordMapper.insertRecordsms(mapParam);
//            }
            mapParam.put("table", "m_record_sms" + AppUtil.getYearMonth());
            mapParam.put("smss", smss);
            recordMapper.insertRecordsmsList(mapParam);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ServiceProxyResponse.serviceException();
//        }
            return ServiceProxyResponse.success();
        }else{
            return ServiceProxyResponse.argsError();
        }

    }

    @Override
    public ServiceProxyResponse selSms(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String headerk = request.getHeader(HttpConstants._headerKey);
        if (StringUtil.isNull(headerk)) {
            return ServiceProxyResponse.argsError();
        }
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        String cipherKey = (String) request.getAttribute(HttpConstants._cipher_key);
        String params = request.getParameter(HttpConstants._params);
        String decodeParams = null;
        try {
            decodeParams = Des3Util.decode(params, cipherKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (decodeParams == null || StringUtil.isBlank(decodeParams.toString())) {
            return ServiceProxyResponse.argsError();
        }
        System.out.println("---------解密参数后------" + decodeParams);
        JSONObject jsonObject = JSONObject.fromObject(decodeParams);
        JSONObject json = jsonObject.getJSONObject("baseParam");
        Map map = new HashMap();
        map.put("deviceId", json.get("deviceId"));
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = mobileButlerMapper.selUserInfo(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        // 下载本地文件
        String fileName = map.get("deviceId") + "-" + userVO.getMobile() + ".xml"; // 文件的默认保存名
        String filePath = DocumentProUtil.getValues("BasePathurl") +
                DocumentProUtil.getValues("filePath") + File.separator +
                BackUpConstants.SMS_PATH + File.separator + fileName;
        File file2 = new File(filePath);
        // 读到流中
        InputStream inStream = new FileInputStream(file2);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/xml");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        int count = 0;
        while (count == 0) {
            count = Integer.parseInt("" + file2.length());//in.available();
        }
        byte[] bytes1 = new byte[count];
        int readCount = 0; // 已经成功读取的字节的个数
        while (readCount <= count) {
            if (readCount == count) break;
            readCount += inStream.read(bytes1, readCount, count - readCount);
        }
        inStream.close();
        response.addHeader("Content-Length", bytes1.length + "");
        //转换成字符串
        response.getOutputStream().write(bytes1);
        return null;
    }

    /**
     * 上传通话记录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertCall(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String headerk = request.getHeader(HttpConstants._headerKey);
        if (StringUtil.isBlank(headerk)) {
            return ServiceProxyResponse.argsError();
        }
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = mobileButlerMapper.selUserInfo(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String destPath = "";//解密base64后目标文件
        String destPath2 = "";//解密aes后目标文件
        String filePath = "";//客户端传过来的文件
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = mulRequest.getFileNames();
            if (iterator.hasNext()) {
                MultipartFile multipartFile = mulRequest.getFile(iterator.next());
                String fileName = multipartFile.getOriginalFilename();
                byte[] bytes = multipartFile.getBytes();
                filePath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + fileName;
                destPath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + "aes" + fileName;
                destPath2 = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + "jiemi" + fileName;
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(bytes);
                stream.close();
            }
        }
        //以下过程最好改成异步的加快备份时间
        CipherUtil.base64TOaes(filePath, destPath);
        CipherUtil.decrypt(destPath, destPath2, BackUpConstants.KEY);
        //解析文件
        File file = new File(destPath2);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStream = new InputStreamReader(fileInputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStream);
        String str = "";
        String str1 = "";
        List<MCallRecord> callList = new ArrayList<MCallRecord>();
        while ((str = bufferedReader.readLine()) != null) {
            str1 += str + "\n";
            if (StringUtil.isNotBlank(str)) {
                String[] contact = str.split(",");
                //解析
                MCallRecord mCallRecord = new MCallRecord();
                mCallRecord.setContact(StringUtil.isBlank(contact[0]) ? "" : contact[0]);
                mCallRecord.setCallNumber(StringUtil.isBlank(contact[1])?"":contact[1]);
                mCallRecord.setCallTime(StringUtil.isBlank(contact[2])?"":contact[2]);
                mCallRecord.setTimeLength(StringUtil.isBlank(Integer.parseInt(contact[3])+"")?0:Integer.parseInt(contact[3]));
                mCallRecord.setType(StringUtil.isBlank(Integer.parseInt(contact[4])+"")?0:Integer.parseInt(contact[4]));
                mCallRecord.setUserId(userVO.getUserId());
                mCallRecord.setOrganId(userVO.getOrganId());
                callList.add(mCallRecord);
            }
        }
        System.out.println("-------解密出来的内容-------" + str1);
        if(callList.size()<1||callList.toString().equals("[]")){
            return ServiceProxyResponse.argsError();
        }
        bufferedReader.close();
        inputStream.close();
        fileInputStream.close();
        file.delete();
        Map mapParam = new HashMap();
        mapParam.put("table", "m_record_call" + AppUtil.getYearMonth());
        mapParam.put("callList", callList);
        recordMapper.insertRecordCallList(mapParam);


        return ServiceProxyResponse.success();
    }

    /**
     * 上传应用列表记录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertAppList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String headerk = request.getHeader(HttpConstants._headerKey);
        if (StringUtil.isBlank(headerk)) {
            return ServiceProxyResponse.argsError();
        }
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = mobileButlerMapper.selUserInfo(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String destPath = "";//解密base64后目标文件
        String destPath2 = "";//解密aes后目标文件
        String filePath = "";
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = mulRequest.getFileNames();
            if (iterator.hasNext()) {
                MultipartFile multipartFile = mulRequest.getFile(iterator.next());
                String fileName = multipartFile.getOriginalFilename();
                byte[] bytes = multipartFile.getBytes();
                filePath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + fileName;
                destPath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + "aes" + fileName;
                destPath2 = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + "jiemi" + fileName;
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(bytes);
                stream.close();
            }
        }
        //以下过程最好改成异步的加快备份时间
        CipherUtil.base64TOaes(filePath, destPath);
        CipherUtil.decrypt(destPath, destPath2, BackUpConstants.KEY);
        //解析文件
        File file = new File(destPath2);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStream = new InputStreamReader(fileInputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStream);
        String str = "";
        String str1 = "";


        List<MAppListRecord> appListRecords = new ArrayList<MAppListRecord>();
        while ((str = bufferedReader.readLine()) != null) {
            str1 += str + "\n";
            if (StringUtil.isNotBlank(str)) {
                String[] contact = str.split(",");
                System.out.println("--------------" + contact[0] + "@@@@@@@@@@" + contact[1]+"-----"+contact[4].length());
                MAppListRecord mAppListRecord = new MAppListRecord();
                mAppListRecord.setSoftName(StringUtil.isNull(contact[0])?"":contact[0]);
                mAppListRecord.setAppMd5Sign(StringUtil.isBlank(contact[1]) ? "" : contact[1]);
                mAppListRecord.setPackageName(StringUtil.isBlank(contact[2])? "" : contact[2]);
                mAppListRecord.setVersion(StringUtil.isBlank(contact[3])? "" : contact[3]);
                mAppListRecord.setVersionCode(StringUtil.isBlank(contact[4])? "" : contact[4]);
                mAppListRecord.setUserId(userVO.getUserId());
                mAppListRecord.setOrganId(userVO.getOrganId());
                appListRecords.add(mAppListRecord);
            }
        }
        // 当读取的一行不为空时,把读到的str的值赋给str1
        System.out.println("&&&&&&&&&&" + str1);// 打印出str1

        if(appListRecords.size()<1||appListRecords.toString().equals("[]")){
            return ServiceProxyResponse.argsError();
        }

        bufferedReader.close();
        inputStream.close();
        fileInputStream.close();
        file.delete();
//          插入到数据库
        Map mapParam = new HashMap();
        mapParam.put("appListRecords", appListRecords);
        recordMapper.insertRecordCallListByMap(mapParam);

        return ServiceProxyResponse.success();
    }

    /**
     *通讯录文件上传
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse contactFile(HttpServletRequest request) throws Exception {
        String headerk = request.getHeader(HttpConstants._headerKey);
        if (StringUtil.isNull(headerk)) {
            return ServiceProxyResponse.argsError();
        }
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("md5") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = mobileButlerMapper.selUserInfo(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        map.put("mobile", userVO.getMobile());
        map.put("type", 1);
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String dateTime = format.format(new Date());
        MBackupRecord mBackupRecord = mobileButlerMapper.selBackupRecord(map);
        if (mBackupRecord != null && mBackupRecord.getFileMd5().equals(map.get("md5"))) {
            return ServiceProxyResponse.success();//没有改动则直接返回成功
        }
        String fileName = "";
        String filePath = "";
        String destPath = "";//解密后目标文件
        String destPath2 = "";//解密后目标文件
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            if (iter.hasNext()) {//只取一个文件
                MultipartFile contactsfile = multiRequest.getFile(iter.next());
                fileName = contactsfile.getOriginalFilename();
                System.out.println("wenjianming:" + fileName);
                File dir = new File(DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                fileName = map.get("deviceId") + "-" + userVO.getMobile()+"-"+dateTime+ fileName.substring(fileName.lastIndexOf("."), fileName.length());
                byte[] bytes = contactsfile.getBytes();
                filePath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + fileName;
                destPath = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + "aes" + fileName;
                destPath2 = DocumentProUtil.getValues("BasePathurl") +
                        DocumentProUtil.getValues("filePath") + File.separator +
                        BackUpConstants.CONTACT_PATH + File.separator + "jiemi" + fileName;
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(bytes);
                stream.close();
            }
        }
        //以下过程最好改成异步的加快备份时间
        CipherUtil.base64TOaes(filePath, destPath);
        CipherUtil.decrypt(destPath, destPath2, BackUpConstants.KEY);
        File file = new File(destPath2);
        String str = "";
        String str1 = "";
        FileInputStream fis = new FileInputStream(file);// FileInputStream
        // 从文件系统中的某个文件中获取字节
        InputStreamReader isr = new InputStreamReader(fis, "utf-8");// InputStreamReader 是字节流通向字符流的桥梁,
        BufferedReader br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
        List<MContactsBackup> contacts = new ArrayList<MContactsBackup>();
        while ((str = br.readLine()) != null) {
            System.out.println("----------------"+str);
            str1 += str + "\n";
            if (StringUtil.isNotBlank(str)) {
                String[] contact = str.split(",");
                if(contact.length<3){
                    return ServiceProxyResponse.argsError();
                }
                MContactsBackup mContactsBackup = new MContactsBackup();
                mContactsBackup.setContact(StringUtil.isBlank(contact[0]) ? "" : contact[0]);
                mContactsBackup.setAddTime(StringUtil.isBlank(contact[1])?"":contact[1]);
                mContactsBackup.setCallNumber(StringUtil.isBlank(contact[2])?"":contact[2]);
                mContactsBackup.setDeviceId(map.get("deviceId").toString());
                mContactsBackup.setMobile(userVO.getMobile());
                mContactsBackup.setUserId(userVO.getUserId());
                mContactsBackup.setOrganId(userVO.getOrganId());
                contacts.add(mContactsBackup);
            }
        }
        // 当读取的一行不为空时,把读到的str的值赋给str1
        System.out.println("*******************************解析后的值*********"+str1+"****************"+contacts);// 打印出str1
        if(contacts.size()<1||contacts.toString().equals("[]")){
            return ServiceProxyResponse.argsError();
        }
        br.close();
        isr.close();
        fis.close();
//        源文件先不删除
//        file.delete();
        Map mapParam = new HashMap();
        mapParam.put("table", "m_record_im" + AppUtil.getYearMonth());
        mapParam.put("contacts", contacts);
        recordMapper.insertRecordImList(mapParam);
        return ServiceProxyResponse.success();
    }
}
