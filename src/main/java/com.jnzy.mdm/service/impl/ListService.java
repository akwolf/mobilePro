package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.list.ListMobile;
import com.jnzy.mdm.bean.push.PushAppVO;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.dao.persistence.ListMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IListService;
import com.jnzy.mdm.util.ParameterUtil;
import com.jnzy.mdm.util.Property2JsonUtil;
import com.jnzy.mdm.util.ServiceProxyResponse;
import com.jnzy.mdm.util.StringUtil;
import com.jnzy.mdm.util.mapper.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/7.
 */
@Service
public class ListService extends BaseService implements IListService{
    @Autowired
    private ListMapper listMapper;

    /**
     * 获取手机黑名单
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selListMobile(HttpServletRequest request) throws Exception {
        Map map=ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("addTime")+"")) {
            map.put("addTime", null);
        }
        //查询手机黑名单
        List<ListMobile> mobileList = listMapper.selMobileList(map);
        if(mobileList.size()==0){
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<mobileList.size();i++){
            ListMobile listMobile = mobileList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number",listMobile.getNumber());
            jsonObject.put("addTime",listMobile.getAddTime());
            jsonObject.put("isDel",listMobile.getIsDel());
            jsonArray.add(jsonObject);
        }
        return ServiceProxyResponse.success(jsonArray);
    }

    @Override
    protected IAdapter createAdapter() {
        return null;
    }
}
