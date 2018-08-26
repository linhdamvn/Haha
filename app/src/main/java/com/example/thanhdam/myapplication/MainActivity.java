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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView ivRecord;
    ImageView ivBack;
    private int GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivRecord = findViewById(R.id.iv_record);
      //  Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/hahalinh/1535271035657.jpg");
       // ivRecord.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 600, 600, false));
        Picasso.get().load(R.drawable.round).into(ivRecord);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // Nhan anh - chon anh tu thu vien
        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // Nhay tu activity 1 sang activity co san
                startActivityForResult( gallery, GALLERY ); // Goi ham,lay anh
            }
        });
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
                    String path = ImageManager.saveImage(bitmap,this);
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