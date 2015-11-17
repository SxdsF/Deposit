package com.sxdsf.deposit.service.sharedpreferences;

import java.util.Map;
import java.util.Set;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import com.sxdsf.deposit.service.DepositService;
import com.sxdsf.deposit.service.sharedpreferences.impl.SharedPreferencesWrapper;

public interface SharedPreferencesDepositService extends DepositService {

	public void create(String fileName, int mode);

	public SharedPreferencesWrapper write(String fileName);

	public boolean contains(String fileName, String key);

	public void register(String fileName,
			OnSharedPreferenceChangeListener listener);

	public void unRegister(String fileName,
			OnSharedPreferenceChangeListener listener);

	public Map<String, ?> getAll(String fileName);

	public String getString(String fileName, String key, String defaultValue);

	public boolean getBoolean(String fileName, String key, boolean defaultValue);

	public int getInt(String fileName, String key, int defaultValue);

	public long getLong(String fileName, String key, long defaultValue);

	public float getFloat(String fileName, String key, float defaultValue);

	public Set<String> getStringSet(String fileName, String key,
			Set<String> defaultValue);
}
