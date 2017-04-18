package com.jnzy.mdm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by hardy on 2016/5/19.
 */
public class DocumentProUtil {
    private static Properties properties = new Properties();

    static {
        InputStream inputStream = DocumentProUtil.class.getClassLoader().getResourceAsStream("document.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getValues(String key) {
        Enumeration en = properties.propertyNames();
        String name = null;
        while (en.hasMoreElements()) {
            String per_key = (String) en.nextElement();
            if (key.equals(per_key)) {
                name = properties.getProperty(key);
            }
        }
        return name;
    }

    public static Integer getIntValues(String key) {
        return (Integer) properties.get(key);
    }
}
