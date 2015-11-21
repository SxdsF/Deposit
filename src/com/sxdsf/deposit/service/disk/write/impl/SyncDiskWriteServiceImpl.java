package com.sxdsf.deposit.service.disk.write.impl;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.sxdsf.deposit.service.disk.impl.FileWrapper;
import com.sxdsf.deposit.service.disk.write.SyncDiskWriteService;

public class SyncDiskWriteServiceImpl extends SyncDiskWriteService {

	public SyncDiskWriteServiceImpl(Map<String, Reference<FileWrapper>> fileMap) {
		super(fileMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> boolean save(String root, String fileName, T value) {
		// TODO Auto-generated method stub
		boolean result = false;
		FileWrapper file = this.getFile(root);
		if (file != null) {
			result = file.save(fileName, value);
		}
		return result;
	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		boolean result = false;
		// 20151120 sunbowen 在此需要保证系列操作的原子性
		synchronized (this.fileMap) {
			Set<Entry<String, Reference<FileWrapper>>> set = this.fileMap
					.entrySet();
			if (set != null) {
				for (Entry<String, Reference<FileWrapper>> entry : set) {
					if (entry != null) {
						this.delete(entry.getKey(), true);
					}
				}
				this.fileMap.clear();
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean delete(String root, boolean include) {
		// TODO Auto-generated method stub
		boolean result = false;
		synchronized (this.fileMap) {
			FileWrapper file = this.getFile(root);
			if (file != null) {
				result = file.deleteAll(include);
				if (include) {
					boolean clear = false;
					this.fileMap.remove(root);
					clear = true;
					result = result && clear;
				}
			}
		}
		return result;
	}

	@Override
	public boolean delete(String root, String fileName, boolean include) {
		// TODO Auto-generated method stub
		boolean result = false;
		FileWrapper file = this.getFile(root);
		if (file != null) {
			result = file.delete(fileName, include);
		}
		return result;
	}

}
