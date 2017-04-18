package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.market.MarketAppCategoryVO;
import com.jnzy.mdm.bean.market.MarketAppVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.persistence.MarketMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IMarketService;
import com.jnzy.mdm.util.ParameterUtil;
import com.jnzy.mdm.util.Property2JsonUtil;
import com.jnzy.mdm.util.ServiceProxyResponse;
import com.jnzy.mdm.util.StringUtil;
import com.jnzy.mdm.util.mapper.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by hardy on 2016/5/20.
 */
@Service
public class MarketService extends BaseService implements IMarketService {

    @Autowired
    private MarketMapper marketMapper;

    /**
     * 获取应用市场分类
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse marketClass() throws Exception {
        JSONArray jsonArray=new JSONArray();
        //获取0级
        List<MarketAppCategoryVO> marketAppCategoryVOList = marketMapper.selMarketClass(0);
        if(marketAppCategoryVOList.size()==0||marketAppCategoryVOList.toString().equals("[]")||marketAppCategoryVOList.toString().equals("[{}]")){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        //获取1级
        for(MarketAppCategoryVO marketVO:marketAppCategoryVOList){
            JSONObject jsonObject=new JSONObject();
            List<MarketAppCategoryVO> marketChildVOList=marketMapper.selMarketClass(marketVO.getCategoryId());
            if(marketChildVOList.size()==0){
                jsonObject.put("marketChildeList", "[]");
            }else {
                String[] needAddDomain={"categoryImg"};
                jsonObject.put("marketChildeList", ObjectMapper.mapListOnlyCont(marketChildVOList,Arrays.asList(needAddDomain),"categoryId","categoryName","categoryImg"));
            }
            jsonObject.put("categoryId",marketVO.getCategoryId());
            jsonObject.put("categoryName",marketVO.getCategoryName());
            jsonArray.add(jsonObject);
        }

        return ServiceProxyResponse.success(jsonArray);
    }

    /**
     * 获取分类下的列表
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse marketClassList(HttpServletRequest request) throws Exception {
        Map map= ParameterUtil.getMapParamsByRequest(request);
        Integer categoryId=Integer.parseInt(map.get("categoryId")+"");
        String type= (String) map.get("type");
        if(StringUtil.isBlank(categoryId+"")||StringUtil.isBlank(type)){
            return ServiceProxyResponse.argsError();
        }
        JSONObject jsResultObj=new JSONObject();
        //根据categoryId获取子分类名称
        List<String> typeList=marketMapper.selMarketChild(categoryId);
        if(typeList.size()==0){
            jsResultObj.put("typeObj","[\"全部\"]");
        }else {
            typeList.add("全部");
            jsResultObj.put("typeObj",JSONArray.fromObject(typeList));
        }
        if(type.equals("全部")){
            map.put("categoryName",null);
        }else {
            map.put("categoryName",type);
            //根据软件分类名称查询id
            categoryId= marketMapper.selMarketIdByName(type);
            map.put("categoryId",categoryId);

        }
        //根据categoryId获取列表
        List<MarketAppVO> marketAppVOList=marketMapper.selMarketApp(map);
        if(marketAppVOList.size()==0){
            jsResultObj.put("marketAppList","[]");
        }else{
            JSONArray jsonArray=new JSONArray();
            for(MarketAppVO marketAppVO:marketAppVOList){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("appId",marketAppVO.getAppId()+"");
                jsonObject.put("softname",marketAppVO.getSoftname());
                jsonObject.put("icon",StringUtil.addHttp2UriStart(marketAppVO.getIcon()));
                jsonObject.put("description",StringUtil.isNull(marketAppVO.getDescription())?"":marketAppVO.getDescription());
                jsonObject.put("version",marketAppVO.getVersion());
                jsonObject.put("downUrl",StringUtil.isNull(marketAppVO.getDownUrl())?"":StringUtil.addHttp2UriStart(marketAppVO.getDownUrl()));
                jsonObject.put("appSize",StringUtil.isNull(marketAppVO.getAppSize())?"":marketAppVO.getAppSize());
                jsonObject.put("appPackage",StringUtil.isNull(marketAppVO.getPackageStr())?"":marketAppVO.getPackageStr());
                jsonObject.put("downloadNum",marketAppVO.getDownloadNum()+"");
                jsonArray.add(jsonObject);
            }
            jsResultObj.put("marketAppList",jsonArray);
        }
        return ServiceProxyResponse.success(jsResultObj);
    }

    /**
     * app详情
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse marketAppDetail(HttpServletRequest request) throws Exception {
        Map map=ParameterUtil.getMapParamsByRequest(request);
        //app的id
        if(StringUtil.isBlank(map.get("appId")+"")){
            return ServiceProxyResponse.argsError();
        }
        Integer appId=Integer.parseInt(map.get("appId")+"");
        //根据appId获取app详情
        MarketAppVO marketAppVO=marketMapper.selAppDetailById(appId);
        if(marketAppVO==null){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        //根据appId获取app的图片
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("softname",marketAppVO.getSoftname());
        jsonObject.put("icon",StringUtil.addHttp2UriStart(marketAppVO.getIcon()));
        jsonObject.put("description",StringUtil.isNull(marketAppVO.getDescription())?"":marketAppVO.getDescription());
        jsonObject.put("version",marketAppVO.getVersion());
        jsonObject.put("downloadNum",marketAppVO.getDownloadNum()+"");
        jsonObject.put("downUrl",StringUtil.isNull(marketAppVO.getDownUrl())?"":StringUtil.addHttp2UriStart(marketAppVO.getDownUrl()));
        jsonObject.put("appSize",StringUtil.isNull(marketAppVO.getAppSize())?"":marketAppVO.getAppSize());
        jsonObject.put("appPackage",StringUtil.isNull(marketAppVO.getPackageStr())?"":marketAppVO.getPackageStr());
        //根据appId获取app的原图
        List<String> imgList=marketMapper.selMarketImgList(appId);
        if(imgList.size()==0){
            jsonObject.put("imgList","[]");
        }else{
            List<String> imgLastList=new ArrayList<String>();
            for(int i=0;i<imgList.size();i++){
                imgLastList.add(StringUtil.addHttp2UriStart(imgList.get(i)));
            }
            jsonObject.put("imgList",JSONArray.fromObject(imgLastList).toString());
        }

        return ServiceProxyResponse.success(jsonObject.toString());
    }

    /**
     *搜索app名
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse searchMarketApp(HttpServletRequest request) throws Exception {
        Map map= ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank((String)map.get("softname"))){
            return ServiceProxyResponse.argsError();
        }
        String softname=(String)map.get("softname");
        if(softname.contains("'")){
            map.put("softname",softname.replace("'",""));
        }
        if(softname.contains("\\")){
            map.put("softname",softname.replace("\\",""));
        }
        //根据app名获取app信息
        List<MarketAppVO> marketAppVOList=marketMapper.selMarketAppName(map);
        if(marketAppVOList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        JSONArray jsonArray=new JSONArray();
        for(MarketAppVO marketAppVO:marketAppVOList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("appId",marketAppVO.getAppId()+"");
            jsonObject.put("softname",marketAppVO.getSoftname());
            jsonObject.put("icon",StringUtil.addHttp2UriStart(marketAppVO.getIcon()));
            jsonObject.put("description",StringUtil.isNull(marketAppVO.getDescription())?"":marketAppVO.getDescription());
            jsonObject.put("version",marketAppVO.getVersion());
            jsonObject.put("downUrl",StringUtil.isNull(marketAppVO.getDownUrl())?"":StringUtil.addHttp2UriStart(marketAppVO.getDownUrl()));
            jsonObject.put("appSize",StringUtil.isNull(marketAppVO.getAppSize())?"":marketAppVO.getAppSize());
            jsonObject.put("appPackage",StringUtil.isNull(marketAppVO.getPackageStr())?"":marketAppVO.getPackageStr());
            jsonObject.put("downloadNum",marketAppVO.getDownloadNum()+"");
            jsonObject.put("categoryId",marketAppVO.getCategoryId()+"");
            jsonObject.put("categoryName",marketAppVO.getCategoryName());
            jsonArray.add(jsonObject);
        }
        return ServiceProxyResponse.success(jsonArray);
    }

    /**
     * 统计下载次数
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse updateAppDownNum(HttpServletRequest request) throws Exception {
        Map map=ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("appId")+"")){
            return ServiceProxyResponse.argsError();
        }
        //给该应用下载次数+1
        Integer updateNum=marketMapper.updateAppDownNum(map);
        System.out.println("*************调用统计下载次数更新了"+updateNum+"条记录");
        if(updateNum==0){
            return ServiceProxyResponse.argsError();
        }else{
            return ServiceProxyResponse.success();
        }
    }

    /**
     *可升级的app列表
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse updateAppList(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getDecodeParamsMapByJSON(request);
        String packageNameList=  map.get("packageNameList")+"";
        if(StringUtil.isBlank(packageNameList)){
            return ServiceProxyResponse.argsError();
        }
        JSONArray packageArr=JSONArray.fromObject(packageNameList);
        if(packageArr.size()==0){
            return ServiceProxyResponse.argsError();
        }
        JSONArray jsonArr = new JSONArray();
        for(int i=0;i<packageArr.size();i++){
            JSONObject packageObject= (JSONObject) packageArr.get(i);
            String packageName= (String) packageObject.get("packName");
            String version= (String) packageObject.get("version");

            //根据包名获取app详情
            MarketAppVO marketAppVO = marketMapper.selMarketAppInfoByName(packageName);
            if(marketAppVO!=null){
                System.out.println("&&&&&&&&&&客户端上传版本:"+version+"@@@@@@@@@@@@@@@数据库中版本:"+marketAppVO.getVersion());
                if(!version.equals(marketAppVO.getVersion())){
                    System.out.println("----------------------------需要版本升级");
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("appId",marketAppVO.getAppId()+"");
                    jsonObject.put("softname",marketAppVO.getSoftname());
                    jsonObject.put("icon",StringUtil.addHttp2UriStart(marketAppVO.getIcon()));
                    jsonObject.put("description",StringUtil.isNull(marketAppVO.getDescription())?"":marketAppVO.getDescription());
                    jsonObject.put("version",marketAppVO.getVersion());
                    jsonObject.put("downUrl",StringUtil.isNull(marketAppVO.getDownUrl())?"":StringUtil.addHttp2UriStart(marketAppVO.getDownUrl()));
                    jsonObject.put("appSize",StringUtil.isNull(marketAppVO.getAppSize())?"":marketAppVO.getAppSize());
                    jsonObject.put("appPackage",StringUtil.isNull(marketAppVO.getPackageStr())?"":marketAppVO.getPackageStr());
                    jsonObject.put("downloadNum",marketAppVO.getDownloadNum()+"");
                    jsonObject.put("categoryId",marketAppVO.getCategoryId()+"");
                    jsonObject.put("categoryName",marketAppVO.getCategoryName());
                    jsonArr.add(jsonObject);
                }
            }
        }
        if(jsonArr.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(jsonArr);
    }

    /**
     * 获取所有应用市场的包名
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selMarketPackage(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("deviceId")+"")){
            return ServiceProxyResponse.argsError();
        }
        List<String> packageList = marketMapper.selMarketPackage();
        if(packageList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        String[] keys={"appPackage"};
        return ServiceProxyResponse.success(Property2JsonUtil.property2JsonArray(keys,packageList));
    }

    @Override
    protected IAdapter createAdapter() {
        return null;
    }

    /**
     * 获取所有应用市场的包名 分页
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selMarketPackagePage(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("deviceId")+"")){
            return ServiceProxyResponse.argsError();
        }
        List<String> packageList = marketMapper.selMarketPackagePage();
        if(packageList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        String[] keys={"appPackage"};
        return ServiceProxyResponse.success(Property2JsonUtil.property2JsonArray(keys,packageList));
    }

    /**
     * 应用市场意见反馈
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertMarketFeedback(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("deviceId")+"")||StringUtil.isBlank(map.get("feedbackContent")+"")||StringUtil.isBlank(map.get("feedbackContact")+"")){
            return ServiceProxyResponse.argsError();
        }
        map.put("userId",((UserVO) request.getAttribute("userVO")).getUserId());
        marketMapper.insertMarketFeedback(map);
        return ServiceProxyResponse.success();
    }
}
