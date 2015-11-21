package com.sxdsf.deposit.service.memory.read;

import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;

public abstract class SyncMemoryReadService extends AbstractMemoryReadService {

	public SyncMemoryReadService(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave) {
		super(diskService, stringMemorySave, intMemorySave);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MemoryReadMode getReadMode() {
		// TODO Auto-generated method stub
		return MemoryReadMode.SYNC;
	}

	/**
	 * 根据key取出值
	 * 
	 * @param key
	 * @return
	 */
	public abstract <V> V get(String key);

	/**
	 * 根据key取出值
	 * 
	 * @param key
	 * @return
	 */
	public abstract <V> V get(int key);

}
