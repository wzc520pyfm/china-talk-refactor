package com.baidu.duer.chinatalk_refactor.utils;

public class StringUtils {
    /**
     * 去掉String字符串中的.
     * @param str
     * @return
     */
    public static String GetDeleteShort(String str){
        String s = str.replace(".", "");
        s=s.replace("。","");
        return s;
    }
}
