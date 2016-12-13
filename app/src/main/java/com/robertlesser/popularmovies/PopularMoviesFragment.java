package com.robertlesser.popularmovies;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

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
                    "/nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", false, info, 7, new Date(2016-11-16)),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "/nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", false, info, 7, new Date(2016-11-16)),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "/nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", false, info, 7, new Date(2016-11-16)),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "/nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", false, info, 7, new Date(2016-11-16)),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "/nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", false, info, 7, new Date(2016-11-16)),
            new MovieDetails(259316, "Fantastic Beasts and Where to Find Them",
                    "/nHXiMnWUAUba2LZ0dFkNDVdvJ1o.jpg", false, info, 7, new Date(2016-11-16))
    };

    public PopularMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pupular_movies_fragment, container, false);

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies));

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieAdapter);

        return rootView;

    }


    public class FetchPopularMoviesTask extends AsyncTask<Void, Void, Void>{

        private final String LOG_TAG = FetchPopularMoviesTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... voids) {
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
                final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
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
                        Log.e("MovieFragment", "Error closing stream", e);
                    }
                }
            }

            return null;
        }
    };
}
