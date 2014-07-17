package com.softbell.volleytest;

import android.app.Application;

import com.android.volley.advanced.VolleyNetwork;

/**
 * Created by jongyoulpark on 2014. 7. 17..
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyNetwork.init(this);
    }
}
