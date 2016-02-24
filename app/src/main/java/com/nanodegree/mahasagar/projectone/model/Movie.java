package com.nanodegree.mahasagar.projectone.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by sagar on 31/1/16.
 */
public class Movie implements Parcelable {

    private String title, overview, vote_average,release_date;
    public String img,backdrop_path;

    public Movie() {
    }
    //original_title, overview, vote_average,release_date,poster_path
    public Movie(String title, String overview, String vote_average,String release_date,String img,String backdrop_path) {
        this.title = title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date=release_date;
        this.img = img;
        this.backdrop_path = backdrop_path;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        release_date = in.readString();
        img = in.readString();
        backdrop_path = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(vote_average);
        dest.writeString(release_date);
        dest.writeString(img);
        dest.writeString(backdrop_path);
    }
}