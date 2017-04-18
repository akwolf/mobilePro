package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.market.MarketAppCategoryVO;
import com.jnzy.mdm.bean.market.MarketAppVO;
import com.jnzy.mdm.dao.SqlMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yyj on 2016/5/19.
 */

public interface MarketMapper extends SqlMapper{
    /**
     *获取应用市场分类
     * @return
     */
    List<MarketAppCategoryVO> selMarketClass(@Param("categoryId") Integer categoryId) throws Exception;

    /**
     * 获取分类下的列表
     * @param map
     * @return
     * @throws Exception
     */
    List<MarketAppVO> selMarketApp(Map map) throws Exception;

    /**
     * 根据categoryId获取子分类名称
     * @return
     * @throws Exception
     */
    List<String> selMarketChild(Integer categoryId) throws Exception;

    /**
     * 根据软件分类名称查询id
     * @param categoryName
     * @return
     * @throws Exception
     */
    Integer selMarketIdByName(String categoryName) throws Exception;
    /**
     *根据app名获取app信息
     * @return
     * @throws Exception
     */
    List<MarketAppVO> selMarketAppName(Map map) throws Exception;

    /**
     * 根据appId获取app详情
     * @param appId
     * @return
     * @throws Exception
     */
    MarketAppVO selAppDetailById(Integer appId) throws Exception;
    /**
     *根据appId获取app的图片
     * @return
     * @throws Exception
     */
    List<String> selMarketImgList(Integer appId) throws Exception;

    /**
     * 给该应用下载次数+1
     * @param map
     * @return
     * @throws Exception
     */
    Integer updateAppDownNum(Map map) throws Exception;
    /**
     * 根据包名获取app详情
     * @param packageName
     * @return
     * @throws Exception
     */
    MarketAppVO selMarketAppInfoByName(@Param("packageName") String packageName) throws Exception;

    /**
     *获取所有应用市场的包名
     * @return
     * @throws Exception
     */
    List<String> selMarketPackage() throws Exception;


    /**
     *获取所有应用市场的包名 分页
     * @return
     * @throws Exception
     */
    List<String> selMarketPackagePage() throws Exception;

    /**
     * 应用市场意见反馈
     * @return
     * @throws Exception
     */
    Integer insertMarketFeedback(Map map) throws Exception;

}
