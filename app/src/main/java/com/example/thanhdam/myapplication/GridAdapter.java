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
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item,viewGroup, false);

        final StoryModel storyModel = storyModels.get(i);

        ImageView imageView = view.findViewById(R.id.iv_image);
        TextView textView = view.findViewById(R.id.tv_textview);

        textView.setText(storyModel.name);

        ImageView changebutton = view.findViewById(R.id.iv_changebutton);
        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("change", "true");
                intent.putExtra("id", 1);
                intent.putExtra("tittle", storyModel.name);
                intent.putExtra("content", storyModel.content);
                intent.putExtra("image", storyModel.image);
                intent.putExtra("audio", storyModel.audio);
                context.startActivity(intent);
            }
        });

        if (storyModel.image == null) imageView.setBackgroundResource(R.drawable.round);
        else {
            Bitmap bitmap = BitmapFactory.decodeFile(storyModel.image);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, false));
        }

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
