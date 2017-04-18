package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.push.PushAppVO;
import com.jnzy.mdm.bean.sensiWords.SensitiveVO;
import com.jnzy.mdm.bean.sensiWords.SensitiveWebVO;
import com.jnzy.mdm.dao.SqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2016/6/2.
 */
public interface SensiWordsMapper extends SqlMapper{

    /**
     *根据设备号查询出该组织id
     * @return
     */
    Integer selOrganIdByDeviceId(String deviceId) throws Exception;

    /**
     * 根据组织id查询出该组织所有敏感词
     * @param map
     * @return
     * @throws Exception
     */
    List<PushAppVO> selSensitiveSmsInfo(Map map) throws Exception;
    /**
     * 根据组织id查询出该组织所有上网敏感词
     * @param map
     * @return
     * @throws Exception
     */
    List<SensitiveWebVO> selSensitiveWebInfo(Map map) throws Exception;
    /**
     * 文件敏感词的获取
     * @param map
     * @return
     * @throws Exception
     */
    List<PushAppVO> selSensitiveFileInfo(Map map) throws Exception;

    /**
     *   查询出该所有短信敏感词 分页
     * @param map
     * @return
     * @throws Exception
     */
    List<SensitiveVO> selSensitiveSms(Map map) throws Exception;
    /**
     *   查询出该所有上网敏感词 分页
     * @param map
     * @return
     * @throws Exception
     */
    List<SensitiveVO> selSensitiveWebPage(Map map) throws Exception;
    /**
     *   查询出该所有文件敏感词 分页
     * @param map
     * @return
     * @throws Exception
     */
    List<SensitiveVO> selSensitiveFilePage(Map map) throws Exception;

    /**
     *   查询出该所有短信敏感词 数量
     * @param map
     * @return
     * @throws Exception
     */
    Map selSensitiveSmsCount(Map map) throws Exception;
    /**
     *   查询出该所有上网敏感词 数量
     * @param map
     * @return
     * @throws Exception
     */
    Map selSensitiveWebCount(Map map) throws Exception;
    /**
     *   查询出该所有文件敏感词 数量
     * @param map
     * @return
     * @throws Exception
     */
    Map selSensitiveFileCount(Map map) throws Exception;

}
