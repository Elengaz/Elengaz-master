package com.SemiColon.Hmt.elengaz.API.Service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Delta on 28/02/2018.
 */

public class Preferences {

    Context context;

    public Preferences(Context context) {
        this.context = context;
    }

    public void CreateSharedPref(String id)
    {
        SharedPreferences pref = context.getSharedPreferences("user_id",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id",id);
        editor.apply();
    }
    public void ClearSharedPref()
    {
        SharedPreferences pref = context.getSharedPreferences("user_id",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
