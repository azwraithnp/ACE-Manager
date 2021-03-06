package com.example.ribes.anew;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Avinash Mishra on 6/24/2018.
 */

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidHiveLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static final String KEY_ID = "user_id";

    private static final String KEY_TYPE = "user_type";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setID(String id)
    {
        editor.putString(KEY_ID, id);

        editor.apply();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setType(String user_type)
    {
        editor.putString(KEY_TYPE, user_type);
        editor.apply();

    }

    public String getType()
    {
        return pref.getString(KEY_TYPE, "");
    }

    public String getID() { return pref.getString(KEY_ID, "");}
}