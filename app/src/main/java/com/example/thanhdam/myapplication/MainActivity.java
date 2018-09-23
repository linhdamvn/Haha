package com.example.thanhdam.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView ivRecord;
    ImageView ivBack;
    ImageView ivCheck;
    EditText etTittle;
    String path;
    boolean isChange = false;
    EditText etContent;
    FloatingActionButton fbRecord;
    boolean isplaying = true;
    private int GALLERY = 1;
    MediaRecorder mediaRecorder;
    StoryModel Pstorymodel;
    int PId;
    private static final String AUDIO_DIRECTORY = "/storyAudio";// tao bien cuc bo, duong dan
    String audioPath = null; // dan toi audio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        final String change = data.getString("change");
        if (change.equals("true")) {
            isChange = true;
            String name = data.getString("tittle");
            String content = data.getString("content");
            String image = data.getString("image");
            String audio = data.getString("audio");
            PId = data.getInt("id");
            Pstorymodel = new StoryModel(name, image, content, audio);
            Log.d(TAG, "onCreate: " + Pstorymodel.name + " " + Pstorymodel.content + " " + Pstorymodel.image + " " + Pstorymodel.audio + " " + PId);
        }

        ivCheck = findViewById(R.id.iv_check);
        ivRecord = findViewById(R.id.iv_record);
        etContent = findViewById(R.id.et_content);
        Picasso.get().load(R.drawable.round).into(ivRecord);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        etTittle = findViewById(R.id.et_title);
        fbRecord = findViewById(R.id.fb_record);


        if (change.equals("true"))
        {
            etTittle.setText(Pstorymodel.name);
            etContent.setText(Pstorymodel.content);

            if (Pstorymodel.image != null)
            {
                Bitmap bitmap = BitmapFactory.decodeFile(Pstorymodel.image);
                ivRecord.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, false));
            }

            audioPath = Pstorymodel.audio;
            path = Pstorymodel.image;
        }

        // Nhan anh - chon anh tu thu vien
        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // Nhay tu activity 1 sang activity co san
                startActivityForResult( gallery, GALLERY ); // Goi ham,lay anh
            }
        });

        ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change.equals("true")) update();
                else {
                    String name = etTittle.getText().toString();
                    String content = etContent.getText().toString();
                    StoryModel storyModel = new StoryModel(name, path, content, audioPath);
                    DataManager.getInstances(MainActivity.this).saveData(storyModel);
                    long count = DataManager.getInstances(MainActivity.this).getNumOfData();
                    Toast.makeText(MainActivity.this, "Story saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fbRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isplaying) { // neu dang choi = true
                    isplaying = false; // chuyen thanh false
                    fbRecord.setImageResource(R.drawable.ic_pause_black_24dp);// chuyen hinh anh
                    startrecord(); // dinh nghia duoi
                }
                else{
                    isplaying = true;
                    fbRecord.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    stoprecord();
                }

            }
        });

    }

    private void startrecord() {
        // su dung thu vien media recorder
        mediaRecorder = new MediaRecorder(); // khoi tao o nho trong may
        audioPath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_DIRECTORY; // dan toi folder + folder ten laf audio_directory
        File file = new File(audioPath);
        if (!file.exists()){ // neu file khong ton tai thi tao file
            file.mkdir();
        }
        audioPath += "/" + Calendar.getInstance().getTimeInMillis() + ".3gp"; // Cho ra day so dua tren thoi diem hien tai
        Log.d(TAG, "startrecord: " + audioPath);

        // khoi tao cac thong so
        mediaRecorder.setOutputFile(audioPath);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // lay nguon am thanh tu mic
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            mediaRecorder.prepare();
        } catch ( IOException e)
        {

        }

        mediaRecorder.start();
    }

    private void update()
    {
        Pstorymodel.name = etTittle.getText().toString();
        Pstorymodel.content = etContent.getText().toString();
        Pstorymodel.image = path;
        Pstorymodel.audio = audioPath;
        DataManager.getInstances(this).updateData(Pstorymodel, PId);
        Toast.makeText(MainActivity.this, "Story updated", Toast.LENGTH_SHORT).show();
    }

    private void stoprecord(){
        mediaRecorder.stop();
        mediaRecorder.release(); // huy starreocord
        mediaRecorder = null;
    }
    @Override // xu ly lai
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == this.RESULT_CANCELED) {
            return;
        }

        if ( requestCode == GALLERY )// LAY ANH GALLERY
        {
            if ( data != null)
            {
                Uri uri = data.getData();
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri); // doi anh thanh bitmap
                    path = ImageManager.saveImage(bitmap,this);
                    Log.d(TAG, "onActivityResult: " + path);
                    ivRecord.setImageBitmap(Bitmap.createScaledBitmap(bitmap, ivRecord.getWidth(), ivRecord.getHeight(),false));
                    Toast.makeText(MainActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}