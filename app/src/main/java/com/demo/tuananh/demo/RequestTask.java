package com.demo.tuananh.demo;


import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Tuan Anh on 3/26/2018.
 */

public class RequestTask extends AsyncTask<String, Void, byte[]> {

    OkHttpClient client = new OkHttpClient();

    @Override
    protected byte[] doInBackground(String... params) {

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = null;
        if(params.length > 1){
             request = builder.addHeader("X-Requested-With","XMLHttpRequest")
                    .addHeader("Referer", params[1])
                    .addHeader("User-Agent", params[2])
                    .build();
        }else{
            request = builder.build();
        }
        try {
            Response response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (Exception e) {
            return null;
        }
    }
}