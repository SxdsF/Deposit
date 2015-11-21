package com.sxdsf.deposit.service.disk;

import com.sxdsf.deposit.service.DepositService;
import com.sxdsf.deposit.service.disk.read.AsyncDiskReadService;
import com.sxdsf.deposit.service.disk.read.SyncDiskReadService;
import com.sxdsf.deposit.service.disk.write.AsyncDiskWriteService;
import com.sxdsf.deposit.service.disk.write.SyncDiskWriteService;

public interface DiskService extends DepositService {

	public String create(DiskFolder type, String dirName);

	public AsyncDiskReadService asyncRead();

	public SyncDiskReadService syncRead();

	public AsyncDiskWriteService asyncWrite();

	public SyncDiskWriteService syncWrite();
}
