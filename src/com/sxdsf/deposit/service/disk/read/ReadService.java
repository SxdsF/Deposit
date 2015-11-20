package com.sxdsf.deposit.service.disk.read;

import com.sxdsf.deposit.exception.WrongMethodCallException;
import com.sxdsf.deposit.service.disk.Callback;

public interface ReadService {
	/**
	 * 根据指定路径和指定文件返回文件里的内容
	 * 
	 * @param root
	 *            根路径
	 * @param fileName
	 *            文件名
	 * @return 文件里的内容
	 */
	public <T> T get(String root, String fileName);

	public <T> void get(String root, String fileName, Callback<T> callback)
			throws WrongMethodCallException;

	/**
	 * 根据指定路径和指定文件名获取其修改时间
	 * 
	 * @param root
	 * @param fileName
	 * @return
	 */
	public long getModifyTime(String root, String fileName);

	public void getModifyTime(String root, String fileName,
			Callback<Long> callback);

}
