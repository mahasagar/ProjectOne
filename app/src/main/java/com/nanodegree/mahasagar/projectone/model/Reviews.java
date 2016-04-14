package com.nanodegree.mahasagar.projectone.model;

/**
 * Created by mahasagar on 14/4/16.
 */
public class Reviews {

    String Author;
    String Content;

    public Reviews(String Author,String Content){
        this.Author = Author;
        this.Content = Content;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getAuthor() {
        return Author;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
    }
}
