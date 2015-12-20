package com.sxdsf.deposit.service.memory;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemorySave<T> {

	public final String root;
	public final Map<T, Reference<?>> mapMemoryCache = new ConcurrentHashMap<>();
	public final Map<T, Time> saveTimeMap = new ConcurrentHashMap<>();
	public final String memoryFileName;
	public final String saveTimeMapFileName;

	public MemorySave(String root, String memoryFileName,
			String saveTimeMapFileName) {
		this.root = root;
		this.memoryFileName = memoryFileName;
		this.saveTimeMapFileName = saveTimeMapFileName;
	}
}
