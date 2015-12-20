package com.sxdsf.deposit.sample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sxdsf.deposit.Deposit;
import com.sxdsf.deposit.service.DepositServiceMode;
import com.sxdsf.deposit.service.disk.DiskFolder;
import com.sxdsf.deposit.service.disk.DiskService;
import com.sxdsf.deposit.service.memory.MemoryService;
import com.sxdsf.deposit.service.sharedpreferences.SharedPreferencesService;
import com.sxdsf.deposit.service.sharedpreferences.SharedPreferencesType;

public class MainActivity extends AppCompatActivity {

    private DiskService diskService;
    private MemoryService memoryService;
    private SharedPreferencesService sharedPreferencesService;

    private String root;

    private TextView disk;
    private TextView memory;
    private TextView sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.disk = (TextView) this.findViewById(R.id.disk);
        this.memory = (TextView) this.findViewById(R.id.memory);
        this.sharedPreferences = (TextView) this.findViewById(R.id.sharedPreference);
        this.userDiskService();
        this.userMemoryService();
        this.userSharedPreferencesService();
    }

    private void userDiskService() {
        //初始化diskService，并且创建一个存储的目录
        this.diskService = Deposit.create(this, DepositServiceMode.DISK);
        this.root = this.diskService.create(DiskFolder.CACHE_FOLDER, "test");
        //同步存储和读取
        this.diskService.syncWrite().save(this.root, "test", "disk service test");
        String text = this.diskService.syncRead().get(this.root, "test");
        this.disk.setText(text);
    }

    private void userMemoryService() {
        //初始化memoryService
        this.memoryService = Deposit.create(this, DepositServiceMode.MEMORY);
        //同步存储和读取
        this.memoryService.syncWrite().save("test", "memory service test");
        String text = this.memoryService.syncRead().get("test");
        this.memory.setText(text);
    }

    private void userSharedPreferencesService() {
        //初始化sharedPreferencesService，并且创建一个存储文件
        this.sharedPreferencesService = Deposit.create(this, DepositServiceMode.SHAREDPREFERENCES);
        this.sharedPreferencesService.create("test", Context.MODE_PRIVATE);

        //存储和读取
        this.sharedPreferencesService.write("test").
                save("test", "sharedPreferences service test", SharedPreferencesType.STRING).
                commit();
        String text = this.sharedPreferencesService.getString("test", "test", "");
        this.sharedPreferences.setText(text);
    }
}
