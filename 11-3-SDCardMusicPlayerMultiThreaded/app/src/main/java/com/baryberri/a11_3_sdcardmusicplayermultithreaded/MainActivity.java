package com.baryberri.a11_3_sdcardmusicplayermultithreaded;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button startButton;
    Button stopButton;
    TextView playingText;
    ProgressBar progressBar;

    ArrayList<String> musicNameList = new ArrayList<>();
    String musicPath = Environment.getExternalStorageDirectory().getPath() + "/Music/";
    String selectedMusicName = "";

    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.musicListView);
        startButton = (Button)findViewById(R.id.startButton);
        stopButton = (Button)findViewById(R.id.stopButton);
        playingText = (TextView)findViewById(R.id.playingMusic);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // getting music lists and adding them into musicList.
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        File[] musicFileLists = new File(musicPath).listFiles();
        for (File file : musicFileLists) {
            String fileName = file.getName();
            String extensionName = fileName.substring(fileName.length() - 3);
            if (extensionName.equals("mp3")) {
                musicNameList.add(fileName);
            }
        }

        // making an adapter and register.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                musicNameList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);
        listView.setItemChecked(0, true);
        selectedMusicName = musicNameList.get(0);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMusicName = musicNameList.get(position);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource(musicPath + selectedMusicName);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    startButton.setClickable(false);
                    stopButton.setClickable(true);
                    playingText.setText(selectedMusicName);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread() {
                        @Override
                        public void run() {
                            if (mediaPlayer == null) {
                                return;
                            }
                            progressBar.setMax(mediaPlayer.getDuration());
                            while (mediaPlayer.isPlaying()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(mediaPlayer.getCurrentPosition());
                                    }
                                });
                                SystemClock.sleep(200);
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                startButton.setClickable(true);
                stopButton.setClickable(false);
                playingText.setText("");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
