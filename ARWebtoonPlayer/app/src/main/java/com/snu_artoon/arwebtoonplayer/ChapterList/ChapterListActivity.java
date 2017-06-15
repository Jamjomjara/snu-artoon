// MIT License
// Copyright (c) 2017 SNU_ARToon Team

package com.snu_artoon.arwebtoonplayer.ChapterList;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.snu_artoon.arwebtoonplayer.DBManager.DBManager;
import com.snu_artoon.arwebtoonplayer.R;

import java.util.ArrayList;

public class ChapterListActivity extends AppCompatActivity {
    RecyclerView chapterListRecyclerView;
    DBManager dbManager;

    // ArrayLists that are used for saving RecyclerView data
    ArrayList<String> chapterNumbers = new ArrayList<>();
    ArrayList<String> chapterTitles = new ArrayList<>();
    ArrayList<String> chapterUploadedDates = new ArrayList<>();
    ArrayList<String> chapterThumbnailImages = new ArrayList<>();
    ArrayList<Float> chapterRatings = new ArrayList<>();

    // Intent from the previous WebtoonListActivity view, and fetched information.
    Intent inIntent;
    String webtoonHashID;
    String webtoonTitle;
    String webtoonAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chapter_list);

        // get the intent information.
        inIntent = getIntent();
        webtoonHashID = inIntent.getStringExtra("webtoonHashID");
        webtoonTitle = inIntent.getStringExtra("webtoonTitle");
        webtoonAuthor = inIntent.getStringExtra("webtoonAuthor");

        setTitle(webtoonTitle + " - " + webtoonAuthor);

        // Link the RecyclerView, and generate the dbManager.
        chapterListRecyclerView = (RecyclerView)findViewById(R.id.chapter_list_recycler_view);
        dbManager = new DBManager(this);

        // no need for asking the permission as WebtoonListActivity checked it.
        initializeLists();

        // setting the recyclerView.
        ChapterListRecyclerAdapter adapter = new ChapterListRecyclerAdapter(
                this,
                chapterNumbers,
                chapterTitles,
                chapterUploadedDates,
                chapterRatings,
                chapterThumbnailImages,
                webtoonHashID);
        chapterListRecyclerView.setAdapter(adapter);
        chapterListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Get and save WebtoonList information from the DB to the corresponding lists.
     */
    void initializeLists() {
        String tableName = "ChapterListDB_" + webtoonHashID;
        Cursor cursor = dbManager.get("*", tableName);
        while (cursor.moveToNext()) {
            chapterNumbers.add(cursor.getString(0));
            chapterTitles.add(cursor.getString(1));
            chapterUploadedDates.add(cursor.getString(2));
            int numLike = cursor.getInt(3);
            int numDislike = cursor.getInt(4);
            // star's maximum score is 5, and the maximum score for textView is 10.
            // so when printing the score at textView, it should be multiplied by 2.
            chapterRatings.add(((float)numLike / (numLike + numDislike)) * 5);
            chapterThumbnailImages.add(cursor.getString(5));
        }
        cursor.close();
        dbManager.close();
    }
}

