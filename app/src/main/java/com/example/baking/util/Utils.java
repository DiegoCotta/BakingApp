package com.example.baking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.baking.model.Recipe;
import com.google.gson.Gson;

/**
 * Created by diegocotta on 19/10/2018.
 */

public class Utils {

    private static final String PREFS = "preferencs";

    public static boolean hasInternet(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();

    }

    public static void saveRecipePref(Recipe recipe, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefsEditor.putString("recipe", json);
        prefsEditor.commit();
    }

    public static Recipe getRecipePref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("recipe", "");
        if (json.isEmpty())
            return null;
        return gson.fromJson(json, Recipe.class);
    }
}
