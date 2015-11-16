package com.sxdsf.deposit.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import android.content.Context;
import android.os.Environment;

/**
 * 提供android下八种文件夹内的同步存储服务
 * 
 * @author sunbowen
 * 
 */
public class SyncDiskDepositServiceImpl implements SyncDiskDepositService {

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

	private final Map<String, FileWrapper> fileMap = new ConcurrentHashMap<>();

	public SyncDiskDepositServiceImpl(Context context) {
		CACHE_FOLDER = context.getCacheDir().getPath();
		EXTERNAL_CACHE_FOLDER = context.getExternalCacheDir().getPath();
		FILE_FOLDER = context.getFilesDir().getPath();
		OBB_FOLDER = context.getObbDir().getPath();

	}

	@Override
	public String create(DiskDepositType type, String dirName) {
		// TODO Auto-generated method stub
		String fullPath = null;
		if (dirName != null) {
			String dirPath = null;
			switch (type) {
			case CACHE_FOLDER:
				dirPath = this.CACHE_FOLDER;
				break;
			case DATA_FOLDER:
				dirPath = DATA_FOLDER;
				break;
			case DOWNLOAD_CACHE_FOLDER:
				dirPath = DOWNLOAD_CACHE_FOLDER;
				break;
			case EXTERNAL_CACHE_FOLDER:
				dirPath = this.EXTERNAL_CACHE_FOLDER;
				break;
			case EXTERNAL_STORAGE_FOLDER:
				dirPath = EXTERNAL_STORAGE_FOLDER;
				break;
			case FILE_FOLDER:
				dirPath = this.FILE_FOLDER;
				break;
			case OBB_FOLDER:
				dirPath = this.OBB_FOLDER;
				break;
			case ROOT_FOLDER:
				dirPath = ROOT_FOLDER;
				break;
			default:
				break;
			}
			if (dirPath != null) {
				fullPath = this.create(dirPath, dirName);
			}
		}
		return fullPath;
	}

	private String create(String dirPath, String dirName) {
		String fullPath = null;
		FileWrapper file = new FileWrapper(dirPath, dirName);
		if (file != null && !file.exists()) {
			if (file.mkdirs()) {
				fullPath = file.getPath();
				this.fileMap.put(fullPath, file);
			}
		}
		return fullPath;
	}

	@Override
	public <T> boolean save(String root, String fileName, T value) {
		// TODO Auto-generated method stub
		boolean result = false;
		FileWrapper file = this.fileMap.get(root);
		if (file != null) {
			result = file.save(fileName, value);
		}
		return result;
	}

	@Override
	public <T> T get(String root, String fileName) {
		// TODO Auto-generated method stub
		T result = null;
		FileWrapper file = this.fileMap.get(root);
		if (file != null) {
			result = file.get(fileName);
		}
		return result;
	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		synchronized (this.fileMap) {
			this.fileMap.clear();
		}
		return true;
	}

	@Override
	public boolean deleteAll(String root, boolean include) {
		// TODO Auto-generated method stub
		boolean result = false;
		FileWrapper file = this.fileMap.get(root);
		if (file != null) {
			result = file.deleteAll(include);
			if (include) {
				boolean clear = false;
				synchronized (this.fileMap) {
					this.fileMap.remove(root);
					clear = true;
				}
				result = result && clear;
			}
		}
		return result;
	}

	@Override
	public boolean delete(String root, String fileName, boolean include) {
		// TODO Auto-generated method stub
		boolean result = false;
		FileWrapper file = this.fileMap.get(root);
		if (file != null) {
			result = file.delete(fileName, include);
		}
		return result;
	}

	@Override
	public long getModifyTime(String root, String fileName) {
		// TODO Auto-generated method stub
		long result = 0;
		FileWrapper file = this.fileMap.get(root);
		if (file != null) {
			result = file.getModifyTime(fileName);
		}
		return result;
	}
}
