package com.sxdsf.deposit.service.impl;

public interface DiskDepositService {

	/**
	 * 创建一个目录
	 * 
	 * @param type
	 *            目录的根目录类型
	 * @param dirName
	 *            目录的名字
	 * @return 创建好的目录的完整路径
	 */
	public String create(DiskDepositType type, String dirName);

	public <T> boolean save(String root, String fileName, T value);

	public <T> T get(String root, String fileName);

	public boolean deleteAll();

	public boolean deleteAll(String root, boolean include);

	public boolean delete(String root, String fileName, boolean include);

	public long getModifyTime(String root, String fileName);
}
