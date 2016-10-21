package com.example.qeto.retrofit;

import com.example.qeto.retrofit.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by QETO on 10/21/2016.
 */
public interface ApiInterface {
    @GET("search/movie")
    Call<MovieResponse> getSearchedMovies(@Query("api_key") String apiKey, @Query("query") String search);
}
