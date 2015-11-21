package com.sxdsf.deposit.service.memory.read.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;
import com.sxdsf.deposit.service.memory.Time;
import com.sxdsf.deposit.service.memory.read.AsyncMemoryReadService;

public class AsyncMemoryReadServiceImpl extends AsyncMemoryReadService {

	public AsyncMemoryReadServiceImpl(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave, ExecutorService executorService) {
		super(diskService, stringMemorySave, intMemorySave, executorService);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <V> void get(final String key, final Callback<V> callback) {
		// TODO Auto-generated method stub
		this.executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				V obj = null;
				synchronized (stringMemorySave) {
					@SuppressWarnings("unchecked")
					Reference<V> temp = (Reference<V>) stringMemorySave.mapMemoryCache
							.get(key);

					long time = diskService.syncRead().getModifyTime(
							stringMemorySave.root, generateStringKey(key));

					if (time != 0) {
						long now = System.currentTimeMillis();
						Time t = stringMemorySave.saveTimeMap.get(key);
						if (t != null) {
							if (!Time.WILL_NOT_INVALID.equals(t)) {
								long l = TimeUnit.MILLISECONDS.convert(t.time,
										t.tu);
								if (!((now - time) > l)) {
									if (temp != null) {
										obj = temp.get();
									}
									if (obj == null) {
										obj = diskService.syncRead().get(
												stringMemorySave.root,
												generateStringKey(key));
										Reference<V> sr = new SoftReference<V>(
												obj);
										stringMemorySave.mapMemoryCache.put(
												key, sr);
									}
								}
							} else {
								if (temp != null) {
									obj = temp.get();
								}
								if (obj == null) {
									obj = diskService.syncRead().get(
											stringMemorySave.root,
											generateStringKey(key));
									Reference<V> sr = new SoftReference<V>(obj);
									stringMemorySave.mapMemoryCache
											.put(key, sr);
								}
							}
						}
					} else {
						if (temp != null) {
							obj = temp.get();
						}
					}
				}
				if (callback != null) {
					callback.onResult(obj);
				}
			}
		});
	}

	@Override
	public <V> void get(final int key, final Callback<V> callback) {
		// TODO Auto-generated method stub
		if (callback != null) {
			this.executorService.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					V obj = null;
					synchronized (intMemorySave) {
						@SuppressWarnings("unchecked")
						Reference<V> temp = (Reference<V>) intMemorySave.mapMemoryCache
								.get(key);

						long time = diskService.syncRead().getModifyTime(
								intMemorySave.root, generateIntKey(key));

						if (time != 0) {
							long now = System.currentTimeMillis();
							Time t = intMemorySave.saveTimeMap.get(key);
							if (t != null) {
								if (!Time.WILL_NOT_INVALID.equals(t)) {
									long l = TimeUnit.MILLISECONDS.convert(
											t.time, t.tu);
									if (!((now - time) > l)) {
										if (temp != null) {
											obj = temp.get();
										}
										if (obj == null) {
											obj = diskService.syncRead().get(
													intMemorySave.root,
													generateIntKey(key));
											Reference<V> sr = new SoftReference<V>(
													obj);
											intMemorySave.mapMemoryCache.put(
													key, sr);
										}
									}
								} else {
									if (temp != null) {
										obj = temp.get();
									}
									if (obj == null) {
										obj = diskService.syncRead().get(
												intMemorySave.root,
												generateIntKey(key));
										Reference<V> sr = new SoftReference<V>(
												obj);
										intMemorySave.mapMemoryCache.put(key,
												sr);
									}
								}
							}
						} else {
							if (temp != null) {
								obj = temp.get();
							}
						}
					}
					callback.onResult(obj);
				}
			});
		}
	}

}
