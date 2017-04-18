package com.jnzy.mdm.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证版本号
 * Created by yyj on 2016/5/20.
 */
public interface IBeforeChainService {
    boolean checkVersionCode(HttpServletRequest request) throws Exception;
    String selNewDownUrl(HttpServletRequest request) throws Exception;
}
