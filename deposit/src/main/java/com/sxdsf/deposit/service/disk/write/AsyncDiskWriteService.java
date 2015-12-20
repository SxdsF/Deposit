package com.sxdsf.deposit.service.disk.write;

import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.disk.impl.FileWrapper;

import java.lang.ref.Reference;
import java.util.Map;

public abstract class AsyncDiskWriteService extends AbstractDiskWriteService {
    public AsyncDiskWriteService(Map<String, Reference<FileWrapper>> fileMap) {
        super(fileMap);
        // TODO Auto-generated constructor stub
    }

    public abstract <T> Call<Boolean> save(String root, String fileName, T value);

    public abstract <T> Call<Boolean> save(String root, String fileName, T value, String... dirs);

    public abstract Call<Boolean> deleteAll();

    public abstract Call<Boolean> delete(String root, boolean include);

    public abstract Call<Boolean> delete(String root, String fileName);

    public abstract Call<Boolean> delete(String root, String fileName, String... dirs);

    public abstract Call<Boolean> delete(String root, boolean include, String... dirs);

    @Override
    public DiskWriteMode getWriteMode() {
        // TODO Auto-generated method stub
        return DiskWriteMode.ASYNC;
    }
}
