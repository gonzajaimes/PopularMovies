package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gonzajaimes on 21/09/15.
 *
 * Movies Class storing id and Poster info
 */
public class Movie implements Parcelable {

    private String movieId;
    private String moviePosterPath;

    public Movie (String id, String posterPath){
        movieId = id;
        moviePosterPath = posterPath;


    }

    public String getMoviePosterPath (){

        return moviePosterPath;
    }

    //This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(movieId);
        out.writeString(moviePosterPath);

    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Movie(Parcel in) {

        movieId = in.readString();
        moviePosterPath = in.readString();

    }




    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }


        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //public String getMovieId (){

      //  return movieId;
    //}




}
