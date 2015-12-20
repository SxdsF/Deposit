package com.sxdsf.deposit.service;

public interface Callback<T> {

	void onResult(T t);

	void onError(Exception e);
}
