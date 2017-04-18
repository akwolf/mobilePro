package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.list.ListMobile;
import com.jnzy.mdm.bean.push.PushAppVO;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/7.
 */
public interface ListMapper extends SqlMapper{
    /**
     * 根据时间查询出手机黑名单
     * @param map
     * @return
     * @throws Exception
     */
    List<PushAppVO> selListMobile(Map map) throws Exception;

    /**
     * 查询手机黑名单
     * @param map
     * @return
     * @throws Exception
     */
    List<ListMobile> selMobileList(Map map) throws Exception;
}
