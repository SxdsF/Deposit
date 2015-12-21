package com.sxdsf.deposit.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sxdsf.deposit.Deposit;
import com.sxdsf.deposit.service.Call;
import com.sxdsf.deposit.service.Callback;
import com.sxdsf.deposit.service.DepositServiceMode;
import com.sxdsf.deposit.service.disk.DiskFolder;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemoryService;
import com.sxdsf.deposit.service.sharedpreferences.SharedPreferencesService;

public class MainActivity extends AppCompatActivity {

    private DiskService diskService;
    private MemoryService memoryService;
    private SharedPreferencesService sharedPreferencesService;

    private String root;

    private TextView syncDisk;
    private TextView asyncDisk;
    private TextView syncMemory;
    private TextView asyncMemory;
    private TextView sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.syncDisk = (TextView) this.findViewById(R.id.sync_disk);
        this.asyncDisk = (TextView) this.findViewById(R.id.async_disk);
        this.syncMemory = (TextView) this.findViewById(R.id.sync_memory);
        this.asyncMemory = (TextView) this.findViewById(R.id.async_memory);
        this.sharedPreferences = (TextView) this.findViewById(R.id.sharedPreference);
        this.syncUserDiskService();
        this.asyncUserDiskService();
        this.syncUserMemoryService();
        this.asyncUserMemoryService();
        this.userSharedPreferencesService();
    }

    private void syncUserDiskService() {
        //初始化diskService，并且创建一个存储的目录
        this.diskService = Deposit.create(this, DepositServiceMode.DISK);
        this.root = this.diskService.create(DiskFolder.CACHE_FOLDER, "sync_test");
        //同步存储和读取
        this.diskService.syncWrite().save(this.root, "sync_test", "syncDisk service sync_test");
        String text = this.diskService.syncRead().get(this.root, "sync_test");
        this.syncDisk.setText(text);
    }

    private void asyncUserDiskService() {
        //初始化diskService，并且创建一个存储的目录
        this.diskService = Deposit.create(this, DepositServiceMode.DISK);
        this.root = this.diskService.create(DiskFolder.CACHE_FOLDER, "async_test");
        //同步存储和读取
        this.diskService.asyncWrite().save(this.root, "async_test", "asyncDisk service async_test");
        Call<String> call = this.diskService.asyncRead().get(this.root, "async_test");
        call.execute(new Callback<String>() {
            @Override
            public void onResult(String s) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void syncUserMemoryService() {
        //初始化memoryService
        this.memoryService = Deposit.create(this, DepositServiceMode.MEMORY);
        //同步存储和读取
        this.memoryService.syncWrite().save("sync_test", "syncMemory service sync_test");
        String text = this.memoryService.syncRead().get("sync_test");
        this.syncMemory.setText(text);
    }

    private void asyncUserMemoryService() {
        //初始化memoryService
        this.memoryService = Deposit.create(this, DepositServiceMode.MEMORY);
        //同步存储和读取
        this.memoryService.asyncWrite().save("async_test", "asyncMemory service async_test");
        Call<String> call = this.memoryService.asyncRead().get("async_test");
        call.execute(new Callback<String>() {
            @Override
            public void onResult(String s) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void userSharedPreferencesService() {
        //初始化sharedPreferencesService，并且创建一个存储文件
        this.sharedPreferencesService = Deposit.create(this, DepositServiceMode.SHAREDPREFERENCES);
        this.sharedPreferencesService.create("test", Context.MODE_PRIVATE);

        //存储和读取
        this.sharedPreferencesService.write("test").edit().putString("test", "sharedPreferences service test").
                commit();
        String text = this.sharedPreferencesService.getString("test", "test", "");
        this.sharedPreferences.setText(text);
    }
}
