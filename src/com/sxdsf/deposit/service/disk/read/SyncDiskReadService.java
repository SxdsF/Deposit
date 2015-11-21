package com.sxdsf.deposit.service.disk.read;

import java.lang.ref.Reference;
import java.util.Map;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;

public abstract class SyncDiskReadService extends AbstractDiskReadService {

	public SyncDiskReadService(Map<String, Reference<FileWrapper>> fileMap) {
		super(fileMap);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据指定路径和指定文件返回文件里的内容
	 * 
	 * @param root
	 *            根路径
	 * @param fileName
	 *            文件名
	 * @return 文件里的内容
	 */
	public abstract <T> T get(String root, String fileName);

	/**
	 * 根据指定路径和指定文件名获取其修改时间
	 * 
	 * @param root
	 *            根路径
	 * @param fileName
	 *            文件名
	 * @return 修改的时间
	 */
	public abstract long getModifyTime(String root, String fileName);

	@Override
	public DiskReadMode getReadMode() {
		// TODO Auto-generated method stub
		return DiskReadMode.SYNC;
	}

}
