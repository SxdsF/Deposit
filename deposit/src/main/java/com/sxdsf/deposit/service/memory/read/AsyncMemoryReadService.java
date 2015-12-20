package com.sxdsf.deposit.service.memory.read;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemorySave;

import java.util.concurrent.ExecutorService;

public abstract class AsyncMemoryReadService extends AbstractMemoryReadService {

    protected final ExecutorService executorService;

    public AsyncMemoryReadService(DiskService diskService,
                                  MemorySave<String> stringMemorySave,
                                  MemorySave<Integer> intMemorySave, ExecutorService executorService) {
        super(diskService, stringMemorySave, intMemorySave);
        // TODO Auto-generated constructor stub
        this.executorService = executorService;
    }

    @Override
    public MemoryReadMode getReadMode() {
        // TODO Auto-generated method stub
        return MemoryReadMode.ASYNC;
    }

    /**
     * 根据key取出值
     *
     * @param key
     * @return
     */
    public abstract <V> Call<V> get(String key);

    /**
     * 根据key取出值
     *
     * @param key
     * @return
     */
    public abstract <V> Call<V> get(int key);

}
