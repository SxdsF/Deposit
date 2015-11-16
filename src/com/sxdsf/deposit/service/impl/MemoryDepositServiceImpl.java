package com.sxdsf.deposit.service.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import android.content.Context;

public class MemoryDepositServiceImpl implements MemoryDepositService {

	private final Map<String, Reference<?>> mapMemoryCache = new ConcurrentHashMap<>();
	private final Map<String, Time> saveTimeMap = new ConcurrentHashMap<>();
	private final SyncDiskDepositService diskDepositServiceImpl;
	private final String root;
	private static final String DIR = "MEMORY_CACHE";
	private static final String TIME_MAP = "timeMap";

	private final Time WILL_NOT_INVALID = new Time(TimeUnit.DAYS,
			Integer.MAX_VALUE);

	public MemoryDepositServiceImpl(
			SyncDiskDepositService diskDepositServiceImpl) {
		this.diskDepositServiceImpl = diskDepositServiceImpl;
		/**
		 * 默认采用本应用包下的cache文件夹来存储
		 */
		this.root = this.diskDepositServiceImpl.create(
				DiskDepositType.CACHE_FOLDER, DIR);
		@SuppressWarnings("unchecked")
		Map<String, Time> temp = (Map<String, Time>) this.diskDepositServiceImpl
				.get(this.root, TIME_MAP);
		if (temp != null) {
			Set<Entry<String, Time>> set = temp.entrySet();
			if (set != null) {
				Iterator<Entry<String, Time>> it = set.iterator();
				if (it != null) {
					while (it.hasNext()) {
						Entry<String, Time> entry = it.next();
						if (entry != null) {
							String key = entry.getKey();
							Time value = entry.getValue();
							this.saveTimeMap.put(key, value);
						}
					}
				}
			}
		}
	}

	public MemoryDepositServiceImpl(Context context) {
		this(new SyncDiskDepositServiceImpl(context));
	}

	@Override
	public <T> boolean save(String key, T value) {
		// TODO Auto-generated method stub
		return this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE);
	}

	@Override
	public <T> boolean save(String key, T value, TimeUnit tu, int time) {
		// TODO Auto-generated method stub
		Reference<T> temp = new SoftReference<T>(value);
		this.mapMemoryCache.put(key, temp);
		boolean saveValueInDisk = this.diskDepositServiceImpl.save(this.root,
				key, value);
		Time t = new Time(tu, time);
		this.saveTimeMap.put(key, t);
		boolean saveTimeInDisk = this.diskDepositServiceImpl.save(this.root,
				TIME_MAP, this.saveTimeMap);
		return saveValueInDisk && saveTimeInDisk;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		// TODO Auto-generated method stub
		T obj = null;
		Reference<T> temp = (Reference<T>) this.mapMemoryCache.get(key);

		long time = this.diskDepositServiceImpl.getModifyTime(this.root, key);

		if (time != 0) {
			long now = System.currentTimeMillis();
			Time t = this.saveTimeMap.get(key);
			if (t != null) {
				if (!WILL_NOT_INVALID.equals(t)) {
					long l = TimeUnit.MILLISECONDS.convert(t.time, t.tu);
					if (!((now - time) > l)) {
						if (temp != null) {
							obj = temp.get();
						}
						if (obj == null) {
							obj = this.diskDepositServiceImpl.get(this.root,
									key);
							Reference<T> sr = new SoftReference<T>(obj);
							this.mapMemoryCache.put(key, sr);
						}
					}
				} else {
					if (temp != null) {
						obj = temp.get();
					}
					if (obj == null) {
						obj = this.diskDepositServiceImpl.get(this.root, key);
						Reference<T> sr = new SoftReference<T>(obj);
						this.mapMemoryCache.put(key, sr);
					}
				}
			}
		} else {
			if (temp != null) {
				obj = temp.get();
			}
		}

		return obj;
	}

	@Override
	public boolean clear(String key) {
		// TODO Auto-generated method stub
		this.mapMemoryCache.remove(key);
		return this.diskDepositServiceImpl.delete(this.root, key, true);
	}

	@Override
	public boolean clearAll() {
		// TODO Auto-generated method stub
		this.mapMemoryCache.clear();
		return this.diskDepositServiceImpl.deleteAll(this.root, false);
	}

	private class Time {
		public TimeUnit tu;
		public int time;

		public Time(TimeUnit tu, int time) {
			this.tu = tu;
			this.time = time;
		}

		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			boolean result = false;
			if (o != null && o instanceof Time) {
				if (this.tu == ((Time) o).tu && this.time == ((Time) o).time) {
					result = true;
				}
			}
			return result;
		}

	}

}
