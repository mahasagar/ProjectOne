package com.nanodegree.mahasagar.projectone.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.mahasagar.projectone.R;
import com.nanodegree.mahasagar.projectone.model.Movie;

import java.util.List;

/**
 * Created by mahasagar on 16/4/16.
 */
public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView itemImage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            itemImage = (ImageView) view.findViewById(R.id.image);
        }
    }


    public FavMovieAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }
    View v ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        byte[] decodedString = Base64.decode(movie.getImg(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.itemImage.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
