// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

package com.snu_artoon.arwebtoonplayer.WebtoonList;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.snu_artoon.arwebtoonplayer.DBManager.DBManager;
import com.snu_artoon.arwebtoonplayer.R;

import org.tensorflow.demo.TensorFlowYoloDetector;

import java.util.ArrayList;

public class WebtoonListActivity extends AppCompatActivity {
    RecyclerView webtoonListRecyclerView;
    DBManager dbManager;

    // ArrayLists that are used for saving RecyclerView data
    ArrayList<String> webtoonTitles = new ArrayList<>();
    ArrayList<String> webtoonAuthors = new ArrayList<>();
    ArrayList<String> webtoonThumbnailImages = new ArrayList<>();

    // a private constant that is used for asking permission.
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webtoon_list);
        setTitle(R.string.webtoon_list_activity_title);

        // Link the RecyclerView, and generate the dbManager.
        webtoonListRecyclerView = (RecyclerView)findViewById(R.id.webtoon_list_recycler_view);
        dbManager = new DBManager(this);

        // set each arrayList by calling initializeLists(), if permission is granted.
        // if not, ask the permission, and call initializeLists() at the handler.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            initializeLists();
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_CODE);
        }

        // setting the recyclerView.
        WebtoonListRecyclerAdapter adapter = new WebtoonListRecyclerAdapter(
                this,
                webtoonTitles,
                webtoonAuthors,
                webtoonThumbnailImages);
        webtoonListRecyclerView.setAdapter(adapter);
        webtoonListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeLists();
                    return;
                }
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.model_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.face:
                TensorFlowYoloDetector.selectedModel = 0;
                return true;

            case R.id.hand:
                TensorFlowYoloDetector.selectedModel = 1;
                return true;

            default:
                return false;
        }
    }

    /**
     * Get and save WebtoonList information from the DB to the corresponding lists.
     */
    void initializeLists() {
        Cursor cursor = dbManager.get("*", "WebtoonListDB");
        while (cursor.moveToNext()) {
            webtoonTitles.add(cursor.getString(0));
            webtoonAuthors.add(cursor.getString(1));
            webtoonThumbnailImages.add(cursor.getString(2));
        }
        cursor.close();
        dbManager.close();
    }
}

