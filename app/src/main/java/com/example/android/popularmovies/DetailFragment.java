package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    final String BASE_MOVIE_URL = "http://image.tmdb.org/t/p/w500";
    private Movie mMovie;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for movie data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie")) {



            mMovie = intent.getParcelableExtra("movie");
            String posterPath = BASE_MOVIE_URL + mMovie.getMoviePosterPath();

            ImageView imageView = (ImageView) rootView.findViewById(R.id.detailImageView);

            Picasso.with(getActivity()).
                    load(posterPath).into(imageView);
            ((TextView) rootView.findViewById(R.id.titleText))
                    .setText(mMovie.getTitle());
            ((TextView) rootView.findViewById(R.id.releaseText))
                    .setText(mMovie.getReleaseDate());
            ((TextView) rootView.findViewById(R.id.voteText))
                    .setText(mMovie.getVoteAverage());
            ((TextView) rootView.findViewById(R.id.synopsisText))
                    .setText(mMovie.getSynopsis());
        }

        return rootView;
    }
}
