package com.sxdsf.deposit.service.disk;

public interface Callback<T> {

	public void onResult(T t);

	public void onError(Exception e);
}
