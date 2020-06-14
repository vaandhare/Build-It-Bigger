package com.udacity.gradle.builditbigger.paid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.R;

public class MainActivityFragment extends Fragment {

    public boolean testFlag = false;
    ProgressBar progressBar = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_activity, container, false);

        // Set onClickListener for the button
        Button button = (Button) root.findViewById(R.id.joke_button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);
                        getJoke();
                    }
                });

        progressBar = (ProgressBar) root.findViewById(R.id.joke_progressbar);
        progressBar.setVisibility(View.GONE);

        return root;
    }

    public void getJoke() {
        new EndpointAsyncTask().execute(this);
    }
}
