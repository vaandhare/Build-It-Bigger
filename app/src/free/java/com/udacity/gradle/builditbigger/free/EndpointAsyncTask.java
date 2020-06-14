package com.udacity.gradle.builditbigger.free;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.android.jokedisplayactivity.DisplayJokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointAsyncTask
        extends AsyncTask<com.udacity.gradle.builditbigger.free.MainActivityFragment, Void, String> {
    private static MyApi myApiService = null;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private MainActivityFragment mainActivityFragment;

    @Override
    protected String doInBackground(MainActivityFragment... params) {

        mainActivityFragment = params[0];
        context = mainActivityFragment.getActivity();

        if (myApiService == null) { // Only do this once
            MyApi.Builder builder =
                    new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // ­ 10.0.2.2 is localhost's IP address in Android emulator
                            // ­ turn off compression when running against local devappserver
//                            .setRootUrl("https://192.168.1.133:8080/_ah/api/")
                            .setRootUrl("https://10.0.2.2/_ah/api/")
                            .setGoogleClientRequestInitializer(
                                    new GoogleClientRequestInitializer() {
                                        @Override
                                        public void initialize(
                                                AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                                throws IOException {
                                            abstractGoogleClientRequest.setDisableGZipContent(true);
                                        }
                                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJokes().execute().getData();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            Toast.makeText(context, "something wrong with the server", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, DisplayJokeActivity.class);
        intent.putExtra("jokes", result);
        context.startActivity(intent);
        mainActivityFragment.progressBar.setVisibility(View.GONE);
    }
}

