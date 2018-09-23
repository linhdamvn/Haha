package com.example.thanhdam.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.util.Log;

import java.net.URI;
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

    public List<StoryModel> getListStory(){
        sqLiteDatabase = assetHelper.getReadableDatabase();

        List<StoryModel> storyModels = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_DATA,null); // tao con tro
        cursor.moveToFirst(); //cho con tro len dau

        while (!cursor.isAfterLast())
        {
            //read data
            String name = cursor.getString(0);
            String url = cursor.getString(1);
            String content = cursor.getString(2);
            String audio = cursor.getString(3);

            Log.d(TAG, "getListImage: "+ url);
            StoryModel storyModel = new StoryModel(name, url,content, audio);
            storyModels.add(storyModel);

            //dua con tro sang hang tiep theo
            cursor.moveToNext();
        }

        return  storyModels;
    }

    public long getNumOfData() {
        sqLiteDatabase = assetHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_DATA);
        return count;
    }

    //truyen data tu mainactivity
    public void saveData(StoryModel storyModel) {
        sqLiteDatabase = assetHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();//de viet data vao bang
        contentValues.put("name", storyModel.name);
        contentValues.put("image", storyModel.image);
        contentValues.put("content", storyModel.content);
        contentValues.put("audio", storyModel.audio);
        sqLiteDatabase.insert(TABLE_DATA, null, contentValues);
    }

    public void updateData(StoryModel storyModel, int id)
    {
        sqLiteDatabase = assetHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", storyModel.name);
        contentValues.put("image", storyModel.image);
        contentValues.put("content", storyModel.content);
        contentValues.put("audio", storyModel.audio);
        Log.d(TAG, "updateData: " + storyModel.name);
        sqLiteDatabase.update(TABLE_DATA, contentValues, "_id="+id, null);
    }
}
