package com.nanodegree.mahasagar.projectone.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by sagar on 31/1/16.
 */
public class Movie implements Serializable {

    private String title, overview, vote_average,release_date;
    public String img;

    public Movie() {
    }
//original_title, overview, vote_average,release_date,poster_path
    public Movie(String title, String overview, String vote_average,String release_date,String img) {
        this.title = title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date=release_date;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }


}
