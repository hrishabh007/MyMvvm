package com.app.mymvvm.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.mymvvm.model.MovieRepository;
import com.app.mymvvm.model.Result;

import java.util.List;

public class MainactivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MainactivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository=new MovieRepository(application);
    }

    public LiveData<List<Result>> getAllMovies(){

        return movieRepository.getMutableLivedata();
    }
}
