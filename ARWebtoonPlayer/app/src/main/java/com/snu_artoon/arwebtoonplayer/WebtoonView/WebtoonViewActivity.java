// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

package com.snu_artoon.arwebtoonplayer.WebtoonView;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.snu_artoon.arwebtoonplayer.DBManager.DBManager;
import com.snu_artoon.arwebtoonplayer.R;

import java.util.ArrayList;

public class WebtoonViewActivity extends AppCompatActivity {
    RecyclerView chapterListRecyclerView;
    DBManager dbManager;

    // ArrayLists that are used for saving RecyclerView data
    ArrayList<String> webtoonImages = new ArrayList<>();

    // Intent from the previous WebtoonListActivity view, and fetched information.
    Intent inIntent;
    String webtoonHashID;
    String chapterHashID;
    String chapterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chapter_list);

        // get the intent information.
        inIntent = getIntent();
        webtoonHashID = inIntent.getStringExtra("webtoonHashID");
        chapterHashID = inIntent.getStringExtra("chapterHashID");
        chapterTitle = inIntent.getStringExtra("chapterTitle");

        setTitle(chapterTitle);

        // Link the RecyclerView, and generate the dbManager.
        chapterListRecyclerView = (RecyclerView)findViewById(R.id.webtoon_view_recycler_view);
        dbManager = new DBManager(this);

        // no need for asking the permission as WebtoonListActivity checked it.
        initializeLists();

        // setting the recyclerView.
        WebtoonViewRecyclerAdapter adapter = new WebtoonViewRecyclerAdapter(this, webtoonImages);
        chapterListRecyclerView.setAdapter(adapter);
        chapterListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Get and save webtoonImages information from the DB to the corresponding lists.
     */
    void initializeLists() {
        String tableName = "ChapterListDB_" + webtoonHashID + "_" + chapterHashID;
        Cursor cursor = dbManager.get("*", tableName);
        while (cursor.moveToNext()) {
            webtoonImages.add(cursor.getString(1));
        }
        cursor.close();
        dbManager.close();
    }
}

