package com.demo.tuananh.demo;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 123;
    private ControlData controlData;
    private String url = "http://vuighe.net/api/v2/films/5853/episodes/128911";
    private RecyclerViewAdapter adapter;
    private List<Film> list;
    private Integer pageLoad = 0;
    private boolean isLoad = true;
    private static final int LIMIT = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlData = new ControlData();
        list = controlData.getDayFilm(pageLoad * LIMIT, LIMIT);
        pageLoad++;


        RecyclerView rcrView = (RecyclerView) findViewById(R.id.recycleView);
        rcrView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecyclerViewAdapter(rcrView, list, this);
        rcrView.setAdapter(adapter);
        rcrView.post(new Runnable() {
            public void run() {
                adapter.notifyItemInserted(list.size() - 1);
                adapter.notifyItemInserted(list.size() - 1);
            }
        });
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (list.size() <= 200 && isLoad) {
                    list.add(null);
                    list.add(null);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            list.remove(list.size() - 1);
                            list.remove(list.size() - 1);
                            adapter.notifyItemRemoved(list.size());
                            //Generating more data
                            List<Film> listTemp = controlData.getDayFilm(pageLoad * LIMIT, LIMIT);
                            if(listTemp == null || listTemp.size() == 0){
                                isLoad = false;
                            }
                            pageLoad++;
                            list.addAll(listTemp);
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },1000);
                } else {
                    Toast.makeText(MainActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
