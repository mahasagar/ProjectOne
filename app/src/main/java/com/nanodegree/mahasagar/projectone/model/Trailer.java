package com.nanodegree.mahasagar.projectone.model;

/**
 * Created by mahasagar on 10/4/16.
 */
public class Trailer {
    String video_thumbnail;
    String video_link;
    public Trailer(String video_thumbnail,String video_link){
        this.video_thumbnail = video_thumbnail;
        this.video_link = video_link;
    }

    public void setVideo_thumbnail(String video_thumbnail) {
        this.video_thumbnail = video_thumbnail;
    }

    public String getVideo_thumbnail() {
        return video_thumbnail;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getVideo_link() {
        return video_link;
    }
}
