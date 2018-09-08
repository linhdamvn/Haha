package com.example.thanhdam.myapplication;

// bieu dien main page
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class GridAdapter extends BaseAdapter{
    Context context;

    List<StoryModel> storyModels;

    public GridAdapter(Context context, List<StoryModel> storyModels) {
        this.context = context;
        this.storyModels = storyModels;
    }

    @Override
    public int getCount() {
        return storyModels.size();
    }

    @Override
    public Object getItem(int i) {
        return storyModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item,viewGroup, false);

        final StoryModel storyModel = storyModels.get(i);

        ImageView imageView = view.findViewById(R.id.iv_image);
        TextView textView = view.findViewById(R.id.tv_textview);

        textView.setText(storyModel.name);

        Bitmap bitmap = BitmapFactory.decodeFile(storyModel.image);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,150, 150, false));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadActivity.class); // Intent = chuyen activity nay sang khac, o main sang man hinh doc= Readactivity
                intent.putExtra("tittle", storyModel.name); // gan key tittle 1 gia tri name
                intent.putExtra("image", storyModel.image);
                intent.putExtra("story", storyModel.content);
                intent.putExtra("audio", storyModel.audio);
                Log.d(TAG, "onClick: " + storyModel.audio);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
