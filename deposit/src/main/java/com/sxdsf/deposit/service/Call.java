package com.sxdsf.deposit.service;

/**
 * Created by sunbowen on 2015/12/20.
 */
public interface Call<T> {
    void execute(Callback<T> callback);
}
