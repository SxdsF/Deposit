package com.sxdsf.deposit.service.disk.read.impl;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;
import com.sxdsf.deposit.service.disk.read.AsyncDiskReadService;

public class AsyncDiskReadServiceImpl extends AsyncDiskReadService {

	public AsyncDiskReadServiceImpl(
			Map<String, Reference<FileWrapper>> fileMap,
			ExecutorService executorService) {
		super(fileMap, executorService);
	}

	@Override
	public <T> void get(final String root, final String fileName,
			final Callback<T> callback) {
		// TODO Auto-generated method stub
		if (callback != null) {
			this.executorService.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					T result = null;
					FileWrapper file = getFile(root);
					if (file != null) {
						result = file.get(fileName);
					}
					callback.onResult(result);
				}
			});
		}
	}

	@Override
	public void getModifyTime(final String root, final String fileName,
			final Callback<Long> callback) {
		// TODO Auto-generated method stub
		if (callback != null) {
			this.executorService.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					long result = 0;
					FileWrapper file = getFile(root);
					if (file != null) {
						result = file.getModifyTime(fileName);
					}
					callback.onResult(result);
				}
			});
		}
	}

}
