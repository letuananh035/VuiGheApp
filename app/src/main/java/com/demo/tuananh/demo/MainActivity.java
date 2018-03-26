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
    private  ControlData controlData;
    private String url ="http://vuighe.net/api/v2/films/5853/episodes/128911";
    private RecyclerViewAdapter adapter;
    private List<Film> list;
    private Integer pageLoad = 0;
    private static final int LIMIT = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // queue = Volley.newRequestQueue(this);

        controlData = new ControlData();
        list = controlData.getNewFilm(pageLoad*LIMIT,LIMIT);
        pageLoad ++;

       // list.add( new Film("XXX1","Thông tin","category",R.drawable.ic1));
        //list.add( new Film("XXX2","Thông tin","category",R.drawable.ic1));
        //list.add( new Film("XXX3","Thông tin","category",R.drawable.ic1));
        //list.add( new Film("XXX4","Thông tin","category",R.drawable.ic1));
        RecyclerView rcrView = (RecyclerView) findViewById(R.id.recycleView);
        rcrView.setLayoutManager(new GridLayoutManager(this,2));
        //rcrView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(rcrView,list,this);
        rcrView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (list.size() <= 200) {
                    list.add(null);
                    adapter.notifyItemInserted(list.size() - 1);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            list.remove(list.size() - 1);
                            adapter.notifyItemRemoved(list.size());

                            //Generating more data
                            List<Film> listTemp = controlData.getNewFilm(pageLoad*LIMIT,LIMIT);
                            pageLoad++;
                            list.addAll(listTemp);
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void sendRequest(){

    }
}
