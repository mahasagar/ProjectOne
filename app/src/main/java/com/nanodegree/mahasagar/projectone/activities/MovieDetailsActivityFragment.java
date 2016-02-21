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

public class MovieDetailsActivityFragment extends Fragment {

    Activity activity;
    static Movie movieDetails;
    TextView title,overview,release_date,rating;
    ImageView poster,backdrop;

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
        Intent intent = getActivity().getIntent();
        Movie movie =(Movie)intent.getSerializableExtra("Movie");

        title = (TextView)view.findViewById(R.id.title);
        poster = (ImageView)view.findViewById(R.id.poster);
        backdrop = (ImageView)view.findViewById(R.id.backdrop);
        overview = (TextView)view.findViewById(R.id.overview);
        rating = (TextView)view.findViewById(R.id.rating);
        release_date = (TextView)view.findViewById(R.id.release_date);

        Log.d("movie.getImg()",movie.getImg());
        Log.d("movie.getImg()",movie.getBackdrop_path());
        title.setText(movie.getTitle().toString());
        release_date.setText(movie.getRelease_date().toString());
        overview.setText(movie.getOverview().toString());
        rating.setText(movie.getVote_average().toString()+"/");
        Picasso.with(view.getContext()).load(movie.getImg()).into(poster);
        Picasso.with(view.getContext()).load(movie.getBackdrop_path()).into(backdrop);

//        Toast.makeText(view.getContext(), "movie is :"+movieDetails.getOverview(), Toast.LENGTH_SHORT).show();

        return view;

    }


}