package com.sxdsf.deposit.service.impl;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import android.content.Context;
import android.os.Environment;
import com.sxdsf.deposit.service.DepositService;

public class DiskDepositServiceImpl implements DepositService {

	public static final String DATA_FOLDER = Environment.getDataDirectory()
			.getPath();
	public static final String DOWNLOAD_CACHE_FOLDER = Environment
			.getDownloadCacheDirectory().getPath();
	public static final String EXTERNAL_STORAGE_FOLDER = Environment
			.getExternalStorageDirectory().getPath();
	public static final String ROOT_FOLDER = Environment.getRootDirectory()
			.getPath();
	private final String CACHE_FOLDER;
	private final String EXTERNAL_CACHE_FOLDER;
	private final String FILE_FOLDER;
	private final String OBB_FOLDER;

	private static final String DIR = "DISK_CACHE";
	private final Context context;

	private final Map<String, File> fileMap = new ConcurrentHashMap<>();

	public DiskDepositServiceImpl(Context context) {
		this.context = context;
		CACHE_FOLDER = context.getCacheDir().getPath();
		EXTERNAL_CACHE_FOLDER = context.getExternalCacheDir().getPath();
		FILE_FOLDER = context.getFilesDir().getPath();
		OBB_FOLDER = context.getObbDir().getPath();

	}

	public void create(String dirPath, String fileName) {
		File file = new File(dirPath, fileName);
		if (file != null && !file.exists()) {
			if (file.mkdirs()) {
				this.fileMap.put(file.getPath(), file);
			}
		}
	}

	public void create(File dirFile, String fileName) {
		File file = new File(dirFile, fileName);
		if (file != null && !file.exists()) {
			if (file.mkdirs()) {
				this.fileMap.put(file.getPath(), file);
			}
		}
	}
	
	// public static String getRootDir(){
	// return null;}
	// }
	//
	// public static File getRootDir(){
}
