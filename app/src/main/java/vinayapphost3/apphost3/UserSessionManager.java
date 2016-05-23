package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* This class is responsible for maintaining the user sessions for app in phone
* here we check if user is already loggedIn, LoggedOut and creating a new session
* due to this class user not needed to login enery time he opens the app, once login
* also helps some other classes to pull needed user information
* */
import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Toast;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_UNAME = "uname";

    // Email address (make variable public to access from outside)
    public static final String KEY_REAL_OWNER = "realOwner";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String realOwner){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_UNAME, name);

        // Storing email in pref
        editor.putString(KEY_REAL_OWNER, realOwner);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        else{
            Intent i = new Intent(_context, ViewListing.class);
            // get user data from session
            HashMap<String, String> user = this.getUserDetails();
            String name = user.get(UserSessionManager.KEY_UNAME);
            String realOwner = user.get(UserSessionManager.KEY_REAL_OWNER);
           // Toast.makeText(_context, "welcome! " + name, Toast.LENGTH_LONG).show();
            i.putExtra(Config.REAL_OWNER_ID,realOwner);
            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
            return false;
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_UNAME, pref.getString(KEY_UNAME, null));

        // user email id
        user.put(KEY_REAL_OWNER, pref.getString(KEY_REAL_OWNER, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){


        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, Login.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}