package com.nanodegree.mahasagar.projectone.common;

/**
 * Created by sagar on 8/2/16.
 */
public class Constants {
    public static final String API_KEY = "@@@@@@@@@ API KEY @@@@@@@@@@@";
    public static final String URL_POPULARITY = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+API_KEY +"#results/7";
    public static final String URL_VOTES="http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key="+API_KEY+"#results/7";
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
