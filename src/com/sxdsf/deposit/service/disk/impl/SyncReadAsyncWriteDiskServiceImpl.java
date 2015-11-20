package com.sxdsf.deposit.service.disk.impl;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.content.Context;
import android.os.Environment;
import com.sxdsf.deposit.service.ServiceMode;
import com.sxdsf.deposit.service.disk.AsyncDiskService;
import com.sxdsf.deposit.service.disk.DiskOperationMode;
import com.sxdsf.deposit.service.disk.DiskType;
import com.sxdsf.deposit.service.disk.read.ReadService;
import com.sxdsf.deposit.service.disk.write.WriteService;

public class SyncReadAsyncWriteDiskServiceImpl implements
		AsyncDiskService {

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
	private final ExecutorService executorService = Executors
			.newSingleThreadExecutor();

	public SyncReadAsyncWriteDiskServiceImpl(Context context) {
		CACHE_FOLDER = context.getCacheDir().getPath();
		EXTERNAL_CACHE_FOLDER = context.getExternalCacheDir().getPath();
		FILE_FOLDER = context.getFilesDir().getPath();
		OBB_FOLDER = context.getObbDir().getPath();

	}

	@Override
	public String create(DiskType type, String dirName) {
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
		return false;
	}

	@Override
	public <T> T get(String root, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(String root, boolean include) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String root, String fileName, boolean single) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getModifyTime(String root, String fileName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.executorService.shutdown();
	}

	@Override
	public ServiceMode getServiceMode() {
		// TODO Auto-generated method stub
		return ServiceMode.DISK;
	}

	@Override
	public DiskOperationMode getDiskDepositMode() {
		// TODO Auto-generated method stub
		return DiskOperationMode.ASYNC;
	}

	@Override
	public ReadService read(DiskOperationMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteService write(DiskOperationMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
