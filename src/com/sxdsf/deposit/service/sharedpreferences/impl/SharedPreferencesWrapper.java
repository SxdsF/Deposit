package com.sxdsf.deposit.service.sharedpreferences.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.sxdsf.deposit.service.sharedpreferences.SharedPreferencesDepositType;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public final class SharedPreferencesWrapper {

	private final SharedPreferences sharedPreferences;

	public SharedPreferencesWrapper(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	protected boolean contains(String arg0) {
		// TODO Auto-generated method stub
		return this.sharedPreferences.contains(arg0);
	}

	protected Editor edit() {
		// TODO Auto-generated method stub
		return this.sharedPreferences.edit();
	}

	protected Map<String, ?> getAll() {
		// TODO Auto-generated method stub
		return this.sharedPreferences.getAll();
	}

	protected boolean getBoolean(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return this.sharedPreferences.getBoolean(arg0, arg1);
	}

	protected float getFloat(String arg0, float arg1) {
		// TODO Auto-generated method stub
		return this.sharedPreferences.getFloat(arg0, arg1);
	}

	protected int getInt(String arg0, int arg1) {
		// TODO Auto-generated method stub
		return this.sharedPreferences.getInt(arg0, arg1);
	}

	protected long getLong(String arg0, long arg1) {
		// TODO Auto-generated method stub
		return this.sharedPreferences.getLong(arg0, arg1);
	}

	protected String getString(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return this.sharedPreferences.getString(arg0, arg1);
	}

	protected Set<String> getStringSet(String arg0, Set<String> arg1) {
		// TODO Auto-generated method stub
		return this.sharedPreferences.getStringSet(arg0, arg1);
	}

	protected void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener arg0) {
		// TODO Auto-generated method stub
		this.sharedPreferences.registerOnSharedPreferenceChangeListener(arg0);
	}

	protected void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener arg0) {
		// TODO Auto-generated method stub
		this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(arg0);
	}

	@SuppressLint("CommitPrefEdits")
	@SuppressWarnings("unchecked")
	public <T> SharedPreferencesWrapper save(String key, T value,
			SharedPreferencesDepositType type) {
		Editor e = this.edit();
		if (e != null) {
			switch (type) {
			case BOOLEAN:
				if (value != null && value.getClass() == Boolean.class) {
					e.putBoolean(key, (Boolean) value);
				}
				break;
			case FLOAT:
				if (value != null && value.getClass() == Float.class) {
					e.putFloat(key, (Float) value);
				}
				break;
			case INTEGER:
				if (value != null && value.getClass() == Integer.class) {
					e.putInt(key, (Integer) value);
				}
				break;
			case LONG:
				if (value != null && value.getClass() == Long.class) {
					e.putLong(key, (Long) value);
				}
				break;
			case STRING:
				if (value != null && value.getClass() == String.class) {
					e.putString(key, (String) value);
				}

				break;
			case STRINGSET:
				if (value != null && value.getClass() == Set.class) {
					Set<?> set = (Set<?>) value;
					if (!set.isEmpty()) {
						Iterator<?> it = set.iterator();
						if (it != null && it.next() != null
								&& it.next().getClass() == String.class) {
							e.putStringSet(key, (Set<String>) value);
						}
					}
				}
				break;
			default:
				break;
			}
		}
		return this;
	}

	@SuppressLint("CommitPrefEdits")
	public SharedPreferencesWrapper remove(String key) {
		Editor e = this.edit();
		if (e != null) {
			e.remove(key);
		}
		return this;
	}

	@SuppressLint("CommitPrefEdits")
	public SharedPreferencesWrapper clear() {
		Editor e = this.edit();
		if (e != null) {
			e.clear();
		}
		return this;
	}

	public boolean commit() {
		boolean result = false;
		Editor e = this.edit();
		if (e != null) {
			result = e.commit();
		}
		return result;
	}

	public void apply() {
		Editor e = this.edit();
		if (e != null) {
			e.apply();
		}
	}

}
