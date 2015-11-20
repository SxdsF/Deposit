package com.sxdsf.deposit.service.disk.write;

import com.sxdsf.deposit.service.disk.Callback;

public interface WriteService {
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
	public <T> boolean save(String root, String fileName, T value);

	public <T> void save(String root, String fileName, T value,
			Callback<Boolean> callback);

	/**
	 * 删除该服务下所有存储的文件
	 * 
	 * @return
	 */
	public boolean deleteAll();

	public void deleteAll(Callback<Boolean> callback);

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

	public void deleteAll(String root, boolean include,
			Callback<Boolean> callback);

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

	public void delete(String root, String fileName, boolean include,
			Callback<Boolean> callback);
}
