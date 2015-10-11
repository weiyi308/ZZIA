package com.rdc.ruan.zzia.Main.Utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Ruan on 2015/9/29.
 */
public class Tools {
    /**
     * 更改字符编码
     * @param src
     * @param oldCharSet
     * @param newCharSet
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharSet(byte[] bytes,String oldCharSet,String newCharSet) throws UnsupportedEncodingException {
        if (bytes.length != 0){
            String temp = new String(bytes,oldCharSet);
            return new String(temp.getBytes(newCharSet),newCharSet);
        }
        return null;
    }
}
