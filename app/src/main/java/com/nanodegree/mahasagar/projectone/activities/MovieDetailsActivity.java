package com.nanodegree.mahasagar.projectone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.model.Movie;

/**
 * Created by sagar on 7/2/16.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);

        Intent intent = getIntent();
        Movie movie =(Movie)intent.getSerializableExtra("Movie");
        if (savedInstanceState == null) {
            Fragment detailsFragment = MovieDetailsActivityFragment.newInstance(movie, this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, detailsFragment,
                            MovieDetailsActivityFragment.class.getSimpleName())
                    .commit();
        }

    }


}
