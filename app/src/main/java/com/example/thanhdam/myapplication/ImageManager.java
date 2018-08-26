package com.example.thanhdam.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ImageManager {
    private static final String IMAGE_DIRECTORY = "/hahalinh";
    private static File tempFile;

    // tu bitmap nhan vao tra ve 1 duong dan

    public static String saveImage(Bitmap bitmap, Context context){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory()+ IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) { // neu ko co file
            wallpaperDirectory.mkdir(); // tao file day
        }
        try{
            File f = new File( wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg"); // Tao file anh co thoi gian nay
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f); // fo viet vao file f
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getParent()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        } catch (IOException e1){
            e1.printStackTrace();
        }
        return "";
    }
}
