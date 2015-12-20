package com.sxdsf.deposit.service.memory.write;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AsyncMemoryWriteService extends
        AbstractMemoryWriteService {
    protected final ExecutorService executorService;

    public AsyncMemoryWriteService(DiskService diskService,
                                   MemorySave<String> stringMemorySave,
                                   MemorySave<Integer> intMemorySave, ExecutorService executorService) {
        super(diskService, stringMemorySave, intMemorySave);
        // TODO Auto-generated constructor stub
        this.executorService = executorService;
    }

    @Override
    public MemoryWriteMode getWriteMode() {
        // TODO Auto-generated method stub
        return MemoryWriteMode.ASYNC;
    }

    /**
     * 将一个值永久性存入
     *
     * @param key
     * @param value
     * @return
     */
    public abstract <V> Call<Boolean> save(String key, V value);

    /**
     * 将一个值存入，并赋予一个时间期限
     *
     * @param key
     * @param value
     * @param tu
     * @param time
     * @return
     */
    public abstract <V> Call<Boolean> save(String key, V value, TimeUnit tu, int time);

    /**
     * 清除该key的值
     *
     * @param key
     * @return
     */
    public abstract Call<Boolean> remove(String key);

    /**
     * 将一个值永久性存入
     *
     * @param key
     * @param value
     * @return
     */
    public abstract <V> Call<Boolean> save(int key, V value);

    /**
     * 将一个值存入，并赋予一个时间期限
     *
     * @param key
     * @param value
     * @param tu
     * @param time
     * @return
     */
    public abstract <V> Call<Boolean> save(int key, V value, TimeUnit tu, int time);

    /**
     * 清除该key的值
     *
     * @param key
     * @return
     */
    public abstract Call<Boolean> remove(int key);

    /**
     * 清除所有存入的值
     *
     * @return
     */
    public abstract Call<Boolean> clear();

}
