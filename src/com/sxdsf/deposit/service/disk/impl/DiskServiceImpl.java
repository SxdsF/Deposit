package com.sxdsf.deposit.service.disk.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.content.Context;
import android.os.Environment;
import com.sxdsf.deposit.service.ServiceMode;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.disk.DiskFolder;
import com.sxdsf.deposit.service.disk.read.AsyncDiskReadService;
import com.sxdsf.deposit.service.disk.read.SyncDiskReadService;
import com.sxdsf.deposit.service.disk.read.impl.AsyncDiskReadServiceImpl;
import com.sxdsf.deposit.service.disk.read.impl.SyncDiskReadServiceImpl;
import com.sxdsf.deposit.service.disk.write.AsyncDiskWriteService;
import com.sxdsf.deposit.service.disk.write.SyncDiskWriteService;
import com.sxdsf.deposit.service.disk.write.impl.AsyncDiskWriteServiceImpl;
import com.sxdsf.deposit.service.disk.write.impl.SyncDiskWriteServiceImpl;

public class DiskServiceImpl implements DiskService {

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

	/**
	 * 每创建一个文件，就放到这个map里，为了防止占内存，采用软引用存储
	 */
	private final Map<String, Reference<FileWrapper>> fileMap = new ConcurrentHashMap<>();
	private final ExecutorService executorService = Executors
			.newSingleThreadExecutor();

	private final SyncDiskReadService syncRead = new SyncDiskReadServiceImpl(
			this.fileMap);
	private final AsyncDiskReadService asyncRead = new AsyncDiskReadServiceImpl(
			this.fileMap, this.executorService);
	private final SyncDiskWriteService syncWrite = new SyncDiskWriteServiceImpl(
			this.fileMap);
	private final AsyncDiskWriteService asyncWrite = new AsyncDiskWriteServiceImpl(
			this.fileMap, this.executorService);

	public DiskServiceImpl(Context context) {
		CACHE_FOLDER = context.getCacheDir().getPath();
		EXTERNAL_CACHE_FOLDER = context.getExternalCacheDir().getPath();
		FILE_FOLDER = context.getFilesDir().getPath();
		OBB_FOLDER = context.getObbDir().getPath();

	}

	@Override
	public ServiceMode getServiceMode() {
		// TODO Auto-generated method stub
		return ServiceMode.DISK;
	}

	@Override
	public AsyncDiskReadService asyncRead() {
		// TODO Auto-generated method stub
		return this.asyncRead;
	}

	@Override
	public SyncDiskReadService syncRead() {
		// TODO Auto-generated method stub
		return this.syncRead;
	}

	@Override
	public AsyncDiskWriteService asyncWrite() {
		// TODO Auto-generated method stub
		return this.asyncWrite;
	}

	@Override
	public SyncDiskWriteService syncWrite() {
		// TODO Auto-generated method stub
		return this.syncWrite;
	}

	@Override
	public String create(DiskFolder type, String dirName) {
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
		Reference<FileWrapper> fileReference = new SoftReference<FileWrapper>(
				file);
		if (file != null && !file.exists()) {
			if (file.mkdirs()) {
				fullPath = file.getPath();
				this.fileMap.put(fullPath, fileReference);
			}
		}
		return fullPath;
	}

}
