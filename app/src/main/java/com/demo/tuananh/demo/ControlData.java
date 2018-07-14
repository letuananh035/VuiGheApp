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
    public static String urlBase = "http://vuighe.net";
    private static String urlAPI = ControlData.urlBase + "/api/v2/";
    private static String UserAgent = "Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.102011-10-16 20:23:10";
    private static String Referer = ControlData.urlBase;

    public static String Request(String url) {
        RequestTask http = new RequestTask();
        try {
            String data = new String(http.execute(url, Referer, UserAgent).get(), StandardCharsets.UTF_8);
            return data;
        } catch (Exception e) {
            return "";
        }
    }

    public static String Request(String url,String referer) {
        RequestTask http = new RequestTask();
        try {
            String data = new String(http.execute(url, referer, UserAgent).get(), StandardCharsets.UTF_8);
            return data;
        } catch (Exception e) {
            return "";
        }
    }

    private static List<Film> getFilm(String url) {
        String json = Request(url); //Request data json
        //Parse object json with gson
        Response list = new Gson().fromJson(json, Response.class);
        return list.data;
    }

    private static Bitmap getBitmap(String url){
        RequestTask http = new RequestTask();
        try {
            byte[] data = http.execute(url).get();
            Bitmap bmp= BitmapFactory.decodeByteArray(data,0,data.length);
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Film> getNewFilm(Integer offset, Integer limit){
        String url = ControlData.createPathApi("episodes/latest?offset=" + offset.toString() + "&limit=" + limit.toString());
        List<Film> list = getFilm(url);
        for (Film item:list) {
            item.TypeFilm = "NewFilm";
        }
        return list;
    }

    public static List<Film> getDayFilm(Integer offset, Integer limit){
        String url = ControlData.createPathApi("films?picked=true&offset=" + offset.toString() + "&limit=" + limit.toString());
        List<Film> list = getFilm(url);
        for (Film item:list) {
            item.TypeFilm = "DayFilm";
        }
        return list;
    }

    public static List<Film> getListEpisodes(String id )
    {
        String path_listEpisodes = ControlData.createPathApi("films/" + id + "/episodes?sort=name");
        return ControlData.getFilm(path_listEpisodes);
    }

    public static Episodes getEpisodes(String id, String ep, String Referer)
    {
        String path_Episodes = ControlData.createPathApi("films/" + id + "/episodes/" + ep);

        String json = Request(path_Episodes, Referer); //Request data json
        //Parse object json with gson
        Episodes item = new Gson().fromJson(json, Episodes.class);

        return item;

    }

    public static String createPathApi(String path){
        return ControlData.urlAPI + path;
    }
}
