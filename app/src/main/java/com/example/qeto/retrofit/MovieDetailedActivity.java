package com.example.qeto.retrofit;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qeto.retrofit.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by QETO on 10/24/2016.
 */
public class MovieDetailedActivity extends AppCompatActivity {
    TextView textViewTitle,textViewRating,textViewDate,textViewLanguage,textViewOverview;
    ImageView imageViewBig;
    Movie movie;
    String ImagePath;

    android.text.format.DateFormat df = new android.text.format.DateFormat();
    Date date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_movie_item);
        textViewTitle= (TextView)findViewById(R.id.textViewTitle);
        textViewRating= (TextView)findViewById(R.id.textViewRating);
        textViewDate= (TextView)findViewById(R.id.textViewDate);
        textViewLanguage= (TextView)findViewById(R.id.textViewLanguage);
        textViewOverview= (TextView)findViewById(R.id.textViewOverview);
        imageViewBig= (ImageView)findViewById(R.id.imageViewBig);

        movie =  (Movie)getIntent().getSerializableExtra("Movie");

        textViewTitle.setText(movie.getTitle());
        date = movie.getReleaseDate();
        textViewDate.setText( df.format("dd MMM yyyy", date));
        textViewRating.setText((String.valueOf(movie.getPopularity())));
        textViewLanguage.setText(movie.getOriginalLanguage());
        textViewOverview.setText(movie.getOverview());

        ImagePath = Constants.Image_URL + movie.getPosterPath().toString();

        Picasso.with(this).load(ImagePath).into(imageViewBig);

    }
}
