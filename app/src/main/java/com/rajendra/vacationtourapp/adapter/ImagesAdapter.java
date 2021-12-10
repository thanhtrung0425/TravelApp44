package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.Images;
import com.rajendra.vacationtourapp.model.Photos;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends PagerAdapter {

    private Context mContext;
    private List<Photos> mListImg;

    public ImagesAdapter(Context mContext, List<Photos> mListImg) {
        this.mContext = mContext;
        this.mListImg = mListImg;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_images, container, false);
        ImageView imagePT = view.findViewById(R.id.imgPhoto);

        Photos photos = mListImg.get(position);
        if (photos != null){
            Picasso.get().load(mListImg.get(position).getUrl()).into(imagePT);
        }
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (mListImg != null)
            return mListImg.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
