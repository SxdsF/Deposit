package com.sxdsf.deposit.dao.disk;

import java.io.File;

/**
 * 提供基本的disk存储方法
 * 
 * @author sunbowen
 * 
 */
public interface DiskDAO {
	/**
	 * 将value存入file中
	 * 
	 * @param file
	 * @param value
	 * @return
	 */
	<T> boolean save(File file, T value);

	/**
	 * 将value存入路径为filePath的文件中
	 * 
	 * @param filePath
	 * @param value
	 * @return
	 */
	<T> boolean save(String filePath, T value);

	/**
	 * 获取文件file中的值
	 * 
	 * @param file
	 * @return
	 */
	<T> T get(File file);

	/**
	 * 获取路径为filePath的文件中的值
	 * 
	 * @param filePath
	 * @return
	 */
	<T> T get(String filePath);

	/**
	 * 删除文件file以及路径下的子文件
	 * 
	 * @param file
	 * @param include
	 *            是否包括file
	 * @return
	 */
	boolean delete(File file, boolean include);

	/**
	 * 删除路径为filePath的文件以及路径下的子文件
	 * 
	 * @param filePath
	 * @param include
	 *            是否包括file
	 * @return
	 */
	boolean delete(String filePath, boolean include);

	/**
	 * 获取文件file的最后修改时间
	 * 
	 * @param file
	 * @return
	 */
	long getModifyTime(File file);

	/**
	 * 获取路径为filePath的文件的最后修改时间
	 * 
	 * @param filePath
	 * @return
	 */
	long getModifyTime(String filePath);

	/**
	 * 将文件from复制到文件to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	boolean copy(File from, File to);

	/**
	 * 将路径为from的文件复制到路径为to的文件
	 * 
	 * @param fromPath
	 * @param toPath
	 * @return
	 */
	boolean copy(String fromPath, String toPath);
}
