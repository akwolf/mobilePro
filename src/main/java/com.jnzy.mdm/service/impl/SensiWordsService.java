package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.sensiWords.SensitiveVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.dao.persistence.RecordMapper;
import com.jnzy.mdm.dao.persistence.SensiWordsMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.ISensiWordsService;
import com.jnzy.mdm.util.ParameterUtil;
import com.jnzy.mdm.util.ServiceProxyResponse;
import com.jnzy.mdm.util.StringUtil;
import com.jnzy.mdm.util.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/2.
 */
@Service
public class SensiWordsService extends BaseService implements ISensiWordsService {
    @Autowired
    private SensiWordsMapper sensiWordsMapper;
    @Autowired
    private RecordMapper recordMapper;


    @Override
    protected IAdapter createAdapter() {
        return null;
    }

    /**
     * 通讯敏感词的获取
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse sensiWordsSms(HttpServletRequest request) throws Exception {
        Map map= ParameterUtil.getMapParamsByRequest(request);
        //设备号
        String deviceId= (String) map.get("deviceId");
        if(StringUtil.isBlank(deviceId)){
            return ServiceProxyResponse.argsError();
        }
        if(StringUtil.isBlank(map.get("addTime")+"")){
            map.put("addTime",null);
        }
        //  查询出该所有敏感词
        List<SensitiveVO> sensitiveSmsVOList=sensiWordsMapper.selSensitiveSms(map);
        if(sensitiveSmsVOList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(sensitiveSmsVOList,"keywords","addTime","isDel"));
    }

    /**
     * 网址敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse sensiWordsWebPage(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        //设备号
        if(StringUtil.isBlank(map.get("deviceId")+"")){
            return ServiceProxyResponse.argsError();
        }
        if(StringUtil.isBlank(map.get("addTime")+"")){
            map.put("addTime",null);
        }

//        以下代码先注释掉 等有db方式后在加上
//        Map counts=sensiWordsMapper.selSensitiveWebCount(map);
//        if(Integer.valueOf(counts.get("num").toString())>MAX_NUM){//大于200条则使用db方式返回数据量太大
//            return ServiceProxyResponse.error(BusinessDataCode.BIG_DATA);
//        }
        //  查询出该所有上网敏感词
        List<SensitiveVO> sensitiveWebVOList=sensiWordsMapper.selSensitiveWebPage(map);
        if(sensitiveWebVOList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(sensitiveWebVOList,"keywords","addTime","isDel"));
    }

    /**
     *文件敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse sensiWordsFilePage(HttpServletRequest request) throws Exception {
        Map map =ParameterUtil.getMapParamsByRequest(request);
        //设备号
        String deviceId= (String) map.get("deviceId");
        if(StringUtil.isBlank(deviceId)){
            return ServiceProxyResponse.argsError();
        }
        if(StringUtil.isBlank(map.get("addTime")+"")){
            map.put("addTime",null);
        }
//        //根据设备号查询出该组织id
//        Integer organId=sensiWordsMapper.selOrganIdByDeviceId(deviceId);
//        if(StringUtil.isBlank(organId+"")){
//            return ServiceProxyResponse.argsError();
//        }
//        map.put("organId",organId);
//        List<PushAppVO> senFileVOList = sensiWordsMapper.selSensitiveFileInfo(map);
//        if(senFileVOList.size()==0){
//            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
//        }
//
//        String[] keys={"keywords","addTime","isDel"};
//        List<String> values = new ArrayList<String>();
//        for(PushAppVO pushAppVO:senFileVOList){
//            String pushMsg = pushAppVO.getPushMsg();
//            if(pushMsg.contains("keywords")){
//                JSONObject jsonObject = JSONObject.fromObject(pushMsg);
//                String keywords=jsonObject.getString("keywords");
//                values.add(keywords);
//                values.add(pushAppVO.getAddTime());
//                //是否删除 0:否1:是
//                String isDel=pushAppVO.getIsDel()+"";
//                //是否使用 0使用，锁屏  1禁止，解锁
//                if(pushAppVO.getIsUse()==1){
//                    isDel="1";
//                }
//                values.add(isDel);
//            }
//        }
//        if(values.size()==0){
//            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
//        }
//        return ServiceProxyResponse.success(Property2JsonUtil.property2JsonArray(keys,values));

//以下代码先注释掉 等有db方式后在加上
//        Map counts=sensiWordsMapper.selSensitiveFileCount(map);
//        if(Integer.valueOf(counts.get("num").toString())>MAX_NUM){//大于200条则使用db方式返回数据量太大
//            return ServiceProxyResponse.error(BusinessDataCode.BIG_DATA);
//        }

        //  查询出该所有文件敏感词
        List<SensitiveVO> sensitiveFileVOList=sensiWordsMapper.selSensitiveFilePage(map);
        if(sensitiveFileVOList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(sensitiveFileVOList,"keywords","addTime","isDel"));
    }



}
