package com.jnzy.mdm.service.impl;

import com.google.gson.Gson;
import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.comm.AppUpdateVO;
import com.jnzy.mdm.bean.other.*;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.dao.persistence.BeforeChainMapper;
import com.jnzy.mdm.dao.persistence.OtherMapper;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.dao.persistence.RedisGeneratorDao;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IOtherService;
import com.jnzy.mdm.thead.notification.AsyncNotification;
import com.jnzy.mdm.util.*;
import com.jnzy.mdm.util.mapper.ObjectMapper;
import com.mysql.fabric.Server;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.sun.tools.internal.xjc.reader.xmlschema.parser.IncorrectNamespaceURIChecker;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hardy on 2016/6/7.
 */
@Service
public class OtherService extends BaseService implements IOtherService {

    @Autowired
    private BeforeChainMapper beforeChainMapper;
    @Autowired
    private OtherMapper otherMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private AsyncNotification async;
    @Autowired
    private RedisGeneratorDao redisDao;


    /**
     * 从数据库中获取客户端升级地址
     *
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selAppUploadUrl(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = (UserVO) request.getAttribute("userVO");

        map.put("modelId", userVO.getModelId());
        AppUpdateVO appUpdateVO = beforeChainMapper.selNewDownUrl(map);

        if (appUpdateVO == null || StringUtil.isNull(appUpdateVO.getVersionCode()) || StringUtil.isNull(appUpdateVO.getDownUrl())) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        } else {
            Integer versionCode = Integer.parseInt(appUpdateVO.getVersionCode());
            Integer versionCodeParam = Integer.parseInt(map.get("paramsCode") + "");
            if (versionCode > versionCodeParam) {

                return ServiceProxyResponse.success(BASE_URL + File.separator + appUpdateVO.getDownUrl());
            } else {
                return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
            }
        }

    }

    /**
     * proxy     demo     contacts
     * 获取客户端升级地址
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selAppUploadUrlSystem(HttpServletRequest request) throws Exception {
        List<AppUpdateVO> appUpdateVOList = beforeChainMapper.selAppUrlSystem();
        JSONObject jsonObject = new JSONObject();
        for (AppUpdateVO appUpdateVO : appUpdateVOList) {
            if (appUpdateVO.getPackageName().equals("me.uniauto.mdm.proxy")) {
                jsonObject.put("proxy", BASE_URL + File.separator + appUpdateVO.getDownUrl());
            } else if (appUpdateVO.getPackageName().equals("me.uniauto.mdm.demo")) {
                jsonObject.put("demo", BASE_URL + File.separator + appUpdateVO.getDownUrl());
            } else if (appUpdateVO.getPackageName().equals("me.uniauto.mdm.contacts")) {
                jsonObject.put("contacts", BASE_URL + File.separator + appUpdateVO.getDownUrl());
            }

        }
        return ServiceProxyResponse.success(jsonObject);
    }

    /**
     * 获取用户在线状态
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse updateUserLastTime(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        if (StringUtil.isNull(map.get("deviceId") + "") || StringUtil.isNull(map.get("imsi") + "")) {
            return ServiceProxyResponse.argsError();
        }
        Map userInfoMap = new HashMap();
        //根据设备号查询出该用户信息
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        if (StringUtil.isBlank(userVO.getImsi())) {
            userInfoMap.put("imsi", map.get("imsi") + "");

        } else {
            userInfoMap.put("imsi", null);
            //手机IMSI不一致  写入到违章记录中
            if (!(map.get("imsi") + "").equals(userVO.getImsi())) {
                //判断数据库中是否有用户id跟imsi的违规记录
                map.put("userId", userVO.getUserId());
                map.put("table", "m_record_illegal" + AppUtil.getYearMonth());
                Integer illegalId = otherMapper.selrecodrdIllegalInfo(map);
                if (illegalId == null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    map.put("addTime", dateFormat.format(new Date()));
                    map.put("illegalType", 6);
                    map.put("oId", userVO.getOrganId());
                    map.put("content", "IMSI变更");
                    map.put("status", 0);
                    recordMapper.insertRecordIllegal(map);
                }
                return ServiceProxyResponse.error(BusinessDataCode.OTHER_IMSI_ERROR);
            }
        }
        userInfoMap.put("userId", userVO.getUserId());
        map.put("userId", userVO.getUserId());
        //如果离线，则立即更改状态为在线
        if (userVO.getIsOnline() == 1) {
            userInfoMap.put("isOnline", 2);
        } else {
            userInfoMap.put("isOnline", null);
        }
        //如果imsi不为null，isOnline不为null
        if (StringUtil.isNotBlank(userInfoMap.get("imsi") + "") || StringUtil.isNotBlank(userInfoMap.get("isOnline") + "")) {
            otherMapper.updateUserImsiOnline(userInfoMap);
        }
        //查看数据库中是否有存在该用户最后一次登录时间
        String createTimeSql = otherMapper.selUserLastTime(userInfoMap);
        if (StringUtil.isBlank(createTimeSql)) {
            //插入用户的最后一次登陆时间
            otherMapper.insertUserLastTime(userInfoMap);
        } else {
            //更新用户的最后一次登陆时间
            otherMapper.updateUserLastTime(map);
        }
        return ServiceProxyResponse.success();
    }

    /**
     * 终端管控中的锁屏
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse deviceLockscreen(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //更新时间倒叙查询出该设备最新一条锁屏状态    0使用，锁屏  1禁止，解锁'
        //1关于策略推送(组、单)，2关于敏感词推送(组)，3关于版本升级推送(群)，
        // 4关于手机黑名单推送(群)，5关于手机锁屏(单)，6锁屏应用（群），
        // 7应用基础配置，8应用黑名单，9应用白名单，10网址敏感词
        map.put("pushModel", 6);
        Integer isUse = otherMapper.selPushLogLockscreen(map);
        if (StringUtil.isBlank(isUse + "")) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isUse", isUse + "");
        return ServiceProxyResponse.success(jsonObject);
    }

    /**
     * 锁屏应用   返回所有可以使用的应用
     *
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse lockscreenApp(HttpServletRequest request) throws Exception {

        /**
         * 2016.10.10  改为直接从数据库表m_app_lockscreen中获取数据
         */
        String[] keys = {"packageName"};
        List<String> appLockscreenList = otherMapper.selByLockscreenApp();
        if (appLockscreenList.size() == 0) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(Property2JsonUtil.property2JsonArray(keys, appLockscreenList));
    }

    /**
     * 应用基础配置
     *
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse appConfig() throws Exception {
        Integer type = otherMapper.selAppConfig();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type + "");
        return ServiceProxyResponse.success(jsonObject.toString());
    }

    /**
     * 应用黑白名单 返回所有可以使用的应用
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse appListBlackWhite(HttpServletRequest request) throws Exception {

        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isNull(map.get("type") + "")) {
            return ServiceProxyResponse.argsError();
        }
        List<Map> packages = otherMapper.selListBlackWhite(map);
        if (packages.size() == 0) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(packages);
    }

    /**
     * 网址基础设置
     *
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse urlConfig(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        //设备号
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备id查询该用户
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        //用户状态,1启用 2禁用
        if (userVO.getStatus() == 2) {
            return ServiceProxyResponse.error(BusinessDataCode.USER_DISABLE);
        }
        //是否删除，0否，1是
        if (userVO.getIsDelete() == 1) {
            return ServiceProxyResponse.error(BusinessDataCode.USER_DELETE);
        }

        Integer type = otherMapper.selUrlConfig();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type + "");
        return ServiceProxyResponse.success(jsonObject.toString());
    }

    /**
     * 网址黑白名单   根据时间正序
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse urlListBlackWhite(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        //设备号
        if (StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        if (StringUtil.isNull(map.get("addTime") + "")) {
            map.put("addTime", null);
        }
        //获取网址黑白名单地址
        List<ListWebsiteVO> packageNameList = otherMapper.selUrlListBlackWhite(map);
        if (packageNameList.size() == 0) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(packageNameList, "websiteUrl", "type", "addTime", "isDel"));
    }

    /**
     * 网址导航数据
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse websiteNavigation(HttpServletRequest request) throws Exception {
        List<WebsiteNavigationVO> websiteNavigationsList = otherMapper.selWebsiteNavigation();
        if (websiteNavigationsList.size() == 0) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        String[] needAddDomain = {"websiteLogo"};
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(websiteNavigationsList, Arrays.asList(needAddDomain), "websiteName", "websiteLogo", "websiteUrl"));
    }

    /**
     * 手机一键定位
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertMobileGps(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isNull(map.get("deviceId") + "") || StringUtil.isNull(map.get("lng") + "") || StringUtil.isNull(map.get("lat") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查出该用户
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        //插入到数据库中
        map.put("userId", userVO.getUserId());
        otherMapper.insertMobileGps(map);
        return ServiceProxyResponse.success();
    }


    /**
     * 客户端电子围栏报警记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertRailIllegal(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isNull(map.get("deviceId") + "") || StringUtil.isNull(map.get("lng") + "") || StringUtil.isNull(map.get("lat") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备号查出该用户
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        MemberRedis memberRedis = redisDao.get(map.get("deviceId") + "");
//        String lngLatBefore =(map.get("lng")+"").trim()+","+(map.get("lat")+"").trim()+","+date.trim();
        long lngLatBefore = dateFormat.parse(date).getTime();
        if (memberRedis == null) {
            System.out.println("*******************redis为空**************");
            MemberRedis memberRedisAfter = new MemberRedis();
            memberRedisAfter.setKeyRedis(map.get("deviceId") + "");
            memberRedisAfter.setValueRedis(lngLatBefore + "");
            redisDao.add(memberRedisAfter);
            map.put("userId", userVO.getUserId());
            map.put("addTime", dateFormat.format(new Date()));
            map.put("table", "m_record_illegal_rail" + AppUtil.getYearMonth());
            map.put("illegalType", 8);
            map.put("oId", userVO.getOrganId());
            map.put("content", (map.get("lng") + "") + "," + (map.get("lat") + ""));
            map.put("status", 0);
            recordMapper.insertRecordIllegal(map);
        }

        if (memberRedis != null) {
            String resultRedis = memberRedis.getValueRedis();
            System.out.println("---redis不为空---resultRedis--------" + resultRedis + "========" + lngLatBefore + "********" + (lngLatBefore - Long.parseLong(resultRedis)));
            if (lngLatBefore / 1000 - Long.parseLong(resultRedis) / 1000 < 90) {
                //记录重复 不需要操作
                return ServiceProxyResponse.success();
            } else {
                MemberRedis memberRedisAfter = new MemberRedis();
                memberRedisAfter.setKeyRedis(map.get("deviceId") + "");
                memberRedisAfter.setValueRedis(lngLatBefore + "");
                redisDao.update(memberRedisAfter);
                map.put("userId", userVO.getUserId());
                map.put("addTime", dateFormat.format(new Date()));
                map.put("table", "m_record_illegal_rail" + AppUtil.getYearMonth());
                map.put("illegalType", 8);
                map.put("oId", userVO.getOrganId());
                map.put("content", (map.get("lng") + "") + "," + (map.get("lat") + ""));
                map.put("status", 0);
                recordMapper.insertRecordIllegal(map);
            }

        }

//        //插入到数据库中
//        map.put("userId",userVO.getUserId());
//        map.put("addTime",date);
//        map.put("table","m_record_illegal_rail"+AppUtil.getYearMonth());
////      判断电子围栏是否重复
//        if(StringUtil.isBlank(recordMapper.selRecordIllegal(map)+"")){
//            map.put("illegalType",8);
//            map.put("oId",userVO.getOrganId());
//            map.put("content",(map.get("lng")+"")+","+(map.get("lat")+""));
//            map.put("status",0);
//            recordMapper.insertRecordIllegal(map);
//        }
        return ServiceProxyResponse.success();
    }

    /**
     * 手机基本信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertMobieUserInfo(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("isRoot") + "")) {
            return ServiceProxyResponse.argsError();
        }
        if (StringUtil.isBlank(map.get("manufacturer") + "")) {
            map.put("manufacturer", null);
        }
        if (StringUtil.isBlank(map.get("mobileos") + "")) {
            map.put("mobileos", null);
        }
        if (StringUtil.isBlank(map.get("sdCapacity") + "")) {
            map.put("sdCapacity", null);
        }
        if (StringUtil.isBlank(map.get("outlaySdCapacity") + "")) {
            map.put("outlaySdCapacity", null);
        }
        if (StringUtil.isBlank(map.get("mac") + "")) {
            map.put("mac", null);
        }
        if (StringUtil.isBlank(map.get("serialNumber") + "")) {
            map.put("serialNumber", null);
        }
        //更新用户手机信息
        otherMapper.updateUserMobileInfo(map);
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        map.put("userId", userVO.getUserId());
        //插入到手机开机跟是否root表
        otherMapper.insertRootTimeLog(map);

        return ServiceProxyResponse.success();
    }


    @Override
    protected IAdapter createAdapter() {
        return null;
    }

    /**
     * 网址黑白名单 分页
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse urlListBlackWhitePage(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isNull(map.get("addTime") + "")) {
            map.put("addTime", null);
        }
        /**
         Map counts=otherMapper.selWebSiteCount(map);
         if(Integer.valueOf(counts.get("num").toString())>MAX_NUM){//大于200条则使用db方式返回数据量太大
         return ServiceProxyResponse.error(BusinessDataCode.BIG_DATA);
         }
         */
        //获取网址黑白名单地址 分页
        List<ListWebsiteVO> packageNameList = otherMapper.selUrlListBlackWhitePage(map);
        if (packageNameList.size() == 0) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(packageNameList, "websiteUrl", "type", "addTime", "isDel"));
    }

    /**
     * (0:网址黑名单1:文件敏感词2:通讯录敏感词3:网址敏感词) DB文件Url
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse DbFileUrls(HttpServletRequest request) throws Exception {
        //type(0:网址黑名单1:文件敏感词2:通讯录敏感词3:网址敏感词4:应用黑名单5:返回应用市场所有包名)
        Map map = ParameterUtil.getDecodeParamsMap(request, String.class, String.class);
        //设备号
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("type") + "")) {
            return ServiceProxyResponse.argsError();
        }
        if (StringUtil.isNull(map.get("addTime") + "")) {
            map.put("addTime", null);
        }
        //根据设备id查询该用户
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        if (userVO == null) {
            return ServiceProxyResponse.argsError();
        }
        //用户状态,1启用 2禁用
        if (userVO.getStatus() == 2) {
            return ServiceProxyResponse.error(BusinessDataCode.USER_DISABLE);
        }
        //是否删除，0否，1是
        if (userVO.getIsDelete() == 1) {
            return ServiceProxyResponse.error(BusinessDataCode.USER_DELETE);
        }
        String[] dbFolders = DB_PATH.split(",");
        if (Integer.valueOf(map.get("type").toString()) >= dbFolders.length) {
            return ServiceProxyResponse.argsError();
        }
        String dbFolder = dbFolders[Integer.valueOf(map.get("type").toString())];
        //查询文件目录,遍历文件名,按日期排序
        File file = new File(BASE_PATH + dbFolder);
        File[] tempList = file.listFiles();
        List<Date> dbName = new ArrayList<Date>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dburls = new ArrayList<String>();
        if (tempList == null || tempList.length == 0) {//没有文件返回空list
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        System.out.println("该目录下对象个数：" + tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文     件：" + tempList[i]);
                String fileName = tempList[i].getName();
                dbName.add(sf.parse(fileName.substring(0, fileName.lastIndexOf("."))));
            }
        }
        Collections.sort(dbName, new Comparator<Date>() {//排序
            public int compare(Date arg0, Date arg1) {
                return arg0.compareTo(arg1);
            }
        });

        for (Date date : dbName) {
            dburls.add(BASE_URL + dbFolder + sf.format(date) + ".db");
        }
        if (map.get("addTime") == null) {//没有更新日期则返回所有
            //返回所有
            return ServiceProxyResponse.success(dburls);
        }
        dburls.clear();
        for (Date date : dbName) {
            if (date.compareTo(sf.parse(map.get("addTime").toString())) > 0) {//只返回大于addTime的数据
                dburls.add(BASE_URL + dbFolder + sf.format(date) + ".db");
            }
        }
        return ServiceProxyResponse.success(dburls);
    }

    /**
     *
     * 记录用户动态ip日志
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertUserIpLog(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("appLocalIp") + "")
                || StringUtil.isBlank(map.get("beginTime") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据设备id查出该用户
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        map.put("userId", userVO.getUserId());
        //查出最后一次用户ip
        UserIpLogVO userIpLogVO = otherMapper.selUserLastIp(map);
        if (userIpLogVO != null && (!(userIpLogVO.getAppLocalIp().equals(map.get("appLocalIp") + "")))) {
            map.put("userIpLogId", userIpLogVO.getId());
            //更改该条记录时间
            otherMapper.updateUserLastIp(map);
            //插入到用户动态ip日志
            otherMapper.insertUserIpLog(map);
        } else if (userIpLogVO != null && (userIpLogVO.getAppLocalIp().equals(map.get("appLocalIp") + ""))) {

        } else {
            //插入到用户动态ip日志
            otherMapper.insertUserIpLog(map);
        }

        return ServiceProxyResponse.success();
    }


    /**
     * 获取手机端文件目录跟文件路径
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertAppCatalog(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getDecodeParamsMapByJSON(request);
        if (StringUtil.isBlank(map.get("catalogJson") + "")) {
            return ServiceProxyResponse.argsError();
        }
        String catalogJson = map.get("catalogJson") + "";
        JSONArray cataArray = JSONArray.fromObject(catalogJson);
        List<AppCatalogVO> appCatalogVOList = new ArrayList<AppCatalogVO>();
        //根据设备号查询用户
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        Integer userId = userVO.getUserId();
        if (cataArray.size() < 1 || cataArray.toString().equals("[]")) {
            return ServiceProxyResponse.argsError();
        }
        //判断级别
        /**
         *  /storage/sdcard1（0级）/DCIM/手机.doc 2级目录
         *  /storage/sdcard0（0级）/DCIM/手机.doc 2级目录
         *  /storage/emulated/0/Music  1级
         *
         */
        int level = 0;
        if ((((JSONObject) (cataArray.get(0))).toString()).contains("catalogFile")) {
            String path = ((JSONObject) (cataArray.get(0))).getString("catalogFile");
            if (path.length() <= 3) {
                return ServiceProxyResponse.argsError();
            }
            if ((path.split("/")[3] + "").equals("0")) {
                level = path.split("/").length - 4;
            } else {
                level = path.split("/").length - 3;
            }
        } else {
            return ServiceProxyResponse.argsError();
        }
        map.put("userId", userId);
        map.put("catalogLevel", level);

        for (int i = 0; i < cataArray.size(); i++) {

            JSONObject cataJson = (JSONObject) cataArray.get(i);
            if (!cataJson.toString().contains("catalogName") || !cataJson.toString().contains("catalogFile")
                    || !cataJson.toString().contains("catalogType") || !cataJson.toString().contains("catalogSize")) {
                return ServiceProxyResponse.argsError();
            }

            AppCatalogVO appCatalogVO = new AppCatalogVO();
            appCatalogVO.setUserId(userId);
            //目录名称
            appCatalogVO.setCurrentCatalogName(cataJson.getString("catalogName"));
            //目录完整路径
            appCatalogVO.setCurrentCatalogFile(cataJson.getString("catalogFile"));
            //类型，0目录，1文件
            appCatalogVO.setCatalogType(Integer.parseInt(cataJson.get("catalogType") + ""));
            appCatalogVO.setCatalogLevel(level);
            //文件大小
            appCatalogVO.setCatalogSize(Long.parseLong(cataJson.get("catalogSize") + ""));
            appCatalogVOList.add(appCatalogVO);
        }
        map.put("list", appCatalogVOList);
        map.put("table", "m_app_catalog" + AppUtil.getYearMonth());
        //将数据插入到手机目录文件表
        otherMapper.insertAppCatalog(map);
        return ServiceProxyResponse.success();
    }

    /**
     * 根据路径上传手机端文件
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertCatalogFile(HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 手机sd卡记录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertSDcardLog(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("isSdcard") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        map.put("userId", userVO.getUserId());
        otherMapper.insertSDcardLog(map);
        return ServiceProxyResponse.success();
    }

    /**
     * 获取手机当前权限
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertUserPermissions(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("deviceId") + "") || StringUtil.isBlank(map.get("permissions") + "")) {
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        map.put("userId", userVO.getUserId());
        otherMapper.insertUserPermissions(map);
        return ServiceProxyResponse.success();
    }

    /**
     * 获取技术客户邮箱
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selEmail(HttpServletRequest request) throws Exception {
        String email = otherMapper.selEmail();
        if (StringUtil.isBlank(email)) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        return ServiceProxyResponse.success(jsonObject);
    }

    /**
     * 定期获取手机推送注册ID
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertPushRegistId(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("registrationId") + "") || StringUtil.isBlank(map.get("deviceId") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据registrationId跟deviceId查询是否存在该条记录
        Integer regId = otherMapper.selPushRegistId(map);
        if (StringUtil.isBlank(regId + "")) {
            otherMapper.insertPushRegistId(map);
        } else {
            //存在记录
            otherMapper.updatePushRegistId(map);
        }
        return ServiceProxyResponse.success();
    }

}
