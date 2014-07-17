package com.softbell.volleytest.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.advanced.OnNetworkCallback;
import com.android.volley.advanced.VolleyNetwork;
import com.android.volley.advanced.request.GsonRequest;
import com.android.volley.toolbox.ImageLoader;
import com.softbell.volleytest.R;
import com.softbell.volleytest.data.MD5Data;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView view = (ImageView) findViewById(R.id.netView);

        //view.setImageUrl("http://i.imgur.com/7spzG.png", NetworkVolley.getInstance().getImageLoader());
        VolleyNetwork.getInstance().getImageLoader().get("http://i.imgur.com/7spzG.png",
                ImageLoader.getImageListener(view, R.drawable.ic_launcher, R.drawable.ic_launcher));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            GsonRequest<MD5Data> req = new GsonRequest<MD5Data>("http://md5.jsontest.com/?text=123", null, MD5Data.class, new OnNetworkCallback<MD5Data>() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(MD5Data result) {
                    Toast.makeText(MainActivity.this, result.md5, Toast.LENGTH_SHORT).show();
                }
            });
            VolleyNetwork.getInstance().addToRequestQueue(req);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
