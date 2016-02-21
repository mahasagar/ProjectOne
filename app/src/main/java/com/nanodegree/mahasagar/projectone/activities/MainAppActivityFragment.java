package com.nanodegree.mahasagar.projectone.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.nanodegree.mahasagar.projectone.model.Movie;
import com.nanodegree.mahasagar.projectone.adapters.MoviesAdapter;
import com.nanodegree.mahasagar.projectone.utilities.MyApplication;
import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.utilities.DividerItemDecoration;
import com.nanodegree.mahasagar.projectone.utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainAppActivityFragment extends Fragment  {

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String URL_POPULARITY = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=382b15ec50c5e60faff2089c899e2448#results/7";
    String URL_VOTES="http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key=382b15ec50c5e60faff2089c899e2448#results/7";

    RequestQueue requestQueue;
    public MainAppActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main_app, container, false);
        setHasOptionsMenu(true);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = movieList.get(position);
                //Toast.makeText(getActivity().getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("Movie", movie);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareMovieData(URL_POPULARITY);
        return view;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_highest_rated:
                prepareMovieData(URL_VOTES);
                return true;
            case R.id.sort_most_popular:
                prepareMovieData(URL_POPULARITY);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void prepareMovieData(String Url) {
        final String img_url = "http://image.tmdb.org/t/p/w500/";
        movieList.clear();

        Toast.makeText(getActivity().getApplicationContext(), "Url :" + Url, Toast.LENGTH_SHORT).show();

        StringRequest reqList2 = new StringRequest(com.android.volley.Request.Method.GET, Url,new
                Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        // Toast.makeText(getActivity(), "Response"+response,
                        //       Toast.LENGTH_SHORT).show();
                        try {

                            Log.d("response",response.toString());

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jarray = jsono.getJSONArray("results");
                            Movie movie = null;
                            for (int i = 0; i < jarray.length(); i++) {

                                JSONObject object = jarray.getJSONObject(i);
                                String original_title = object.getString("original_title").toString();
                                String poster_path = img_url + object.getString("poster_path");
                                String overview =  object.getString("overview").toString();
                                String vote_average =  object.getString("vote_average").toString();
                                String release_date = object.getString("release_date").toString();
                                String backdrop_path = img_url +  object.getString("backdrop_path").toString();

                                movie = new Movie(original_title, overview, vote_average,release_date,poster_path,backdrop_path);
                                movieList.add(movie);

                            }
                            Toast.makeText(getActivity().getApplicationContext(), "movieList.size() :"+ movieList.size(), Toast.LENGTH_SHORT).show();
                            recyclerView.setAdapter(mAdapter);
                        }catch(Exception e){

                        }

                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("error",error.toString());
                Toast.makeText(getActivity(), "Errorr..!!"+error.toString(),
                        Toast.LENGTH_SHORT).show();
            }

        });
        requestQueue.add(reqList2);
        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}