package com.sxdsf.deposit.service;

import com.sxdsf.deposit.service.disk.impl.DiskServiceImpl;
import com.sxdsf.deposit.service.memory.impl.MemoryServiceImpl;
import com.sxdsf.deposit.service.sharedpreferences.impl.SharedPreferencesServiceImpl;

public enum DepositServiceMode {
    MEMORY(MemoryServiceImpl.class),
    DISK(DiskServiceImpl.class),
    SHAREDPREFERENCES(SharedPreferencesServiceImpl.class);

    private Class<?> cls;

    DepositServiceMode(Class<?> cls) {
        this.cls = cls;
    }

    public Class<?> getCls() {
        return cls;
    }
}
