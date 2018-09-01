package com.example.thanhdam.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 1;
    GridView gridView;
    GridAdapter gridAdapter;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION
            );
        }

        gridView = findViewById(R.id.gv_demo);
        fabAdd = findViewById(R.id.fab_add);

        List<StoryModel> storyModels = DataManager.getInstances(this).getListStory();

        gridAdapter = new GridAdapter(this, storyModels);
        gridView.setAdapter(gridAdapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class); //nhay tu activity 2 sang 1
                Main2Activity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning!")
                        .setMessage("Without permission you can not use this app." + "Do you want to grant permission?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(
                                        Main2Activity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        REQUEST_PERMISSION
                                );
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Main2Activity.this.finish();
                            }
                        })
                        .show();
            }
        }
    }

    @Override // thuc hien hanh dong moi khi quay tro lai activity : nho noi dung truoo
    protected void onStart() {
        super.onStart();
        // lay list data
        List<StoryModel> storyModels = DataManager.getInstances(this).getListStory();
        // cho data len grid view
        gridAdapter = new GridAdapter(this, storyModels);
        gridView.setAdapter(gridAdapter); // hien thi
    }
}
