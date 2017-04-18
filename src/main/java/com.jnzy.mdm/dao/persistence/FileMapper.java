package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.push.PushAppVO;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/7.
 */
public interface FileMapper extends SqlMapper{
    /**
     * 保存到定制机远程文件表中
     * @param map
     * @return
     * @throws Exception
     */
    Integer insertFileRemote(Map map) throws Exception;
}
