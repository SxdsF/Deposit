package com.sxdsf.deposit.service.memory.impl;

import android.content.Context;

import com.sxdsf.deposit.service.DepositServiceMode;
import com.sxdsf.deposit.service.disk.DiskFolder;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.disk.impl.DiskServiceImpl;
import com.sxdsf.deposit.service.memory.MemorySave;
import com.sxdsf.deposit.service.memory.MemoryService;
import com.sxdsf.deposit.service.memory.Time;
import com.sxdsf.deposit.service.memory.read.AsyncMemoryReadService;
import com.sxdsf.deposit.service.memory.read.SyncMemoryReadService;
import com.sxdsf.deposit.service.memory.read.impl.AsyncMemoryReadServiceImpl;
import com.sxdsf.deposit.service.memory.read.impl.SyncMemoryReadServiceImpl;
import com.sxdsf.deposit.service.memory.write.AsyncMemoryWriteService;
import com.sxdsf.deposit.service.memory.write.SyncMemoryWriteService;
import com.sxdsf.deposit.service.memory.write.impl.AsyncMemoryWriteServiceImpl;
import com.sxdsf.deposit.service.memory.write.impl.SyncMemoryWriteServiceImpl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemoryServiceImpl implements MemoryService {

    private final String root;
    private static final String DIR = "MEMORY_CACHE";
    private final String KEY_STRING = "KEY_STRING";
    private final String STRING_TIME_MAP = "stringTimeMap";
    private final String KEY_INT = "KEY_INT";
    private final String INT_TIME_MAP = "intTimeMap";
    private final DiskService diskService;
    private final MemorySave<String> stringMemorySave;
    private final MemorySave<Integer> intMemorySave;

    private final ExecutorService executorService = Executors
            .newSingleThreadExecutor();

    private final SyncMemoryReadService syncMemoryReadService;
    private final AsyncMemoryReadService asyncMemoryReadService;
    private final SyncMemoryWriteService syncMemoryWriteService;
    private final AsyncMemoryWriteService asyncMemoryWriteService;

    public MemoryServiceImpl(Context context) {
        this(new DiskServiceImpl(context));
    }

    private MemoryServiceImpl(DiskService diskServiceImpl) {
        this.diskService = diskServiceImpl;
        /**
         * 默认采用本应用包下的cache文件夹来存储
         */
        this.root = this.diskService.create(DiskFolder.CACHE_FOLDER, DIR);
        this.stringMemorySave = new MemorySave<>(this.root, KEY_STRING,
                STRING_TIME_MAP);
        this.intMemorySave = new MemorySave<>(this.root, KEY_INT,
                INT_TIME_MAP);

        //20151220 sunbowen 初始化以string为key的时间映射
        Map<String, Time> stringTimeMap = this.diskService
                .syncRead().get(this.stringMemorySave.root,
                        this.stringMemorySave.saveTimeMapFileName);
        if (stringTimeMap != null) {
            Set<Entry<String, Time>> set = stringTimeMap.entrySet();
            if (set != null) {
                Iterator<Entry<String, Time>> it = set.iterator();
                if (it != null) {
                    while (it.hasNext()) {
                        Entry<String, Time> entry = it.next();
                        if (entry != null) {
                            String key = entry.getKey();
                            Time value = entry.getValue();
                            this.stringMemorySave.saveTimeMap.put(key, value);
                        }
                    }
                }
            }
        }

        //20151220 sunbowen 初始化以int为key的时间映射
        Map<Integer, Time> intTimeMap = this.diskService
                .syncRead().get(this.intMemorySave.root,
                        this.intMemorySave.saveTimeMapFileName);
        if (intTimeMap != null) {
            Set<Entry<Integer, Time>> set = intTimeMap.entrySet();
            if (set != null) {
                Iterator<Entry<Integer, Time>> it = set.iterator();
                if (it != null) {
                    while (it.hasNext()) {
                        Entry<Integer, Time> entry = it.next();
                        if (entry != null) {
                            int key = entry.getKey();
                            Time value = entry.getValue();
                            this.intMemorySave.saveTimeMap.put(key, value);
                        }
                    }
                }
            }
        }
        this.syncMemoryReadService = new SyncMemoryReadServiceImpl(
                this.diskService, this.stringMemorySave, this.intMemorySave);
        this.asyncMemoryReadService = new AsyncMemoryReadServiceImpl(
                this.diskService, this.stringMemorySave, this.intMemorySave,
                this.executorService);
        this.syncMemoryWriteService = new SyncMemoryWriteServiceImpl(
                this.diskService, this.stringMemorySave, this.intMemorySave);
        this.asyncMemoryWriteService = new AsyncMemoryWriteServiceImpl(
                this.diskService, this.stringMemorySave, this.intMemorySave,
                this.executorService);
    }

    @Override
    public DepositServiceMode getServiceMode() {
        // TODO Auto-generated method stub
        return DepositServiceMode.MEMORY;
    }

    @Override
    public AsyncMemoryReadService asyncRead() {
        // TODO Auto-generated method stub
        return this.asyncMemoryReadService;
    }

    @Override
    public SyncMemoryReadService syncRead() {
        // TODO Auto-generated method stub
        return this.syncMemoryReadService;
    }

    @Override
    public AsyncMemoryWriteService asyncWrite() {
        // TODO Auto-generated method stub
        return this.asyncMemoryWriteService;
    }

    @Override
    public SyncMemoryWriteService syncWrite() {
        // TODO Auto-generated method stub
        return this.syncMemoryWriteService;
    }

}
