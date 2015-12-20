package com.sxdsf.deposit.service.disk.impl;

import android.util.Log;

import com.sxdsf.deposit.dao.disk.DiskDAO;
import com.sxdsf.deposit.dao.disk.impl.DiskDAOImpl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * File类的包装类，包含一个成员变量file，提供基本的file操作方法，还有一个读写锁
 *
 * @author sunbowen
 */
public class FileWrapper implements Serializable, Comparable<FileWrapper> {

    /**
     *
     */
    private static final long serialVersionUID = -2230823279328319036L;

    private final File file;
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    private final DiskDAO diskDao = new DiskDAOImpl();
    private static final String EXTENSION = ".deposit";

    private static final String TAG = "FileWrapper";

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
            fileName += EXTENSION;
            File target = new File(this.file, fileName);
            boolean exists = target.exists();
            boolean isFile = false;
            if (!exists) {
                try {
                    isFile = target.createNewFile();
                    exists = target.exists();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            } else {
                isFile = target.isFile();
            }
            if (exists && isFile) {
                result = this.diskDao.save(target, value);
            }
        } finally {
            this.rwl.writeLock().unlock();
        }
        return result;
    }

    public <T> boolean save(String fileName, T value, String... dirs) {
        // TODO Auto-generated method stub
        boolean result = false;
        this.rwl.writeLock().lock();
        try {
            fileName = this.makeDirs(dirs) + fileName + EXTENSION;
            File target = new File(this.file, fileName);
            boolean exists = target.exists();
            boolean isFile = false;
            if (!exists) {
                try {
                    isFile = target.createNewFile();
                    exists = target.exists();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            } else {
                isFile = target.isFile();
            }
            if (exists && isFile) {
                result = this.diskDao.save(target, value);
            }
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
            fileName += EXTENSION;
            File target = new File(this.file, fileName);
            result = this.diskDao.get(target);
        } finally {
            this.rwl.readLock().unlock();
        }
        return result;
    }

    public <T> T get(String fileName, String... dirs) {
        // TODO Auto-generated method stub
        T result = null;
        this.rwl.readLock().lock();
        try {
            fileName = this.makeDirs(dirs) + fileName + EXTENSION;
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

    public boolean delete(String fileName) {
        // TODO Auto-generated method stub
        boolean result = false;
        this.rwl.writeLock().lock();
        try {
            fileName += EXTENSION;
            File target = new File(this.file, fileName);
            result = this.diskDao.delete(target, false);
        } finally {
            this.rwl.writeLock().unlock();
        }
        return result;
    }

    public boolean delete(String fileName, String... dirs) {
        boolean result = false;
        this.rwl.writeLock().lock();
        try {
            fileName = this.makeDirs(dirs) + fileName + EXTENSION;
            File target = new File(this.file, fileName);
            result = this.diskDao.delete(target, false);
        } finally {
            this.rwl.writeLock().unlock();
        }
        return result;
    }

    public boolean delete(boolean include, String... dirs) {
        // TODO Auto-generated method stub
        boolean result = false;
        this.rwl.writeLock().lock();
        try {
            String fileName = this.makeDirs(dirs);
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
            fileName += EXTENSION;
            File target = new File(this.file, fileName);
            result = this.diskDao.getModifyTime(target);
        } finally {
            this.rwl.readLock().unlock();
        }
        return result;
    }

    public long getModifyTime(String fileName, String... dirs) {
        // TODO Auto-generated method stub
        long result = 0;
        this.rwl.readLock().lock();
        try {
            fileName = this.makeDirs(dirs) + fileName + EXTENSION;
            File target = new File(this.file, fileName);
            result = this.diskDao.getModifyTime(target);
        } finally {
            this.rwl.readLock().unlock();
        }
        return result;
    }

    private String makeDirs(String... dirs) {
        String path = null;
        if (dirs != null) {
            StringBuilder sb = new StringBuilder();
            for (String dir : dirs) {
                sb.append(dir).append(File.separator);
            }
            path = sb.toString();
        }
        return path;
    }

}
