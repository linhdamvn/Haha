package com.example.thanhdam.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends BaseAdapter{
    Context context;

    List<ImageModel> imageModels;

    public GridAdapter(Context context, List<ImageModel> imageModels) {
        this.context = context;
        this.imageModels = imageModels;
    }

    @Override
    public int getCount() {
        return imageModels.size();
    }

    @Override
    public Object getItem(int i) {
        return imageModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item,viewGroup, false);

        ImageModel imageModel = imageModels.get(i);

        ImageView imageView = view.findViewById(R.id.iv_image);
        TextView textView = view.findViewById(R.id.tv_textview);

        textView.setText(imageModel.text);
        Bitmap bitmap = BitmapFactory.decodeFile(imageModel.URL);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,150, 150, false));
        return view;
    }
}
