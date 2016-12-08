package com.robertlesser.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Robert on 07/12/2016.
 */

public class MainActivityFragment extends Fragment {

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

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies));

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieAdapter);

        return rootView;

    }
}
