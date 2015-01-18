
package com.dekkoh.util;

import java.util.Arrays;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceManager {
    private static final String TAG = "SharedPreferenceManager";
    private static final String MyPREFERENCES = "DEKKOH_ANDROID";
    private static SharedPreferenceManager sharedPreferenceManager;
    private SharedPreferences sharedpreferences;
    private Editor editor;

    public SharedPreferenceManager(Activity activity) {
        sharedpreferences = activity.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public static SharedPreferenceManager getInstance(Activity activity) {
        if (sharedPreferenceManager == null) {
            sharedPreferenceManager = new SharedPreferenceManager(activity);
        }
        return sharedPreferenceManager;
    }

    public void save(String key, String value) {
        if (Log.DEBUG) {
            Log.i(TAG, "save(key : " + key + " | value : " + value + ")");
        }
        editor.putString(key, value);
        editor.apply();
    }

    public void save(String key, boolean value) {
        if (Log.DEBUG) {
            Log.i(TAG, "save(key : " + key + " | value : " + value + ")");
        }
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void save(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    public void save(String key, long value) {
        if (Log.DEBUG) {
            Log.i(TAG, "save(key : " + key + " | value : " + value + ")");
        }
        editor.putLong(key, value);
        editor.apply();
    }

    public void save(String key, int value) {
        if (Log.DEBUG) {
            Log.i(TAG, "save(key : " + key + " | value : " + value + ")");
        }
        editor.putInt(key, value);
        editor.apply();
    }

    public void save(String key, Set<String> values) {
        if (Log.DEBUG) {
            Log.i(TAG,
                    "save(key : " + key + " | value : "
                            + Arrays.toString(values.toArray()) + ")");
        }
        editor.putStringSet(key, values);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedpreferences.getBoolean(key, false);
    }

    public float getFloat(String key) {
        return sharedpreferences.getFloat(key, 0);
    }

    public int getInt(String key) {
        return sharedpreferences.getInt(key, -1);
    }

    public long getLong(String key) {
        return sharedpreferences.getLong(key, 0);
    }

    public String getString(String key) {
        return sharedpreferences.getString(key, null);
    }

    public Set<String> getStringSet(String key) {
        return sharedpreferences.getStringSet(key, null);
    }

    public boolean contains(String key) {
        return sharedpreferences.contains(key);

    }

    public void remove(String key) {
        editor.remove(key);
        editor.apply();

    }

    public void removeAll(String preferenceName) {
        editor.clear();
        editor.apply();

    }
}
