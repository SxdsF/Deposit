package com.sxdsf.deposit.service.memory.impl;

import java.io.File;
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

	private final String root;
	private static final String DIR = "MEMORY_CACHE";
	private final SyncDiskDepositService diskDepositServiceImpl;

	/**
	 * key类型为string的相关变量
	 */
	private final Map<String, Reference<?>> stringMapMemoryCache = new ConcurrentHashMap<>();
	private final Map<String, Time> stringSaveTimeMap = new ConcurrentHashMap<>();
	private static final String KEY_STRING = "KEY_STRING";
	private static final String STRING_TIME_MAP = "stringTimeMap";

	/**
	 * key类型为int的相关变量
	 */
	private final Map<Integer, Reference<?>> intMapMemoryCache = new ConcurrentHashMap<>();
	private final Map<Integer, Time> intSaveTimeMap = new ConcurrentHashMap<>();
	private static final String KEY_INT = "KEY_INT";
	private static final String INT_TIME_MAP = "intTimeMap";

	/**
	 * 永久性存储的默认time值
	 */
	private final Time WILL_NOT_INVALID = new Time(TimeUnit.DAYS,
			Integer.MAX_VALUE);

	public MemoryDepositServiceImpl(Context context) {
		this(new SyncDiskDepositServiceImpl(context));
	}

	public MemoryDepositServiceImpl(
			SyncDiskDepositService diskDepositServiceImpl) {
		this.diskDepositServiceImpl = diskDepositServiceImpl;
		/**
		 * 默认采用本应用包下的cache文件夹来存储
		 */
		this.root = this.diskDepositServiceImpl.create(
				DiskDepositType.CACHE_FOLDER, DIR);
		@SuppressWarnings("unchecked")
		Map<String, Time> stringTimeMap = (Map<String, Time>) this.diskDepositServiceImpl
				.get(this.root, STRING_TIME_MAP);
		if (stringTimeMap != null) {
			Set<Entry<String, Time>> set = stringTimeMap.entrySet();
			if (set != null) {
				Iterator<Entry<String, Time>> it = set.iterator();
				if (it != null) {
					while (it.hasNext()) {
						Entry<String, Time> entry = it.next();
						if (entry != null) {
							String key = entry.getKey();
							Time value = entry.getValue();
							this.stringSaveTimeMap.put(key, value);
						}
					}
				}
			}
		}
		@SuppressWarnings("unchecked")
		Map<Integer, Time> intTimeMap = (Map<Integer, Time>) this.diskDepositServiceImpl
				.get(this.root, INT_TIME_MAP);
		if (intTimeMap != null) {
			Set<Entry<Integer, Time>> set = intTimeMap.entrySet();
			if (set != null) {
				Iterator<Entry<Integer, Time>> it = set.iterator();
				if (it != null) {
					while (it.hasNext()) {
						Entry<Integer, Time> entry = it.next();
						if (entry != null) {
							int key = entry.getKey();
							Time value = entry.getValue();
							this.intSaveTimeMap.put(key, value);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean clearAll() {
		// TODO Auto-generated method stub
		this.stringMapMemoryCache.clear();
		this.intMapMemoryCache.clear();
		return this.diskDepositServiceImpl.deleteAll(this.root, false);
	}

	@Override
	public ServiceMode getServiceMode() {
		// TODO Auto-generated method stub
		return ServiceMode.MEMORY;
	}

	@Override
	public <V> boolean save(String key, V value) {
		// TODO Auto-generated method stub
		return this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE);
	}

	@Override
	public <V> boolean save(String key, V value, TimeUnit tu, int time) {
		// TODO Auto-generated method stub
		Reference<V> temp = new SoftReference<V>(value);
		this.stringMapMemoryCache.put(key, temp);
		boolean saveValueInDisk = this.diskDepositServiceImpl.save(this.root,
				this.generateStringKey(key), value);
		Time t = new Time(tu, time);
		this.stringSaveTimeMap.put(key, t);
		boolean saveTimeInDisk = this.diskDepositServiceImpl.save(this.root,
				STRING_TIME_MAP, this.stringSaveTimeMap);
		return saveValueInDisk && saveTimeInDisk;
	}

	@Override
	public <V> V get(String key) {
		// TODO Auto-generated method stub
		V obj = null;
		@SuppressWarnings("unchecked")
		Reference<V> temp = (Reference<V>) this.stringMapMemoryCache.get(key);

		long time = this.diskDepositServiceImpl.getModifyTime(this.root,
				this.generateStringKey(key));

		if (time != 0) {
			long now = System.currentTimeMillis();
			Time t = this.stringSaveTimeMap.get(key);
			if (t != null) {
				if (!WILL_NOT_INVALID.equals(t)) {
					long l = TimeUnit.MILLISECONDS.convert(t.time, t.tu);
					if (!((now - time) > l)) {
						if (temp != null) {
							obj = temp.get();
						}
						if (obj == null) {
							obj = this.diskDepositServiceImpl.get(this.root,
									this.generateStringKey(key));
							Reference<V> sr = new SoftReference<V>(obj);
							this.stringMapMemoryCache.put(key, sr);
						}
					}
				} else {
					if (temp != null) {
						obj = temp.get();
					}
					if (obj == null) {
						obj = this.diskDepositServiceImpl.get(this.root,
								this.generateStringKey(key));
						Reference<V> sr = new SoftReference<V>(obj);
						this.stringMapMemoryCache.put(key, sr);
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
		this.stringMapMemoryCache.remove(key);
		return this.diskDepositServiceImpl.delete(this.root,
				this.generateStringKey(key), true);
	}

	@Override
	public <V> boolean save(int key, V value) {
		// TODO Auto-generated method stub
		return this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE);
	}

	@Override
	public <V> boolean save(int key, V value, TimeUnit tu, int time) {
		// TODO Auto-generated method stub
		Reference<V> temp = new SoftReference<V>(value);
		this.intMapMemoryCache.put(key, temp);
		boolean saveValueInDisk = this.diskDepositServiceImpl.save(this.root,
				this.generateIntKey(key), value);
		Time t = new Time(tu, time);
		this.intSaveTimeMap.put(key, t);
		boolean saveTimeInDisk = this.diskDepositServiceImpl.save(this.root,
				INT_TIME_MAP, this.intSaveTimeMap);
		return saveValueInDisk && saveTimeInDisk;
	}

	@Override
	public <V> V get(int key) {
		// TODO Auto-generated method stub
		V obj = null;
		@SuppressWarnings("unchecked")
		Reference<V> temp = (Reference<V>) this.intMapMemoryCache.get(key);

		long time = this.diskDepositServiceImpl.getModifyTime(this.root,
				this.generateIntKey(key));

		if (time != 0) {
			long now = System.currentTimeMillis();
			Time t = this.intSaveTimeMap.get(key);
			if (t != null) {
				if (!WILL_NOT_INVALID.equals(t)) {
					long l = TimeUnit.MILLISECONDS.convert(t.time, t.tu);
					if (!((now - time) > l)) {
						if (temp != null) {
							obj = temp.get();
						}
						if (obj == null) {
							obj = this.diskDepositServiceImpl.get(this.root,
									this.generateIntKey(key));
							Reference<V> sr = new SoftReference<V>(obj);
							this.intMapMemoryCache.put(key, sr);
						}
					}
				} else {
					if (temp != null) {
						obj = temp.get();
					}
					if (obj == null) {
						obj = this.diskDepositServiceImpl.get(this.root,
								this.generateIntKey(key));
						Reference<V> sr = new SoftReference<V>(obj);
						this.intMapMemoryCache.put(key, sr);
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
	public boolean clear(int key) {
		// TODO Auto-generated method stub
		this.intMapMemoryCache.remove(key);
		return this.diskDepositServiceImpl.delete(this.root,
				this.generateIntKey(key), true);
	}

	/**
	 * 时间存储的基本类型
	 * 
	 * @author sunbowen
	 * 
	 */
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

	private String generateStringKey(String key) {
		return KEY_STRING + File.separator + key;
	}

	private String generateIntKey(int key) {
		return KEY_INT + File.separator + key;
	}

}
