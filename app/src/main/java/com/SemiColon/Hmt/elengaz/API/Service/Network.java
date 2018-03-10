package com.SemiColon.Hmt.elengaz.API.Service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Delta on 08/03/2018.
 */

public class Network {

    public static boolean getConnection(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo!=null&& networkInfo.isAvailable()&&networkInfo.isConnected())
        {
            return true;
        }

        return false;
    }
}
