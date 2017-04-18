package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.comm.AppUpdateVO;
import com.jnzy.mdm.dao.persistence.BeforeChainMapper;
import com.jnzy.mdm.service.BaseService;
import com.jnzy.mdm.service.IAdapter;
import com.jnzy.mdm.service.IBeforeChainService;
import com.jnzy.mdm.util.DocumentProUtil;
import com.jnzy.mdm.util.ParameterUtil;
import com.jnzy.mdm.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * Created by yyj on 2016/5/20.
 */
@Service
public class BeforeChainService extends BaseService implements IBeforeChainService{
    @Autowired
    private BeforeChainMapper beforeChainMapper;
    @Override
    public boolean checkVersionCode(HttpServletRequest request) throws Exception {

        Map map=ParameterUtil.getMapParamsByRequest(request);
        AppUpdateVO appVersionVO=beforeChainMapper.checkVersionCode(map);
        String paramsCode= (String) map.get("paramsCode");
        if(appVersionVO!=null&&paramsCode.equals(appVersionVO.getVersionCode())){
            return true;
        }
        return false;
    }


    /**
     * 获取最新的版本号地址
     * @return
     * @throws Exception
     */
    @Override
    public String selNewDownUrl(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        AppUpdateVO appUpdateVO = beforeChainMapper.selNewDownUrl(map);
        String downUrl="";
        if(appUpdateVO==null||StringUtil.isNull(appUpdateVO.getDownUrl())){
            downUrl="";
        }else{
            downUrl= BASE_URL+ File.separator+appUpdateVO.getDownUrl();
        }

        return downUrl;
    }

    @Override
    protected IAdapter createAdapter() {
        return null;
    }
}
