package com.sxdsf.deposit.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import com.sxdsf.deposit.service.DepositService;

public class SharedPreferencesDepositServiceImpl implements DepositService {

	// 20151114 sunbowen 用来保存每个对应存储的SharedPreferences，其中key为文件名
	private final Map<String, SharedPreferences> sharedPreferencesMap = new ConcurrentHashMap<>();
	private final Context context;

	public SharedPreferencesDepositServiceImpl(Context context) {
		this.context = context;
	}

	public void create(String fileName, int mode) {
		if (this.context != null) {
			SharedPreferences sp = this.context.getSharedPreferences(fileName,
					mode);
			this.sharedPreferencesMap.put(fileName, sp);
		}

	}

	@SuppressWarnings("unchecked")
	public <T> boolean syncSave(String fileName, String key, T value,
			SharedPreferencesDepositType type) {
		boolean flag = false;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			Editor e = sp.edit();
			if (e != null) {
				switch (type) {
				case BOOLEAN:
					if (value != null && value.getClass() == Boolean.class) {
						flag = e.putBoolean(key, (Boolean) value).commit();
					}
					break;
				case FLOAT:
					if (value != null && value.getClass() == Float.class) {
						flag = e.putFloat(key, (Float) value).commit();
					}
					break;
				case INTEGER:
					if (value != null && value.getClass() == Integer.class) {
						flag = e.putInt(key, (Integer) value).commit();
					}
					break;
				case LONG:
					if (value != null && value.getClass() == Long.class) {
						flag = e.putLong(key, (Long) value).commit();
					}
					break;
				case STRING:
					if (value != null && value.getClass() == String.class) {
						flag = e.putString(key, (String) value).commit();
					}

					break;
				case STRINGSET:
					if (value != null && value.getClass() == Set.class) {
						Set<?> set = (Set<?>) value;
						if (!set.isEmpty()) {
							Iterator<?> it = set.iterator();
							if (it != null && it.next() != null
									&& it.next().getClass() == String.class) {
								flag = e.putStringSet(key, (Set<String>) value)
										.commit();
							}
						}
					}
					break;
				default:
					break;
				}

			}
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public <T> void asyncSave(String fileName, String key, T value,
			SharedPreferencesDepositType type) {
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			Editor e = sp.edit();
			if (e != null) {
				switch (type) {
				case BOOLEAN:
					if (value != null && value.getClass() == Boolean.class) {
						e.putBoolean(key, (Boolean) value).apply();
					}
					break;
				case FLOAT:
					if (value != null && value.getClass() == Float.class) {
						e.putFloat(key, (Float) value).apply();
					}
					break;
				case INTEGER:
					if (value != null && value.getClass() == Integer.class) {
						e.putInt(key, (Integer) value).apply();
					}
					break;
				case LONG:
					if (value != null && value.getClass() == Long.class) {
						e.putLong(key, (Long) value).apply();
					}
					break;
				case STRING:
					if (value != null && value.getClass() == String.class) {
						e.putString(key, (String) value).apply();
					}

					break;
				case STRINGSET:
					if (value != null && value.getClass() == Set.class) {
						Set<?> set = (Set<?>) value;
						if (!set.isEmpty()) {
							Iterator<?> it = set.iterator();
							if (it != null && it.next() != null
									&& it.next().getClass() == String.class) {
								e.putStringSet(key, (Set<String>) value)
										.apply();
							}
						}
					}
					break;
				default:
					break;
				}

			}
		}
	}

	public Map<String, ?> getAll(String fileName) {
		Map<String, ?> map = null;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			map = sp.getAll();
		}
		return map;
	}

	public String getString(String fileName, String key, String defaultValue) {
		String result = null;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getString(key, defaultValue);
		}
		return result;
	}

	public Boolean getBoolean(String fileName, String key, Boolean defaultValue) {
		Boolean result = false;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getBoolean(key, defaultValue);
		}
		return result;
	}

	public Integer getInt(String fileName, String key, Integer defaultValue) {
		Integer result = -1;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getInt(key, defaultValue);
		}
		return result;
	}

	public Long getLong(String fileName, String key, Long defaultValue) {
		Long result = -1L;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getLong(key, defaultValue);
		}
		return result;
	}

	public Float getFloat(String fileName, String key, Float defaultValue) {
		Float result = -1.0f;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getFloat(key, defaultValue);
		}
		return result;
	}

	public Set<String> getStringSet(String fileName, String key,
			Set<String> defaultValue) {
		Set<String> result = null;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			result = sp.getStringSet(key, defaultValue);
		}
		return result;
	}

	public void asyncRemove(String fileName, String key) {
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			Editor e = sp.edit();
			if (e != null) {
				e.remove(key).apply();
			}
		}
	}

	public boolean syncRemove(String fileName, String key) {
		boolean flag = false;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			Editor e = sp.edit();
			if (e != null) {
				flag = e.remove(key).commit();
			}
		}
		return flag;
	}

	public void asyncClear(String fileName) {
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			Editor e = sp.edit();
			if (e != null) {
				e.clear().apply();
			}
		}
	}

	public boolean syncClear(String fileName) {
		boolean flag = false;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			Editor e = sp.edit();
			if (e != null) {
				flag = e.clear().commit();
			}
		}
		return flag;
	}

	public boolean contains(String fileName, String key) {
		boolean flag = false;
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			sp.contains(key);
		}
		return flag;
	}

	public void register(String fileName,
			OnSharedPreferenceChangeListener listener) {
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			sp.registerOnSharedPreferenceChangeListener(listener);
		}
	}

	public void unRegister(String fileName,
			OnSharedPreferenceChangeListener listener) {
		SharedPreferences sp = this.sharedPreferencesMap.get(fileName);
		if (sp != null) {
			sp.unregisterOnSharedPreferenceChangeListener(listener);
		}
	}
}
