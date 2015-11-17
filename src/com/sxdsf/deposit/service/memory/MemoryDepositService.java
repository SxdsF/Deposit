package com.sxdsf.deposit.service.memory;

import java.util.concurrent.TimeUnit;
import com.sxdsf.deposit.service.DepositService;

/**
 * 面向运存的服务，以key-value的形式，key的类型为String和int，value可以为任意类型
 * 
 * @author sunbowen
 * 
 */
public interface MemoryDepositService extends DepositService {

	/**
	 * 将一个值永久性存入
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <V> boolean save(String key, V value);

	/**
	 * 将一个值存入，并赋予一个时间期限
	 * 
	 * @param key
	 * @param value
	 * @param tu
	 * @param time
	 * @return
	 */
	public <V> boolean save(String key, V value, TimeUnit tu, int time);

	/**
	 * 根据key取出值
	 * 
	 * @param key
	 * @return
	 */
	public <V> V get(String key);

	/**
	 * 清除该key的值
	 * 
	 * @param key
	 * @return
	 */
	public boolean clear(String key);

	/**
	 * 将一个值永久性存入
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <V> boolean save(int key, V value);

	/**
	 * 将一个值存入，并赋予一个时间期限
	 * 
	 * @param key
	 * @param value
	 * @param tu
	 * @param time
	 * @return
	 */
	public <V> boolean save(int key, V value, TimeUnit tu, int time);

	/**
	 * 根据key取出值
	 * 
	 * @param key
	 * @return
	 */
	public <V> V get(int key);

	/**
	 * 清除该key的值
	 * 
	 * @param key
	 * @return
	 */
	public boolean clear(int key);

	/**
	 * 清除所有存入的值
	 * 
	 * @return
	 */
	public boolean clearAll();
}
