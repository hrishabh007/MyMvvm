package com.app.mymvvm.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.app.mymvvm.Adapter.MovieAdapter;
import com.app.mymvvm.model.Result;
import com.app.mymvvm.R;
import com.app.mymvvm.databinding.ActivityMainBinding;
import com.app.mymvvm.viewModel.MainactivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainactivityViewModel mainactivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private RecyclerView recyclerView;
    private List<Result> results = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainactivityViewModel = ViewModelProviders.of(this).get(MainactivityViewModel.class);
        getPopularMovies();
        swipeRefreshLayout = activityMainBinding.swipeLayout;
        swipeRefreshLayout.setOnRefreshListener(this::getPopularMovies);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });

    }





    public void getPopularMovies() {

        mainactivityViewModel.getAllMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> moviesFromLiveData) {
                results = moviesFromLiveData;
                showOnRecyclerView();
            }
        });


    }

    private void showOnRecyclerView() {

        recyclerView = activityMainBinding.rvMovies;
        movieAdapter = new MovieAdapter(this, results);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {


            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

    }
}