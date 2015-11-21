package com.sxdsf.deposit.service.memory.read;

import java.io.File;

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

	protected String generateStringKey(String key) {
		return this.stringMemorySave.memoryFileName + File.separator + key;
	}

	protected String generateIntKey(int key) {
		return this.intMemorySave.memoryFileName + File.separator + key;
	}
}
