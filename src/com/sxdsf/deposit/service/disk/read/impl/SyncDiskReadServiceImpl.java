package com.sxdsf.deposit.service.disk.read.impl;

import java.lang.ref.Reference;
import java.util.Map;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;
import com.sxdsf.deposit.service.disk.read.SyncDiskReadService;

public class SyncDiskReadServiceImpl extends SyncDiskReadService {

	public SyncDiskReadServiceImpl(Map<String, Reference<FileWrapper>> fileMap) {
		super(fileMap);
	}

	@Override
	public <T> T get(String root, String fileName) {
		// TODO Auto-generated method stub
		T result = null;
		FileWrapper file = this.getFile(root);
		if (file != null) {
			result = file.get(fileName);
		}
		return result;
	}

	@Override
	public long getModifyTime(String root, String fileName) {
		// TODO Auto-generated method stub
		long result = 0;
		FileWrapper file = this.getFile(root);
		if (file != null) {
			result = file.getModifyTime(fileName);
		}
		return result;
	}

}
