package com.sxdsf.deposit.service.impl;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Directory implements Serializable, Comparable<Directory> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1505090176408432080L;
	private final File file;
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();

	public Directory(File dir, String name) {
		this.file = new File(dir, name);
	}

	public Directory(String dirPath, String name) {
		this.file = new File(dirPath, name);
	}

	public Directory(String path) {
		this.file = new File(path);
	}

	public Directory(URI uri) {
		this.file = new File(uri);
	}

	@Override
	public int compareTo(Directory arg0) {
		// TODO Auto-generated method stub
		return arg0 != null ? this.file.compareTo(arg0.file) : 0;
	}

}
