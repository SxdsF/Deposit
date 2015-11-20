package com.sxdsf.deposit.service.sharedpreferences.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import android.content.Context;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import com.sxdsf.deposit.service.ServiceMode;
import com.sxdsf.deposit.service.sharedpreferences.SharedPreferencesService;

public class SharedPreferencesServiceImpl implements
		SharedPreferencesService {

	// 20151114 sunbowen 用来保存每个对应存储的SharedPreferences，其中key为文件名
	private final Map<String, SharedPreferencesWrapper> sharedPreferencesMap = new ConcurrentHashMap<>();
	private final Context context;

	public SharedPreferencesServiceImpl(Context context) {
		this.context = context;
	}

	@Override
	public void create(String fileName, int mode) {
		if (this.context != null) {
			SharedPreferencesWrapper sp = new SharedPreferencesWrapper(
					this.context.getSharedPreferences(fileName, mode));
			this.sharedPreferencesMap.put(fileName, sp);
		}

	}

	@Override
	public ServiceMode getServiceMode() {
		// TODO Auto-generated method stub
		return ServiceMode.SHAREDPREFERENCES;
	}

	@Override
	public SharedPreferencesWrapper write(String fileName) {
		// TODO Auto-generated method stub
		return this.sharedPreferencesMap.get(fileName);
	}

	@Override
	public boolean getBoolean(String fileName, String key, boolean defaultValue) {
		// TODO Auto-generated method stub
		boolean result = false;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getBoolean(key, defaultValue);
		}
		return result;
	}

	@Override
	public int getInt(String fileName, String key, int defaultValue) {
		// TODO Auto-generated method stub
		int result = 0;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getInt(key, defaultValue);
		}
		return result;
	}

	@Override
	public long getLong(String fileName, String key, long defaultValue) {
		// TODO Auto-generated method stub
		long result = 0;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getLong(key, defaultValue);
		}
		return result;
	}

	@Override
	public float getFloat(String fileName, String key, float defaultValue) {
		// TODO Auto-generated method stub
		float result = 0;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getFloat(key, defaultValue);
		}
		return result;
	}

	@Override
	public Map<String, ?> getAll(String fileName) {
		// TODO Auto-generated method stub
		Map<String, ?> map = null;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			map = sp.getAll();
		}
		return map;
	}

	@Override
	public String getString(String fileName, String key, String defaultValue) {
		// TODO Auto-generated method stub
		String result = null;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getString(key, defaultValue);
		}
		return result;
	}

	@Override
	public Set<String> getStringSet(String fileName, String key,
			Set<String> defaultValue) {
		// TODO Auto-generated method stub
		Set<String> set = null;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			set = sp.getStringSet(key, defaultValue);
		}
		return set;
	}

	@Override
	public boolean contains(String fileName, String key) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			sp.contains(key);
		}
		return flag;
	}

	@Override
	public void register(String fileName,
			OnSharedPreferenceChangeListener listener) {
		// TODO Auto-generated method stub
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			sp.registerOnSharedPreferenceChangeListener(listener);
		}
	}

	@Override
	public void unRegister(String fileName,
			OnSharedPreferenceChangeListener listener) {
		// TODO Auto-generated method stub
		SharedPreferencesWrapper sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			sp.unregisterOnSharedPreferenceChangeListener(listener);
		}
	}
}
