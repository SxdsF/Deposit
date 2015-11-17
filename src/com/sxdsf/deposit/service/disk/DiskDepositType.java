package com.sxdsf.deposit.service.disk;

public enum DiskDepositType {
	// "/data"
	DATA_FOLDER,
	// "/cache"
	DOWNLOAD_CACHE_FOLDER,
	// "/mnt/sdcard"
	EXTERNAL_STORAGE_FOLDER,
	// "/system"
	ROOT_FOLDER,
	// "/data/data/packageName/cache"
	CACHE_FOLDER,
	// "/mnt/sdcard/Android/data/packageName/cache"
	EXTERNAL_CACHE_FOLDER,
	// "/data/data/packageName/files"
	FILE_FOLDER,
	// "/mnt/sdcard/Android/obb/packageName"
	OBB_FOLDER;
}
