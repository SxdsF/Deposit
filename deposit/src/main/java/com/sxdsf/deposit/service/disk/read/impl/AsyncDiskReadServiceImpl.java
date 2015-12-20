package com.sxdsf.deposit.service.disk.read.impl;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;
import com.sxdsf.deposit.service.disk.read.AsyncDiskReadService;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class AsyncDiskReadServiceImpl extends AsyncDiskReadService {

    public AsyncDiskReadServiceImpl(
            Map<String, Reference<FileWrapper>> fileMap,
            ExecutorService executorService) {
        super(fileMap, executorService);
    }

    @Override
    public <T> Call<T> get(final String root, final String fileName) {
        // TODO Auto-generated method stub
        return new Call<T>() {
            @Override
            public void execute(final Callback<T> callback) {
                if (callback != null) {
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            T result = null;
                            FileWrapper file = getFile(root);
                            if (file != null) {
                                result = file.get(fileName);
                            }
                            if (callback != null) {
                                callback.onResult(result);
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    public <T> Call<T> get(final String root, final String fileName, final String... dirs) {
        return new Call<T>() {
            @Override
            public void execute(final Callback<T> callback) {
                if (callback != null) {
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            T result = null;
                            FileWrapper file = getFile(root);
                            if (file != null) {
                                result = file.get(fileName, dirs);
                            }
                            if (callback != null) {
                                callback.onResult(result);
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    public Call<Long> getModifyTime(final String root, final String fileName) {
        // TODO Auto-generated method stub
        return new Call<Long>() {
            @Override
            public void execute(final Callback<Long> callback) {
                if (callback != null) {
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            long result = 0;
                            FileWrapper file = getFile(root);
                            if (file != null) {
                                result = file.getModifyTime(fileName);
                            }
                            if (callback != null) {
                                callback.onResult(result);
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    public Call<Long> getModifyTime(final String root, final String fileName, final String... dirs) {
        return new Call<Long>() {
            @Override
            public void execute(final Callback<Long> callback) {
                if (callback != null) {
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            long result = 0;
                            FileWrapper file = getFile(root);
                            if (file != null) {
                                result = file.getModifyTime(fileName, dirs);
                            }
                            if (callback != null) {
                                callback.onResult(result);
                            }
                        }
                    });
                }
            }
        };
    }

}
