package com.example.qeto.retrofit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qeto.retrofit.models.Movie;
import com.example.qeto.retrofit.models.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private EditText searchEditText;
    private ProgressDialog pd;
    Button getMovies;
    ArrayList<Movie> movies = new ArrayList<>();

    RecyclerView recyclerView;
    Button buttonBack, buttonForward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);

//        resultTextView = (TextView) findViewById(R.id.tv_result);
//        searchEditText = (EditText) findViewById(R.id.et_search);
        getMovies=(Button)findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
buttonBack = (Button)findViewById(R.id.buttonBack);
buttonForward = (Button)findViewById(R.id.buttonForward);

    }

    public void onGetDataClick(View view) {
//        String searh = searchEditText.getText().toString().trim();
//        if (TextUtils.isEmpty(searh)) {
//            searchEditText.setError("Enter Search Text");
//            return;
//        }
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getSearchedMovies(Constants.API_KEY, 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.body() != null) {
                            MovieResponse movieResponse = response.body();
//                            resultTextView.setText(movieResponse.getMovieList().size() + "");

                            movies.addAll(movieResponse.getMovieList());
                            recyclerView.setAdapter(new MovieAdapter(movies));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("GITA-ANDROID", t.getMessage());
            }
        });
    }
}
