package com.baidu.duer.chinatalk_refactor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * 图像处理工具类
 */
public class ImageUtil {
    private static ImageUtil mUtil; //声明一个图像处理工具类的实例
    //通过单例模式获取图像处理工具类的唯一实例
    public static ImageUtil getInstance(Context ctx){
        if(mUtil==null){
            mUtil=new ImageUtil();
        }
        return mUtil;
    }

    /**
     * 将图片转成byte数组
     *
     * @param bitmap 图片
     * @return 图片的字节数组
     */
    public static byte[] bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //把bitmap100%高质量压缩 到 output对象里
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 将byte数组转成Base64
     *
     * @param imageByte 图片
     * @return Base64 String
     */
    public static String byte2Base64(byte[] imageByte) {
        if(null == imageByte) return null;
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

}
