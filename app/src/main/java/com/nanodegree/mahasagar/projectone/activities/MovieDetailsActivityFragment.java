package com.nanodegree.mahasagar.projectone.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import com.nanodegree.mahasagar.projectone.model.Reviews;
import com.nanodegree.mahasagar.projectone.model.Trailer;
import com.nanodegree.mahasagar.projectone.utilities.SharedPreference;
import com.nanodegree.mahasagar.projectone.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;

public class MovieDetailsActivityFragment extends Fragment implements View.OnClickListener {

    static android.support.v7.widget.Toolbar toolbarMovie;
    Activity activity;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.release_date) TextView release_date;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.overview) TextView overview;
    @Bind(R.id.poster) ImageView poster;
    @Bind(R.id.backdrop) ImageView backdrop;

    @Bind(R.id.trailers)            ViewGroup trailerLayout;
    @Bind(R.id.reviews)            ViewGroup reviewLayout;

    @Bind(R.id.fabButton)
    FloatingActionButton mFavoriteBtn;

    @BindDrawable(R.drawable.fav)
    Drawable fav;
    @BindDrawable(R.drawable.select_fav)  Drawable not_fav;


    private List<Trailer> TrailerList = new ArrayList<>();
    private List<Reviews> ReviewList = new ArrayList<>();

    RequestQueue requestQueue;
    SharedPreference sharedPreference;
    Movie movie;

    public MovieDetailsActivityFragment() {
        sharedPreference = new SharedPreference();
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
        movie =(Movie)intent.getParcelableExtra("Movie");

        title.setText(movie.getTitle().toString());
        getActivity().setTitle(movie.getTitle().toString());


        release_date.setText(movie.getRelease_date().toString());
        overview.setText(movie.getOverview().toString());
        rating.setText(movie.getVote_average().toString() + "/");
        if (sharedPreference.checkFavoriteItem(movie, getContext())) {
            movie = sharedPreference.getMovieObject(movie.getTitle(), getContext());
            byte[] decodedString = Base64.decode(movie.getImg(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            poster.setImageBitmap(decodedByte);

            byte[] decodedString_backdrop = Base64.decode(movie.getBackdrop_path(), Base64.DEFAULT);
            Bitmap decodedByte_backdrop = BitmapFactory.decodeByteArray(decodedString_backdrop, 0, decodedString_backdrop.length);
            backdrop.setImageBitmap(decodedByte_backdrop);
        } else {
            Picasso.with(view.getContext()).load(movie.getImg()).into(poster);
            Picasso.with(view.getContext()).load(movie.getBackdrop_path()).into(backdrop);
        }
        prepareTrailerData(movie.getId());
        prepareReviewData(movie.getId());

        mFavoriteBtn.setOnClickListener(this);
        updateFabButton();

        return view;
    }

    private void updateFabButton() {
        if (sharedPreference.checkFavoriteItem(movie, getContext())) {
            Toast.makeText(getContext(), movie.getTitle()+" is Fav", Toast.LENGTH_SHORT).show();
            mFavoriteBtn.setImageDrawable(fav);
        } else {
            mFavoriteBtn.setImageDrawable(not_fav);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabButton ) {
            if(!sharedPreference.checkFavoriteItem(movie, getContext())) {
                Bitmap bitmap = ((BitmapDrawable) poster.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String img_str = Base64.encodeToString(byteArray, 0);


                Bitmap bitmap_backPoster = ((BitmapDrawable) backdrop.getDrawable()).getBitmap();
                ByteArrayOutputStream stream_backPoster = new ByteArrayOutputStream();
                bitmap_backPoster.compress(Bitmap.CompressFormat.PNG, 100, stream_backPoster);
                byte[] byteArray_backPoster = stream_backPoster.toByteArray();
                String encodedString_backPoster = Base64.encodeToString(byteArray_backPoster, Base64.DEFAULT);

                movie.setImg(img_str);
                movie.setBackdrop_path(encodedString_backPoster);


                sharedPreference.addFavorite(v.getContext(), movie);
                ArrayList<Movie> favourites = sharedPreference.loadFavorites(getActivity());
                updateFabButton();
            }else{
                sharedPreference.removeFavorite(getContext(),movie);
                updateFabButton();

            }
        }
    }

    private void prepareReviewData(String Id) {

        String URL = Constants.REVIEW_URL +Id+Constants.REVIEW_URL_LAST+ Constants.API_KEY;
        StringRequest reqList2 = new StringRequest(com.android.volley.Request.Method.GET, URL,new
                Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        Reviews reviewData = null;
                        try {
                            JSONArray jarray = new JSONObject(response).getJSONArray("results");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject object = jarray.getJSONObject(i);
                                String review_content =  object.getString("content").toString();
                                String review_author = object.getString("author").toString();
                                reviewData = new Reviews(review_author,review_content);
                                ReviewList.add(reviewData);
                            }

                        }catch(Exception e){

                        }
                        reviewLayout.removeAllViews();
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        for (Reviews review : ReviewList) {
                            ViewGroup thumbContainer = (ViewGroup) inflater.inflate(R.layout.review_item_layout, reviewLayout,
                                    false);
                            TextView review_author = (TextView)thumbContainer.findViewById(R.id.review_author);
                            TextView review_content = (TextView)thumbContainer.findViewById(R.id.review_content);
                            review_author.setText(review.getAuthor().toString());
                            review_content.setText(review.getContent().toString());
                            reviewLayout.addView(thumbContainer);
                        }

                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }

        });
        requestQueue.add(reqList2);
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
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject object = jarray.getJSONObject(i);
                                String youtube_path = Constants.YOUTUBE_THUMBNAIL_URL +  object.getString("key").toString() + Constants.YOUTUBE_THUMBNAIL_URL_LAST;
                                String youtube_link =Constants.YOUTUBE_VIDEO_LINK+object.getString("key").toString();
                                trailerData = new Trailer(youtube_path,youtube_link);
                                TrailerList.add(trailerData);

                            }

                        }catch(Exception e){

                        }
                        trailerLayout.removeAllViews();
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        Picasso picasso = Picasso.with(getActivity());
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

                        }
                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }

        });
        requestQueue.add(reqList2);
    }


}