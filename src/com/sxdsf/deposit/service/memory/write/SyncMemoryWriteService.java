package com.sxdsf.deposit.service.memory.write;

import java.util.concurrent.TimeUnit;

import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;

public abstract class SyncMemoryWriteService extends AbstractMemoryWriteService {

	public SyncMemoryWriteService(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave) {
		super(diskService, stringMemorySave, intMemorySave);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MemoryWriteMode getWriteMode() {
		// TODO Auto-generated method stub
		return MemoryWriteMode.SYNC;
	}

	/**
	 * 将一个值永久性存入
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract <V> boolean save(String key, V value);

	/**
	 * 将一个值存入，并赋予一个时间期限
	 * 
	 * @param key
	 * @param value
	 * @param tu
	 * @param time
	 * @return
	 */
	public abstract <V> boolean save(String key, V value, TimeUnit tu, int time);

	/**
	 * 清除该key的值
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean remove(String key);

	/**
	 * 将一个值永久性存入
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract <V> boolean save(int key, V value);

	/**
	 * 将一个值存入，并赋予一个时间期限
	 * 
	 * @param key
	 * @param value
	 * @param tu
	 * @param time
	 * @return
	 */
	public abstract <V> boolean save(int key, V value, TimeUnit tu, int time);

	/**
	 * 清除该key的值
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean remove(int key);

	/**
	 * 清除所有存入的值
	 * 
	 * @return
	 */
	public abstract boolean clear();

}
