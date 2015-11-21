package com.sxdsf.deposit.service;

public interface Callback<T> {

	public void onResult(T t);

	public void onError(Exception e);
}
