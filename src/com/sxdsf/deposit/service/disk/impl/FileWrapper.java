package com.sxdsf.deposit.service.disk.impl;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sxdsf.deposit.dao.disk.DiskDepositDAO;
import com.sxdsf.deposit.dao.disk.impl.DiskDepositDAOImpl;

/**
 * File类的包装类，包含一个成员变量file，提供基本的file操作方法，还有一个读写锁
 * 
 * @author sunbowen
 * 
 */
public class FileWrapper implements Serializable, Comparable<FileWrapper> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2230823279328319036L;

	private final File file;
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	private final DiskDepositDAO diskDao = new DiskDepositDAOImpl();

	public FileWrapper(File dir, String name) {
		this.file = new File(dir, name);
	}

	public FileWrapper(String dirPath, String name) {
		this.file = new File(dirPath, name);
	}

	public FileWrapper(String path) {
		this.file = new File(path);
	}

	public FileWrapper(URI uri) {
		this.file = new File(uri);
	}

	public boolean exists() {
		return this.file.exists();
	}

	public String getPath() {
		return this.file.getPath();
	}

	public boolean mkdirs() {
		return this.file.mkdirs();
	}

	@Override
	public int compareTo(FileWrapper arg0) {
		// TODO Auto-generated method stub
		return arg0 != null ? this.file.compareTo(arg0.file) : 0;
	}

	public <T> boolean save(String fileName, T value) {
		// TODO Auto-generated method stub
		boolean result = false;
		this.rwl.writeLock().lock();
		try {
			File target = new File(this.file, fileName);
			result = this.diskDao.save(target, value);
		} finally {
			this.rwl.writeLock().unlock();
		}
		return result;
	}

	public <T> T get(String fileName) {
		// TODO Auto-generated method stub
		T result = null;
		this.rwl.readLock().lock();
		try {
			File target = new File(this.file, fileName);
			result = this.diskDao.get(target);
		} finally {
			this.rwl.readLock().unlock();
		}
		return result;
	}

	public boolean deleteAll(boolean include) {
		// TODO Auto-generated method stub
		boolean result = false;
		this.rwl.writeLock().lock();
		try {
			result = this.diskDao.delete(this.file, include);
		} finally {
			this.rwl.writeLock().unlock();
		}
		return result;
	}

	public boolean delete(String fileName, boolean include) {
		// TODO Auto-generated method stub
		boolean result = false;
		this.rwl.writeLock().lock();
		try {
			File target = new File(this.file, fileName);
			result = this.diskDao.delete(target, include);
		} finally {
			this.rwl.writeLock().unlock();
		}
		return result;
	}

	public long getModifyTime(String fileName) {
		// TODO Auto-generated method stub
		long result = 0;
		this.rwl.readLock().lock();
		try {
			File target = new File(this.file, fileName);
			result = this.diskDao.getModifyTime(target);
		} finally {
			this.rwl.readLock().unlock();
		}
		return result;
	}

}
