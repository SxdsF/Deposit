package com.sxdsf.deposit.service.disk.read;

import com.sxdsf.deposit.exception.WrongMethodCallException;
import com.sxdsf.deposit.service.disk.Callback;

public abstract class SyncReadServiceAdapter implements ReadService {

	@Override
	public final <T> void get(String root, String fileName, Callback<T> callback)
			throws WrongMethodCallException {
		// TODO Auto-generated method stub
		throw new WrongMethodCallException();
	}

	@Override
	public final void getModifyTime(String root, String fileName,
			Callback<Long> callback) {
		// TODO Auto-generated method stub

	}

}
