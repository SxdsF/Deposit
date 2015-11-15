package com.sxdsf.deposit.dao;

import java.io.File;

public interface DiskDepositDAO {
	public <T> boolean save(File file, T value);

	public <T> boolean save(String filePath, T value);

	public <T> T get(File file);

	public <T> T get(String filePath);

	public boolean delete(File file, boolean single);

	public boolean delete(String filePath, boolean single);

	public long getModifyTime(File file);

	public long getModifyTime(String filePath);

	public boolean copy(File from, File to);

	public boolean copy(String fromPath, String toPath);
}
