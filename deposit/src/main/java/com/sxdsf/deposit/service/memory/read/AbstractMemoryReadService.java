package com.sxdsf.deposit.service.memory.read;

import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;

public abstract class AbstractMemoryReadService implements MemoryReadService {

	protected final DiskService diskService;
	protected final MemorySave<String> stringMemorySave;
	protected final MemorySave<Integer> intMemorySave;

	public AbstractMemoryReadService(DiskService diskService,
			MemorySave<String> stringMemorySave,
			MemorySave<Integer> intMemorySave) {
		this.diskService = diskService;
		this.stringMemorySave = stringMemorySave;
		this.intMemorySave = intMemorySave;
	}
}
