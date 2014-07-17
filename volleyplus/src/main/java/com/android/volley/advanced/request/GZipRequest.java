package com.android.volley.advanced.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.advanced.OnNetworkCallback;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class GZipRequest extends StringRequest {

    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final OnNetworkCallback<String> listener;

    public GZipRequest(String url, HashMap<String, String> headers, OnNetworkCallback<String> listener) {
        this(Method.GET, url, headers, null, listener);
    }

    public GZipRequest(String url, HashMap<String, String> headers, HashMap<String, String> params,
                       OnNetworkCallback<String> listener) {
        this(Method.POST, url, headers, params, listener);
    }

    private GZipRequest(int method, String url, HashMap<String, String> headers,
                        HashMap<String, String> params, OnNetworkCallback<String> listener) {
        super(method, url, listener, listener);
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

    // parse the gzip response using a GZIPInputStream
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String output = "";
        try {
            GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
            InputStreamReader reader = new InputStreamReader(gStream);
            BufferedReader in = new BufferedReader(reader);
            String read;
            while ((read = in.readLine()) != null) {
                output += read;
            }
            reader.close();
            in.close();
            gStream.close();
        } catch (IOException e) {
            return Response.error(new ParseError());
        }
        return Response.success(output, HttpHeaderParser.parseCacheHeaders(response));
    }
}