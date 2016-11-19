package com.example.mahirhasan.registrationandlogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by MAHIR HASAN on 12/10/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private String username;

    public Session(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public boolean setLogin(boolean status) {
        spEditor = sp.edit();
        spEditor.putBoolean("is_logged_in", status);
        spEditor.commit();
        return true;
    }

    public String getUsername() {

        return username;
    }

    public boolean getLoggedIn() {
        return sp.getBoolean("is_logged_in", false);
    }
    public void setUsername(String st){
        this.username = st;
    }
}