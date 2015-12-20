package com.sxdsf.deposit.service.sharedpreferences;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.sxdsf.deposit.service.DepositService;
import com.sxdsf.deposit.service.sharedpreferences.impl.SharedPreferencesWrapper;

import java.util.Map;
import java.util.Set;

public interface SharedPreferencesService extends DepositService {

	void create(String fileName, int mode);

	SharedPreferencesWrapper write(String fileName);

	boolean contains(String fileName, String key);

	void register(String fileName,
			OnSharedPreferenceChangeListener listener);

	void unRegister(String fileName,
			OnSharedPreferenceChangeListener listener);

	Map<String, ?> getAll(String fileName);

	String getString(String fileName, String key, String defaultValue);

	boolean getBoolean(String fileName, String key, boolean defaultValue);

	int getInt(String fileName, String key, int defaultValue);

	long getLong(String fileName, String key, long defaultValue);

	float getFloat(String fileName, String key, float defaultValue);

	Set<String> getStringSet(String fileName, String key,
			Set<String> defaultValue);
}
