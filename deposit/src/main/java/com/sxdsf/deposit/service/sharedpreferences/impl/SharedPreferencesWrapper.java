package com.sxdsf.deposit.service.sharedpreferences.impl;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public final class SharedPreferencesWrapper implements SharedPreferences {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesWrapper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //    @SuppressLint("CommitPrefEdits")
    //    @SuppressWarnings("unchecked")
    //    public <T> SharedPreferencesWrapper save(String key, T value,
    //                                             SharedPreferencesType type) {
    //        Editor e = this.edit();
    //        if (e != null) {
    //            switch (type) {
    //                case BOOLEAN:
    //                    if (value != null && value.getClass() == Boolean.class) {
    //                        e.putBoolean(key, (Boolean) value).commit();
    //                    }
    //                    break;
    //                case FLOAT:
    //                    if (value != null && value.getClass() == Float.class) {
    //                        e.putFloat(key, (Float) value).commit();
    //                    }
    //                    break;
    //                case INTEGER:
    //                    if (value != null && value.getClass() == Integer.class) {
    //                        e.putInt(key, (Integer) value).commit();
    //                    }
    //                    break;
    //                case LONG:
    //                    if (value != null && value.getClass() == Long.class) {
    //                        e.putLong(key, (Long) value).commit();
    //                    }
    //                    break;
    //                case STRING:
    //                    if (value != null && value.getClass() == String.class) {
    //                        e.putString(key, (String) value).commit();
    //                    }
    //
    //                    break;
    //                case STRINGSET:
    //                    if (value != null && value.getClass() == Set.class) {
    //                        Set<?> set = (Set<?>) value;
    //                        if (!set.isEmpty()) {
    //                            Iterator<?> it = set.iterator();
    //                            if (it != null && it.next() != null
    //                                    && it.next().getClass() == String.class) {
    //                                e.putStringSet(key, (Set<String>) value).commit();
    //                            }
    //                        }
    //                    }
    //                    break;
    //                default:
    //                    break;
    //            }
    //        }
    //        return this;
    //    }

    @Override
    public Map<String, ?> getAll() {
        return this.sharedPreferences.getAll();
    }

    @Nullable
    @Override
    public String getString(String key, String defValue) {
        return this.sharedPreferences.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return this.sharedPreferences.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return this.sharedPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return this.sharedPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return this.sharedPreferences.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return this.sharedPreferences.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return this.sharedPreferences.contains(key);
    }

    @Override
    public Editor edit() {
        return this.sharedPreferences.edit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
