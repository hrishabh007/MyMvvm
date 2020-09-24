package com.app.mymvvm.Retrofit;


import com.app.mymvvm.model.MoviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String CACHE = "Cache-Control: max-age=0";
    String AGENT = "Data-Agent: Android News App";

    @GET("movie/popular")
    Call<MoviewResponse> getPopularMovies(@Query("api_key") String apiKey);


}
