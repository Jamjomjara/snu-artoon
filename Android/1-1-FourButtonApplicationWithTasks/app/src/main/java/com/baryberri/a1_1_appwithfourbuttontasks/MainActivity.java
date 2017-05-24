package com.baryberri.a1_1_appwithfourbuttontasks;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // objects
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link each button outlet into its xml objects
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        // set the button colors, using Color class.
        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.GREEN);
        button3.setBackgroundColor(Color.YELLOW);
        button4.setBackgroundColor(Color.RED);

        // set onClick listeners so they can do certain jobs when clicked
        // the new View.onClickListener() { } is a structure
        // for creating anonymous inner class.
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // from now, I cannot understand what this code does.
                // But in my personal opinion, I think Intent is an object that represents
                // a certain job to do, and to make it I should give the Intent's type and the job to do.
                // And to start that Intent, I think we should call the startActiviy function.
                // I think Uri.parse can parse some kind of jobs, described in some kind of strings.

                // This is an internet page presentation intent.
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.comic.naver.com/index.nhn"));
                startActivity(mIntent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is a calling intent.
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/028807287"));
                startActivity(mIntent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opening contents intent.
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(mIntent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this finishes the current job(maybe application?).
                // from now on, what I know is when the button 4 is clicked, the application is finished.
                finish();
            }
        });
    }
}
