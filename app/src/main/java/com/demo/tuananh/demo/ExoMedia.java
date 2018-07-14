package com.demo.tuananh.demo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

public class ExoMedia extends AppCompatActivity implements OnPreparedListener {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_media);

        Intent intent = getIntent();
        final Episodes film = (Episodes) intent.getSerializableExtra("Episodes");
        MediaData data = null;
        if(film.sources.fb != null && film.sources.fb.length > 0){
            data = getMedia(film.sources.fb);
        }
        if(data == null){
            if(film.sources.vip != null && film.sources.vip.length > 0){
                data = getMedia(film.sources.vip);
            }
        }
        setupVideoView(data);
    }

    MediaData getMedia(MediaData[] souceData){
        MediaData data = null;
        if(souceData != null && souceData.length > 0){
            for (int i =0; i < souceData.length;++i){
                MediaData item = souceData[i];
                if(item.quality.indexOf("1080") == 0){
                    data = item;
                    break;
                }else if(item.quality.indexOf("720") == 0){
                    if(data != null){
                        if(data.quality.indexOf("1080") == 0){
                            continue;
                        }else{
                            data = item;
                        }
                    }else{
                        data = item;
                    }
                }else if(item.quality.indexOf("480") == 0){
                    if(data != null){
                        if(data.quality.indexOf("1080") == 0 && data.quality.indexOf("720") == 0){
                            continue;
                        }else{
                            data = item;
                        }
                    }else{
                        data = item;
                    }
                }
            }
        }
        return data;
    }

    private void setupVideoView(MediaData data) {
        // Make sure to use the correct VideoView import
        videoView = (VideoView)findViewById(R.id.video_view);
        videoView.setOnPreparedListener(this);

        //For now we just picked an arbitrary item to play
        videoView.setVideoURI(Uri.parse(data.src));
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }
}
