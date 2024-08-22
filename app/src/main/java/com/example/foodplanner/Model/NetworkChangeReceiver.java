package com.example.foodplanner.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetworkUtil.isNetworkConnected(context)) {
            Toast.makeText(context, "Network connection lost", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Network connection Restored", Toast.LENGTH_SHORT).show();
        }
    }
}
