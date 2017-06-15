// MIT License
// Copyright (c) 2017 SNU_ARToon Team

package com.snu_artoon.arwebtoonplayer.WebtoonList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.snu_artoon.arwebtoonplayer.ChapterList.ChapterListActivity;
import com.snu_artoon.arwebtoonplayer.HashManager.HashManager;
import com.snu_artoon.arwebtoonplayer.ImageLoader.LocalImageLoader;
import com.snu_artoon.arwebtoonplayer.R;

import java.util.ArrayList;

public class WebtoonListRecyclerAdapter
        extends RecyclerView.Adapter<WebtoonListRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> webtoonTitles;
    private ArrayList<String> webtoonAuthors;
    private ArrayList<String> webtoonThumbnailImages;

    // Constructor
    public WebtoonListRecyclerAdapter(Context context,
                                      ArrayList<String> webtoonTitles,
                                      ArrayList<String> webtoonAuthors,
                                      ArrayList<String> webtoonThumbnailImages) {
        this.context = context;
        this.webtoonTitles = webtoonTitles;
        this.webtoonAuthors = webtoonAuthors;
        this.webtoonThumbnailImages = webtoonThumbnailImages;
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView webtoonThumbnailImage;
        public TextView webtoonTitleText;
        public TextView webtoonAuthorText;

        public ViewHolder(View itemView) {
            super(itemView);
            webtoonThumbnailImage = (ImageView)itemView.findViewById(R.id.webtoon_thumbnail_image);
            webtoonTitleText = (TextView)itemView.findViewById(R.id.webtoon_title_text);
            webtoonAuthorText = (TextView)itemView.findViewById(R.id.webtoon_author_text);
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
        final String webtoonTitle = webtoonTitles.get(position);
        final String webtoonAuthor = webtoonAuthors.get(position);
        LocalImageLoader.load(holder.webtoonThumbnailImage, webtoonThumbnailImages.get(position));
        holder.webtoonTitleText.setText(webtoonTitle);
        holder.webtoonAuthorText.setText(webtoonAuthor);

        // OnClickListener to call WebtoonViewActivity when clicked.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webtoonKey = webtoonTitle + "_" + webtoonAuthor;
                String webtoonHashID = HashManager.md5(webtoonKey);
                System.out.println("Webtoon ID : " + webtoonHashID);

                // Make an intent and call WebtoonViewActivity.
                Intent intent = new Intent(context, ChapterListActivity.class);
                intent.putExtra("webtoonHashID", webtoonHashID);
                intent.putExtra("webtoonTitle", webtoonTitle);
                intent.putExtra("webtoonAuthor", webtoonAuthor);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return webtoonTitles.size();
    }
}
