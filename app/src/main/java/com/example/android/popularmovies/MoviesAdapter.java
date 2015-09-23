package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gonzajaimes on 17/09/15.
 * An Image adapter to store images
 */


public class MoviesAdapter extends ArrayAdapter<Movie> {

    private final String LOG_TAG = MoviesAdapter.class.getSimpleName();

    ArrayList<Movie> movies = new ArrayList<Movie>();

    final String BASE_MOVIE_URL = "http://image.tmdb.org/t/p/w185";

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param c        The current context. Used to inflate the layout file.
     * @param movies A List of movies objects to display in a grid
     */

    public MoviesAdapter(Context c, ArrayList<Movie> movies) {

        // Here, we initialize the ArrayAdapter's internal storage for the context and the grid.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.

        super(c, 0, movies);
        this.movies = movies;

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */



    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Movie tempMovie = movies.get(position);
        String posterPath = BASE_MOVIE_URL + tempMovie.getMoviePosterPath();
        if (convertView == null) {
            // if it's not recycled, initialize some attributes


           //imageView = new ImageView(mContext);
           // imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
           // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           //  imageView.setPadding(8, 8, 8, 8);
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.image_display, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.imageview_movies);






        } else {
            imageView = (ImageView) convertView;
        }


        Picasso.with(this.getContext()).
                load(posterPath).into(imageView);
        return imageView;
    }


}
