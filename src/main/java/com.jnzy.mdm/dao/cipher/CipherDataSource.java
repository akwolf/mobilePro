package com.jnzy.mdm.dao.cipher;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.druid.pool.DruidDataSource;
import com.jnzy.mdm.util.JnzyLogger;

@SuppressWarnings("serial")
public class CipherDataSource extends DruidDataSource{
    private JnzyLogger logger = JnzyLogger.getLogger(CipherDataSource.class);
    @Override
    public void setPassword(String password) {
        Base64 base64 = new Base64();
        password = new String(base64.decode(password.getBytes()));
        logger.debug("setPassword....{}  "+password);
        super.setPassword(password);
    }

    @Override
    public void setUsername(String username) {
        Base64 base64 = new Base64();
        username = new String(base64.decode(username.getBytes()));
        logger.debug("setUsername....{}  "+username);
        super.setUsername(username);
    }

    public static void main(String[] args) {
        Base64 base64 = new Base64();
        String name = new String(base64.encode("wujing".getBytes()));
        System.out.println(name);

        name = new String(base64.decode(name.getBytes()));
        System.out.println(name);

        String pwd = new String(base64.encode("IXAb4SJ69FRv".getBytes()));
        System.out.println(pwd);

        pwd = new String(base64.decode(pwd.getBytes()));
        System.out.println(pwd);


    }
}
