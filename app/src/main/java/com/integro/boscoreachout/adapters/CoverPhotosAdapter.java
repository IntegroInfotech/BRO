package com.integro.boscoreachout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.integro.boscoreachout.R;
import com.integro.boscoreachout.model.CoverPhotos;

import java.util.ArrayList;

public class CoverPhotosAdapter extends PagerAdapter {
    Context context;
    ArrayList<CoverPhotos> coverPhotosArrayList;

    public CoverPhotosAdapter(Context context, ArrayList<CoverPhotos> coverPhotosArrayList) {
        this.context = context;
        this.coverPhotosArrayList = coverPhotosArrayList;
    }

    @Override
    public int getCount() {
        return coverPhotosArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView ivNewsImage;
        View view = LayoutInflater.from(context).inflate(R.layout.card_news_image, container, false);
        ivNewsImage = view.findViewById(R.id.iv_NewsImage);
        Glide.with(context)
                .load(coverPhotosArrayList.get(position).getImage())
                .into(ivNewsImage);

        ((ViewPager) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }
}
