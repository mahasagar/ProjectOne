package com.nanodegree.mahasagar.projectone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by sagar on 7/2/16.
 */
public class MovieDetailsActivityFragment extends Fragment {

    Activity activity;
    static Movie movieDetails;
    TextView original_title,overview,release_date,vote_average;
    ImageView poster_path;

    @Bind(R.id.toolbar) Toolbar mToolbar;

    public MovieDetailsActivityFragment() {

    }

    public static MovieDetailsActivityFragment newInstance(Movie movie,Activity act) {
        MovieDetailsActivityFragment fragment = new MovieDetailsActivityFragment();
        movieDetails = movie;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_movie_details, container, false);
        setHasOptionsMenu(true);
        Intent intent = getActivity().getIntent();
        Movie movie =(Movie)intent.getSerializableExtra("Movie");

//        mToolbar = (Toolbar)view.findViewById(R.id.toolbarMovieDetails);
//        getActivity().setActionBar(mToolbar);

        getActivity().getActionBar().setTitle(movie.getTitle().toString());
//        original_title = (TextView)view.findViewById(R.id.original_title);
//        poster_path = (ImageView)view.findViewById(R.id.poster_path);
//        overview = (TextView)view.findViewById(R.id.overview);
//        vote_average = (TextView)view.findViewById(R.id.vote_average);
//        release_date = (TextView)view.findViewById(R.id.release_date);
//
//        original_title.setText(movie.getTitle().toString());
//        release_date.setText(movie.getRelease_date().toString());
//        overview.setText(movie.getOverview().toString());
//        vote_average.setText(movie.getVote_average().toString());
//
//        Picasso.with(view.getContext()).load(movie.getImg()).into(poster_path);
//        Toast.makeText(view.getContext(), "movie is :"+movieDetails.getOverview(), Toast.LENGTH_SHORT).show();

        return view;

    }


}
