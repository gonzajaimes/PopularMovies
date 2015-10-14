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

    final String BASE_MOVIE_URL = "http://image.tmdb.org/t/p/w500";

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param c      The current context. Used to inflate the layout file.
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

     // create Viewholder Class to avoid using findViewByID so often
    class ViewHolder {

        ImageView movieImageView;

        ViewHolder(View v) {
            movieImageView = (ImageView) v.findViewById(R.id.imageview_movies);
        }
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */


    public View getView(int position, View convertView, ViewGroup parent) {
        View imageView = convertView;
        ViewHolder holder = null;


        Movie tempMovie = movies.get(position);
          //construct the posterPath to retrieve the poster from the internet
        String posterPath = BASE_MOVIE_URL + tempMovie.getMoviePosterPath();

        if (imageView == null) {
            // if it's not recycled, initialize some attributes

            imageView = LayoutInflater.from(getContext()).
                    inflate(R.layout.image_display, parent, false);
            holder = new ViewHolder(imageView);
            imageView.setTag(holder);


        } else {
            // set the viewholder to the imageView
            holder = (ViewHolder) imageView.getTag();
        }


        Picasso.with(this.getContext()).
                load(posterPath).into(holder.movieImageView);
        return imageView;
    }


}
