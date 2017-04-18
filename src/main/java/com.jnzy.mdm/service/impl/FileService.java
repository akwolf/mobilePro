package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.push.PushAppVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.dao.persistence.FileMapper;
import com.jnzy.mdm.dao.persistence.ListMapper;
import com.jnzy.mdm.dao.persistence.PushMapper;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IFileService;
import com.jnzy.mdm.service.IListService;
import com.jnzy.mdm.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 上传文件类
 * Created by hardy on 2016/6/7.
 */
@Service
public class FileService extends BaseService implements IFileService {
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PushMapper pushMapper;

    /**
     * 文件上传
     *
     * @return
     * @throws Exception
     */
//    @Override
//    public ServiceProxyResponse insertFileRemote(HttpServletRequest request) throws Exception {
//        String headerk = request.getHeader(HttpConstants._headerKey);
//        if (StringUtil.isNull(headerk)) {
//            return ServiceProxyResponse.argsError();
//        }
//        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
//        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
//        if (StringUtil.isBlank(map.get("pushId") + "") || StringUtil.isBlank(map.get("type") + "")
//                || StringUtil.isBlank(map.get("deviceId") + "")) {
//            return ServiceProxyResponse.argsError();
//        }
//
//        Integer pushModel = 0;
//        if ((map.get("type") + "").equals("1") || (map.get("type") + "").equals("2")) {
//            pushModel = 10;
//        } else if ((map.get("type") + "").equals("3") || (map.get("type") + "").equals("4")) {
//            pushModel = 11;
//        } else if ((map.get("type") + "").equals("5")) {
//            pushModel = 12;
//        } else {
//            return ServiceProxyResponse.argsError();
//        }
//        map.put("pushModel", pushModel);
//        //判断pushid是否存在
//        Integer pushIdMysql = pushMapper.selPushLogByIdModel(map);
//        if (pushIdMysql == null) {
//            return ServiceProxyResponse.argsError();
//        }
//
//        if (((map.get("type") + "").equals("5")) && StringUtil.isBlank(map.get("fileType") + "")) {
//            return ServiceProxyResponse.argsError();
//        }
//
//        if(StringUtil.isBlank(map.get("fileType") + "")){
//            map.put("fileType",null);
//        }
//
//        UserVO userVO = recordMapper.selUserIdByDeviceId(map);
//        if (userVO == null) {
//            return ServiceProxyResponse.argsError();
//        }
//        map.put("userId", userVO.getUserId());
//
//        //根据设备号查询出用户id
//        //多文件上传
//        // 创建一个通用的多部分解析器
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        //上传图片路径
//        List<String> imgList = new ArrayList<String>();
//
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        List<MultipartFile> fileMap = multipartRequest.getFiles("file");
//        //解析Map
//        for (MultipartFile multipartFile:fileMap) {
//            //获取到原来的文件类型
//            String fileType = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
//            System.out.println("----文件类型————————————————————————:"+fileType);
//
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
////            String dateFile = sdf.format(new Date());
//
//            String relativePath=DocumentProUtil.getValues("filePath")+File.separator + (map.get("deviceId")+"");
//            //服务器上的文件
//            File fileRoot = new File(BASE_PATH+ relativePath);
//            if (!fileRoot.exists()) {
//                fileRoot.mkdirs();
//            }
//            //新文件名
//            String newFileName = timeUid()+AppUtil.getRandom(2) + fileType;
//            System.out.println("保存的文件*****************************:"+fileRoot.getAbsolutePath());
//            //创建服务器上的文件
//            File serverFile = new File(fileRoot.getAbsolutePath(), newFileName);
//            //保存文件
//            multipartFile.transferTo(serverFile);
//            imgList.add(relativePath + File.separator + newFileName);
//        }
//
////       保存到数据库中
//        for (String serverPath : imgList) {
//            map.put("serverPath", serverPath);
//            fileMapper.insertFileRemote(map);
//        }
//
//        return ServiceProxyResponse.success();
//    }


    @Override
    protected IAdapter createAdapter() {
        return null;
    }

