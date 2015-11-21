package com.sxdsf.deposit.service.memory.read.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;
import com.sxdsf.deposit.service.memory.Time;
import com.sxdsf.deposit.service.memory.read.SyncMemoryReadService;

public class SyncMemoryReadServiceImpl extends SyncMemoryReadService {

	public SyncMemoryReadServiceImpl(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave) {
		super(diskService, stringMemorySave, intMemorySave);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <V> V get(String key) {
		// TODO Auto-generated method stub
		V obj = null;
		synchronized (this.stringMemorySave) {
			@SuppressWarnings("unchecked")
			Reference<V> temp = (Reference<V>) this.stringMemorySave.mapMemoryCache
					.get(key);

			long time = this.diskService.syncRead().getModifyTime(
					this.stringMemorySave.root, this.generateStringKey(key));

			if (time != 0) {
				long now = System.currentTimeMillis();
				Time t = this.stringMemorySave.saveTimeMap.get(key);
				if (t != null) {
					if (!Time.WILL_NOT_INVALID.equals(t)) {
						long l = TimeUnit.MILLISECONDS.convert(t.time, t.tu);
						if (!((now - time) > l)) {
							if (temp != null) {
								obj = temp.get();
							}
							if (obj == null) {
								obj = this.diskService.syncRead().get(
										this.stringMemorySave.root,
										this.generateStringKey(key));
								Reference<V> sr = new SoftReference<V>(obj);
								this.stringMemorySave.mapMemoryCache.put(key,
										sr);
							}
						}
					} else {
						if (temp != null) {
							obj = temp.get();
						}
						if (obj == null) {
							obj = this.diskService.syncRead().get(
									this.stringMemorySave.root,
									this.generateStringKey(key));
							Reference<V> sr = new SoftReference<V>(obj);
							this.stringMemorySave.mapMemoryCache.put(key, sr);
						}
					}
				}
			} else {
				if (temp != null) {
					obj = temp.get();
				}
			}
		}
		return obj;
	}

	@Override
	public <V> V get(int key) {
		// TODO Auto-generated method stub
		V obj = null;
		synchronized (this.intMemorySave) {
			@SuppressWarnings("unchecked")
			Reference<V> temp = (Reference<V>) this.intMemorySave.mapMemoryCache
					.get(key);

			long time = this.diskService.syncRead().getModifyTime(
					this.intMemorySave.root, this.generateIntKey(key));

			if (time != 0) {
				long now = System.currentTimeMillis();
				Time t = this.intMemorySave.saveTimeMap.get(key);
				if (t != null) {
					if (!Time.WILL_NOT_INVALID.equals(t)) {
						long l = TimeUnit.MILLISECONDS.convert(t.time, t.tu);
						if (!((now - time) > l)) {
							if (temp != null) {
								obj = temp.get();
							}
							if (obj == null) {
								obj = this.diskService.syncRead().get(
										this.intMemorySave.root,
										this.generateIntKey(key));
								Reference<V> sr = new SoftReference<V>(obj);
								this.intMemorySave.mapMemoryCache.put(key, sr);
							}
						}
					} else {
						if (temp != null) {
							obj = temp.get();
						}
						if (obj == null) {
							obj = this.diskService.syncRead().get(
									this.intMemorySave.root,
									this.generateIntKey(key));
							Reference<V> sr = new SoftReference<V>(obj);
							this.intMemorySave.mapMemoryCache.put(key, sr);
						}
					}
				}
			} else {
				if (temp != null) {
					obj = temp.get();
				}
			}
		}
		return obj;
	}

}
