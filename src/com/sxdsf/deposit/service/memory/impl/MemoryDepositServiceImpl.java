package com.sxdsf.deposit.service.memory.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import com.sxdsf.deposit.service.ServiceMode;
import com.sxdsf.deposit.service.disk.DiskDepositType;
import com.sxdsf.deposit.service.disk.SyncDiskDepositService;
import com.sxdsf.deposit.service.disk.impl.SyncDiskDepositServiceImpl;
import com.sxdsf.deposit.service.memory.MemoryDepositService;
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

	@Override
	public ServiceMode getServiceMode() {
		// TODO Auto-generated method stub
		return ServiceMode.MEMORY;
	}

	@Override
	public <K, V> boolean save(K key, V value) {
		// TODO Auto-generated method stub
		return this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE);
	}

	@Override
	public <K, V> boolean save(K key, V value, TimeUnit tu, int time) {
		// TODO Auto-generated method stub
		Reference<V> temp = new SoftReference<V>(value);
		this.mapMemoryCache.put(key.toString(), temp);
		boolean saveValueInDisk = this.diskDepositServiceImpl.save(this.root,
				key.toString(), value);
		Time t = new Time(tu, time);
		this.saveTimeMap.put(key.toString(), t);
		boolean saveTimeInDisk = this.diskDepositServiceImpl.save(this.root,
				TIME_MAP, this.saveTimeMap);
		return saveValueInDisk && saveTimeInDisk;
	}

	@Override
	public <K, V> V get(K key) {
		// TODO Auto-generated method stub
		V obj = null;
		@SuppressWarnings("unchecked")
		Reference<V> temp = (Reference<V>) this.mapMemoryCache.get(key
				.toString());

		long time = this.diskDepositServiceImpl.getModifyTime(this.root,
				key.toString());

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
									key.toString());
							Reference<V> sr = new SoftReference<V>(obj);
							this.mapMemoryCache.put(key.toString(), sr);
						}
					}
				} else {
					if (temp != null) {
						obj = temp.get();
					}
					if (obj == null) {
						obj = this.diskDepositServiceImpl.get(this.root,
								key.toString());
						Reference<V> sr = new SoftReference<V>(obj);
						this.mapMemoryCache.put(key.toString(), sr);
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
	public <K> boolean clear(K key) {
		// TODO Auto-generated method stub
		this.mapMemoryCache.remove(key);
		return this.diskDepositServiceImpl.delete(this.root, key.toString(),
				true);
	}

}