    /**
     * 文件上传
     * @param request
     * @param multipartFiles
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertFileRemote(HttpServletRequest request, MultipartFile[] multipartFiles) throws Exception {
        if (StringUtil.isNull(request.getHeader(HttpConstants._headerKey))) {
            return ServiceProxyResponse.argsError();
        }
        String headerk = request.getHeader(HttpConstants._headerKey);
        request.setAttribute(HttpConstants._cipher_key, headerk.substring(0, 8) + HttpConstants._key_end);
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if (StringUtil.isBlank(map.get("pushId") + "") || StringUtil.isBlank(map.get("type") + "")
                || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }

        Integer pushModel = 0;
        if ((map.get("type") + "").equals("1")) {
            pushModel = 10;
        } else if ((map.get("type") + "").equals("3") ) {
            pushModel = 11;
        }else if ((map.get("type") + "").equals("2") ) {
            pushModel = 18;
        } else if ((map.get("type") + "").equals("5")) {
            pushModel = 12;
        }else if ((map.get("type") + "").equals("4")) {
            pushModel = 15;
        } else {
            return ServiceProxyResponse.argsError();
        }
        map.put("pushModel", pushModel);
        //判断pushid是否存在
        Integer pushIdMysql = pushMapper.selPushLogByIdModel(map);
        if (pushIdMysql == null) {
            return ServiceProxyResponse.argsError();
        }

        if (((map.get("type") + "").equals("5")) && StringUtil.isBlank(map.get("fileType") + "")&&StringUtil.isBlank(map.get("fileAppPath") + "")) {
            return ServiceProxyResponse.argsError();
        }


        if(multipartFiles==null||multipartFiles.length<=0){
            return ServiceProxyResponse.argsError();
        }

        if(StringUtil.isBlank(map.get("fileType") + "")){
            map.put("fileType",null);
        }

        map.put("biaoshi",map.get("biaoshi")+"");

        UserVO userVO = recordMapper.selUserIdByDeviceId(map);
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        //用户状态,1启用 2禁用
        if(userVO.getStatus()==2){
            return ServiceProxyResponse.error(BusinessDataCode.USER_DISABLE);
        }
        //是否删除，0否，1是
        if(userVO.getIsDelete()==1){
            return ServiceProxyResponse.error(BusinessDataCode.USER_DELETE);
        }


        map.put("userId", userVO.getUserId());

        //根据设备号查询出用户id
        //多文件上传
        // 创建一个通用的多部分解析器
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //上传服务器图片路径、客户端存放的文件名
        List<Map> imgMapList = new ArrayList<Map>();

//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        List<MultipartFile> fileMap = multipartRequest.getFiles("file");
        //解析Map
        for (int i=0;i<multipartFiles.length;i++) {
            Map imgMap = new HashMap();
            imgMap.clear();
            System.out.println("----————————————————————————");
            MultipartFile multipartFile = multipartFiles[i];
            //获取到原来的文件类型
            String fileType = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            System.out.println("----文件类型————————————————————————:"+fileType);

            String relativePath=DocumentProUtil.getValues("filePath")+File.separator + (map.get("deviceId")+"");
            //服务器上的文件
            File fileRoot = new File(BASE_PATH+ relativePath);
            if (!fileRoot.exists()) {
                fileRoot.mkdirs();
            }
            //新文件名
            String newFileName = timeUid()+AppUtil.getRandom(2) + fileType;
//            String newFileName=multipartFile.getOriginalFilename();
            System.out.println("保存的文件*******:"+multipartFile.getOriginalFilename()+"###:"+new String(multipartFile.getOriginalFilename().getBytes(),"utf-8")+"@@@@："+fileRoot.getAbsolutePath());
            //创建服务器上的文件
            File serverFile = new File(fileRoot.getAbsolutePath(), newFileName);
            //保存文件
            multipartFile.transferTo(serverFile);
            imgMap.put("serverPath",relativePath + File.separator + newFileName);
            imgMap.put("fileAppName",multipartFile.getOriginalFilename());
            imgMapList.add(imgMap);
        }

//       保存到数据库中
        for(Map imgSql:imgMapList){
            map.put("serverPath", imgSql.get("serverPath"));
            map.put("fileAppName", imgSql.get("fileAppName"));
            fileMapper.insertFileRemote(map);
        }

        return ServiceProxyResponse.success();
    }
}
