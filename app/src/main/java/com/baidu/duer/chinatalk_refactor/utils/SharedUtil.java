package com.baidu.duer.chinatalk_refactor.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.duer.chinatalk_refactor.SampleApplicationLike;

/************************************************************************************
 * 类:                工具类
 * 用途:              共享参数管理,统一对共享参数的读写操作
 *
 *================================================================================= */
public class SharedUtil {
    private static SharedUtil mUtil;//声明一个共享参数工具类的实例
    private static SharedPreferences mShared;//声明一个共享参数的实例

    //通过单例模式获取共享参数工具类的唯一实例
    public static SharedUtil getInstance(){
        if(mUtil==null){
            mUtil=new SharedUtil();
        }
        //从share.xml中获取共享参数对象
        mShared= SampleApplicationLike.getContext().getSharedPreferences("share",Context.MODE_PRIVATE);
        return mUtil;
    }


    //把配对信息写入共享参数
    public void writeShared(String key,String value){
        SharedPreferences.Editor editor=mShared.edit();//获取编辑器的对象
        editor.putString(key,value);//添加一个指定键名的字符串参数
        editor.apply();//提交编辑器中的修改
    }

    //把配对信息写入共享参数
    public void writeShared(String key,Integer value){
        SharedPreferences.Editor editor=mShared.edit();//获取编辑器的对象
        editor.putInt(key,value);//添加一个指定键名的字符串参数
        editor.apply();//提交编辑器中的修改
    }

    //根据键名到共享参数中查找对应的值对象--String值
    public String readShared(String key,String defaultValue){
        return mShared.getString(key,defaultValue);
    }

    //根据键名到共享参数中查找对应的值对象---Int值
    public Integer readIntShared(String key,Integer defaultValue){
        return mShared.getInt(key,defaultValue);
    }
}