package com.jnzy.mdm.dao.persistence;

import com.jnzy.mdm.bean.browser.BookMarkVO;
import com.jnzy.mdm.bean.other.AppCityVO;
import com.jnzy.mdm.bean.other.WebsiteNavigationVO;
import com.jnzy.mdm.dao.SqlMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2017/2/20.
 */
public interface BrowserMapper extends SqlMapper{
    /**
     *获取城市
     * @return
     * @throws Exception
     */
    List<AppCityVO> selCityProvince(Map map) throws Exception;

    /**
     * 根据城市获取天气预报
     */
    String  selCityCode(Map map) throws Exception;

    /**
     * 首页中的网址导航
     * @return
     * @throws Exception
     */
    List<WebsiteNavigationVO> websiteNavigationHome(Map map) throws Exception;

    /**
     * 根据标识查询是否存在改数据
     * @return
     * @throws Exception
     */
    Integer selBookmarkByIdent(@Param("bookMarkVO")BookMarkVO bookMarkVO) throws Exception;
    /**
     * 更新数据
     * @return
     * @throws Exception
     */
    Integer updateBookmark(@Param("bookMarkVO")BookMarkVO bookMarkVO) throws Exception;
    /**
     * 插入书签数据
     * @return
     * @throws Exception
     */
    Integer insertBookmark(@Param("bookMarkVOList")List<BookMarkVO> bookMarkVOList) throws Exception;
    /**
     * 删除书签数据
     * @return
     * @throws Exception
     */
    Integer delBookmark(Map map) throws Exception;

    /**
     * 查询书签数据
     * @param map
     * @return
     * @throws Exception
     */
    List<BookMarkVO> selBookmarkByUserId(Map map) throws Exception;
}
