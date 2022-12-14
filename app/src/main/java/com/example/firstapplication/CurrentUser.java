package com.example.firstapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CurrentUser {
    private SharedPreferences prefs;

    public CurrentUser(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setuserid(long id) {
        prefs.edit().putLong("userid", id).commit();
    }

    public long getuserid() {
        long userid = prefs.getLong("userid",-1);
        return userid;
    }
}
