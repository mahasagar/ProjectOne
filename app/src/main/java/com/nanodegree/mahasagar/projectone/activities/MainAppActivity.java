package com.nanodegree.mahasagar.projectone.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.nanodegree.mahasagar.projectone.R;

public class MainAppActivity extends AppCompatActivity {

    private  Boolean mTwoPane = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.fragment_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                Fragment detailsFragment = MovieDetailsActivityFragment.newInstance( this,toolbar);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment,
                                MovieDetailsActivityFragment.class.getSimpleName())
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }

    public boolean isItTab(){
        return mTwoPane;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_app, menu);
        return true;
    }
}
