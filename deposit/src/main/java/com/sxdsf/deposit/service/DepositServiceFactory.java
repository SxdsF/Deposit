package com.sxdsf.deposit.service;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by sunbowen on 2015/12/20.
 */
public class DepositServiceFactory {
    private static final String TAG = "DepositServiceFactory";

    public static <T> T createDepositService(Context context, Class<T> cls) {
        T service = null;
        if (context != null && cls != null) {
            try {
                Constructor<T> constructor = cls.getConstructor(Context.class);
                if (constructor != null) {
                    service = constructor.newInstance(context);
                }
            } catch (InstantiationException e) {
                Log.e(TAG, e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage());
            } catch (NoSuchMethodException e) {
                Log.e(TAG, e.getMessage());
            } catch (InvocationTargetException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return service;
    }
}
