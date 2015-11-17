package com.sxdsf.deposit.service.memory;

import java.util.concurrent.TimeUnit;
import com.sxdsf.deposit.service.DepositService;

/**
 * 面向运存的服务，以key-value的形式
 * 
 * @author sunbowen
 * 
 */
public interface MemoryDepositService extends DepositService {

	public <K, V> boolean save(K key, V value);

	public <K, V> boolean save(K key, V value, TimeUnit tu, int time);

	public <K, V> V get(K key);

	public <K> boolean clear(K key);

	public boolean clearAll();
}
