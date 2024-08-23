package com.example.foodplanner.Model.Network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NetworkChangeReceiver extends BroadcastReceiver {

    Boolean flag = true;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetworkUtil.isNetworkConnected(context);

        if (context instanceof Activity) {
            View rootView = ((Activity) context).findViewById(android.R.id.content);

            if (isConnected) {
                if(!flag)
                {
                    flag = true;
                    Snackbar.make(rootView, "Network connected", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(context.getResources().getColor(android.R.color.holo_green_dark))
                            .setTextColor(context.getResources().getColor(android.R.color.white))
                            .show();
                }
            } else {
                flag = false;
                Snackbar.make(rootView, "Network disconnected", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(context.getResources().getColor(android.R.color.holo_red_dark))
                        .setTextColor(context.getResources().getColor(android.R.color.white))
                        .show();
            }
        } else {
            Toast.makeText(context, "Network status changed", Toast.LENGTH_SHORT).show();
        }
    }
}
