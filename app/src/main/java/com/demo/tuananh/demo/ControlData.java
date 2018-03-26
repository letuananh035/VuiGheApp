package com.demo.tuananh.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan Anh on 3/26/2018.
 */

public class ControlData {
    private String urlBase = "http://vuighe.net";
    private String urlAPI = this.urlBase + "/api/v2/";
    private String UserAgent = "Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.102011-10-16 20:23:10";
    private String Referer = this.urlBase;

    public String Request(String url) {
        RequestTask http = new RequestTask();
        try {
            String data = new String(http.execute(url, Referer, UserAgent).get(), StandardCharsets.UTF_8);
            return data;
        } catch (Exception e) {
            return "";
        }
    }

    private List<Film> getFilm(String url) {
        String json = Request(url); //Request data json
        //Parse object json with gson
        //Type listType = new TypeToken<ArrayList<Film>>(){}.getType();
        Response list = new Gson().fromJson(json, Response.class);
        return list.data;
    }

    private Bitmap getBitmap(String url){
        RequestTask http = new RequestTask();
        try {
            byte[] data = http.execute(url).get();
            Bitmap bmp= BitmapFactory.decodeByteArray(data,0,data.length);
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }

    public  List<Film> getNewFilm(Integer offset, Integer limit){
        String url = "episodes/latest?offset=" + offset.toString() + "&limit=" + limit.toString();
        url = this.urlAPI + url;
        List<Film> list = getFilm(url);
        for (Film item:list) {
            item.TypeFilm = "NewFilm";
        }
        return list;
    }

    public  List<Film> getDayFilm(Integer offset, Integer limit){
        String url = "films?picked=true&offset=" + offset.toString() + "&limit=" + limit.toString();
        url = this.urlAPI + url;
        List<Film> list = getFilm(url);
        for (Film item:list) {
            item.TypeFilm = "DayFilm";
        }
        return list;

    }

}
