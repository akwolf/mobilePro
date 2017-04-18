package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**上传文件类
 * Created by hardy on 2016/8/2.
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private IFileService fileService;


//    /**
//     * 文件上传
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @ResponseBody
//    @RequestMapping(value = "insertFileRemote")
//    public Object insertFileRemote(HttpServletRequest request) throws Exception{
//        return fileService.insertFileRemote(request);
//    }


    /**
     * 文件上传
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "insertFileRemote")
    public Object insertFileRemote(HttpServletRequest request,@RequestParam(value="file") MultipartFile[] multipartFiles) throws Exception{
        return fileService.insertFileRemote(request,multipartFiles);
    }

}
