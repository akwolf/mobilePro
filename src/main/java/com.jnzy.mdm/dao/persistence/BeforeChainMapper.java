package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.comm.AppUpdateVO;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/5/20.
 */
public interface BeforeChainMapper extends SqlMapper{
    /**
     * 检测版本号
     * @return
     */
    AppUpdateVO checkVersionCode(Map map);

    /**
     * 获取最新的版本号地址
     * @return
     */
    AppUpdateVO selNewDownUrl(Map map);
    /**
     * proxy     demo     contacts    获取客户端升级地址
     * @return
     */
    List<AppUpdateVO> selAppUrlSystem();
}
