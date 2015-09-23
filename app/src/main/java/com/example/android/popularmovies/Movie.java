package com.example.android.popularmovies;

/**
 * Created by gonzajaimes on 21/09/15.
 *
 * Movies Class storing id and Poster info
 */
public class Movie {

    public String movieId;
    public String moviePosterPath;

    public Movie (String id, String posterPath){
        movieId = id;
        moviePosterPath = posterPath;


    }
    //public String getMovieId (){

      //  return movieId;
    //}

    public String getMoviePosterPath (){

        return moviePosterPath;
    }


}
