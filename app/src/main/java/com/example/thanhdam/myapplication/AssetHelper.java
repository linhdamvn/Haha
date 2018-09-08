package com.example.thanhdam.myapplication;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

//Quan ly data

public class AssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "linhhaha.db";  //ten tu java->asset
    private static final  int DATABASE_VERSION = 1; //trinh do it thi de so 1

    public AssetHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
}
