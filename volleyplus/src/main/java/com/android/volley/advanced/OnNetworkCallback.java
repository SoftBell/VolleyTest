package com.android.volley.advanced;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by jongyoulpark on 2014. 7. 17..
 */
public abstract class OnNetworkCallback<T> implements Response.Listener<T>, Response.ErrorListener {

    @Override
    public abstract void onErrorResponse(VolleyError error);

    @Override
    public abstract void onResponse(T result);
}
