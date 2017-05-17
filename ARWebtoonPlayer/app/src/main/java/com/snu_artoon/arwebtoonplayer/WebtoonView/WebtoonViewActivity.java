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
    RecyclerView webtoonViewRecyclerView;
    DBManager dbManager;

    // ArrayLists that are used for saving RecyclerView data
    ArrayList<String> webtoonImages = new ArrayList<>();

    // Intent from the previous WebtoonListActivity view, and fetched information.
    Intent inIntent;
    String webtoonHashID;
    String chapterHashID;
    String chapterTitle;

    // used for hiding supportActionBar.
    static boolean isScrolledDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webtoon_view);

        // get the intent information.
        inIntent = getIntent();
        webtoonHashID = inIntent.getStringExtra("webtoonHashID");
        chapterHashID = inIntent.getStringExtra("chapterHashID");
        chapterTitle = inIntent.getStringExtra("chapterTitle");

        setTitle(chapterTitle);

        // Link the RecyclerView, and generate the dbManager.
        webtoonViewRecyclerView = (RecyclerView)findViewById(R.id.webtoon_view_recycler_view);
        dbManager = new DBManager(this);

        // no need for asking the permission as WebtoonListActivity checked it.
        initializeLists();

        // setting the recyclerView.
        WebtoonViewRecyclerAdapter adapter = new WebtoonViewRecyclerAdapter(this, webtoonImages);
        webtoonViewRecyclerView.setAdapter(adapter);
        webtoonViewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Make action bar hidden when scrolled.
        webtoonViewRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 40) {
                    isScrolledDown = true;
                } else if (dy < -5) {
                    isScrolledDown = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isScrolledDown) {
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }
            }
        });
    }

    /**
     * Get and save webtoonImages information from the DB to the corresponding lists.
     */
    void initializeLists() {
        String tableName = "ImageListDB_" + webtoonHashID + "_" + chapterHashID;
        Cursor cursor = dbManager.get("*", tableName);
        while (cursor.moveToNext()) {
            webtoonImages.add(cursor.getString(0));
        }
        cursor.close();
        dbManager.close();
    }
}

