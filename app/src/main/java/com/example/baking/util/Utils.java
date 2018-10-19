package com.example.baking.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by diegocotta on 19/10/2018.
 */

public class Utils {

    public static boolean hasInternet(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();

    }
}
