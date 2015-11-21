package com.sxdsf.deposit.service.memory.read;

import java.util.concurrent.ExecutorService;

import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;

public abstract class AsyncMemoryReadService extends AbstractMemoryReadService {

	protected final ExecutorService executorService;

	public AsyncMemoryReadService(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave, ExecutorService executorService) {
		super(diskService, stringMemorySave, intMemorySave);
		// TODO Auto-generated constructor stub
		this.executorService = executorService;
	}

	@Override
	public MemoryReadMode getReadMode() {
		// TODO Auto-generated method stub
		return MemoryReadMode.ASYNC;
	}

	/**
	 * 根据key取出值
	 * 
	 * @param key
	 * @return
	 */
	public abstract <V> void get(String key, Callback<V> callback);

	/**
	 * 根据key取出值
	 * 
	 * @param key
	 * @return
	 */
	public abstract <V> void get(int key, Callback<V> callback);

}
