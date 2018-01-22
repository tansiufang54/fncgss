package co.id.franknco.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import co.id.franknco.ui.login.LoginActivity;

import java.util.HashMap;

/**
 * Created by GSS-NB-2016-0012 on 7/19/2017.
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
    private static final String PREF_NAME = "MPos Login";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    //ID (make variable public to access from outside)
    public static final String KEY_ID = "id";

    //Password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "pass";

    //Name User (make variable public to access from outside)
    public static final String USER = "User";

    //merchant Address (make variable public to access from outside)
    public static final String MERCHANT_ADDRESS = "MerchantAddress";

    //merchant Id (make variable public to access from outside)
    public static final String TOKEN_ID = "token";

    //merchant Id (make variable public to access from outside)
    public static final String STATUS_TOKEN = "status_token";


    public SessionManager(Context context) {
        this._context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    //For Session Login
    public void createLoginSession(String id, String user, String token_id) {
        // Storing name in pref
        editor.putString(KEY_ID, id);

        // Storing email in pref
        editor.putString(USER, user);

        // Storing token in pref
        editor.putString(TOKEN_ID, token_id);

        // commit changes
        editor.commit();
    }

    public void setStatusToken(String statusToken) {

        // Storing char pass in pref
        editor.putString(STATUS_TOKEN, statusToken);
        // commit changes
        editor.commit();

    }

    public void setTokenId(String tokenId) {

        // Storing char pass in pref
        editor.putString(TOKEN_ID, tokenId);
        // commit changes
        editor.commit();

    }

    public void setUser(String user) {

        // Storing char pass in pref
        editor.putString(USER, user);
        // commit changes
        editor.commit();

    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

         }

    public void logoutUser_L() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         // After logout redirect user to Loing activity


    }

    public String getUsername() {
        return pref.getString(KEY_ID, "null");
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, "null");
    }

    public String getTokenId() {
        return pref.getString(TOKEN_ID, "null");

    }

    public String getUser() {
        return pref.getString(USER, "null");

    }

    public String getStatusToken() {
        return pref.getString(STATUS_TOKEN, "null");

    }

}