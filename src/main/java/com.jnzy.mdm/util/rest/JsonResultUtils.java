package com.jnzy.mdm.util.rest;

import com.google.common.collect.Maps;
import com.jnzy.mdm.util.mapper.JsonMapper;

import java.util.Map;

public final class JsonResultUtils {

        final static JsonMapper mapper = JsonMapper.buildNormalMapper();

        private JsonResultUtils() {
                throw new Error("Utility classes should not instantiated!");
        }

        /**
         * 构建返回值，默认方式。没有data，只有code和message。Code.Success实现。
         *
         * @return
         */
        public static Map<String, Object> buildResult() {
                Map<String, Object> resultMap = Maps.newHashMap();
                resultMap.put("code", Code.SUCCESS.getCode());
                resultMap.put("message", Code.SUCCESS.getMessage());
                return resultMap;
        }

        /**
         * 构建返回值，自定义code和message。没有data。
         *
         * @param code
         * @param message
         * @return
         */
        public static Map<String, Object> buildResult(Integer code, String message) {
                Map<String, Object> resultMap = Maps.newHashMap();
                resultMap.put("code", code);
                resultMap.put("message", message);
                return resultMap;
        }

        /**
         * 构建返回值，没有data。参数status采用枚举{@link Code}实现
         *
         * @param status
         * @return
         */
        public static Map<String, Object> buildResult(Code status) {
                return buildResult(status.getCode(), status.getMessage());
        }

        /**
         * 构建返回值，有data属性，由传入的object决定
         *
         * @param code    自定义的状态码
         * @param object  返回的数据。javabean
         * @param message 状态消息
         * @return
         */
        public static <T> Map<String, Object> buildResultWithObject(Integer code,
                                                                    Object object, String message) {
                Map<String, Object> resultMap = Maps.newHashMap();
                resultMap.put("code", code);
                resultMap.put("data", object);
                resultMap.put("message", message);
                return resultMap;
        }

        /**
         * 构建返回值，有data属性，由传入的object决定
         *
         * @param object 实际返回值
         * @param status {@link Code}枚举类型作为状态参数
         * @return
         */
        public static <T> Map<String, Object> buildResultWithObject(Object object,
                                                                    Code status) {
                return buildResultWithObject(status.getCode(), object,
                        status.getMessage());
        }

        /**
         * {@link #buildResult()}方法的简化版，直接返回序列化为json的String
         *
         * @param code
         * @param message
         * @return
         */
        public static String buildResultAsString(Integer code, String message) {
                return mapper.toJson(buildResult(code, message));
        }

        /**
         * {@link #buildResult(Code)}方法的简化版，直接返回序列化为json的String
         *
         * @param status
         * @return
         */
        public static String buildResultAsString(Code status) {
                return mapper.toJson(buildResult(status));
        }

        /**
         * {@link #buildResultWithObjectAsString(Integer, Object, String)}
         * 方法的简化版，直接返回序列化为json的String
         *
         * @param code
         * @param object
         * @param message
         * @return
         */
        public static String buildResultWithObjectAsString(Integer code,
                                                           Object object, String message) {
                return mapper.toJson(buildResultWithObject(code, object, message));
        }

        /**
         * {@link #buildResultWithObjectAsString(Object, Code)}
         * 方法的简化版，直接返回序列化为json的String
         *
         * @param object
         * @param status
         * @return
         */
        public static String buildResultWithObjectAsString(Object object,
                                                           Code status) {
                return mapper.toJson(buildResultWithObject(object, status));
        }

        /**
         * 将传入对象转换为json字符串
         *
         * @param obj
         * @return
         */
        public static String toJson(Object obj) {
                return mapper.toJson(obj);
        }

        public enum Code {
                SUCCESS(200, "操作成功！"), //
                ERROR(500, "对不起，操作出错！"), //
                IMGCODE(499, "验证码错误"),//
                NOTFOUND(404, "对不起，您请求的资源不存在！"), //
                DUPLICATE(302, "重复操作！"), //
                UNAUTHORIZED(401, "您未登录或登录过期，请重新登录！"), //
                FORBIDDEN(403, "对不起，拒绝访问！"), //
                FORBIDDEN_TOOMANY(402, "访问频率过高，服务拒绝请求！"), //
                FAIL(412, "请求失败，请检查输入！"), //
                ILLEGA(422, "请求服务参数异常！");
                private final int code;
                private final String message;

                private Code(int code, String message) {
                        this.code = code;
                        this.message = message;
                }

                public String getMessage() {
                        return message;
                }

                public int getCode() {
                        return code;
                }

        }
}
