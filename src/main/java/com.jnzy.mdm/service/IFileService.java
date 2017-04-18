package com.jnzy.mdm.service;

import com.jnzy.mdm.util.ServiceProxyResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**上传文件类
 * Created by hardy on 2016/6/7.
 */
public interface IFileService {
    /**
     * 文件上传
     * @return
     * @throws Exception
     */
//    ServiceProxyResponse insertFileRemote(HttpServletRequest request) throws Exception;
    ServiceProxyResponse insertFileRemote(HttpServletRequest request,MultipartFile[] multipartFiles) throws Exception;
}
