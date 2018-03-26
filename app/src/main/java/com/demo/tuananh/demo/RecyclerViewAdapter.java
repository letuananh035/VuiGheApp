package com.demo.tuananh.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Tuan Anh on 3/26/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private RecyclerView mContext;
    private List<Film> mData;
    private Activity activity;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;

    public RecyclerViewAdapter(RecyclerView mContext, List<Film> mData, Activity activity) {
        this.activity = activity;
        this.mData = mData;

        final GridLayoutManager linearLayoutManager = (GridLayoutManager) mContext.getLayoutManager();
        mContext.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    isLoading = true;
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.cardview_item, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;

        //View view;
        //LayoutInflater mInflater = LayoutInflater.from(mContext);
        //view = mInflater.inflate(R.layout.cardview_item,parent,false);

        // return new MyViewHolder(view);
    }

    private Bitmap getBitmap(String url) {
        RequestTask http = new RequestTask();
        try {
            byte[] data = http.execute(url).get();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }

    void UpdateLayerNewFilm(MyViewHolder item,final Film mData){

        item.film_title.setText(mData.film_name);
        if (mData.thumbnail != null) {
            Glide.with(activity)
                    .load(mData.thumbnail)
                    .into(item.thumbnail);
        } else if (mData.thumbnail_small != null) {
            Glide.with(activity)
                    .load(mData.thumbnail_small)
                    .into(item.thumbnail);
        }
        if (mData.name != null) {
            item.episode.setText(mData.name);
        }
        //Set action
        item.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(activity, ViewFilm.class);
                //Film data = mData;
                //intent.putExtra("Film", data);
                //activity.startActivityForResult(intent, 1);
            }
        });
    }

    void UpdateLayerDayFilm(MyViewHolder item,final Film mData){
        item.film_title.setText(mData.name);
        if (mData.thumbnail != null) {
            Glide.with(activity)
                    .load(mData.thumbnail)
                    .into(item.thumbnail);
        } else if (mData.thumbnail_small != null) {
            Glide.with(activity)
                    .load(mData.thumbnail_small)
                    .into(item.thumbnail);
        }
        item.episode.setVisibility(View.INVISIBLE);
        //Set action
        item.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ViewFilm.class);
                Film data = mData;
                intent.putExtra("Film", data);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder item = (MyViewHolder) holder;
            Film film = mData.get(position);
            if(film.TypeFilm.equals("NewFilm")){
                UpdateLayerNewFilm(item,film);
            }else if(film.TypeFilm.equals("DayFilm")){
                UpdateLayerDayFilm(item,film);
            }

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView film_title;
        ImageView thumbnail;
        CardView cardView;
        TextView episode;

        public MyViewHolder(View itemView) {
            super(itemView);
            film_title = (TextView) itemView.findViewById(R.id.name_film);
            thumbnail = (ImageView) itemView.findViewById(R.id.image_film);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            episode = (TextView) itemView.findViewById(R.id.episode);
        }
    }
}
