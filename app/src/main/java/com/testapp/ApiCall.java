package com.testapp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by osman on 03-11-2016.
 */

public class ApiCall {

    //GET network request
    public static String GET(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //POST network request
    public void POST(OkHttpClient client, String url, RequestBody body,
                     final IAppDataDownloader listener) {

        Request request = new Request.Builder().url(url).post(body).build();
        client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //System.out.println("onFailure " + e);
                listener.downloaderStatus(202, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("onResponse " + response);
                if (!response.isSuccessful()) {
                    listener.downloaderStatus(response.code(), "Server not found");
                } else {
                    // do something wih the result
                    String responseStr = response.body().string();
                    listener.downloaderStatus(response.code(), responseStr);
                }
            }
        });

    }
}

