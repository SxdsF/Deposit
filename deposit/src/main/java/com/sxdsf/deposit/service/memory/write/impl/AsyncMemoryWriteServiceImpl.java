package com.sxdsf.deposit.service.memory.write.impl;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;
import com.sxdsf.deposit.service.memory.Time;
import com.sxdsf.deposit.service.memory.write.AsyncMemoryWriteService;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncMemoryWriteServiceImpl extends AsyncMemoryWriteService {

    public AsyncMemoryWriteServiceImpl(DiskService diskService,
                                       MemorySave<String> stringMemorySave,
                                       MemorySave<Integer> intMemorySave, ExecutorService executorService) {
        super(diskService, stringMemorySave, intMemorySave, executorService);
        // TODO Auto-generated constructor stub
    }

    @Override
    public <V> Call<Boolean> save(String key, V value) {
        // TODO Auto-generated method stub
        return this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE);
    }

    @Override
    public <V> Call<Boolean> save(final String key, final V value, final TimeUnit tu,
                                  final int time) {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Reference<V> temp = new SoftReference<V>(value);
                        boolean saveValueInDisk;
                        boolean saveTimeInDisk;
                        synchronized (stringMemorySave) {
                            stringMemorySave.mapMemoryCache.put(key, temp);
                            saveValueInDisk = diskService.syncWrite().save(
                                    stringMemorySave.root, key,
                                    value, stringMemorySave.memoryFileName);
                            Time t = new Time(tu, time);
                            stringMemorySave.saveTimeMap.put(key, t);
                            saveTimeInDisk = diskService.syncWrite().save(
                                    stringMemorySave.root,
                                    stringMemorySave.saveTimeMapFileName,
                                    stringMemorySave.saveTimeMap);
                        }
                        if (callback != null) {
                            callback.onResult(saveValueInDisk && saveTimeInDisk);
                        }
                    }
                });
            }
        };
    }

    @Override
    public Call<Boolean> remove(final String key) {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result;
                        synchronized (stringMemorySave) {
                            stringMemorySave.mapMemoryCache.remove(key);
                            result = diskService.syncWrite()
                                    .delete(stringMemorySave.root,
                                            key, stringMemorySave.memoryFileName);
                        }
                        if (callback != null) {
                            callback.onResult(result);
                        }
                    }
                });
            }
        };
    }

    @Override
    public <V> Call<Boolean> save(int key, V value) {
        // TODO Auto-generated method stub
        return this.save(key, value, TimeUnit.DAYS, Integer.MAX_VALUE);
    }

    @Override
    public <V> Call<Boolean> save(final int key, final V value, final TimeUnit tu,
                                  final int time) {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Reference<V> temp = new SoftReference<V>(value);
                        boolean saveValueInDisk;
                        boolean saveTimeInDisk;
                        synchronized (intMemorySave) {
                            intMemorySave.mapMemoryCache.put(key, temp);
                            saveValueInDisk = diskService.syncWrite().save(
                                    intMemorySave.root, String.valueOf(key), value, intMemorySave.memoryFileName);
                            Time t = new Time(tu, time);
                            intMemorySave.saveTimeMap.put(key, t);
                            saveTimeInDisk = diskService.syncWrite().save(
                                    intMemorySave.root,
                                    intMemorySave.saveTimeMapFileName,
                                    intMemorySave.saveTimeMap);
                        }
                        if (callback != null) {
                            callback.onResult(saveValueInDisk && saveTimeInDisk);
                        }
                    }
                });
            }
        };
    }

    @Override
    public Call<Boolean> remove(final int key) {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result;
                        synchronized (intMemorySave) {
                            intMemorySave.mapMemoryCache.remove(key);
                            result = diskService.syncWrite().delete(intMemorySave.root,
                                    String.valueOf(key), intMemorySave.memoryFileName);
                        }
                        if (callback != null) {
                            callback.onResult(result);
                        }
                    }
                });
            }
        };
    }

    @Override
    public Call<Boolean> clear() {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result;
                        synchronized (AsyncMemoryWriteServiceImpl.this) {
                            stringMemorySave.mapMemoryCache.clear();
                            intMemorySave.mapMemoryCache.clear();
                            result = diskService.syncWrite().delete(
                                    stringMemorySave.root, false) &&
                                    diskService.syncWrite().delete(intMemorySave.root, false);
                        }
                        if (callback != null) {
                            callback.onResult(result);
                        }
                    }
                });
            }
        };
    }

}
