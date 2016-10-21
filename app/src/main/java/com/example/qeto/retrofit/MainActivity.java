package com.example.qeto.retrofit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qeto.retrofit.models.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private EditText searchEditText;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView) findViewById(R.id.tv_result);
        searchEditText = (EditText) findViewById(R.id.et_search);
    }

    public void onGetDataClick(View view) {
        String searh = searchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(searh)) {
            searchEditText.setError("Enter Search Text");
            return;
        }
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getSearchedMovies(Constants.API_KEY, searh);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.body() != null) {
                            MovieResponse movieResponse = response.body();
                            resultTextView.setText(movieResponse.getMovieList().size() + "");
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
