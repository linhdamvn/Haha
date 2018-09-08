package com.example.thanhdam.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ReadActivity extends AppCompatActivity {

    private static final String TAG = "ReadActivity";
    ImageView igView;
    TextView tvTittle;
    TextView tvContent;
    FloatingActionButton fbPlay;
    boolean playingnow = false;
    String audio;
    MediaPlayer mediaPlayer = null; // hien tai audio chua co gi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        //lay gia tri tu griadadapter - 8/9
        Intent intent = getIntent();
        Bundle data = intent.getExtras(); // bundle bien de lay data
        String tittle = (String)data.get("tittle"); // = storymodel.name be gridadapter
        String image = (String)data.get("image"); //1 - se duoc lay sau
        String story = (String)data.get("story");
        audio = (String)data.get("audio"); // khai bao tren
        Log.d(TAG, "onCreate: " + audio);

        // end 8/9

        tvContent = findViewById(R.id.tv_content);
        tvTittle = findViewById(R.id.tv_title);
        igView = findViewById(R.id.imageView); // anh cho review
        fbPlay = findViewById(R.id.fb_play);

        //8/9
        tvTittle.setText(tittle);  // truyen thong tin
        tvContent.setText(story);
        Bitmap bitmap = BitmapFactory.decodeFile(image);  //set anh lay tu //1
        igView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 250, false)); //createScaledBitmap - tra ve bitmap nhan duoc va scale lai

        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playingnow){
                    playingnow = false;
                    fbPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    StopPlay(); //  dung am thanh tu ben ngoai
                }
                else {
                    playingnow = true;
                    fbPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    StartPlaying();
                }
            }
        });
    }

    // 8/9
    private void StartPlaying (){  // ham play audio khi duoc truyen
        mediaPlayer = new MediaPlayer(); // khoi tao
        try{
            mediaPlayer.setDataSource(audio); // truyen link audio
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e){

        }
    }
    private void StopPlay(){
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
