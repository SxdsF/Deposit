package com.sxdsf.deposit.service.disk.read;

import com.sxdsf.deposit.service.disk.impl.FileWrapper;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;

public abstract class AbstractDiskReadService implements DiskReadService {
	private final Map<String, Reference<FileWrapper>> fileMap;

	public AbstractDiskReadService(Map<String, Reference<FileWrapper>> fileMap) {
		this.fileMap = fileMap;
	}

	protected FileWrapper getFile(String key) {
		FileWrapper file = null;
		// 20151120 sunbowen 保证put和get两个操作一起原子性的执行
		synchronized (this.fileMap) {
			if (!this.fileMap.isEmpty()) {
				Reference<FileWrapper> fileReference = this.fileMap.get(key);
				if (fileReference != null) {
					file = fileReference.get();
					/**
					 * 如果file取出来时null，说明被清除了，所以重新创建一个放进去
					 */
					if (file == null) {
						file = new FileWrapper(key);
						fileReference = new SoftReference<>(file);
						this.fileMap.put(key, fileReference);
					}
				}
			}
		}
		return file;
	}
}
