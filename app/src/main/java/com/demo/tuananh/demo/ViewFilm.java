package com.demo.tuananh.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewFilm extends AppCompatActivity {


    private ImageView imgThumbnail;
    private TextView description;
    private TextView nameFilm;
    private TextView viewShow;
    private TextView likes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfilm);
        Intent intent = getIntent();
        Film film = (Film)intent.getSerializableExtra("Film");


        imgThumbnail = (ImageView)findViewById(R.id.imgThumbnail);
        description = (TextView)findViewById(R.id.description);
        nameFilm = (TextView) findViewById(R.id.nameFilm);
        viewShow = (TextView)findViewById(R.id.viewShow);
        likes = (TextView)findViewById(R.id.likes);

       // item.film_title.setText(mData.get(position).film_name);
        String des =  Html.fromHtml(film.description).toString();
        description.setText(Html.fromHtml(des));
        nameFilm.setText(film.name);
        viewShow.setText(film.views);
        likes.setText(film.likes);


        if(film.thumbnail != null){
            Glide.with(this)
                    .load(film.thumbnail)
                    .into(imgThumbnail);
        }
        else if (film.thumbnail_medium != null) {
            Glide.with(this)
                    .load(film.thumbnail_medium)
                    .into(imgThumbnail);
        }
        else if (film.thumbnail_small != null) {
            Glide.with(this)
                    .load(film.thumbnail_small)
                    .into(imgThumbnail);
        }


        setTitle("Thông tin");
    }
}
