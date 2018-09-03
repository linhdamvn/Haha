package com.example.thanhdam.myapplication;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReadActivity extends AppCompatActivity {

    ImageView igView;
    TextView tvTittle;
    TextView tvContent;
    FloatingActionButton fbPlay;
    boolean playingnow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        tvContent = findViewById(R.id.tv_content);
        tvTittle = findViewById(R.id.tv_title);
        igView = findViewById(R.id.imageView);
        fbPlay = findViewById(R.id.fb_play);

        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playingnow){
                    playingnow = false;
                    fbPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                }
                else {
                    playingnow = true;
                    fbPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            }
        });
    }
}
