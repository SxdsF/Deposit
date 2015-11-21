package com.sxdsf.deposit.service.disk.write;

import java.lang.ref.Reference;
import java.util.Map;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;

public abstract class SyncDiskWriteService extends AbstractDiskWriteService {

	public SyncDiskWriteService(Map<String, Reference<FileWrapper>> fileMap) {
		super(fileMap);
		// TODO Auto-generated constructor stub
	}

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
	public abstract <T> boolean save(String root, String fileName, T value);

	/**
	 * 删除该服务下所有存储的文件
	 * 
	 * @return
	 */
	public abstract boolean deleteAll();

	/**
	 * 删除指定路径下的文件
	 * 
	 * @param root
	 *            根路径
	 * @param include
	 *            是否连本文件夹一起删除
	 * @return
	 */
	public abstract boolean delete(String root, boolean include);

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
	public abstract boolean delete(String root, String fileName, boolean include);

	@Override
	public DiskWriteMode getWriteMode() {
		// TODO Auto-generated method stub
		return DiskWriteMode.SYNC;
	}
}
