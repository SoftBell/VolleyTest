package com.android.volley.advanced.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.advanced.OnNetworkCallback;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {

    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final OnNetworkCallback<T> listener;

    public GsonRequest(String url, HashMap<String, String> headers, Class<T> clazz, OnNetworkCallback<T> listener) {
        this(Method.GET, url, headers, null, clazz, listener);
    }

    public GsonRequest(String url, HashMap<String, String> headers, HashMap<String, String> params,
                       Class<T> clazz, OnNetworkCallback<T> listener) {
        this(Method.POST, url, headers, params, clazz, listener);
    }

    private GsonRequest(int method, String url, HashMap<String, String> headers,
                        HashMap<String, String> params, Class<T> clazz, OnNetworkCallback<T> listener) {
        super(method, url, listener);
        this.clazz = clazz;
        this.headers = headers;
        this.params = params;
        this.listener = listener;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        if (null != listener) {
            listener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    new Gson().fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}