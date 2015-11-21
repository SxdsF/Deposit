package com.sxdsf.deposit.service.disk.write;

import java.lang.ref.Reference;
import java.util.Map;

import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;

public abstract class AsyncDiskWriteService extends AbstractDiskWriteService {
	public AsyncDiskWriteService(Map<String, Reference<FileWrapper>> fileMap) {
		super(fileMap);
		// TODO Auto-generated constructor stub
	}

	public abstract <T> void save(String root, String fileName, T value,
			Callback<Boolean> callback);

	public abstract void deleteAll(Callback<Boolean> callback);

	public abstract void delete(String root, boolean include,
			Callback<Boolean> callback);

	public abstract void delete(String root, String fileName, boolean include,
			Callback<Boolean> callback);

	@Override
	public DiskWriteMode getWriteMode() {
		// TODO Auto-generated method stub
		return DiskWriteMode.ASYNC;
	}
}
