package com.sxdsf.deposit;

import android.content.Context;

import com.sxdsf.deposit.service.DepositService;
import com.sxdsf.deposit.service.DepositServiceFactory;
import com.sxdsf.deposit.service.DepositServiceMode;

/**
 * Created by sunbowen on 2015/12/20.
 */
public class Deposit {

    public static <T extends DepositService> T create(Context context, DepositServiceMode mode) {
        T service = null;
        if (context != null && mode != null) {
            service = (T) DepositServiceFactory.createDepositService(context, mode.getCls());
            if (service != null) {
                if (mode != service.getServiceMode()) {
                    service = null;
                }
            }
        }
        return service;
    }
}
