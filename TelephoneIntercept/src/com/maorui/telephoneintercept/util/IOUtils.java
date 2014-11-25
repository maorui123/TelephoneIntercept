package com.maorui.telephoneintercept.util;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class IOUtils {

	private static String rootPath = "/intercepter";

	public static String getRootPath() {
		//sdcard 存在 并且 剩余 大小大于 5M 就存在sdcard
		if (sdCardIsExit() && getSDFreeSize() > 5) {
			return Environment.getExternalStorageDirectory() + rootPath;
		}
		return null;
	}

	public static boolean sdCardIsExit() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		// return freeBlocks * blockSize; //单位Byte
		// return (freeBlocks * blockSize)/1024; //单位KB
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

}
