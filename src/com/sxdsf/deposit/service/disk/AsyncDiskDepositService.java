package com.sxdsf.deposit.service.disk;


public interface AsyncDiskDepositService extends DiskDepositService {

	public void init();

	public void close();
}
