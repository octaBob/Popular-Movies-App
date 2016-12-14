package com.robertlesser.popularmovies;

/**
 * Created by Robert on 07/12/2016.
 */

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
    String release_date;

    /***
     *
     * @param id - [int] The id of the movie as shown in https://www.themoviedb.org/ api
     * @param original_title - [String] The original title of the movie
     * @param poster_path - [String] The path to the poster to be concatenated to the appropriate api
     * @param overview - [String] The plot synopsis
     * @param user_rating - [double] User rating of the movie
     * @param release_date - [Date] Release date of the film
     */
    public MovieDetails(int id, String original_title, String poster_path, String overview,
                        double user_rating, String release_date){


        this.id = id;
        this.title = original_title;
        this.poster_path = poster_path;
        this.plot_synopsis = overview;
        this.user_rating = user_rating;
        this.release_date = release_date;
    }


    public String getPosterPath() {
        return this.poster_path;
    }
}
