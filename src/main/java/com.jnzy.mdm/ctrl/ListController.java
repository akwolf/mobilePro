package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.IListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**各种名单表
 * Created by hardy on 2016/6/7.
 */
@Controller
@RequestMapping("list")
public class ListController {
    @Autowired
    private IListService listService;

    /**
     * 获取手机黑名单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selListMobile")
    private Object selListMobile(HttpServletRequest request) throws Exception {
        return listService.selListMobile(request);
    }
}
