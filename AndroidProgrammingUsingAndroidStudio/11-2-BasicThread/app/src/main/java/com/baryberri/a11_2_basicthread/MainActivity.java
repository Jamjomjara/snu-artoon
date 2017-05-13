package com.baryberri.a11_2_basicthread;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar1;
    SeekBar seekBar2;
    Button startButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        startButton = (Button)findViewById(R.id.startButton);
        resetButton = (Button)findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar1.setProgress(0);
                seekBar2.setProgress(0);
            }
        });

        // This code below doesn't work, as the for loop runs in the same thread.
        // Therefore, the UI is updated when the for loop finishes.
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < 100; i++) {
//                    seekBar1.setProgress(seekBar1.getProgress() + 1);
//                    seekBar2.setProgress(seekBar2.getProgress() + 2);
//                    SystemClock.sleep(10);
//                }
//            }
//        });


        // This code below works, as the for loop and UI thread
        // run concurrently on the different threads.
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            seekBar1.setProgress(seekBar1.getProgress() + 1);
                            seekBar2.setProgress(seekBar2.getProgress() + 2);
                            SystemClock.sleep(10);
                        }
                    }
                }.start();
            }
        });

    }
}
