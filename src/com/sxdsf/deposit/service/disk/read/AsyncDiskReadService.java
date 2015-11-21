package com.sxdsf.deposit.service.disk.read;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;

public abstract class AsyncDiskReadService extends AbstractDiskReadService {

	protected final ExecutorService executorService;

	public AsyncDiskReadService(Map<String, Reference<FileWrapper>> fileMap,
			ExecutorService executorService) {
		super(fileMap);
		// TODO Auto-generated constructor stub
		this.executorService = executorService;
	}

	/**
	 * 异步执行获取文件内容，回调不会在主线程执行，请注意。
	 * 
	 * @param root
	 * @param fileName
	 * @param callback
	 */
	public abstract <T> void get(String root, String fileName,
			Callback<T> callback);

	/**
	 * 异步执行获取文件修改时间，回调不会在主线程执行，请注意。
	 * 
	 * @param root
	 * @param fileName
	 * @param callback
	 */
	public abstract void getModifyTime(String root, String fileName,
			Callback<Long> callback);

	@Override
	public DiskReadMode getReadMode() {
		// TODO Auto-generated method stub
		return DiskReadMode.ASYNC;
	}
}
