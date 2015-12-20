package com.sxdsf.deposit.service.disk.write.impl;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;
import com.sxdsf.deposit.service.disk.write.AsyncDiskWriteService;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class AsyncDiskWriteServiceImpl extends AsyncDiskWriteService {
    private final ExecutorService executorService;

    public AsyncDiskWriteServiceImpl(
            Map<String, Reference<FileWrapper>> fileMap,
            ExecutorService executorService) {
        super(fileMap);
        this.executorService = executorService;
    }

    @Override
    public <T> Call<Boolean> save(final String root, final String fileName,
                                  final T value) {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result = false;
                        FileWrapper file = getFile(root);
                        if (file != null) {
                            result = file.save(fileName, value);
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
    public <T> Call<Boolean> save(final String root, final String fileName, final T value, final String... dirs) {
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result = false;
                        FileWrapper file = getFile(root);
                        if (file != null) {
                            result = file.save(fileName, value, dirs);
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
    public Call<Boolean> deleteAll() {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result = false;
                        // 20151120 sunbowen 在此需要保证系列操作的原子性
                        synchronized (fileMap) {
                            Set<Entry<String, Reference<FileWrapper>>> set = fileMap
                                    .entrySet();
                            if (set != null) {
                                for (Entry<String, Reference<FileWrapper>> entry : set) {
                                    if (entry != null) {
                                        FileWrapper file = getFile(entry.getKey());
                                        if (file != null) {
                                            result = file.deleteAll(true);
                                            boolean clear;
                                            fileMap.remove(entry.getKey());
                                            clear = true;
                                            result = result && clear;
                                        }
                                    }
                                }
                                fileMap.clear();
                                result = true;
                            }
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
    public Call<Boolean> delete(final String root, final boolean include) {
        // TODO Auto-generated method stub
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result = false;
                        synchronized (fileMap) {
                            FileWrapper file = getFile(root);
                            if (file != null) {
                                result = file.deleteAll(include);
                                if (include) {
                                    boolean clear;
                                    fileMap.remove(root);
                                    clear = true;
                                    result = result && clear;
                                }
                            }
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
    public Call<Boolean> delete(final String root, final String fileName) {
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result = false;
                        FileWrapper file = getFile(root);
                        if (file != null) {
                            result = file.delete(fileName);
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
    public Call<Boolean> delete(final String root, final String fileName, final String... dirs) {
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result = false;
                        FileWrapper file = getFile(root);
                        if (file != null) {
                            result = file.delete(fileName, dirs);
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
    public Call<Boolean> delete(final String root, final boolean include, final String... dirs) {
        return new Call<Boolean>() {
            @Override
            public void execute(final Callback<Boolean> callback) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean result = false;
                        FileWrapper file = getFile(root);
                        if (file != null) {
                            result = file.delete(include, dirs);
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
