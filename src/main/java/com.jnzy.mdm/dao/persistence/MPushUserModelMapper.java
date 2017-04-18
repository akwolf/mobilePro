package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.other.MAppPushUsermodelTag;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yxm on 2017/1/12.
 */
public interface MPushUserModelMapper extends SqlMapper {

    List<Map<String,Object>> selUserModel() throws Exception;

    Integer saveUserModelResults(List<MAppPushUsermodelTag> mAppPushUsermodelTags) throws Exception;
}
