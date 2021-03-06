package com.nanodegree.mahasagar.projectone.activities;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.nanodegree.mahasagar.projectone.adapters.FavMovieAdapter;
import com.nanodegree.mahasagar.projectone.common.Constants;
import com.nanodegree.mahasagar.projectone.model.Movie;
import com.nanodegree.mahasagar.projectone.adapters.MoviesAdapter;
import com.nanodegree.mahasagar.projectone.utilities.MyApplication;
import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.utilities.DividerItemDecoration;
import com.nanodegree.mahasagar.projectone.utilities.SharedPreference;
import com.nanodegree.mahasagar.projectone.utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainAppActivityFragment extends Fragment  {


    private List<Movie> movieList = new ArrayList<>();
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private FavMovieAdapter mFavAdapter;

    int mPosition = 0;

    SharedPreference sharedPreference;
    RequestQueue requestQueue;
    public MainAppActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main_app, container, false);
        setHasOptionsMenu(true);
        sharedPreference = new SharedPreference();
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        ButterKnife.bind(this, view);

        mAdapter = new MoviesAdapter(movieList);
        mFavAdapter = new FavMovieAdapter(movieList);

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
                Movie movieToParcel;
                mPosition = position;
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);

                if (((MainAppActivity) getActivity()).isItTab()) {
                    if (sharedPreference.checkFavoriteItem(movie, getContext())) {
                        movieToParcel = new Movie(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getVote_average(), movie.getRelease_date(), "", "");

                    } else {
                        movieToParcel = new Movie(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getVote_average(), movie.getRelease_date(), movie.getImg(), movie.getBackdrop_path());

                    }
                    Fragment detailsFragment = MovieDetailsActivityFragment.newInstance(getActivity(), null);
                    Bundle arguments = new Bundle();
                    arguments.putParcelable("SelectedMovie", movieToParcel);

                    detailsFragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container, detailsFragment,
                                    MovieDetailsActivityFragment.class.getSimpleName())
                            .commit();

                } else {
                    if (sharedPreference.checkFavoriteItem(movie, getContext())) {
                        movieToParcel = new Movie(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getVote_average(), movie.getRelease_date(), "", "");
                        intent.putExtra("Movie", movieToParcel);
                    } else {
                        movieToParcel = new Movie(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getVote_average(), movie.getRelease_date(), movie.getImg(), movie.getBackdrop_path());
                        intent.putExtra("Movie", movieToParcel);
                    }
                    intent.putExtra("Movie", movieToParcel);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData(Constants.URL_POPULARITY);
        if (savedInstanceState != null && savedInstanceState.containsKey("Selected")) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt("Selected");
            Toast.makeText(getContext(), "Selected: " + mPosition, Toast.LENGTH_SHORT).show();

        }

        return view;

    }

    public void gettabletViewData(){
        Movie movie = movieList.get(0);
        Movie movieToParcel =null;
        if (((MainAppActivity)getActivity()).isItTab() && movieList.size() > 0) {

            if (sharedPreference.checkFavoriteItem(movie, getContext())) {
                movieToParcel= new Movie(movie.getId(),movie.getTitle(), movie.getOverview(), movie.getVote_average(), movie.getRelease_date(), "", "");
            }else{
                movieToParcel = new Movie(movie.getId(),movie.getTitle(), movie.getOverview(), movie.getVote_average(), movie.getRelease_date(),movie.getImg(), movie.getBackdrop_path());
            }
            Fragment detailsFragment = MovieDetailsActivityFragment.newInstance( getActivity(),null);
            Bundle arguments = new Bundle();
            arguments.putParcelable("SelectedMovie", movieToParcel);

            detailsFragment.setArguments(arguments);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, detailsFragment,
                            MovieDetailsActivityFragment.class.getSimpleName())
                    .commit();


        }else{

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt("Selected", mPosition);
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_highest_rated:
                prepareMovieData(Constants.URL_VOTES);
                return true;
            case R.id.sort_most_popular:
                prepareMovieData(Constants.URL_POPULARITY);
                return true;
            case R.id.sort_fav:
                if(sharedPreference.getCountFav(getContext())) {
                    prepareFavMovieData();
                }else{
                    Toast.makeText(getContext(), "There are No Favourites Movies", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareFavMovieData() {
        movieList.clear();
        movieList = sharedPreference.loadFavorites(getActivity());
        restoreRecycleView();
        mFavAdapter = new FavMovieAdapter(movieList);
        recyclerView.setAdapter(mFavAdapter);
        restoreRecycleView();
        mFavAdapter.notifyDataSetChanged();
        gettabletViewData();
    }


    private void restoreRecycleView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

    }


    private void prepareMovieData(String Url) {

        mAdapter = new MoviesAdapter(movieList);
        movieList.clear();
        StringRequest reqList2 = new StringRequest(com.android.volley.Request.Method.GET, Url,new
                Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        try {
                            JSONArray jarray = new JSONObject(response).getJSONArray("results");
                            Movie movie = null;
                            for (int i = 0; i < jarray.length(); i++) {

                                JSONObject object = jarray.getJSONObject(i);
                                String id =  object.getString("id").toString();
                                String original_title = object.getString("original_title").toString();
                                String poster_path = Constants.IMG_URL + object.getString("poster_path");
                                String overview =  object.getString("overview").toString();
                                String vote_average =  object.getString("vote_average").toString();
                                String release_date = object.getString("release_date").toString();
                                String backdrop_path = Constants.IMG_URL +  object.getString("backdrop_path").toString();

                                movie = new Movie(id,original_title, overview, vote_average,release_date,poster_path,backdrop_path);
                                movieList.add(movie);

                            }
                            recyclerView.setAdapter(mAdapter);
                            gettabletViewData();
                        }catch(Exception e){

                        }

                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }

        });
        requestQueue.add(reqList2);
        restoreRecycleView();
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