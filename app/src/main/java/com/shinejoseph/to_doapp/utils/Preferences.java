package com.shinejoseph.to_doapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.shinejoseph.to_doapp.utils.Constants.SHARED_PREF;

public class Preferences {
    private static final String TAG_NAME = "user_name";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        pref = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    public String getName() {
        return pref.getString(TAG_NAME, "");
    }

    public void setName(String name) {
        editor = pref.edit();
        editor.putString(TAG_NAME, name);
        editor.apply();
    }

}
