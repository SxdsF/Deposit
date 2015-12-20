package com.sxdsf.deposit.service.memory.read.impl;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;
import com.sxdsf.deposit.service.memory.Time;
import com.sxdsf.deposit.service.memory.read.AsyncMemoryReadService;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncMemoryReadServiceImpl extends AsyncMemoryReadService {

    public AsyncMemoryReadServiceImpl(DiskService diskService,
                                      MemorySave<String> stringMemorySave,
                                      MemorySave<Integer> intMemorySave, ExecutorService executorService) {
        super(diskService, stringMemorySave, intMemorySave, executorService);
        // TODO Auto-generated constructor stub
    }

    @Override
    public <V> Call<V> get(final String key) {
        // TODO Auto-generated method stub
        return new Call<V>() {
            @Override
            public void execute(final Callback<V> callback) {
                if (callback != null) {
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            V obj = null;
                            synchronized (stringMemorySave) {
                                @SuppressWarnings("unchecked")
                                Reference<V> temp = (Reference<V>) stringMemorySave.mapMemoryCache
                                        .get(key);

                                long time = diskService.syncRead().getModifyTime(
                                        stringMemorySave.root, key);

                                if (time != 0) {
                                    long now = System.currentTimeMillis();
                                    Time t = stringMemorySave.saveTimeMap.get(key);
                                    if (t != null) {
                                        if (!Time.WILL_NOT_INVALID.equals(t)) {
                                            long l = TimeUnit.MILLISECONDS.convert(t.time,
                                                    t.tu);
                                            if (!((now - time) > l)) {
                                                if (temp != null) {
                                                    obj = temp.get();
                                                }
                                                if (obj == null) {
                                                    obj = diskService.syncRead().get(
                                                            stringMemorySave.root,
                                                            key);
                                                    Reference<V> sr = new SoftReference<>(
                                                            obj);
                                                    stringMemorySave.mapMemoryCache.put(
                                                            key, sr);
                                                }
                                            }
                                        } else {
                                            if (temp != null) {
                                                obj = temp.get();
                                            }
                                            if (obj == null) {
                                                obj = diskService.syncRead().get(
                                                        stringMemorySave.root,
                                                        key);
                                                Reference<V> sr = new SoftReference<>(obj);
                                                stringMemorySave.mapMemoryCache
                                                        .put(key, sr);
                                            }
                                        }
                                    }
                                } else {
                                    if (temp != null) {
                                        obj = temp.get();
                                    }
                                }
                            }
                            if (callback != null) {
                                callback.onResult(obj);
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    public <V> Call<V> get(final int key) {
        // TODO Auto-generated method stub
        return new Call<V>() {
            @Override
            public void execute(final Callback<V> callback) {
                if (callback != null) {
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            V obj = null;
                            synchronized (intMemorySave) {
                                @SuppressWarnings("unchecked")
                                Reference<V> temp = (Reference<V>) intMemorySave.mapMemoryCache
                                        .get(key);

                                long time = diskService.syncRead().getModifyTime(
                                        intMemorySave.root, String.valueOf(key));

                                if (time != 0) {
                                    long now = System.currentTimeMillis();
                                    Time t = intMemorySave.saveTimeMap.get(key);
                                    if (t != null) {
                                        if (!Time.WILL_NOT_INVALID.equals(t)) {
                                            long l = TimeUnit.MILLISECONDS.convert(
                                                    t.time, t.tu);
                                            if (!((now - time) > l)) {
                                                if (temp != null) {
                                                    obj = temp.get();
                                                }
                                                if (obj == null) {
                                                    obj = diskService.syncRead().get(
                                                            intMemorySave.root,
                                                            String.valueOf(key));
                                                    Reference<V> sr = new SoftReference<>(
                                                            obj);
                                                    intMemorySave.mapMemoryCache.put(
                                                            key, sr);
                                                }
                                            }
                                        } else {
                                            if (temp != null) {
                                                obj = temp.get();
                                            }
                                            if (obj == null) {
                                                obj = diskService.syncRead().get(
                                                        intMemorySave.root,
                                                        String.valueOf(key));
                                                Reference<V> sr = new SoftReference<>(
                                                        obj);
                                                intMemorySave.mapMemoryCache.put(key,
                                                        sr);
                                            }
                                        }
                                    }
                                } else {
                                    if (temp != null) {
                                        obj = temp.get();
                                    }
                                }
                            }
                            if (callback != null) {
                                callback.onResult(obj);
                            }
                        }
                    });
                }
            }
        };
    }
}
