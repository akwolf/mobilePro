package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.IListService;
import com.jnzy.mdm.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**各种名单表
 * Created by hardy on 2016/6/7.
 */
@Controller
@RequestMapping("record")
public class RecordController {
    @Autowired
    private IRecordService recordService;

    /**
     *通话记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordCall")
    private Object insertRecordCall(HttpServletRequest request) throws Exception {
        return recordService.insertRecordCall(request);
    }
    /**
     *通话拦截记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordCallIntercept")
    private Object insertRecordCallIntercept(HttpServletRequest request) throws Exception {
        return recordService.insertRecordCallIntercept(request);
    }

    /**
     * 通讯录记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordIm")
    private Object insertRecordIm(HttpServletRequest request) throws Exception{
        return recordService.insertRecordIm(request);
    }
    /**
     * 短信记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordsms")
    private Object insertRecordsms(HttpServletRequest request) throws Exception{
        return recordService.insertRecordsms(request);
    }
    /**
     * 短信敏感词拦截记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordsmsSensitive")
    private Object insertRecordsmsSensitive(HttpServletRequest request) throws Exception{
        return recordService.insertRecordsmsSensitive(request);
    }
    /**
     * 应用列表记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordAppList")
    private Object insertRecordAppList(HttpServletRequest request) throws Exception{
        return recordService.insertRecordAppList(request);
    }
    /**
     * 应用使用列表记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordAppUseList")
    private Object insertRecordAppUseList(HttpServletRequest request) throws Exception{
        return recordService.insertRecordAppUseList(request);
    }
    /**
     *客户端上网记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordWebsite")
    private Object insertRecordWebsite(HttpServletRequest request) throws Exception{
        return recordService.insertRecordWebsite(request);
    }
    /**
     *客户端上网敏感词记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordWebsiteSensitive")
    private Object insertRecordWebsiteSensitive(HttpServletRequest request) throws Exception{
        return recordService.insertRecordWebsiteSensitive(request);
    }
    /**
     *客户端上网记录
     * 客户端以list传递
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordWebsiteList")
    private Object insertRecordWebsiteList(HttpServletRequest request) throws Exception{
        return recordService.insertRecordWebsiteList(request);
    }
    /**
     *客户端上网敏感词记录
     * 客户端以list传递
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertRecordWebsiteSensitiveList")
    private Object insertRecordWebsiteSensitiveList(HttpServletRequest request) throws Exception{
        return recordService.insertRecordWebsiteSensitiveList(request);
    }
    /**
     * sd卡违规记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertIllegalSdcard")
    private Object insertIllegalSdcard(HttpServletRequest request) throws Exception{
        return recordService.insertIllegalSdcard(request);
    }
}
