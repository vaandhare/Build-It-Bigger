package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.udacity.gradle.builditbigger.R;

public class MainActivityFragment extends Fragment {

    public boolean testFlag = false;
    ProgressBar progressBar = null;
    private String LOG_TAG = "FREEDUBUG";
    private InterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_activity, container, false);
        progressBar = root.findViewById(R.id.joke_progressbar);
        progressBar.setVisibility(View.GONE);

        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Context context = this.getContext();
        MobileAds.initialize(context, getString(R.string.ad_id_main));
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                progressBar.setVisibility(View.VISIBLE);
                getJoke();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Log.i(LOG_TAG, (getString(R.string.load_fail)));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }

            @Override
            public void onAdLoaded() {
                Log.i(LOG_TAG, (getString(R.string.loaded)));
                super.onAdLoaded();
            }
        });

        // Set onClickListener for the button
        Button button = root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    Log.i(LOG_TAG, (getString(R.string.was_ready)));
                    mInterstitialAd.show();
                } else {
                    Log.i(LOG_TAG, (getString(R.string.was_not_ready)));
                    progressBar.setVisibility(View.VISIBLE);
                    getJoke();
                }
            }
        });

        return root;
    }

    public void getJoke() {
        new EndpointAsyncTask().execute(this);
    }
}
