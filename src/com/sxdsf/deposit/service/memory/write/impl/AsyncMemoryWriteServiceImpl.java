package com.sxdsf.deposit.service.memory.write.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;
import com.sxdsf.deposit.service.memory.Time;
import com.sxdsf.deposit.service.memory.write.AsyncMemoryWriteService;

public class AsyncMemoryWriteServiceImpl extends AsyncMemoryWriteService {

	public AsyncMemoryWriteServiceImpl(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave, ExecutorService executorService) {
		super(diskService, stringMemorySave, intMemorySave, executorService);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <V> void save(String key, V value, Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE, callback);
	}

	@Override
	public <V> void save(final String key, final V value, final TimeUnit tu,
			final int time, final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Reference<V> temp = new SoftReference<V>(value);
				boolean saveValueInDisk = false;
				boolean saveTimeInDisk = false;
				synchronized (stringMemorySave) {
					stringMemorySave.mapMemoryCache.put(key, temp);
					saveValueInDisk = diskService.syncWrite().save(
							stringMemorySave.root, generateStringKey(key),
							value);
					Time t = new Time(tu, time);
					stringMemorySave.saveTimeMap.put(key, t);
					saveTimeInDisk = diskService.syncWrite().save(
							stringMemorySave.root,
							stringMemorySave.saveTimeMapFileName,
							stringMemorySave.saveTimeMap);
				}
				if (callback != null) {
					callback.onResult(saveValueInDisk && saveTimeInDisk);
				}
			}
		});
	}

	@Override
	public void remove(final String key, final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = false;
				synchronized (stringMemorySave) {
					stringMemorySave.mapMemoryCache.remove(key);
					result = diskService.syncWrite()
							.delete(stringMemorySave.root,
									generateStringKey(key), true);
				}
				if (callback != null) {
					callback.onResult(result);
				}
			}
		});
	}

	@Override
	public <V> void save(int key, V value, Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE, callback);
	}

	@Override
	public <V> void save(final int key, final V value, final TimeUnit tu,
			final int time, final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Reference<V> temp = new SoftReference<V>(value);
				boolean saveValueInDisk = false;
				boolean saveTimeInDisk = false;
				synchronized (intMemorySave) {
					intMemorySave.mapMemoryCache.put(key, temp);
					saveValueInDisk = diskService.syncWrite().save(
							intMemorySave.root, generateIntKey(key), value);
					Time t = new Time(tu, time);
					intMemorySave.saveTimeMap.put(key, t);
					saveTimeInDisk = diskService.syncWrite().save(
							intMemorySave.root,
							intMemorySave.saveTimeMapFileName,
							intMemorySave.saveTimeMap);
				}
				if (callback != null) {
					callback.onResult(saveValueInDisk && saveTimeInDisk);
				}
			}
		});
	}

	@Override
	public void remove(final int key, final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = false;
				synchronized (intMemorySave) {
					intMemorySave.mapMemoryCache.remove(key);
					result = diskService.syncWrite().delete(intMemorySave.root,
							generateIntKey(key), true);
				}
				if (callback != null) {
					callback.onResult(result);
				}
			}
		});
	}

	@Override
	public void clear(final Callback<Boolean> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean result = false;
				synchronized (AsyncMemoryWriteServiceImpl.this) {
					stringMemorySave.mapMemoryCache.clear();
					intMemorySave.mapMemoryCache.clear();
					result = diskService.syncWrite().delete(
							stringMemorySave.root, false);
				}
				if (callback != null) {
					callback.onResult(result);
				}
			}
		});
	}

}
