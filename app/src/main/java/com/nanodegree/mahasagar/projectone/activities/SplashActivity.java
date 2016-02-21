 package com.nanodegree.mahasagar.projectone.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.fourmob.panningview.library.PanningView;
import com.nanodegree.mahasagar.projectone.R;

/**
 * Created by sagar on 8/2/16.
 */
public class SplashActivity extends Activity {
    private Thread splashThread;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_splash);


            splashThread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            // Wait given period of time or exit on touch
                            wait(3000);
                        }
                    } catch (InterruptedException ex) {
                    }
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this,MainAppActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            splashThread.start();
    }
}