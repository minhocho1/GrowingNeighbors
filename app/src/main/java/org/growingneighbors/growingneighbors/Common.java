package org.growingneighbors.growingneighbors;

import android.content.Context;
import android.net.ConnectivityManager;

// this class is for common methods that can be called from other classes
public class Common {

    // checking current internet connection
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getActiveNetworkInfo() != null);
    }
}