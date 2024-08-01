package com.example.shoppinglistapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class UserManger {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USER_PREFIX = "user_";
    private SharedPreferences sharedPreferences;

    public UserManger(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // שמירה של משתמש
    public void saveUser(String userId, User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_PREFIX + userId + "_name", user.getName());
        editor.putString(KEY_USER_PREFIX + userId + "_phoneNumber", user.getPhoneNumber());
        editor.apply();
        Log.d("UserManager", "User saved: " + userId + " -> " + user.getName() + ", " + user.getPhoneNumber());
    }

    // קבלת משתמש לפי מזהה
    public User getUser(String userId) {
        String name = sharedPreferences.getString(KEY_USER_PREFIX + userId + "_name", null);
        String phoneNumber = sharedPreferences.getString(KEY_USER_PREFIX + userId + "_phoneNumber", null);

        if (name != null && phoneNumber != null) {
            return new User(name, phoneNumber);
        }
        return null; // או ליצור משתמש ברירת מחדל
    }

    // קבלת רשימה של משתמשים
    public Map<String, User> getUsersMap() {
        Map<String, User> usersMap = new HashMap<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(KEY_USER_PREFIX)) {
                String userId = key.substring(KEY_USER_PREFIX.length()).split("_")[0];
                String value = (String) entry.getValue();

                if (key.endsWith("_name")) {
                    String phoneNumber = sharedPreferences.getString(KEY_USER_PREFIX + userId + "_phoneNumber", null);
                    if (phoneNumber != null) {
                        usersMap.put(userId, new User(value, phoneNumber));
                    }
                }
            }
        }
        Log.d("UserManager", "Users loaded: " + usersMap.size());
        return usersMap;
    }

}
