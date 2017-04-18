package com.jnzy.mdm.ctrl;

import com.jnzy.mdm.util.JnzyLogger;
import com.jnzy.mdm.util.StringUtil;
import com.jnzy.mdm.util.rest.JsonResultUtils;
import com.jnzy.mdm.util.rest.JsonResultUtils.Code;

/**
 * 对controller的公共方法抽象，减少业务controller过多的关注细节
 * Created by hardy on 2016/5/20.
 */
public class BaseController {
    protected static final JnzyLogger _logger = JnzyLogger
            .getLogger(BaseController.class);

    /**
     * 没有找到记录
     *
     * @return
     */
    public Object notFound() {
        return JsonResultUtils.buildResult(Code.NOTFOUND);
    }

    /**
     * 错误
     *
     * @return
     */
    public Object error() {
        return JsonResultUtils.buildResult(Code.ERROR);
    }

    /**
     * 拒绝
     *
     * @return
     */
    public Object forbidden() {
        return JsonResultUtils.buildResult(Code.FORBIDDEN);
    }

    /**
     * 为认证或认证过期，需要登录
     *
     * @return
     */
    public Object unauthorized() {
        return JsonResultUtils.buildResult(Code.UNAUTHORIZED);
    }

    /**
     * 成功，没有返回值。使用默认code:Code.SUCCESS返回状态信息
     *
     * @return
     */
    public Object success() {
        return JsonResultUtils.buildResult(Code.SUCCESS);
    }

    /**
     * 失败，没有返回值。使用默认code.Code.FAIL返回状态信息
     *
     * @return
     */
    public Object fail() {
        return JsonResultUtils.buildResult(Code.FAIL);
    }

    /**
     * 参数验证失败
     *
     * @return
     */
    public Object illegal() {
        return JsonResultUtils.buildResult(Code.ILLEGA);
    }

    /**
     * 返回成功
     *
     * @param obj
     *            service返回结果，如果obj==null，则返回结果不包含obj
     * @return
     */
    public Object success(Object obj) {
        if (obj == null) {
            return success();
        }

        return JsonResultUtils.buildResultWithObject(obj, Code.SUCCESS);
    }

    /**
     * 返回成功，自定义内容和返回结果
     *
     * @param message
     * @param obj
     * @return
     */
    public Object success(String message, Object obj) {
        if (obj == null) {
            return success();
        }
        return JsonResultUtils.buildResultWithObject(Code.SUCCESS.getCode(),
                obj, message);
    }

    /**
     * 错误，自定义错误结果
     *
     * @param msg
     * @return
     */
    public Object error(String msg) {
        if (StringUtil.isBlank(msg)) {
            return error();
        }
        return JsonResultUtils.buildResult(Code.ERROR.getCode(), msg);
    }

    /**
     * 成功，状态码200，状态信息自定义
     *
     * @param msg
     *            状态信息
     * @return
     */
    public Object success(String msg) {
        if (StringUtil.isBlank(msg)) {
            return success();
        }
        return JsonResultUtils.buildResult(Code.SUCCESS.getCode(), msg);
    }

    /**
     * 失败，状态码500,状态信息自定义
     *
     * @param msg
     * @return
     */
    public Object fail(String msg) {
        if (StringUtil.isBlank(msg)) {
            return fail();
        }
        return JsonResultUtils.buildResult(Code.FAIL.getCode(), msg);
    }

    /**
     * 失败，状态码和状态信息自定义
     *
     * @param code
     *            状态码，int
     * @param msg
     *            状态信息
     * @return
     */
    public Object fail(int code, String msg) {
        return JsonResultUtils.buildResult(code, msg);
    }

    /**
     * 失败，状态码为默认，有返回值
     *
     * @param data
     * @param msg
     * @return
     */
    public Object fail(Object data, String msg) {
        return result(data, Code.FAIL.getCode(), msg);
    }

    /**
     * 参数验证失败
     *
     * @param msg
     * @return
     */
    public Object illegal(String msg) {
        return JsonResultUtils.buildResult(Code.ILLEGA.getCode(), msg);
    }

    /**
     * 返回结果包装，自己定义结果状态码
     *
     * @param obj
     *            service返回结果
     * @param code
     *            {@link Code}
     * @return
     */
    public Object result(Object obj, Code code) {
        return JsonResultUtils.buildResultWithObject(obj, code);
    }

    /**
     * 资源不存在
     *
     * @param msg
     * @return
     */
    public Object notFound(String msg) {
        return JsonResultUtils.buildResult(Code.NOTFOUND.getCode(), msg);
    }

    /**
     * 返回结果包装，自定义结果状态码
     *
     * @param obj
     *            service返回结果
     * @param code
     *            {@link int}
     * @param message
     *            {@link String}
     * @return
     */
    public Object result(Object obj, int code, String message) {
        return JsonResultUtils.buildResultWithObject(code, obj, message);
    }

}
