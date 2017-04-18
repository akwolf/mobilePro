package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.ISensiWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**敏感词
 * Created by hardy on 2016/6/2.
 */
@Controller
@RequestMapping("sensiWords")
public class SensiWordsController {
    @Autowired
    private ISensiWordsService sensiWordsService;


    /**
     * 通讯敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "sensiWordsSms")
    private Object sensiWordsSms(HttpServletRequest request)throws Exception{
        return sensiWordsService.sensiWordsSms(request);
    }

    /**
     * 网址敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "sensiWordsWebPage")
    private Object sensiWordsWebPage(HttpServletRequest request)throws Exception{
        return sensiWordsService.sensiWordsWebPage(request);
    }
    /**
     * 文件敏感词的获取 分页
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "sensiWordsFilePage")
    private Object sensiWordsFilePage(HttpServletRequest request)throws Exception{
        return sensiWordsService.sensiWordsFilePage(request);
    }
}
