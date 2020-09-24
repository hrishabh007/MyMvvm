package com.app.mymvvm.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.mymvvm.R;
import com.app.mymvvm.Retrofit.ApiInterface;
import com.app.mymvvm.Retrofit.RestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private List<Result> movies = new ArrayList<>();
    private MutableLiveData<List<Result>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<List<Result>> getMutableLivedata() {
        ApiInterface apiInterface = RestAdapter.createAPI();

        Call<MoviewResponse> responseCall = apiInterface.getPopularMovies(application.getString(R.string.api_key));
        responseCall.enqueue(new Callback<MoviewResponse>() {
            @Override
            public void onResponse(Call<MoviewResponse> call, Response<MoviewResponse> response) {
                MoviewResponse movieDBResponse = response.body();


                if (movieDBResponse != null && movieDBResponse.getResults() != null) {

                    movies = movieDBResponse.getResults();
                    mutableLiveData.setValue(movies);
                }

            }

            @Override
            public void onFailure(Call<MoviewResponse> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
