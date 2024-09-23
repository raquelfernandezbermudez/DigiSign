package com.example.digisign;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    public static final String PREF_NAME = "DigiSign";
    public static final String KEY_USER_ID = "user_id";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserId(long userId) {
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }

    public long getUserId() {
        return sharedPreferences.getLong(KEY_USER_ID, -1);
    }

    public void clearUserId() {
        editor.remove(KEY_USER_ID);
        editor.apply();
    }
}
