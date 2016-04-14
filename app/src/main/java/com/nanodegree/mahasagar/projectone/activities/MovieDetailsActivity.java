package com.nanodegree.mahasagar.projectone.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.model.Movie;

/**
 * Created by sagar on 7/2/16.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);

        Intent intent = getIntent();
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbarMovieDetails);
//        setSupportActionBar(mActionBarToolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActionBarToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (savedInstanceState == null) {
            Fragment detailsFragment = MovieDetailsActivityFragment.newInstance( this,mActionBarToolbar);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, detailsFragment,
                            MovieDetailsActivityFragment.class.getSimpleName())
                    .commit();
        }
    }
}
