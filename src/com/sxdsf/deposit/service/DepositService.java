package com.sxdsf.deposit.service;

public interface DepositService {

	/**
	 * 获取服务的模式： 1、sharedPreferences 2、memory 3、disk 4、database
	 * 
	 * @return
	 */
	public ServiceMode getServiceMode();
}
