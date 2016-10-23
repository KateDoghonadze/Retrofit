package com.example.qeto.retrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.qeto.retrofit.models.Movie;
import com.example.qeto.retrofit.models.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultTextView;
    private EditText searchEditText;
    private ProgressDialog pd;
    Button getMovies;
    ArrayList<Movie> movies = new ArrayList<>();

    RecyclerView recyclerView;
    ImageButton buttonBack, buttonForward;
    Integer pageCount = 1, allPages;
    TextView textView;

    Intent i;

    public static final int DataActivity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);


        getMovies = (Button) findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonBack = (ImageButton) findViewById(R.id.buttonBack);
        buttonForward = (ImageButton) findViewById(R.id.buttonForward);

        buttonBack.setOnClickListener(this);
        buttonForward.setOnClickListener(this);
        textView= (TextView)findViewById(R.id.textView);



    }

    public void onGetDataClick(View view) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getSearchedMovies(Constants.API_KEY, pageCount);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                showData(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("GITA-ANDROID", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBack:
                GoBack();
                break;
            case R.id.buttonForward:
                MoveForward();
                break;
        }
    }


    public void GoBack() {
        if (pageCount > 1) {
            pageCount--;

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieResponse> call = apiService.getSearchedMovies(Constants.API_KEY, pageCount);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                    showData(response);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e("GITA-ANDROID", t.getMessage());
                }
            });
        }
    }


    public void MoveForward() {
        if (pageCount < allPages) {
            pageCount++;
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieResponse> call = apiService.getSearchedMovies(Constants.API_KEY, pageCount);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                    showData(response);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e("GITA-ANDROID", t.getMessage());
                }
            });
        }
    }

    public void showData(final Response<MovieResponse> response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response.body() != null) {
                    MovieResponse movieResponse = response.body();

                    movies.clear();
                    movies.addAll(movieResponse.getMovieList());
//
                    recyclerView.setAdapter(new MovieAdapter(movies,new RecyclerItemClickListener() {
                        @Override
                        public void onItemClicked(int position) {
                            i=new Intent(MainActivity.this,MovieDetailedActivity.class);
                            i.putExtra("Movie",movies.get(position));
                            startActivity(i);

                        }
                    }));
                    allPages = movieResponse.getTotalPages();
                    textView.setText("Pages "+pageCount+"/"+allPages);


                }
            }
        });
    }

}
