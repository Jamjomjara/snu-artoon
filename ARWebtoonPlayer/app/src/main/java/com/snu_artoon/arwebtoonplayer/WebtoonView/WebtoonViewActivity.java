// MIT License
// Copyright (c) 2017 SNU_ARToon Team

package com.snu_artoon.arwebtoonplayer.WebtoonView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.snu_artoon.arwebtoonplayer.DBManager.DBManager;
import com.snu_artoon.arwebtoonplayer.R;
import org.tensorflow.demo.ARToonActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
//        webtoonViewRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 40) {
//                    isScrolledDown = true;
//                } else if (dy < -5) {
//                    isScrolledDown = false;
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (isScrolledDown) {
//                    getSupportActionBar().hide();
//                } else {
//                    getSupportActionBar().show();
//                }
//            }
//        });

        webtoonViewRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isAtBottom(recyclerView)) {
                    Intent intent = new Intent(getApplicationContext(), ARToonActivity.class);
                    getApplicationContext().startActivity(intent);
                }
            }
        });
    }

    public static boolean isAtBottom(RecyclerView recyclerView) {
        return !ViewCompat.canScrollVertically(recyclerView, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.webtoon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lock_rotate:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;

            case R.id.unlock_rotate:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                return true;

            case R.id.capture:
                captureScreen();
                return true;

            default:
                return false;
        }
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

    private void captureScreen() {
        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        try {
            FileOutputStream fos = new FileOutputStream(new File(Environment
                    .getExternalStorageDirectory().toString(), "SCREEN"
                    + System.currentTimeMillis() + ".png"));
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

