package com.sxdsf.deposit.service.impl;

public interface AsyncDiskDepositService extends DiskDepositService {

	public void init();

	public void close();
}
