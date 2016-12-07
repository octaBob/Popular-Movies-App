package com.robertlesser.popularmovies;

/**
 * Created by Robert on 07/12/2016.
 */

import java.util.Date;

/**
 * Created by poornima-udacity on 6/26/15.
 */
public class MovieDetails {
    int id;
    String title;
    String poster_path;
    boolean adult;
    String plot_synopsis;
    double user_rating;
    Date release_date;

    public MovieDetails(int id, String original_title, String poster_path, boolean adult,
                        String overview, double user_rating, Date release_date){


        this.id = id;
        this.title = original_title;
        this.poster_path = poster_path;
        this.adult = adult;
        this.plot_synopsis = overview;
        this.user_rating = user_rating;
        this.release_date = release_date;
    }

}
