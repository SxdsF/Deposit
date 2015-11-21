package com.sxdsf.deposit.service.memory.write.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;
import com.sxdsf.deposit.service.memory.Time;
import com.sxdsf.deposit.service.memory.write.SyncMemoryWriteService;

public class SyncMemoryWriteServiceImpl extends SyncMemoryWriteService {

	public SyncMemoryWriteServiceImpl(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave) {
		super(diskService, stringMemorySave, intMemorySave);
		// TODO Auto-generated constructor stub
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
		boolean saveValueInDisk = false;
		boolean saveTimeInDisk = false;
		synchronized (this.stringMemorySave) {
			this.stringMemorySave.mapMemoryCache.put(key, temp);
			saveValueInDisk = this.diskService.syncWrite().save(
					this.stringMemorySave.root, this.generateStringKey(key),
					value);
			Time t = new Time(tu, time);
			this.stringMemorySave.saveTimeMap.put(key, t);
			saveTimeInDisk = this.diskService.syncWrite().save(
					this.stringMemorySave.root,
					this.stringMemorySave.saveTimeMapFileName,
					this.stringMemorySave.saveTimeMap);
		}
		return saveValueInDisk && saveTimeInDisk;
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		boolean result = false;
		synchronized (this.stringMemorySave) {
			this.stringMemorySave.mapMemoryCache.remove(key);
			result = this.diskService.syncWrite().delete(
					this.stringMemorySave.root, this.generateStringKey(key),
					true);
		}
		return result;

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
		boolean saveValueInDisk = false;
		boolean saveTimeInDisk = false;
		synchronized (this.intMemorySave) {
			this.intMemorySave.mapMemoryCache.put(key, temp);
			saveValueInDisk = this.diskService.syncWrite().save(
					this.intMemorySave.root, this.generateIntKey(key), value);
			Time t = new Time(tu, time);
			this.intMemorySave.saveTimeMap.put(key, t);
			saveTimeInDisk = this.diskService.syncWrite().save(
					this.intMemorySave.root,
					this.intMemorySave.saveTimeMapFileName,
					this.intMemorySave.saveTimeMap);
		}
		return saveValueInDisk && saveTimeInDisk;
	}

	@Override
	public boolean remove(int key) {
		// TODO Auto-generated method stub
		boolean result = false;
		synchronized (this.intMemorySave) {
			this.intMemorySave.mapMemoryCache.remove(key);
			result = this.diskService.syncWrite().delete(
					this.intMemorySave.root, this.generateIntKey(key), true);
		}
		return result;
	}

	@Override
	public boolean clear() {
		// TODO Auto-generated method stub
		boolean result = false;
		synchronized (this) {
			this.stringMemorySave.mapMemoryCache.clear();
			this.intMemorySave.mapMemoryCache.clear();
			result = this.diskService.syncWrite().delete(
					this.stringMemorySave.root, false);
		}
		return result;
	}

}
