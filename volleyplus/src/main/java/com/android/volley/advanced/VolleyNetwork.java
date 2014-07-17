package com.android.volley.advanced;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by jongyoulpark on 2014. 7. 17..
 */
public class VolleyNetwork {

    private static final String VOLLEY_TAG = "VOLLEY";

    private static VolleyNetwork instance;

    private RequestQueue queue;

    public static synchronized void init(Context context) {
        instance = new VolleyNetwork(context);
    }

    public static synchronized VolleyNetwork getInstance() {
        return instance;
    }

    private VolleyNetwork(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        req.setTag(VOLLEY_TAG);
        queue.add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }
}
