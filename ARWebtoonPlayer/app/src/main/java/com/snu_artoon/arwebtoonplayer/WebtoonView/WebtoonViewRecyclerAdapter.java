// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

package com.snu_artoon.arwebtoonplayer.WebtoonView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snu_artoon.arwebtoonplayer.ImageLoader.LocalImageLoader;
import com.snu_artoon.arwebtoonplayer.R;

import java.util.ArrayList;

public class WebtoonViewRecyclerAdapter
        extends RecyclerView.Adapter<WebtoonViewRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> webtoonImages;

    // Constructor
    public WebtoonViewRecyclerAdapter(Context context, ArrayList<String> webtoonImages) {
        this.context = context;
        this.webtoonImages = webtoonImages;
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView webtoonImage;

        public ViewHolder(View itemView) {
            super(itemView);
            webtoonImage = (ImageView)itemView.findViewById(R.id.webtoon_image);
        }
    }

    // RecyclerView.Adapter
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_webtoon_view_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageName = webtoonImages.get(position);
        LocalImageLoader.load(holder.webtoonImage, imageName);

        // as the imageView should not crop the image, make the boundary fit to the image.
        holder.webtoonImage.setAdjustViewBounds(true);
    }

    @Override
    public int getItemCount() {
        return webtoonImages.size();
    }
}