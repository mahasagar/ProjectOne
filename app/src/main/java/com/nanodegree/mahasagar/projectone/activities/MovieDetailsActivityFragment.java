package com.nanodegree.mahasagar.projectone.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.common.Constants;
import com.nanodegree.mahasagar.projectone.model.Movie;
import com.nanodegree.mahasagar.projectone.model.Trailer;
import com.nanodegree.mahasagar.projectone.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailsActivityFragment extends Fragment {

    static android.support.v7.widget.Toolbar toolbarMovie;
    Activity activity;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.release_date) TextView release_date;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.overview) TextView overview;
    @Bind(R.id.poster) ImageView poster;
    @Bind(R.id.backdrop) ImageView backdrop;

    @Bind(R.id.trailers)            ViewGroup trailerLayout;

    private List<Trailer> TrailerList = new ArrayList<>();
    RequestQueue requestQueue;

    public MovieDetailsActivityFragment() {

    }

    public static MovieDetailsActivityFragment newInstance(Activity act, android.support.v7.widget.Toolbar toolbar) {
        MovieDetailsActivityFragment fragment = new MovieDetailsActivityFragment();
        toolbarMovie = toolbar;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();

        try {

            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(Exception e){

        }
        Intent intent = getActivity().getIntent();
        Movie movie =(Movie)intent.getParcelableExtra("Movie");
        System.out.println("movie :" + movie.toString());

        title.setText(movie.getTitle().toString());
        getActivity().setTitle(movie.getTitle().toString());


        Toast.makeText(getContext(), "id : " + movie.getId(), Toast.LENGTH_SHORT).show();
        release_date.setText(movie.getRelease_date().toString());
        overview.setText(movie.getOverview().toString());
        rating.setText(movie.getVote_average().toString() + "/");
        Picasso.with(view.getContext()).load(movie.getImg()).into(poster);
        Picasso.with(view.getContext()).load(movie.getBackdrop_path()).into(backdrop);

        prepareTrailerData(movie.getId());
        return view;
    }


    private void prepareTrailerData(String Id) {

        String URL = Constants.TRAILER_URL_PARTONE +Id+Constants.TRAILER_URL_PARTTWO+ Constants.API_KEY;
        StringRequest reqList2 = new StringRequest(com.android.volley.Request.Method.GET, URL,new
                Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        Trailer trailerData = null;
                        try {
                            JSONArray jarray = new JSONObject(response).getJSONArray("results");
                            System.out.println("here" + jarray.toString());
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject object = jarray.getJSONObject(i);
                                String youtube_path = Constants.YOUTUBE_THUMBNAIL_URL +  object.getString("key").toString() + Constants.YOUTUBE_THUMBNAIL_URL_LAST;
                                System.out.println("new_Path" + youtube_path.toString());
                                String youtube_link =Constants.YOUTUBE_VIDEO_LINK+object.getString("key").toString();
                                trailerData = new Trailer(youtube_path,youtube_link);
                                TrailerList.add(trailerData);
                                System.out.println("TrailerList size :" + TrailerList.size());

                            }

                        }catch(Exception e){

                        }
                        trailerLayout.removeAllViews();
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        Picasso picasso = Picasso.with(getActivity());
                        int i =0 ;
                        for (Trailer trailer : TrailerList) {
                            ViewGroup thumbContainer = (ViewGroup) inflater.inflate(R.layout.trailer_layout, trailerLayout,
                                    false);
                            ImageView thumbView = (ImageView) thumbContainer.findViewById(R.id.trailerImg);
                            thumbView.setTag(trailer.getVideo_link());
                            thumbView.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    String videoUrl = (String) v.getTag();
                                    Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                                    startActivity(playVideoIntent);
                                }
                            });
                            picasso
                                    .load(trailer.getVideo_thumbnail())
                                    .resizeDimen(R.dimen.video_width, R.dimen.video_height)
                                    .centerCrop()
                                    .into(thumbView);
                            trailerLayout.addView(thumbContainer);
                            Toast.makeText(getContext(),"End : "+i,Toast.LENGTH_SHORT).show();
                            i++;

                        }
                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

                Toast.makeText(getContext(),"here error"+error.toString(),Toast.LENGTH_SHORT).show();
            }

        });
        requestQueue.add(reqList2);
    }

}