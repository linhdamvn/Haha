package com.example.thanhdam.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String TAG = "DataManager";
    public static DataManager dataManager;
    private static final String TABLE_DATA = "data";

    private SQLiteDatabase sqLiteDatabase;
    private AssetHelper assetHelper;

    public static DataManager getInstances (Context context)
    {
        if (dataManager == null) {
            dataManager = new DataManager(context);
        }
        return dataManager;
    }
    public DataManager (Context context){
        assetHelper = new AssetHelper(context);
    }

    public List<ImageModel> getListImage(){
        sqLiteDatabase = assetHelper.getReadableDatabase();

        List<ImageModel> imageModels = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_DATA,null); // tao con tro
        cursor.moveToFirst(); //cho con tro len dau

        while (!cursor.isAfterLast())
        {
            //read data
            String name = cursor.getString(0);
            String url = cursor.getString(1);

            ImageModel imageModel = new ImageModel(url, name);
            imageModels.add(imageModel);

            //dua con tro sang hang tiep theo
            cursor.moveToNext();
        }


        return  imageModels;
    }
}
