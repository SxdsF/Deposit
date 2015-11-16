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

	/**
	 * 将指定值存入指定路径的指定文件里
	 * 
	 * @param root
	 *            根路径
	 * @param fileName
	 *            文件名
	 * @param value
	 *            存入值
	 * @return 存入是否成功
	 */
	public <T extends Object> boolean save(String root, String fileName, T value);

	/**
	 * 根据指定路径和指定文件返回文件里的内容
	 * 
	 * @param root
	 *            根路径
	 * @param fileName
	 *            文件名
	 * @return 文件里的内容
	 */
	public <T extends Object> T get(String root, String fileName);

	/**
	 * 删除该服务下所有存储的文件
	 * 
	 * @return
	 */
	public boolean deleteAll();

	/**
	 * 删除指定路径下的文件
	 * 
	 * @param root
	 *            根路径
	 * @param include
	 *            是否连本文件夹一起删除
	 * @return
	 */
	public boolean deleteAll(String root, boolean include);

	/**
	 * 根据指定路径指定文件名删除
	 * 
	 * @param root
	 *            根路径
	 * @param fileName
	 *            文件名
	 * @param include
	 *            是否连本文件夹一起删除
	 * @return
	 */
	public boolean delete(String root, String fileName, boolean include);

	/**
	 * 根据指定路径和指定文件名获取其修改时间
	 * 
	 * @param root
	 * @param fileName
	 * @return
	 */
	public long getModifyTime(String root, String fileName);
}
