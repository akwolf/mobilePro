package com.jnzy.mdm.util;

/**
 * Created by yxm on 2016/9/29.
 */
public class EmojiFilter {
    //替换emoji表情
    public static String removeNonBmpUnicode(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
        return str;
    }
}


