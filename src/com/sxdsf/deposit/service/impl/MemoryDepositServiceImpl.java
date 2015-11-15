package com.sxdsf.deposit.service.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.util.SparseArray;

public class MemoryDepositServiceImpl {

//	private final Map<String, Reference<Object>> mapMemoryCache = new HashMap<>();
//	private final SparseArray<Reference<Object>> sparseMemoryCache = new SparseArray<>();
//	private final Map<Object, Integer> saveTimeMap = new HashMap<>();
//	private final DiskDepositServiceImpl diskDepositServiceImpl;
//	private static final String DIR = "MEMORY_CACHE";
//	private static final String TIME_MAP = "timeMap";
//
//	/**
//	 * 永久保存
//	 */
//	public static final int WILL_NOT_INVALID = -1;
//
//	public MemoryDepositServiceImpl() {
//		this.diskDepositServiceImpl = new DiskDepositServiceImpl(DIR);
//		@SuppressWarnings("unchecked")
//		Map<Object, Integer> temp = (Map<Object, Integer>) this.diskDepositServiceImpl
//				.getCache(TIME_MAP);
//		if (temp != null) {
//			Set<Entry<Object, Integer>> set = temp.entrySet();
//			if (set != null) {
//				Iterator<Entry<Object, Integer>> it = set.iterator();
//				if (it != null) {
//					while (it.hasNext()) {
//						Entry<Object, Integer> entry = it.next();
//						if (entry != null) {
//							Object key = entry.getKey();
//							Integer value = entry.getValue();
//							this.saveTimeMap.put(key, value);
//						}
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * 保存进cahce，时效为永久
//	 * 
//	 * @param key
//	 * @param value
//	 */
//	public void saveInCache(String key, Object value) {
//		this.saveInCache(key, value, WILL_NOT_INVALID);
//	}
//
//	/**
//	 * 保存进cache，有一个失效时间
//	 * 
//	 * @param key
//	 * @param value
//	 * @param saveTime
//	 */
//	public void saveInCache(String key, Object value, int saveTime) {
//		SoftReference<Object> temp = new SoftReference<Object>(value);
//		this.mapMemoryCache.put(key, temp);
//		this.diskDepositServiceImpl.saveInCache(key, value);
//		this.saveTimeMap.put(key, saveTime);
//		this.diskDepositServiceImpl.saveInCache(TIME_MAP, this.saveTimeMap);
//	}
//
//	/**
//	 * 保存进cahce，时效为永久
//	 * 
//	 * @param key
//	 * @param value
//	 */
//	public void saveInCache(int key, Object value) {
//		this.saveInCache(key, value, WILL_NOT_INVALID);
//	}
//
//	/**
//	 * 保存进cache，有一个失效时间
//	 * 
//	 * @param key
//	 * @param value
//	 * @param saveTime
//	 */
//	public void saveInCache(int key, Object value, int saveTime) {
//		SoftReference<Object> temp = new SoftReference<Object>(value);
//		this.sparseMemoryCache.put(key, temp);
//		this.diskDepositServiceImpl.saveInCache(key, value);
//		this.saveTimeMap.put(key, saveTime);
//		this.diskDepositServiceImpl.saveInCache(TIME_MAP, this.saveTimeMap);
//	}
//
//	/**
//	 * 获取缓存
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public Object getCache(String key) {
//		Object obj = null;
//		SoftReference<Object> temp = (SoftReference<Object>) this.mapMemoryCache
//				.get(key);
//
//		long time = this.diskDepositServiceImpl.getModifyTime(key);
//
//		if (time != 0) {
//			long now = System.currentTimeMillis();
//			Integer l = this.saveTimeMap.get(key);
//			if (l != null) {
//				int dis = l.intValue();
//				if (dis != WILL_NOT_INVALID) {
//					if (!((now - time) / 1000 > dis)) {
//						if (temp != null) {
//							obj = temp.get();
//						}
//						if (obj == null) {
//							obj = this.diskDepositServiceImpl.getCache(key);
//							SoftReference<Object> sr = new SoftReference<Object>(
//									obj);
//							this.mapMemoryCache.put(key, sr);
//						}
//					}
//				} else {
//					if (temp != null) {
//						obj = temp.get();
//					}
//					if (obj == null) {
//						obj = this.diskDepositServiceImpl.getCache(key);
//						SoftReference<Object> sr = new SoftReference<Object>(
//								obj);
//						this.mapMemoryCache.put(key, sr);
//					}
//				}
//			}
//		} else {
//			if (temp != null) {
//				obj = temp.get();
//			}
//		}
//
//		return obj;
//	}
//
//	/**
//	 * 获取缓存
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public Object getCache(int key) {
//		Object obj = null;
//		SoftReference<Object> temp = (SoftReference<Object>) this.sparseMemoryCache
//				.get(key);
//
//		long time = this.diskDepositServiceImpl.getModifyTime(key);
//
//		if (time != 0) {
//			long now = System.currentTimeMillis();
//			Integer l = this.saveTimeMap.get(key);
//			if (l != null) {
//				int dis = l.intValue();
//				if (dis != WILL_NOT_INVALID) {
//					if (!((now - time) / 1000 > dis)) {
//						if (temp != null) {
//							obj = temp.get();
//						}
//						if (obj == null) {
//							obj = this.diskDepositServiceImpl.getCache(key);
//							SoftReference<Object> sr = new SoftReference<Object>(
//									obj);
//							this.sparseMemoryCache.put(key, sr);
//						}
//					}
//				} else {
//					if (temp != null) {
//						obj = temp.get();
//					}
//					if (obj == null) {
//						obj = this.diskDepositServiceImpl.getCache(key);
//						SoftReference<Object> sr = new SoftReference<Object>(
//								obj);
//						this.sparseMemoryCache.put(key, sr);
//					}
//				}
//			}
//		} else {
//			if (temp != null) {
//				obj = temp.get();
//			}
//		}
//
//		return obj;
//	}
//
//	/**
//	 * 清理缓存
//	 */
//	public void clearCache() {
//		this.mapMemoryCache.clear();
//		this.sparseMemoryCache.clear();
//		this.diskDepositServiceImpl.clearCache();
//	}
//
//	public void clearCache(String key) {
//		this.mapMemoryCache.remove(key);
//		this.diskDepositServiceImpl.clearCache(key);
//	}
//
//	public void clearCache(int key) {
//		this.sparseMemoryCache.remove(key);
//		this.diskDepositServiceImpl.clearCache(key);
//	}
}
