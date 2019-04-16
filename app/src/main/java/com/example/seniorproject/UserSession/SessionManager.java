package com.example.seniorproject.UserSession;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences prefs;
    Editor editor;
    Context context_;
    int PRIVATE_MODE = 0;
    private static final String PREF_N = "SeniorProject";
    private static final String KEY_LOGIN = "isLoggedIn";

    public boolean isLoggedIn(){
        return prefs.getBoolean(KEY_LOGIN, false);
    }
    public SessionManager(Context context){
        this.context_ = context;
        prefs = context_.getSharedPreferences(PREF_N,PRIVATE_MODE);
        editor = prefs.edit();
    }
    public void setLogIn(boolean isLoggedIn){
        editor.putBoolean(KEY_LOGIN, isLoggedIn);
        editor.commit();
        Log.d(TAG,"LOGIN IS MODIFIED");
    }

}
