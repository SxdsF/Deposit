package com.sxdsf.deposit.service.impl;

import java.util.concurrent.TimeUnit;

import com.sxdsf.deposit.service.DepositService;

public interface MemoryDepositService extends DepositService {

	public <T extends Object> boolean save(String key, T value);

	public <T extends Object> boolean save(String key, T value, TimeUnit tu,
			int time);

	public <T extends Object> T get(String key);

	public boolean clear(String key);

	public boolean clearAll();
}
