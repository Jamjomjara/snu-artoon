// MIT License
// Copyright (c) 2017 SNU_ARToon Team

package com.snu_artoon.arwebtoonplayer.ChapterList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.snu_artoon.arwebtoonplayer.HashManager.HashManager;
import com.snu_artoon.arwebtoonplayer.ImageLoader.LocalImageLoader;
import com.snu_artoon.arwebtoonplayer.R;
import com.snu_artoon.arwebtoonplayer.WebtoonView.WebtoonViewActivity;

import java.util.ArrayList;
import java.util.Locale;

public class ChapterListRecyclerAdapter
        extends RecyclerView.Adapter<ChapterListRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> chapterNumbers;
    private ArrayList<String> chapterTitles;
    private ArrayList<String> chapterUploadedDate;
    private ArrayList<Float> chapterRatings;
    private ArrayList<String> chapterThumbnailImages;
    private String webtoonHashID;

    // Constructor
    public ChapterListRecyclerAdapter(Context context,
                                      ArrayList<String> chapterNumbers,
                                      ArrayList<String> chapterTitles,
                                      ArrayList<String> chapterUploadedDate,
                                      ArrayList<Float> chapterRatings,
                                      ArrayList<String> chapterThumbnailImages,
                                      String webtoonHashID) {
        this.context = context;
        this.chapterNumbers = chapterNumbers;
        this.chapterTitles = chapterTitles;
        this.chapterUploadedDate = chapterUploadedDate;
        this.chapterRatings = chapterRatings;
        this.chapterThumbnailImages = chapterThumbnailImages;
        this.webtoonHashID = webtoonHashID;
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView chapterThumbnailImage;
        public TextView chapterTitleText;
        public TextView chapterUploadedDateText;
        public RatingBar chapterRatingBar;
        public TextView chapterRatingText;

        public ViewHolder(View itemView) {
            super(itemView);
            chapterThumbnailImage = (ImageView)itemView.findViewById(R.id.chapter_thumbnail_image);
            chapterTitleText = (TextView)itemView.findViewById(R.id.chapter_title_text);
            chapterUploadedDateText = (TextView)itemView.findViewById(R.id.chapter_uploaded_date_text);
            chapterRatingBar = (RatingBar)itemView.findViewById(R.id.chapter_rating_bar);
            chapterRatingText = (TextView)itemView.findViewById(R.id.chapter_rating_text);
        }
    }

    // RecyclerView.Adapter
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_chapter_list_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String chapterTitle = chapterNumbers.get(position) + ". " + chapterTitles.get(position);
        LocalImageLoader.load(holder.chapterThumbnailImage, chapterThumbnailImages.get(position));
        holder.chapterTitleText.setText(chapterTitle);
        holder.chapterUploadedDateText.setText(chapterUploadedDate.get(position));
        holder.chapterRatingBar.setRating(chapterRatings.get(position));
        holder.chapterRatingText.setText(String.format(Locale.US,
                "%.2f", chapterRatings.get(position) * 2)); // because textScore's maximum is 10.

        // OnClickListener to call WebtoonViewActivity when clicked.
        final String chapterTitleKey = chapterNumbers.get(position) + "_" + chapterTitles.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chapterHashID = HashManager.md5(chapterTitleKey);
                System.out.println("chapter ID : " + chapterHashID);

                // Make an intent and call WebtoonViewActivity.
                Intent intent = new Intent(context, WebtoonViewActivity.class);
                intent.putExtra("webtoonHashID", webtoonHashID);
                intent.putExtra("chapterHashID", chapterHashID);
                intent.putExtra("chapterTitle", chapterTitle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterTitles.size();
    }
}
