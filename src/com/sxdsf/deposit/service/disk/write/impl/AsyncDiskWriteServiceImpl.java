package com.sxdsf.deposit.service.disk.write.impl;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;

import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;
import com.sxdsf.deposit.service.disk.write.AsyncDiskWriteService;

public class AsyncDiskWriteServiceImpl extends AsyncDiskWriteService {
	private final ExecutorService executorService;

	public AsyncDiskWriteServiceImpl(
			Map<String, Reference<FileWrapper>> fileMap,
			ExecutorService executorService) {
		super(fileMap);
		this.executorService = executorService;
	}

	@Override
	public <T> void save(final String root, final String fileName,
			final T value, final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = false;
				FileWrapper file = getFile(root);
				if (file != null) {
					result = file.save(fileName, value);
				}
				if (callback != null) {
					callback.onResult(result);
				}
			}
		});
	}

	@Override
	public void deleteAll(final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = false;
				// 20151120 sunbowen 在此需要保证系列操作的原子性
				synchronized (fileMap) {
					Set<Entry<String, Reference<FileWrapper>>> set = fileMap
							.entrySet();
					if (set != null) {
						for (Entry<String, Reference<FileWrapper>> entry : set) {
							if (entry != null) {
								FileWrapper file = getFile(entry.getKey());
								if (file != null) {
									result = file.deleteAll(true);
									boolean clear = false;
									fileMap.remove(entry.getKey());
									clear = true;
									result = result && clear;
								}
							}
						}
						fileMap.clear();
						result = true;
					}
				}
				if (callback != null) {
					callback.onResult(result);
				}
			}
		});
	}

	@Override
	public void delete(final String root, final boolean include,
			final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = false;
				synchronized (fileMap) {
					FileWrapper file = getFile(root);
					if (file != null) {
						result = file.deleteAll(include);
						if (include) {
							boolean clear = false;
							fileMap.remove(root);
							clear = true;
							result = result && clear;
						}
					}
				}
				if (callback != null) {
					callback.onResult(result);
				}
			}
		});
	}

	@Override
	public void delete(final String root, final String fileName,
			final boolean include, final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = false;
				FileWrapper file = getFile(root);
				if (file != null) {
					result = file.delete(fileName, include);
				}
				if (callback != null) {
					callback.onResult(result);
				}
			}
		});
	}

}
