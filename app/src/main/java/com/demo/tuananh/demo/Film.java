package com.demo.tuananh.demo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tuan Anh on 3/26/2018.
 */
@SuppressWarnings("serial")
public class Film implements Serializable {
    public String detail_name;
    public String film_name;
    public String full_name;
    public String id;
    public String is_bd;
    public String link;
    public String name;
    public String slug;
    public String special_name;
    public String thumbnail;
    public String thumbnail_medium;
    public String thumbnail_small;
    public boolean upcoming;
    public String views;
    public String time;
    public boolean is_movie;
    public String description;
    public Meta meta;
    public Genres genres;
    public String likes;
    public String ova;
    public String follow;
    public String TypeFilm;
}

