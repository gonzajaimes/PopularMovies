package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MoviesAdapter mPosterAdapter;


    public MainActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.display_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            //updateWeather();
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mPosterAdapter = new MoviesAdapter(getActivity(), new ArrayList<Movie>());


        // get reference to the grid view and use the Image Adapter to populate it

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(mPosterAdapter);

        // show a toast with the image position when each image is clicked

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Movie displayMovie = mPosterAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("movie", displayMovie);
                startActivity(intent);
            }
        });

        return rootView;


    }
    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //String location = prefs.getString(getString(R.string.pref_location_key),
         //       getString(R.string.pref_location_default));
        moviesTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }



    public class FetchMoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private ArrayList<Movie> getMoviesDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_RESULTS = "results";
            final String MDB_ID = "id";
            final String MDB_POSTER_PATH = "poster_path";
            ArrayList<Movie> moviesCollection = new ArrayList<Movie>();

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(MDB_RESULTS);

            // MDB returns movies information based on the type of query made to the API



           // String[] resultStrs = new String[moviesArray.length()];



            for(int i = 0; i < moviesArray.length(); i++) {

                String idMovie;
                String posterPath;


                // Get the JSON object representing the movie
                JSONObject movieInfo = moviesArray.getJSONObject(i);


                // get the ID of the movie.
                idMovie = movieInfo.getString(MDB_ID);

                // get the poster URL Path

                posterPath = movieInfo.getString(MDB_POSTER_PATH);


                // Add the movie to the Movies Array
                moviesCollection.add(new Movie(idMovie,posterPath));




               // resultStrs[i] = idMovie + " - " + posterPath;
               // Log.v(LOG_TAG, "movies reported " + resultStrs[i]);
            }

            return moviesCollection;

        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String sortBy = "popularity.desc";
            String key = "b2b68d438d36572bfca895dd822e6caa";


            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                // Construct the URL for the MovieDB query
                // Possible parameters are avaiable at MDB's forecast API page, at
                // https://www.themoviedb.org

                final String FORECAST_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_BY_PARAM = "sort_by";
                final String KEY_PARAM = "api_key";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_BY_PARAM, sortBy)
                        .appendQueryParameter(KEY_PARAM, key)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());



                // Create the request to TheMovieDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();

                //Log.v(LOG_TAG, "Movies JSON String: " + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            if (result != null) {
                mPosterAdapter.clear();
                for(Movie movie : result) {
                    mPosterAdapter.add(movie);
                }

            }
        }



    }


}






