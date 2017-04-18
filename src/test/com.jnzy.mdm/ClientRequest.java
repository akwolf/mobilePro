package com.jnzy.mdm;

import java.io.File;

public class ClientRequest{
    /**
     *
     */
    private static final long serialVersionUID = 6762586843597574118L;
    private String appid;
    private String appType = "2";
    private String version;

    private String headerKey;
    private String headerSign;
    private String JSESESSION;
    private String params;
    /*上传的文件*/
    private File file;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getJSESESSION() {
        return JSESESSION;
    }

    public void setJSESESSION(String jSESESSION) {
        JSESESSION = jSESESSION;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public String getHeaderSign() {
        return headerSign;
    }

    public void setHeaderSign(String headerSign) {
        this.headerSign = headerSign;
    }
}