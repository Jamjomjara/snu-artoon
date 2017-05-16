// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

package com.snu_artoon.arwebtoonplayer.WebtoonList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snu_artoon.arwebtoonplayer.ImageLoader.LocalImageLoader;
import com.snu_artoon.arwebtoonplayer.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class WebtoonListRecyclerAdapter
        extends RecyclerView.Adapter<WebtoonListRecyclerAdapter.ViewHolder> {
    private ArrayList<String> webtoonTitles;
    private ArrayList<String> webtoonAuthors;
    private ArrayList<Float> webtoonScores;
    private ArrayList<String> webtoonThumbnailImages;

    // Constructor
    public WebtoonListRecyclerAdapter(ArrayList<String> webtoonTitles,
                                      ArrayList<String> webtoonAuthors,
                                      ArrayList<Float> webtoonScores,
                                      ArrayList<String> webtoonThumbnailImages) {
        this.webtoonTitles = webtoonTitles;
        this.webtoonAuthors = webtoonAuthors;
        this.webtoonScores = webtoonScores;
        this.webtoonThumbnailImages = webtoonThumbnailImages;
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView webtoonThumbnailImage;
        public TextView webtoonTitleText;
        public TextView webtoonAuthorText;
        public RatingBar webtoonRatingBar;
        public TextView webtoonRatingText;

        public ViewHolder(View itemView) {
            super(itemView);
            webtoonThumbnailImage = (ImageView)itemView.findViewById(R.id.webtoon_thumbnail_image);
            webtoonTitleText = (TextView)itemView.findViewById(R.id.webtoon_title_text);
            webtoonAuthorText = (TextView)itemView.findViewById(R.id.webtoon_author_text);
            webtoonRatingBar = (RatingBar)itemView.findViewById(R.id.webtoon_rating_bar);
            webtoonRatingText = (TextView)itemView.findViewById(R.id.webtoon_rating_text);
        }
    }

    // RecyclerView.Adapter
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_webtoon_list_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocalImageLoader.load(holder.webtoonThumbnailImage, webtoonThumbnailImages.get(position));
        holder.webtoonTitleText.setText(webtoonTitles.get(position));
        holder.webtoonAuthorText.setText(webtoonAuthors.get(position));
        holder.webtoonRatingBar.setRating(webtoonScores.get(position));
        holder.webtoonRatingText.setText(String.format(Locale.US, "%.2f", webtoonScores.get(position)));
    }

    @Override
    public int getItemCount() {
        return webtoonTitles.size();
    }
}
