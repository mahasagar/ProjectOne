package com.nanodegree.mahasagar.projectone.common;

/**
 * Created by sagar on 8/2/16.
 */
public class Constants {
    public static final String API_KEY = "382b15ec50c5e60faff2089c899e2448";
    public static final String URL_POPULARITY = "https://api.themoviedb.org/3/movie/popular?&api_key="+API_KEY ;
    public static final String URL_VOTES="https://api.themoviedb.org/3/movie/top_rated?&api_key="+API_KEY;
    public static final String IMG_URL = "http://image.tmdb.org/t/p/w500/";
    public static final String TRAILER_URL_PARTONE = "https://api.themoviedb.org/3/movie/";
    public static final String TRAILER_URL_PARTTWO = "/videos?api_key=";
    public static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_THUMBNAIL_URL_LAST = "/0.jpg";
    public static final String YOUTUBE_VIDEO_LINK ="https://www.youtube.com/watch?v=";
    public static final String REVIEW_URL = "https://api.themoviedb.org/3/movie/";
    public static final String REVIEW_URL_LAST = "/reviews?api_key=";

    public static final String PREFS_NAME = "PopularMovies";
    public static final String FAVORITES = "Favorite";


}
