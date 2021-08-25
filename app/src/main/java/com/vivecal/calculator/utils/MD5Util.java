package com.vivecal.calculator.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5操作工具类（字符串、字节、文件）基于org.apache.commons.codec封装
 */
public class MD5Util {
	
	/**
	 * 字符串md5编码
	 * @param stringData
	 * @return
	 */
	public static String encode(final String stringData) {
		String md5Result = null;
		try {
			md5Result = encode(stringData.getBytes("utf-8"));		
		} catch (UnsupportedEncodingException e) {

		}
		return md5Result;			
	}
	
	/**
	 * 字节流md5编码
	 * @param byteData
	 * @return
	 */
	public static String encode(final byte[] byteData) {
		String md5Result = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteData);
			md5Result = HexUtil.encode(md5.digest());
			md5.reset();

		} catch (NoSuchAlgorithmException e) {
			Log.e("com.diguotech.Util.MD5", "get md5 str error!");
		}
		return md5Result;		
	}
}
