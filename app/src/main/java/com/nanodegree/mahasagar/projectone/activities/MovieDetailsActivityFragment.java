package com.nanodegree.mahasagar.projectone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailsActivityFragment extends Fragment {

    Activity activity;
    static Movie movieDetails;

    @Bind(R.id.title) TextView title;
    @Bind(R.id.release_date) TextView release_date;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.overview) TextView overview;
    @Bind(R.id.poster) ImageView poster;
    @Bind(R.id.backdrop) ImageView backdrop;

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
        ButterKnife.bind(this, view);

//        Toolbar  toolbar = (Toolbar)view.findViewById(R.id.toolbarMovieDetails);
//        toolbar.setTitle("here");
//        getActivity().setActionBar(toolbar);
//
//        getActivity().getActionBar().setDisplayShowHomeEnabled(true);
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getActivity().getIntent();
        Movie movie =(Movie)intent.getSerializableExtra("Movie");

        title.setText(movie.getTitle().toString());
        release_date.setText(movie.getRelease_date().toString());
        overview.setText(movie.getOverview().toString());
        rating.setText(movie.getVote_average().toString()+"/");

        Picasso.with(view.getContext()).load(movie.getImg()).into(poster);
        Picasso.with(view.getContext()).load(movie.getBackdrop_path()).into(backdrop);
        return view;
    }


}