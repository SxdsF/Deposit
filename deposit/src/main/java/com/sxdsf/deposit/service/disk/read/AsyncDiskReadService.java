package com.sxdsf.deposit.service.disk.read;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;

import java.lang.ref.Reference;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public abstract class AsyncDiskReadService extends AbstractDiskReadService {

    protected final ExecutorService executorService;

    public AsyncDiskReadService(Map<String, Reference<FileWrapper>> fileMap,
                                ExecutorService executorService) {
        super(fileMap);
        // TODO Auto-generated constructor stub
        this.executorService = executorService;
    }

    /**
     * 异步执行获取文件内容，回调不会在主线程执行，请注意。
     *
     * @param root
     * @param fileName
     */
    public abstract <T> Call<T> get(String root, String fileName);

    /**
     * 异步执行获取文件内容，回调不会在主线程执行，请注意。
     *
     * @param root
     * @param fileName
     * @param dirs
     * @return
     */
    public abstract <T> Call<T> get(String root, String fileName, String... dirs);

    /**
     * 异步执行获取文件修改时间，回调不会在主线程执行，请注意。
     *
     * @param root
     * @param fileName
     */
    public abstract Call<Long> getModifyTime(String root, String fileName);

    /**
     * 异步执行获取文件修改时间，回调不会在主线程执行，请注意。
     *
     * @param root
     * @param fileName
     * @param dirs
     * @return
     */
    public abstract Call<Long> getModifyTime(String root, String fileName, String... dirs);

    @Override
    public DiskReadMode getReadMode() {
        // TODO Auto-generated method stub
        return DiskReadMode.ASYNC;
    }
}
