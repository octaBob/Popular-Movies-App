package com.robertlesser.popularmovies;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Robert on 07/12/2016.
 */


public class PopularMoviesFragment extends Fragment {

    private String info = "In 1926, Newt Scamander arrives at the Magical Congress" +
            " of the United States of America with a magically expanded briefcase, " +
            "which houses a number of dangerous creatures and their habitats. " +
            "When the creatures escape from the briefcase," +
            " it sends the American wizarding authorities after Newt, " +
            "and threatens to strain even further the state of magical and non-magical relations.";


    private MovieAdapter movieAdapter;

    MovieDetails[] movies = {
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "http://image.tmdb.org/t/p/w185//nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", info, 7, "2016-11-16"),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "http://image.tmdb.org/t/p/w185//nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", info, 7, "2016-11-16"),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "http://image.tmdb.org/t/p/w185//nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", info, 7, "2016-11-16"),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "http://image.tmdb.org/t/p/w185//nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", info, 7, "2016-11-16"),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "http://image.tmdb.org/t/p/w185//nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", info, 7, "2016-11-16"),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "http://image.tmdb.org/t/p/w185//nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", info, 7, "2016-11-16")
    };

    public PopularMoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.popular_movies_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_sort_by_popularity){
            FetchPopularMoviesTask fetchMoviesTask = new FetchPopularMoviesTask();
            fetchMoviesTask.execute("popular");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pupular_movies_fragment, container, false);
        ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<MovieDetails>(Arrays.asList(movies));
        movieAdapter = new MovieAdapter(getActivity(), movieDetailsArrayList);

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieAdapter);

        return rootView;

    }


    public class FetchPopularMoviesTask extends AsyncTask<String, Void, Vector<MovieDetails>> {

        private final String LOG_TAG = FetchPopularMoviesTask.class.getSimpleName();

        @Override
        protected Vector<MovieDetails> doInBackground(String... params) {

            if (params.length == 0){
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            String language = "en-US";
            String page = "1";

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at TMDB's API page, at
                // http://openweathermap.org/API#forecast
                final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/" +
                        params[0] +
                        "?";
                final String API_PARAM = "api_key";
                final String LANGUAGE_PARAM = "language";
                final String PAGE_PARAM = "page";

                Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                        .appendQueryParameter(API_PARAM, BuildConfig.TMDB_API_KEY)
                        .appendQueryParameter(LANGUAGE_PARAM, language)
                        .appendQueryParameter(PAGE_PARAM, page)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to TMDB, and open the connection
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
                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, movieJsonStr);
            } catch (IOException e) {
                Log.e("MovieFragment", "Error ", e);
                // If the code didn't successfully get the movies data, there's no point in attempting
                // to parse it.
                return null;
            } finally{
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

            return getMovieDataFromJson(movieJsonStr);
        }

        @Override
        protected void onPostExecute(Vector<MovieDetails> movieDetailsVector) {
            if (movieDetailsVector != null){
                movieAdapter.clear();
                movieAdapter.addAll(movieDetailsVector);
            }
        }

        private Vector<MovieDetails> getMovieDataFromJson(String movieJsonStr) {

            // Movie details
            final String OWM_ID = "id";
            final String OWM_TITLE = "original_title";
            final String OWM_POSTER = "poster_path";
            final String OWM_OVERVIEW = "overview";
            final String OWM_USER_RATING = "vote_average";
            final String OWM_RELEASE_DATE = "release_date";

            final String OWM_RESULTS = "results";


            try{
                JSONObject moviesJson = new JSONObject(movieJsonStr);
                JSONArray resultsArray = moviesJson.getJSONArray(OWM_RESULTS);

                // Insert the new weather information into the database
                Vector<MovieDetails> cVVector = new Vector<>(resultsArray.length());

                for (int i = 0; i <resultsArray.length(); i++){
                    JSONObject movieInfo = resultsArray.getJSONObject(i);
                    int id = movieInfo.getInt(OWM_ID);
                    String original_title = movieInfo.getString(OWM_TITLE);
                    String poster_path = "http://image.tmdb.org/t/p/w185/" +
                            movieInfo.getString(OWM_POSTER);
                    String overview = movieInfo.getString(OWM_OVERVIEW);
                    double vote_average = movieInfo.getDouble(OWM_USER_RATING);
                    String release_date = movieInfo.getString(OWM_RELEASE_DATE);

//                    ContentValues movieDetails = new ContentValues();
//                    movieDetails.put("id", id);
//                    movieDetails.put("title", original_title);
//                    movieDetails.put("poster_path", poster_path);
//                    movieDetails.put("overview", overview);
//                    movieDetails.put("user_rating", vote_average);
//                    movieDetails.put("release_date", release_date);

//                    cVVector.add(movieDetails);

                    // TODO: eventually this will be handled using contentValues
                    MovieDetails movie = new MovieDetails(id, original_title,
                            poster_path, overview, vote_average, release_date);
                    cVVector.add(movie);

                }

                return cVVector;

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    };
}
