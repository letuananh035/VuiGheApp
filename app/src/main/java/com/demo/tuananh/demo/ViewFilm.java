package com.demo.tuananh.demo;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewFilm extends AppCompatActivity {


    private ImageView imgThumbnail;
    private TextView description;
    private TextView nameFilm;
    private TextView viewShow;
    private TextView likes;
    private TextView newEps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfilm);
        Intent intent = getIntent();
        Film film = (Film) intent.getSerializableExtra("Film");


        imgThumbnail = (ImageView) findViewById(R.id.imgThumbnail);
        description = (TextView) findViewById(R.id.description);
        nameFilm = (TextView) findViewById(R.id.nameFilm);
        viewShow = (TextView) findViewById(R.id.viewShow);
        likes = (TextView) findViewById(R.id.likes);
        newEps = (TextView) findViewById(R.id.newEps);

        // item.film_title.setText(mData.get(position).film_name);
        String des = Html.fromHtml(film.description).toString();
        description.setText(Html.fromHtml(des));
        nameFilm.setText(film.name);
        viewShow.setText(film.views);
        likes.setText(film.likes);
        if (film.is_movie == true) {
            newEps.setText("Thời lượng: " + film.time);
        } else {
            newEps.setText("Tập mới nhất: " + film.meta.max_episode_name);
        }


        if (film.thumbnail != null) {
            Glide.with(this)
                    .load(film.thumbnail)
                    .into(imgThumbnail);
        } else if (film.thumbnail_medium != null) {
            Glide.with(this)
                    .load(film.thumbnail_medium)
                    .into(imgThumbnail);
        } else if (film.thumbnail_small != null) {
            Glide.with(this)
                    .load(film.thumbnail_small)
                    .into(imgThumbnail);
        }


        //setTitle("Thông tin");

        CollapsingToolbarLayout collapsingToolbar =  findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle("Thông tin");
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
