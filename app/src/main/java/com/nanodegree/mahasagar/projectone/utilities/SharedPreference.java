package com.nanodegree.mahasagar.projectone.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nanodegree.mahasagar.projectone.common.Constants;
import com.nanodegree.mahasagar.projectone.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mahasagar on 16/4/16.
 */
public class SharedPreference {


    public SharedPreference() {
        super();
    }


    public void storeFavorites(Context context, List<Movie> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(Constants.PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(Constants.FAVORITES, jsonFavorites);

        editor.commit();
    }


    public boolean checkFavoriteItem(Movie movie,Context context) {

        boolean check = false;
        List<Movie> favorites = loadFavorites(context);
        if (favorites != null) {
            for (Movie one : favorites) {
                if (one.getTitle().equals(movie.getTitle())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    public Movie getMovieObject(String title,Context context) {
        List<Movie> favorites = loadFavorites(context);
        if (favorites != null) {
            for (Movie one : favorites) {
                if (one.getTitle().equals(title)) {
                    return one;
                }
            }
        }
        return null;
    }


    public boolean getCountFav(Context context) {
        List<Movie> favorites = loadFavorites(context);
        if (favorites != null) {
            return true;
        }
        return false;
    }

    public ArrayList<Movie> loadFavorites(Context context) {
        SharedPreferences settings;
        List<Movie> favorites;

        settings = context.getSharedPreferences(Constants.PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(Constants.FAVORITES)) {
            String jsonFavorites = settings.getString(Constants.FAVORITES, null);
            System.out.println("jsonFavorites" + jsonFavorites);
            Gson gson = new Gson();
            Movie[] favoriteItems = gson.fromJson(jsonFavorites,Movie[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Movie>(favorites);
        } else
            return null;
        return (ArrayList<Movie>) favorites;
    }


    public void addFavorite(Context context, Movie movie) {
        List<Movie> favorites = loadFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Movie>();
        favorites.add(movie);
        storeFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Movie movie) {
        List<Movie> favorites = loadFavorites(context);
        if (favorites != null) {
            for (Movie one : favorites) {
                if (one.getTitle().equals(movie.getTitle())) {
                    favorites.remove(one);
                    storeFavorites(context, favorites);
                    Toast.makeText(context,"Removed from Fav: "+one.getTitle(),Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }


}
